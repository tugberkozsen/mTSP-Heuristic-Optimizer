import java.util.ArrayList;
import java.util.List;

public class Route {

    private int depotIndex;

    // Sehirlerin indekslerini tutuyoruz.
    private List<Integer> cityIndices;

    public void setDepotIndex(int depotIndex) {
        this.depotIndex = depotIndex;
    }

    public Route(int depotIndex) {
        this.depotIndex = depotIndex;
        this.cityIndices = new ArrayList<>();
    }

    // Kopyalama constructori. Optimizasyon sirasinda orijinal rotayi bozmamak icin
    public Route(Route other) {
        this.depotIndex = other.depotIndex;
        this.cityIndices = new ArrayList<>(other.cityIndices);
    }

    public void addCity(int cityIndex) {
        cityIndices.add(cityIndex);
    }

    public void removeCityAt(int pos) {
        cityIndices.remove(pos);
    }

    public void insertCityAt(int pos, int cityIndex) {
        cityIndices.add(pos, cityIndex);
    }

    public void setCityAt(int pos, int cityIndex) {
        cityIndices.set(pos, cityIndex);
    }

    public int calculateCost() {
        // Eger rota bossa maliyet 0 (satici hic depodan cikmamis)
        if (cityIndices.isEmpty()) {
            return 0;
        }

        var dist = TurkishNetwork.distance;
        var toplamMaliyet = 0;

        // 1. Depodan ilk sehre gidis maliyeti
        toplamMaliyet += dist[depotIndex][cityIndices.get(0)];

        // 2. Sehirler arasi dolasma (aradaki mesafeler)
        for (int i = 0; i < cityIndices.size() - 1; i++) {
            toplamMaliyet += dist[cityIndices.get(i)][cityIndices.get(i + 1)];
        }

        // 3. Son ziyaret edilen sehirden geri depoya donus
        toplamMaliyet += dist[cityIndices.get(cityIndices.size() - 1)][depotIndex];

        return toplamMaliyet;
    }

    public int getDepotIndex() {
        return depotIndex;
    }

    public List<Integer> getCityIndices() {
        return cityIndices;
    }

    public int size() {
        return cityIndices.size();
    }

    public boolean isValid() {
        return !cityIndices.isEmpty();
    }

    @Override
    public String toString() {
        return "Route{depot=" + depotIndex + ", cities=" + cityIndices + "}";
    }
}