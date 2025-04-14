package goldoni.calculator.bar_optimization_system.optimizers;

import goldoni.calculator.bar_optimization_system.model.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BarOptimizer {
    private static final int BAR_LENGTH = 1200; // 12 meters in centimeters
    private static final int MIN_SCRAP_LENGTH = 100; // 1 meter in centimeters

    public static Map<Integer, OptimizationResult> optimizeUsageByDiameter(List<Element> elements) {
        Map<Integer, List<Integer>> cutsByDiameter = new HashMap<>();
        for (Element element : elements) {
            // store the gauge in the map only when the specified gauge does not exist or its value is null
            cutsByDiameter.computeIfAbsent(element.getDiameter(), k -> new ArrayList<>());
            for (int i = 0; i < element.getQuantity(); i++) {
                cutsByDiameter.get(element.getDiameter()).add(element.getTotalLength());
            }
        }

        Map<Integer, OptimizationResult> results = new HashMap<>();

        // get the optimize usage each diameter and put in result
        for (Map.Entry<Integer, List<Integer>> entry : cutsByDiameter.entrySet()) {
            results.put(entry.getKey(), optimizeUsage(entry.getValue()));
        }

        return results;
    }

    public static OptimizationResult optimizeUsage(List<Integer> cuts) {
        cuts.sort((a, b) -> Integer.compare(b, a)); // from largest to smallest

        List<List<Integer>> bars = new ArrayList<>();

        for (int cut : cuts) {
            int bestIndex = -1;
            int minimalLeftover = Integer.MAX_VALUE;

            // Go through all the bars already open to find the best fit
            for (int i = 0; i < bars.size(); i++) {
                List<Integer> bar = bars.get(i);
                int usedLenght = bar.stream().mapToInt(Integer::intValue).sum();
                if (usedLenght + cut <= BAR_LENGTH) {
                    int leftover = BAR_LENGTH - (usedLenght + cut);
                    // If this fitting leaves a smaller leftover, it's the best yet
                    if (leftover < minimalLeftover) {
                        minimalLeftover = leftover;
                        bestIndex = i;
                    }
                }
            }

            // If we find a bar with a socket, add the cut to it
            if(bestIndex != -1) {
                bars.get(bestIndex).add(cut);
            } else {
                List<Integer> newBar = new ArrayList<>();
                newBar.add(cut);
                bars.add(newBar);
            }
        }

        // Calculate the leftovers of each bar that have at least the minimum length
        List<Integer> scraps = new ArrayList<>();
        for (List<Integer> bar : bars) {
            int usedLength = bar.stream().mapToInt(Integer::intValue).sum();
            int leftover = BAR_LENGTH - usedLength;
            if (leftover >= MIN_SCRAP_LENGTH) {
                scraps.add(leftover);
            }
        }

        return new OptimizationResult(bars, scraps);
    }

    public static String formatOptimizationResults(Map<Integer, OptimizationResult> results) {
        if (results == null) {
            return "Erro: Resultados da otimização não podem ser null. Envie um romaneio.";
        }

        StringBuilder output = new StringBuilder();

        output.append("=== SOBRAS ===\n\n");

        for (Map.Entry<Integer, OptimizationResult> entry : results.entrySet()) {
            int diameter = entry.getKey();
            OptimizationResult result = entry.getValue();

            List<Integer> scraps = result.getScraps();

            // group scraps
            Map<Integer, Long> groupedScraps = scraps.stream()
                    .collect(Collectors.groupingBy(s -> s, Collectors.counting()));

            output.append(String.format("Diâmetro %d mm:\n", diameter));

            for (Map.Entry<Integer, Long> scrapEntry: groupedScraps.entrySet()) {
                output.append(String.format(
                        "%d barras com sobra de %d cm \n",
                        scrapEntry.getValue(),
                        scrapEntry.getKey()
                ));
                //output.append(scrapEntry.getValue()).append(" barras de ").append(scrapEntry.getKey()).append(" cm \n");
            }
        }

        output.append("\n=== APROVEITAMENTO ===\n\n");

        for (Map.Entry<Integer, OptimizationResult> entry : results.entrySet()) {
            int diameter = entry.getKey();
            List<List<Integer>> bars = entry.getValue().getBars();

            Map<List<Integer>, Long> groupedBars = bars.stream()
                    .collect(Collectors.groupingBy(s -> s, Collectors.counting()));

            output.append("Ø ").append(diameter).append("mm\n");

            //List<Integer> -> cuts in one bar; Long quantity of bars
            for (Map.Entry<List<Integer>, Long> bar : groupedBars.entrySet()) {
                output.append(String.format(
                        "%d barras com cortes: %s \n",
                        bar.getValue(),
                        bar.getKey()
                ));
            }
            output.append("\n");
        }
        return output.toString();
    }
}
