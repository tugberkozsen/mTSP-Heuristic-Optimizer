import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class RandomSolutionGenerator {

    // Part-1 için 100.000 iterasyon istenmisti
    public static final int NUM_ITERATIONS = 100_000;

    private final int numDepots;
    private final int salesmenPerDepot;
    private final Random rnd;

    public RandomSolutionGenerator(int numDepots, int salesmenPerDepot, Random rnd) {
        this.numDepots = numDepots;
        this.salesmenPerDepot = salesmenPerDepot;
        this.rnd = rnd;
    }

    public Solution generateBest() {
        Solution enIyiCozum = null;

        for (int i = 0; i < NUM_ITERATIONS; i++) {
            var aday = generateOne();
            aday.recalculateCost();

            if (enIyiCozum == null || aday.getTotalCost() < enIyiCozum.getTotalCost()) {
                enIyiCozum = aday;
            }
        }
        return enIyiCozum;
    }

    private Solution generateOne() {
        var toplamSehir = TurkishNetwork.cities.length;
        var toplamRota = numDepots * salesmenPerDepot;

        // Depolar 0'dan numDepots-1'e kadar olan indeksler. Geri kalanlar teslimat yapılacak şehirler.
        var kalanSehirler = new ArrayList<Integer>(toplamSehir - numDepots);
        for (int i = numDepots; i < toplamSehir; i++) {
            kalanSehirler.add(i);
        }

        // OrderBy(x => Guid.NewGuid()) ile liste karıştırma
        Collections.shuffle(kalanSehirler, rnd);

        var solution = new Solution();
        var tumRotalar = new ArrayList<Route>(toplamRota);

        // Önce depoları ve içindeki satıcıları (rotaları) oluşturuyoruz
        for (int d = 0; d < numDepots; d++) {
            var depot = new Depot(d);
            for (int s = 0; s < salesmenPerDepot; s++) {
                var rota = new Route(d);
                depot.addRoute(rota);
                tumRotalar.add(rota);
            }
            solution.addDepot(depot);
        }

        for (int r = 0; r < toplamRota; r++) {
            tumRotalar.get(r).addCity(kalanSehirler.get(r));
        }

        // Geriye kalan tüm şehirleri tamamen rastgele rotalara fırlatıyoruz
        for (int i = toplamRota; i < kalanSehirler.size(); i++) {
            var hedefRota = rnd.nextInt(toplamRota);
            tumRotalar.get(hedefRota).addCity(kalanSehirler.get(i));
        }

        return solution;
    }
}