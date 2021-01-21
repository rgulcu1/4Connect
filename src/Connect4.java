import java.util.Objects;
import java.util.Scanner;

public class Connect4 {

    public void playHumanvsHuman(){



        while (true) {

            Utils.printBoard();
            playerMove(Constant.Player.Player1);
            Utils.printBoard();
            playerMove(Constant.Player.Player2);
        }

    }

    private boolean checkGameOver(Integer lastRow, Integer lastCol , Constant.Player player){

        if(checkHorizontal(lastRow,player) || checkVertical(lastCol,player) || checkDiagonal(lastRow,lastCol,player)) return true;
        else return false;
    }

    private boolean checkHorizontal(Integer lastRow, Constant.Player player){

        Integer winCounter = 1;

        final String[] checkRow = Constant.BOARD[lastRow];

        for (int i = 0; i <6 ; i++) {

            if (!checkRow[i].equals(player.label)) continue;

            if(checkRow[i].equals(checkRow[i+1])) winCounter++;
            else winCounter = 1;
        }

        if(winCounter >= 4) return true;
        else return false;
    }

    private boolean checkVertical(Integer lastCol, Constant.Player player){

        Integer winCounter = 1;

        for (int i = 0; i <5 ; i++) {

            if(!Constant.BOARD[i][lastCol].equals(player.label)) continue;

            if(Constant.BOARD[i][lastCol].equals(Constant.BOARD[i+1][lastCol])) winCounter++;
            else winCounter = 1;
        }

        if(winCounter >= 4) return true;
        else return false;
    }

    private boolean checkDiagonal(Integer lastRow, Integer lastCol , Constant.Player player){

        Integer winCounter = 1;
        Integer row = lastRow;
        Integer col = lastCol;

        while(true){

            if (row - 1 < 0 || col -1 <0 ) break;

            if(Constant.BOARD[--row][--col].equals(player.label)) winCounter++;
            else break;
        }

        row = lastRow;
        col = lastCol;
        while(true){

            if (row + 1 > 5 || col + 1 > 6 ) break;

            if(Constant.BOARD[++row][++col].equals(player.label)) winCounter++;
            else break;
        }

        if(winCounter >= 4) return true;
        else winCounter=1;

        row = lastRow;
        col = lastCol;
        while(true){

            if (row - 1 < 0 || col + 1 >6 ) break;

            if(Constant.BOARD[--row][++col].equals(player.label)) winCounter++;
            else break;
        }

        row = lastRow;
        col = lastCol;
        while(true){

            if (row + 1 > 5 || col - 1 < 0 ) break;

            if(Constant.BOARD[++row][--col].equals(player.label)) winCounter++;
            else break;
        }

        if(winCounter >= 4) return true;
        else return false;
    }

    private void playerMove(Constant.Player player){

        final Scanner scan = new Scanner(System.in);
        Integer colChoice;
        Integer row;

        System.out.print(player.label + "please enter a column:");
        while (true){

            colChoice = scan.nextInt();
            if(colChoice > 7  || colChoice < 1) {
                System.err.println("Please Enter a valid column number!!!");
                continue;
            }
            row = move(colChoice , player);
            if( row != -1) break;
        }

        if(checkGameOver(row,colChoice,player)){
            System.out.println(player.label + "winss!!");
            System.exit(1);
        }
    }

    private Integer move(Integer colChoice , Constant.Player player){

        if(!Constant.BOARD[0][colChoice].equals(" ")){
            System.err.println("Selected column is full!!!");
            return -1;
        }

        int i;
        for (i = 5; i >=0 ; i--) {
            if(Constant.BOARD[i][colChoice].equals(" ")) break;
        }

        Constant.BOARD[i][colChoice] = player.label;
        return i;
    }
}
