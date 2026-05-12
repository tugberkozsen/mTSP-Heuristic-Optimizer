import java.util.List;
import java.util.Random;

public class MoveContext {

    private final List<MoveStrategy> strategies;
    private final int[]              successCounts;
    private final Random             rnd; // rng yerine rnd

    public MoveContext(Random rnd) {
        this.rnd = rnd;

        // Strateji listemizi (Design Pattern) burada set ediyoruz
        this.strategies = List.of(
                new SwapNodesInRoute(rnd),
                new SwapHubWithNodeInRoute(rnd),
                new SwapNodesBetweenRoutes(rnd),
                new InsertNodeInRoute(rnd),
                new InsertNodeBetweenRoutes(rnd)
        );
        this.successCounts = new int[strategies.size()];
    }

    public Solution executeRandomMove(Solution mevcutEnIyi) {
        // Tip cikarimi icin var ve daha anlasilir isimlendirmeler
        var secilenIndex = rnd.nextInt(strategies.size());
        var strategy = strategies.get(secilenIndex);

        var candidate = new Solution(mevcutEnIyi);

        var uygulandiMi = strategy.apply(candidate);

        // Eger rastgele secilen hamle mevcut rotaya uygulanamiyorsa (ornek: rota bossa) pas geciyoruz
        if (!uygulandiMi) {
            return mevcutEnIyi;
        }

        var yeniMaliyet = candidate.recalculateCost();

        // Sadece daha iyi bir sonuc bulduysak kabul ediyoruz (Hill Climbing mantigi)
        if (yeniMaliyet < mevcutEnIyi.getTotalCost()) {
            successCounts[secilenIndex]++;
            return candidate;
        }

        return mevcutEnIyi;
    }

    public String buildSuccessSummaryJson() {

        var sb = new StringBuilder("{\n");

        for (int i = 0; i < strategies.size(); i++) {
            sb.append("  \"")
                    .append(strategies.get(i).getName())
                    .append("\": ")
                    .append(successCounts[i]);

            if (i < strategies.size() - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }
        sb.append("}");

        return sb.toString();
    }

    public List<MoveStrategy> getStrategies() {
        return strategies;
    }
}