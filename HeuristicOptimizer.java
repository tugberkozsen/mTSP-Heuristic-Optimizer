import java.util.Random;

public class HeuristicOptimizer {

    // Yönergede belirtilen 5 milyonluk sınır
    public static final int NUM_ITERATIONS = 5_000_000;

    private final MoveContext moveContext;

    public HeuristicOptimizer(Random rnd) {
        this.moveContext = new MoveContext(rnd);
    }

    public Solution optimize(Solution initialSolution) {
        // Tip tanımı yerine var kullanıyoruz
        var bestSolution = new Solution(initialSolution);
        bestSolution.recalculateCost();

        for (int i = 0; i < NUM_ITERATIONS; i++) {
            bestSolution = moveContext.executeRandomMove(bestSolution);

            // TODO: test ederken konsol çok şişiyor, sadece yarım milyonda bir log bas
            // if (i % 500000 == 0) {
            //     System.out.println("Iterasyon: " + i + " | Su anki durum...");
            // }
        }

        return bestSolution;
    }

    public String getSuccessSummaryJson() {
        return moveContext.buildSuccessSummaryJson();
    }
}