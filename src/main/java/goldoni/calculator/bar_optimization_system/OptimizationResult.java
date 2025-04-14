package goldoni.calculator.bar_optimization_system;

import java.util.List;

public class OptimizationResult {
    private List<List<Integer>> bars;
    private List<Integer> scraps;

    public OptimizationResult(List<List<Integer>> bars, List<Integer> scraps) {
        this.bars = bars;
        this.scraps = scraps;
    }

    public List<List<Integer>> getBars() {
        return bars;
    }

    public List<Integer> getScraps() {
        return scraps;
    }
}
