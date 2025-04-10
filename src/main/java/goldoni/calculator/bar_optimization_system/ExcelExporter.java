package goldoni.calculator.bar_optimization_system;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExcelExporter {
    public static void exportToExcel (Stage stage, Map<Integer, OptimizationResult> results) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
        fileChooser.setInitialFileName("Sobras e aproveitamentos");
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            try (Workbook workbook = new XSSFWorkbook()) {

                CreateSheetResults(workbook, results);
                CreateSheetScraps(workbook, results);

                try (FileOutputStream fileOut = new FileOutputStream(file)) {
                    workbook.write(fileOut);
                }
                System.out.println("Exportação para Excel concluída!");

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Erro ao exportar para Excel.");
            }
        }
    }

    private static Sheet CreateSheetResults(Workbook workbook, Map<Integer, OptimizationResult> results) {
        Sheet sheet = workbook.createSheet("Resultados");
        int rowIndex = 0;
        double WEIGHT_PER_KG = 1.61;

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        Row headerRow = sheet.createRow(rowIndex++);

        Cell cell0 = headerRow.createCell(0);
        cell0.setCellValue("Diametro Ø (mm)");
        cell0.setCellStyle(headerStyle);

        Cell cell1 = headerRow.createCell(1);
        cell1.setCellValue("Cortes por barra");
        cell1.setCellStyle(headerStyle);

        Cell cell2 = headerRow.createCell(2);
        cell2.setCellValue("Soma dos cortes");
        cell2.setCellStyle(headerStyle);

        Cell cell3 = headerRow.createCell(3);
        cell3.setCellValue("Sobras");
        cell3.setCellStyle(headerStyle);

        for (Map.Entry<Integer, OptimizationResult> entry : results.entrySet()) {
            int diameter = entry.getKey();
            OptimizationResult result = entry.getValue();

            for (List<Integer> bar : result.getBars()) {
                Integer totalLength = bar.stream().reduce(0, Integer::sum);

                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(diameter);
                row.createCell(1).setCellValue(bar.toString());
                row.createCell(2).setCellValue(totalLength);
                row.createCell(3).setCellValue(1200 - totalLength);
            }

            Row rowTotal1 = sheet.createRow(rowIndex++);
            rowTotal1.createCell(0).setCellValue("Total de barras:");
            rowTotal1.createCell(1).setCellValue(result.getBars().size());

            Row rowTotal2 = sheet.createRow(rowIndex++);
            rowTotal2.createCell(0).setCellValue("Peso total:");
            rowTotal2.createCell(1).setCellValue(result.getBars().size() * 12 * WEIGHT_PER_KG);

            Row rowTotal3 = sheet.createRow(rowIndex++);
            rowTotal3.createCell(0).setCellValue("Peso do aproveitamento:");
            rowTotal3.createCell(1).setCellValue(0);

            Row rowTotal4 = sheet.createRow(rowIndex++);
            rowTotal4.createCell(0).setCellValue("Peso das sobras:");
            rowTotal4.createCell(1).setCellValue(0);
        }

        return sheet;
    }

    private static Sheet CreateSheetScraps(Workbook workbook, Map<Integer, OptimizationResult> results) {
        Sheet sheet = workbook.createSheet("Sobras agrupadas");
        int rowIndex = 0;

        // Header
        Row headerRow = sheet.createRow(rowIndex++);
        headerRow.createCell(0).setCellValue("Diametro Ø (mm)");
        headerRow.createCell(1).setCellValue("Quantidade");
        headerRow.createCell(2).setCellValue("Sobra");

        for (Map.Entry<Integer, OptimizationResult> entry : results.entrySet()) {
            int diameter = entry.getKey();
            List<Integer> scraps = entry.getValue().getScraps();
            Map<Integer, Long> groupedScraps = scraps.stream().
                    collect(Collectors.groupingBy(s -> s, Collectors.counting()));

            for (Map.Entry<Integer, Long> scrap : groupedScraps.entrySet()) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(diameter);
                row.createCell(1).setCellValue(scrap.getValue());
                row.createCell(2).setCellValue(scrap.getKey());
            }
        }
        return sheet;
    }

    private static Integer getTotalMeters(Map<Integer, OptimizationResult> results) {
        return null;
    }
}
