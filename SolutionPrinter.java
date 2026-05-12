public class SolutionPrinter {

    private final boolean verbose;

    public SolutionPrinter(boolean verbose) {
        this.verbose = verbose;
    }

    public void print(Solution solution, String header) {
        System.out.println("\n------------------------------------------------------------");
        System.out.println("  " + header);
        System.out.println("  Total Cost: " + solution.getTotalCost() + " km");
        System.out.println("------------------------------------------------------------");

        for (int i = 0; i < solution.getDepots().size(); i++) {
            var depo = solution.getDepots().get(i);
            System.out.println("\n  Depot " + i + ": " + getCityName(depo.getDepotIndex()));

            for (int j = 0; j < depo.getRoutes().size(); j++) {
                var rota = depo.getRoutes().get(j);
                var sb = new StringBuilder();

                sb.append("    Salesman ").append(j).append(": ");
                sb.append(getCityName(depo.getDepotIndex())).append(" -> ");

                for (var cityIdx : rota.getCityIndices()) {
                    sb.append(getCityName(cityIdx)).append(" -> ");
                }

                sb.append(getCityName(depo.getDepotIndex()));
                sb.append("  [").append(rota.calculateCost()).append(" km]");

                System.out.println(sb.toString());
            }
        }
        System.out.println("------------------------------------------------------------\n");
    }

    private String getCityName(int index) {
        // Verbose flag'ine gore ya sehir ismini ya da sadece indeksi donuyoruz
        if (verbose) {
            return TurkishNetwork.cities[index];
        }
        return String.valueOf(index);
    }
}