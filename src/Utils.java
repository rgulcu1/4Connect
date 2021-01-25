import java.util.Arrays;
import java.util.List;

public final class Utils {

    public static void printBoard(String[][] state){
        for (int i = 0; i <7 ; i++) {
            System.out.print(" "+(i+1));
        }
        System.out.println();
        for (int i = 0; i <6 ; i++) {
            for (int j = 0; j <7 ; j++) {

                System.out.print("|"+state[i][j]);
            }
            System.out.println("|");
        }
    }

    public static Player reversePlayer(Player player){

        if (player.equals(Constant.player1)) return Constant.player2;
        else return Constant.player1;
    }

    public static void deepCopyDobuleArray(String[][] array1 , String[][] array2){
        for (int i = 0; i < array1.length; i++) {
            array1[i] = Arrays.copyOf(array2[i], array2[i].length);

        }
    }

    public static Constant.GameType getParameters(String[] args){

        List<String> argList = Arrays.asList(args);

        Integer gameTypeIndex = argList.indexOf("-g");
        Integer player1Index = argList.indexOf("-p1");
        Integer player2Index = argList.indexOf("-p2");

        if(gameTypeIndex == -1){
            System.err.println("Wrong parameters");
            System.exit(1);
        }

        Constant.GameType gameType = null;
        switch (argList.get(gameTypeIndex+1)){
            case "1" : gameType = Constant.GameType.HUMANvsHUMAN;
                break;
            case "2" : gameType = Constant.GameType.HUMANvsAI;
                break;
            case "3" : gameType = Constant.GameType.AIvsAI;
                break;
            default: System.err.println("Wrong parameters");
                System.exit(1);
        }

        if(gameType.equals(Constant.GameType.HUMANvsHUMAN)) return gameType;
        else if(gameType.equals(Constant.GameType.HUMANvsAI)){
            configPlayer(Constant.player2 , player2Index , argList);
        }
        else if(gameType.equals(Constant.GameType.AIvsAI)){
            configPlayer(Constant.player1 , player1Index , argList);
            configPlayer(Constant.player2 , player2Index , argList);
        }

        return gameType;
    }

    private static void configPlayer(Player player, Integer playerIndex , List<String> argList){
        if(playerIndex == -1){
            System.err.println("Wrong parameters");
            System.exit(1);
        }
        String heuristicType = argList.get(playerIndex+1);
        switch (heuristicType){
            case "h1" : player.heuristic = Constant.Heuristic.HEURISTIC1;
                break;
            case "h2" : player.heuristic = Constant.Heuristic.HEURISTIC2;
                break;
            case "h3" : player.heuristic = Constant.Heuristic.HEURISTIC3;
                break;
            default: System.err.println("Wrong parameters");
                System.exit(1);
        }
        Integer depth = Integer.parseInt(argList.get(playerIndex+2));

        if(depth<1 || depth > 8){
            System.err.println("Valid depth should between 1 and 8");
            System.exit(1);
        }

        player.depth = depth;
    }
}
