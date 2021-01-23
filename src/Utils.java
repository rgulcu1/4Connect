import java.util.Arrays;

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
}
