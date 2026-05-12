import java.util.ArrayList;
import java.util.List;

public class Depot {

    private int depotIndex;
    private List<Route> routes;

    public void setDepotIndex(int depotIndex) {
        this.depotIndex = depotIndex;
    }

    public Depot(int depotIndex) {
        this.depotIndex = depotIndex;
        this.routes = new ArrayList<>();
    }

    // ICloneable.Clone() mantığı.
    // Optimizasyon iterasyonlarında referanslar birbirine girmesin diye mecburen deep copy yapıyoruz.
    public Depot(Depot other) {
        this.depotIndex = other.depotIndex;
        this.routes = new ArrayList<>();

        for (var r : other.routes) {
            this.routes.add(new Route(r));
        }
    }

    public void addRoute(Route route) {
        routes.add(route);
    }

    public int calculateCost() {
        var toplamMaliyet = 0;

        for (var rota : routes) {
            toplamMaliyet += rota.calculateCost();
        }

        return toplamMaliyet;
    }

    public int getDepotIndex() {
        return depotIndex;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public int getRouteCount() {
        return routes.size();
    }

    @Override
    public String toString() {
        // Console logları için basit override
        return "Depot{index=" + depotIndex + ", routes=" + routes + "}";
    }
}