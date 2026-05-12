import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonExporter {

    // en distaki "solution" anahtarini saglayan sinif
    private static class SolutionWrapper {
        List<DepotJson> solution = new ArrayList<>();
    }

    private static class DepotJson {
        String depot;
        List<String> routes = new ArrayList<>();
    }

    public void export(Solution solution, String fileName) throws IOException {
        var gson = new GsonBuilder().setPrettyPrinting().create();
        var wrapper = new SolutionWrapper();

        for (var depot : solution.getDepots()) {
            var dj = new DepotJson();
            dj.depot = String.valueOf(depot.getDepotIndex());

            for (var rota : depot.getRoutes()) {
                var sb = new StringBuilder();
                int depoId = depot.getDepotIndex();

                for (int cityIdx : rota.getCityIndices()) {
                    // Eger sehir listesinde yanlislikla depo numarasi varsa onu atla
                    if (cityIdx == depoId) {
                        continue;
                    }
                    sb.append(cityIdx).append(" ");
                }

                String temizRota = sb.toString().trim();

                if (!temizRota.isEmpty()) {
                    dj.routes.add(temizRota);
                }
            }
            wrapper.solution.add(dj);
        }

        // wrapper nesnesini yazdiriyoruz
        try (var writer = new FileWriter(fileName)) {
            gson.toJson(wrapper, writer);
        }
    }
}