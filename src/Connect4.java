import jdk.jshell.execution.Util;

import java.util.Objects;
import java.util.Scanner;

public class Connect4 {

    private Integer depthLevel;

    private Constant.Heuristic heuristicFunction;

    public void playHumanvsHuman(){



        while (true) {

            Utils.printBoard();
            playerMove(Constant.Player.Player1);
            Utils.printBoard();
            playerMove(Constant.Player.Player2);
        }

    }

    public void playHumanvsAi(Constant.Heuristic heuristicFunction , Integer depthLevel){

        this.depthLevel= depthLevel;
        this.heuristicFunction = heuristicFunction;

        while(true){
            Utils.printBoard();
            playerMove(Constant.Player.Player1);
            Utils.printBoard();
            String[][] aiPrefer = alfaBetaSearch(Constant.Player.Player2);
            Utils.deepCopyDobuleArray(Constant.BOARD , aiPrefer);
        }
    }

    private String[][] alfaBetaSearch(Constant.Player player){

        final Node node = new Node(Constant.BOARD,Integer.MIN_VALUE,0);
        Node v = maxValue(node , 0,0,Integer.MIN_VALUE , Integer.MAX_VALUE,player);
        return  v.getState() ;
    }

    private Node maxValue(Node node, Integer lastRow, Integer lastCol, Integer alfa , Integer beta, Constant.Player player){

        if(checkGameOver(node.getState(),lastRow,lastCol, player)){

            node.setWeight(Integer.MAX_VALUE);
            return node;

        }

        if(node.getDepth() == depthLevel){
            node.setWeight(heuristicFunction1(node.getState(),player));
            return node;
        }

        String[][] tempNode = new String[node.getState().length][];
        Utils.deepCopyDobuleArray(tempNode , node.getState());

        for (int i = 0; i <7 ; i++) {
            Node currentNode = new Node(tempNode,Integer.MAX_VALUE , node.getDepth()+1);
            Integer rowIndex = move(currentNode.getState(),i,player);
            if( rowIndex == -1){
                continue;
            }

            Node minValue = minValue(currentNode,rowIndex,i,alfa,beta,Utils.reversePlayer(player));
            if( minValue.getWeight() > node.getWeight()){
                node.setWeight(minValue.getWeight());
                node.setState(minValue.getState());
            }
            if(node.getWeight() >= beta) return node;

            alfa = Math.max(alfa , node.getWeight());
        }

        return node;
    }

    private Node minValue(Node node, Integer lastRow, Integer lastCol, Integer alfa , Integer beta, Constant.Player player){

        if(checkGameOver(node.getState(),lastRow,lastCol, player)){

            node.setWeight(Integer.MIN_VALUE);
            return node;

        }


        if(node.getDepth() == depthLevel){
            node.setWeight(heuristicFunction1(node.getState(),player));
            return node;
        }

        for (int i = 0; i <7 ; i++) {
            Node currentNode = new Node(node.getState(),Integer.MIN_VALUE,node.getDepth()+1);
            Integer rowIndex = move(currentNode.getState(),i,player);
            if( rowIndex == -1){
                continue;
            }

            Node maxValue = maxValue(currentNode,rowIndex,i,alfa,beta,Utils.reversePlayer(player));
            if( maxValue.getWeight() < node.getWeight()){
                node.setWeight(maxValue.getWeight());
            }
            if(node.getWeight() <= alfa) return node;

            beta = Math.min(beta , node.getWeight());
        }
        return node;
    }

    private boolean checkGameOver(String[][] state , Integer lastRow, Integer lastCol , Constant.Player player){

        if(checkHorizontal(state,lastRow,player) || checkVertical(state,lastCol,player) || checkDiagonal(state,lastRow,lastCol,player)) return true;
        else return false;
    }

    private boolean checkHorizontal(String[][] state,Integer lastRow, Constant.Player player){

        Integer winCounter = 1;

        final String[] checkRow = state[lastRow];

        for (int i = 0; i <6 ; i++) {

            if (!checkRow[i].equals(player.label)) continue;

            if(checkRow[i].equals(checkRow[i+1])) winCounter++;
            else winCounter = 1;
        }

        if(winCounter >= 4) return true;
        else return false;
    }

