import com.lexicalscope.jewel.cli.ArgumentValidationException;
import com.lexicalscope.jewel.cli.CliFactory;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        try {
            CliOptions options = CliFactory.parseArguments(CliOptions.class, args);
            int d = options.getDepots();
            int s = options.getSalesmen();
            boolean v = options.isVerbose();

            Random rnd = new Random();

            var generator = new RandomSolutionGenerator(d, s, rnd);
            Solution initialSolution = generator.generateBest();

            var printer = new SolutionPrinter(v);
            printer.print(initialSolution, "PART-I: Best of 100,000 Random Solutions");

            var optimizer = new HeuristicOptimizer(rnd);
            Solution optimizedSolution = optimizer.optimize(initialSolution);

            printer.print(optimizedSolution, "PART-II: Optimized Solution after 5,000,000 Iterations");

            System.out.println("Counts of the moves that caused gains:");
            System.out.println(optimizer.getSuccessSummaryJson());

            var exporter = new JsonExporter();
            exporter.export(optimizedSolution, "solution.json");

            System.out.println("\nProcess completed. 'solution.json' has been created.");

        } catch (ArgumentValidationException e) {
            System.err.println("Parameter Error: " + e.getMessage());
            System.err.println("Example usage: java -jar target/mTSP.jar -d 4 -s 2 -v");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}