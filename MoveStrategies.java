import java.util.Random;
import java.util.stream.Collectors;

// Ayni rota icindeki iki sehri yer degistirir
class SwapNodesInRoute implements MoveStrategy {

    private final Random rnd; // rng yerine rnd

    SwapNodesInRoute(Random rnd) { this.rnd = rnd; }

    @Override
    public boolean apply(Solution solution) {
        var tumRotalar = solution.getAllRoutes();

        // En az 2 sehir iceren rotalari filtreliyoruz (swap yapabilmek icin)
        var uygunRotalar = tumRotalar.stream()
                .filter(r -> r.size() >= 2)
                .collect(Collectors.toList());

        if (uygunRotalar.isEmpty()) return false;

        var rota = uygunRotalar.get(rnd.nextInt(uygunRotalar.size()));

        int i = rnd.nextInt(rota.size());
        int j;
        do {
            j = rnd.nextInt(rota.size());
        } while (j == i);

        var gecici = rota.getCityIndices().get(i);
        rota.setCityAt(i, rota.getCityIndices().get(j));
        rota.setCityAt(j, gecici);

        return true;
    }

    @Override public String getName() { return "swapNodesInRoute"; }
}

class SwapHubWithNodeInRoute implements MoveStrategy {

    private final Random rnd;

    SwapHubWithNodeInRoute(Random rnd) { this.rnd = rnd; }

    @Override
    public boolean apply(Solution solution) {
        var depolar = solution.getDepots();
        if (depolar.isEmpty()) return false;

        var secilenDepo = depolar.get(rnd.nextInt(depolar.size()));
        var rotalar = secilenDepo.getRoutes();

        var uygunRotalar = rotalar.stream()
                .filter(r -> r.size() >= 1)
                .collect(Collectors.toList());

        if (uygunRotalar.isEmpty()) return false;

        var secilenRota = uygunRotalar.get(rnd.nextInt(uygunRotalar.size()));
        int sehirPozisyonu = rnd.nextInt(secilenRota.size());

        int eskiSehirId = secilenRota.getCityIndices().get(sehirPozisyonu);
        int eskiDepoId = secilenDepo.getDepotIndex();

        // Deponun ID'sini şehir yap, o şehrin ID'sini de deponun eski ID'si yap
        secilenDepo.setDepotIndex(eskiSehirId);
        secilenRota.setCityAt(sehirPozisyonu, eskiDepoId);

        for (var r : secilenDepo.getRoutes()) {
            r.setDepotIndex(eskiSehirId);
        }

        return true;
    }

    @Override public String getName() { return "swapHubWithNodeInRoute"; }
}

// Iki farkli rota arasinda birer sehir takasi yapar
class SwapNodesBetweenRoutes implements MoveStrategy {

    private final Random rnd;

    SwapNodesBetweenRoutes(Random rnd) { this.rnd = rnd; }

    @Override
    public boolean apply(Solution solution) {
        var tumRotalar = solution.getAllRoutes();

        var uygunRotalar = tumRotalar.stream()
                .filter(r -> r.size() >= 1)
                .collect(Collectors.toList());

        if (uygunRotalar.size() < 2) return false;

        int idxA = rnd.nextInt(uygunRotalar.size());
        int idxB;
        do {
            idxB = rnd.nextInt(uygunRotalar.size());
        } while (idxB == idxA);

        var rotaA = uygunRotalar.get(idxA);
        var rotaB = uygunRotalar.get(idxB);

        int posA = rnd.nextInt(rotaA.size());
        int posB = rnd.nextInt(rotaB.size());

        var sehirA = rotaA.getCityIndices().get(posA);
        var sehirB = rotaB.getCityIndices().get(posB);

        rotaA.setCityAt(posA, sehirB);
        rotaB.setCityAt(posB, sehirA);

        return true;
    }

    @Override public String getName() { return "swapNodesBetweenRoutes"; }
}

// Ayni rota icinde bir sehri sokup, ayni rotanin baska bir yerine insert eder
class InsertNodeInRoute implements MoveStrategy {

    private final Random rnd;

    InsertNodeInRoute(Random rnd) { this.rnd = rnd; }

    @Override
    public boolean apply(Solution solution) {
        var tumRotalar = solution.getAllRoutes();

        var uygunRotalar = tumRotalar.stream()
                .filter(r -> r.size() >= 2)
                .collect(Collectors.toList());

        if (uygunRotalar.isEmpty()) return false;

        var rota = uygunRotalar.get(rnd.nextInt(uygunRotalar.size()));
        int removePos = rnd.nextInt(rota.size());

        var sehir = rota.getCityIndices().get(removePos);
        rota.removeCityAt(removePos);

        int insertPos;
        if (rota.size() == 0) {
            insertPos = 0;
        } else {
            insertPos = rnd.nextInt(rota.size() + 1);
            if (insertPos == removePos && rota.size() > 1) {
                insertPos = (insertPos + 1) % (rota.size() + 1);
            }
        }
        rota.insertCityAt(insertPos, sehir);

        return true;
    }

    @Override public String getName() { return "insertNodeInRoute"; }
}

// Bir rotadan sehri sokup, tamamen farkli bir rotaya insert eder
class InsertNodeBetweenRoutes implements MoveStrategy {

    private final Random rnd;

    InsertNodeBetweenRoutes(Random rnd) { this.rnd = rnd; }

    @Override
    public boolean apply(Solution solution) {
        var tumRotalar = solution.getAllRoutes();

        // Verici rota (en az 2 sehri olmali ki biri gidince rota patlamasin)
        var kaynakRotalar = tumRotalar.stream()
                .filter(r -> r.size() >= 2)
                .collect(Collectors.toList());

        if (kaynakRotalar.isEmpty() || tumRotalar.size() < 2) return false;

        var kaynakRota = kaynakRotalar.get(rnd.nextInt(kaynakRotalar.size()));

        // Alici rota
        var hedefRotalar = tumRotalar.stream()
                .filter(r -> r != kaynakRota)
                .collect(Collectors.toList());

        if (hedefRotalar.isEmpty()) return false;

        var hedefRota = hedefRotalar.get(rnd.nextInt(hedefRotalar.size()));

        // Yanlis depoya atanan sehri söküp, dogru depodaki rotaya atiyor.
        int removePos = rnd.nextInt(kaynakRota.size());
        var sehir = kaynakRota.getCityIndices().get(removePos);
        kaynakRota.removeCityAt(removePos);

        int insertPos = rnd.nextInt(hedefRota.size() + 1);
        hedefRota.insertCityAt(insertPos, sehir);

        return true;
    }

    @Override public String getName() { return "insertNodeBetweenRoutes"; }
}