    private boolean checkVertical(String[][] state,Integer lastCol, Constant.Player player){

        Integer winCounter = 1;

        for (int i = 0; i <5 ; i++) {

            if(!state[i][lastCol].equals(player.label)) continue;

            if(state[i][lastCol].equals(state[i+1][lastCol])) winCounter++;
            else winCounter = 1;
        }

        if(winCounter >= 4) return true;
        else return false;
    }

    private boolean checkDiagonal(String[][] state,Integer lastRow, Integer lastCol , Constant.Player player){

        Integer winCounter = 1;
        Integer row = lastRow;
        Integer col = lastCol;

        while(true){

            if (row - 1 < 0 || col -1 <0 ) break;

            if(state[--row][--col].equals(player.label)) winCounter++;
            else break;
        }

        row = lastRow;
        col = lastCol;
        while(true){

            if (row + 1 > 5 || col + 1 > 6 ) break;

            if(state[++row][++col].equals(player.label)) winCounter++;
            else break;
        }

        if(winCounter >= 4) return true;
        else winCounter=1;

        row = lastRow;
        col = lastCol;
        while(true){

            if (row - 1 < 0 || col + 1 >6 ) break;

            if(state[--row][++col].equals(player.label)) winCounter++;
            else break;
        }

        row = lastRow;
        col = lastCol;
        while(true){

            if (row + 1 > 5 || col - 1 < 0 ) break;

            if(state[++row][--col].equals(player.label)) winCounter++;
            else break;
        }

        if(winCounter >= 4) return true;
        else return false;
    }

    private void playerMove(Constant.Player player){

        final Scanner scan = new Scanner(System.in);
        Integer colChoice;
        Integer row;

        System.out.print("\n" + player.name() + " please enter a column:");
        while (true){

            colChoice = scan.nextInt();
            if(colChoice > 7  || colChoice < 1) {
                System.err.println("Please Enter a valid column number!!!");
                continue;
            }
            row = move(Constant.BOARD,colChoice-1 , player);
            if( row != -1){
                break;
            }
            System.err.println("Selected column is full!!!");
        }

        if(checkGameOver(Constant.BOARD,row,colChoice-1,player)){
            Utils.printBoard();
            System.out.println(player.name() + " winss!!");
            System.exit(1);
        }
    }

    private Integer move(String[][] state , Integer colChoice , Constant.Player player){

        if(!state[0][colChoice].equals(" ")){
            return -1;
        }

        int i;
        for (i = 5; i >=0 ; i--) {
            if(state[i][colChoice].equals(" ")) break;
        }

        state[i][colChoice] = player.label;
        return i;
    }

    private Integer heuristicFunction1(String[][] state , Constant.Player player){

        Integer heuristicCounter = 0;
        for (int i = 0; i <6 ; i++) {
            Integer startIndex=-1;
            Integer endIndex=-1;

            for (int j = 0; j <7 ; j++) {
                if(player.label.equals(state[i][j]) && startIndex== -1) startIndex = j;

                if(!player.label.equals(state[i][j]) && startIndex != -1){
                    endIndex = j-1;
                    heuristicCounter += calculateNeighborValues(state , player , i , startIndex , endIndex);
                    startIndex= -1;
                    endIndex= -1;
                }
            }
            if(startIndex != -1){
                endIndex = 6;
                heuristicCounter += calculateNeighborValues(state , player , i , startIndex , endIndex);
            }
        }
        return  heuristicCounter;
    }

    private Integer calculateNeighborValues(String[][] state , Constant.Player player, Integer i , Integer startIndex , Integer endIndex){
        Integer counter = 0;

        if(startIndex - 1 >= 0 && state[i][startIndex -1].equals(" ")) counter++;
        if(endIndex + 1 <=6  && state[i][endIndex + 1].equals(" ")) counter++;

        if((endIndex - startIndex) > 0) counter += (endIndex - startIndex) * 2;

        if(i <=0) return counter;

        for (int j = 0; j < endIndex - startIndex + 1 ; j++) {

            if(state[i-1][j+startIndex].equals(" ")) counter++;
            if(state[i-1][j+startIndex].equals(player.label)) counter+=2;
        }


        return counter;
    }
}
