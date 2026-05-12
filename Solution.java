import java.util.ArrayList;
import java.util.List;

public class Solution {

    private List<Depot> depots;
    private int totalCost;

    public Solution() {
        this.depots = new ArrayList<>();
        this.totalCost = 0;
    }

    // ICloneable yapisi.
    // Heuristic optimizasyon sirasinda 5 milyon kere yeni cozum uretilecek.
    // Referanslar birbirine girmesin (orijinal cozum bozulmasin) diye mecburen deep copy yapiyoruz.
    public Solution(Solution other) {
        this.depots = new ArrayList<>();

        for (var d : other.depots) {
            this.depots.add(new Depot(d));
        }
        this.totalCost = other.totalCost;
    }

    public void addDepot(Depot depot) {
        depots.add(depot);
    }

    public int recalculateCost() {
        var toplam = 0;

        for (var depo : depots) {
            toplam += depo.calculateCost();
        }

        this.totalCost = toplam;
        return this.totalCost;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public List<Depot> getDepots() {
        return depots;
    }

    // Tum depolarin icindeki rotalari tek bir liste haline getirip dondurur
    public List<Route> getAllRoutes() {
        var tumRotalar = new ArrayList<Route>();

        for (var depo : depots) {
            tumRotalar.addAll(depo.getRoutes());
        }

        return tumRotalar;
    }

    @Override
    public String toString() {
        return "Solution{totalCost=" + totalCost + ", depots=" + depots + "}";
    }
}