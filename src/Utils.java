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
}
