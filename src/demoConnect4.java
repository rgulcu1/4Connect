import java.util.Arrays;
import java.util.UUID;

public class demoConnect4 {

    public static void main(String[] args) {

        Constant.GameType gameType = Utils.getParameters(args);

        MainFrame mainFrame = new MainFrame(gameType);

    }
}
