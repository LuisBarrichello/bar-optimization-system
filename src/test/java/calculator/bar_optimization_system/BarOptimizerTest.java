package calculator.bar_optimization_system;

import calculator.bar_optimization_system.model.Element;
import calculator.bar_optimization_system.optimizers.BarOptimizer;
import calculator.bar_optimization_system.optimizers.OptimizationResult;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class BarOptimizerTest {
    @Test
    public void testOptimizeUsageByDiameter() {
        List<Element> elements = Arrays.asList(
                new Element(1, 4, 16, 600),
                new Element(2, 5, 16, 530),
                new Element(3, 3, 16, 510),
                new Element(3, 3, 20, 510)
        );

        Map<Integer, OptimizationResult> resultMap = BarOptimizer.optimizeUsageByDiameter(elements);

        assertNotNull(resultMap);
        assertEquals(2, resultMap.size());

        OptimizationResult opt16 = resultMap.get(16);
        assertNotNull(opt16);
        assertEquals(6, opt16.getBars().size());

        OptimizationResult opt20 = resultMap.get(20);
        assertNotNull(opt20);
        assertEquals(2, opt20.getBars().size());

        assertTrue(opt16.getBars().stream()
                .mapToInt(List::size).sum() <= 600);
    }

    @Test
    public void testFormatOptimizationResults() {
        Map<Integer, OptimizationResult> results = new HashMap<>();
        OptimizationResult result = new OptimizationResult(
                Arrays.asList(Arrays.asList(3000)),
                Collections.singletonList(200)
        );
        results.put(16, result);

        String formatted = BarOptimizer.formatOptimizationResults(results);
        assertTrue(formatted.contains("=== SOBRAS ==="));
        assertTrue(formatted.contains("Diâmetro 16 mm:"));
        assertTrue(formatted.contains("1 barras com sobra de 200 cm"));
    }

    @Test
    void testFormatOptimizationResultsWithNull() {
        String output = BarOptimizer.formatOptimizationResults(null);
        assertEquals("Erro: Resultados da otimização não podem ser null. Envie um romaneio.", output);
    }

}
