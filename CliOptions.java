import com.lexicalscope.jewel.cli.Option;

public interface CliOptions {

    // Hocanın istediği '-d' parametresi (Toplam depo sayısı)
    @Option(shortName = "d", description = "Number of depots")
    int getDepots();

    // Her depodaki satıcı/araç sayısı
    @Option(shortName = "s", description = "Number of salesmen per depot")
    int getSalesmen();

    // Çıktıda şehir isimlerini görmek için flag
    // TODO: Konsol çıktısı çok şişerse test aşamasında false geçelim
    @Option(shortName = "v", description = "Verbose: print city names instead of indices")
    boolean isVerbose();

    // Standart help menüsü tetikleyicisi
    @Option(helpRequest = true, description = "Display help", shortName = "h")
    boolean getHelp();
}