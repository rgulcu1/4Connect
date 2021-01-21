import java.util.Arrays;

public final class Utils {

    public static void printBoard(){
        for (int i = 0; i <7 ; i++) {
            System.out.print(" "+(i+1));
        }
        System.out.println();
        for (int i = 0; i <6 ; i++) {
            for (int j = 0; j <7 ; j++) {

                System.out.print("|"+Constant.BOARD[i][j]);
            }
            System.out.println("|");
        }
    }

    public static Constant.Player reversePlayer(Constant.Player player){

        if (player.equals(Constant.Player.Player1)) return Constant.Player.Player2;
        else return Constant.Player.Player1;
    }

    public static void deepCopyDobuleArray(String[][] array1 , String[][] array2){
        for (int i = 0; i < array1.length; i++) {
            array1[i] = Arrays.copyOf(array2[i], array2[i].length);

        }
    }
}
