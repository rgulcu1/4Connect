import java.util.Objects;

public class Node {

    private String[][] state;


    public Node( String[][] state) {
        this.state  = new String[state.length][];
        Utils.deepCopyDobuleArray(this.state , state);
    }

    public String[][] getState() {
        return state;
    }

    public Integer getScore(Player player){

        if(Objects.isNull(player.heuristic)){
            return getScoreHeuristic1(player);
        }
        switch (player.heuristic){
            case HEURISTIC1: return getScoreHeuristic1(player);
            case HEURISTIC2: return getScoreHeuristic2(player);
            case HEURISTIC3: return getScoreHeuristic3(player);
            default: return getScoreHeuristic1(player);
        }
    }

    private Integer calculateHorizontalNeighborValues(Player player, Integer i , Integer startIndex , Integer endIndex){

        Integer neighborNumber = endIndex - startIndex + 1;

        Integer leftNeighbor = startIndex -1 ;
        Integer rightNeigbor = endIndex + 1;

        if(neighborNumber == 3){

            Integer freeNeighborCounter = 0;

            if(leftNeighbor >= 0 && state[i][leftNeighbor].equals(" ") && (i+1>=6 || i+1 < 6 && !state[i+1][leftNeighbor].equals(" "))) freeNeighborCounter++;
            if(rightNeigbor < 7 && state[i][rightNeigbor].equals(" ") && (i+1>=6 || i+1 < 6 && !state[i+1][rightNeigbor].equals(" "))) freeNeighborCounter++;

            if (freeNeighborCounter == 1) return 90000;
            else if(freeNeighborCounter == 2) return 900000;
            else return 0;
        }
        else if(neighborNumber == 2) {
            Integer freeNeighborCounter = 0;
            Integer oneBlankNeighborCounter = 0;
            boolean isRightNeighborFree = false;


            if(leftNeighbor >= 0 && state[i][leftNeighbor].equals(" ") && (i+1>=6 || i+1 < 6 && !state[i+1][leftNeighbor].equals(" "))){
                freeNeighborCounter++;
                if(leftNeighbor-1 >= 0 && state[i][leftNeighbor-1].equals(player.label)) oneBlankNeighborCounter++;
            }
            if(rightNeigbor < 7 && state[i][rightNeigbor].equals(" ") && (i+1>=6 || i+1 < 6 && !state[i+1][rightNeigbor].equals(" "))){
                freeNeighborCounter++;
                isRightNeighborFree = true;
                if(rightNeigbor+1 < 7 && state[i][rightNeigbor+1].equals(player.label)) oneBlankNeighborCounter++;
            }

            if (oneBlankNeighborCounter == 1) return 90000;
            else if(oneBlankNeighborCounter == 2) return 900000;

            if (freeNeighborCounter == 2) return 5000;
            else if(freeNeighborCounter == 1) {
                int score = 0;
                if(isRightNeighborFree){
                   while(++rightNeigbor<7){
                       if(state[i][rightNeigbor].equals(" ")) score+=1000;
                   }
                   return score;
                }else{
                    while(--leftNeighbor>=0){
                        if(state[i][leftNeighbor].equals(" ")) score+=1000;
                    }
                    return score;
                }
            }
            else return 0;
        }
        else {
            if(startIndex == 3) return 20;
            else if( startIndex == 2 || startIndex == 4) return 12;
            else if(startIndex == 1 || startIndex == 5) return 7;
            else return 4;
        }
    }

    private Integer calculateDiagonalNeighborValues(Player player, Integer startRow , Integer startCol , Integer endRow , Integer endCol , Integer deltaRow , Integer deltaCol){

        Integer neighborNumber = endCol - startCol + 1;

        Integer leftNeighborRow = startRow - deltaRow;
        Integer leftNeighborCol = startCol - deltaCol;

        Integer rightNeighborRow = endRow + deltaRow;
        Integer rightNeighborCol = endCol + deltaCol;

        if(neighborNumber == 3){
            Integer freeNeighborCounter = 0;

            if(leftNeighborRow >= 0 && leftNeighborRow <6 && leftNeighborCol>=0 && leftNeighborCol <7
                    && state[leftNeighborRow][leftNeighborCol].equals(" ")) freeNeighborCounter++;
            if(rightNeighborRow >= 0 && rightNeighborRow <6 && rightNeighborCol>=0 && rightNeighborCol <7
                    && state[rightNeighborRow][rightNeighborCol].equals(" ")) freeNeighborCounter++;

            if (freeNeighborCounter == 1) return 90000;
            else if(freeNeighborCounter == 2) return 900000;
            else return 0;
        }
        else if(neighborNumber ==2){

            Integer freeNeighborCounter = 0;
            Integer oneBlankNeighborCounter = 0;
            boolean isRightNeighborFree = false;

            if(leftNeighborRow >= 0 && leftNeighborRow <6 && leftNeighborCol>=0 && leftNeighborCol <7
                    && state[leftNeighborRow][leftNeighborCol].equals(" ")){
                freeNeighborCounter++;

                if(leftNeighborRow - deltaRow >= 0 && leftNeighborRow - deltaRow <6 && leftNeighborCol - deltaCol >= 0 && leftNeighborCol - deltaCol < 7
                        && state[leftNeighborRow - deltaRow][leftNeighborCol - deltaCol].equals(player.label)) oneBlankNeighborCounter++;
            }
            if(rightNeighborRow >= 0 && rightNeighborRow <6 && rightNeighborCol>=0 && rightNeighborCol <7
                    && state[rightNeighborRow][rightNeighborCol].equals(" ")){
                freeNeighborCounter++;
                isRightNeighborFree = true;

                if(rightNeighborRow + deltaRow >= 0 && rightNeighborRow + deltaRow <6 && rightNeighborCol + deltaCol >= 0 && rightNeighborCol + deltaCol < 7
                        && state[rightNeighborRow + deltaRow][rightNeighborCol + deltaCol].equals(player.label)) oneBlankNeighborCounter++;
            }
            if (oneBlankNeighborCounter == 1) return 90000;
            else if(oneBlankNeighborCounter == 2) return 900000;

            if (freeNeighborCounter == 2) return 5000;
            else if(freeNeighborCounter == 1) {
                int score = 0;
                if(isRightNeighborFree){
                    rightNeighborRow += deltaRow;
                    rightNeighborCol += deltaCol;
                    while(rightNeighborRow >= 0 && rightNeighborRow <6 && rightNeighborCol>=0 && rightNeighborCol <7){
                        if(state[rightNeighborRow][rightNeighborCol].equals(" ")) score+=1000;

                        rightNeighborRow += deltaRow;
                        rightNeighborCol += deltaCol;
                    }
                    return score;
                }else{
                    leftNeighborRow -= deltaRow;
                    leftNeighborCol -= deltaCol;
                    while(leftNeighborRow >= 0 && leftNeighborRow <6 && leftNeighborCol>=0 && leftNeighborCol <7){
                        if(state[leftNeighborRow][leftNeighborCol].equals(" ")) score+=1000;
                        leftNeighborRow -= deltaRow;
                        leftNeighborCol -= deltaCol;
                    }
                    return score;
                }
            }
            else return 0;
        }
        else {
            if(startCol == 3) return 20;
            else if( startCol == 2 || startCol == 4) return 12;
            else if(startCol == 1 || startCol == 5) return 7;
            else return 4;
        }

    }

    private Integer checkHorizontal( Player player){
        Integer heuristicCounter = 0;
        for (int i = 0; i <6 ; i++) {
            Integer startIndex=-1;
            Integer endIndex=-1;

            for (int j = 0; j <7 ; j++) {
                if(player.label.equals(state[i][j]) && startIndex== -1) startIndex = j;

                if(!player.label.equals(state[i][j]) && startIndex != -1){
                    endIndex = j-1;
                    heuristicCounter += calculateHorizontalNeighborValues(player , i , startIndex , endIndex);
                    startIndex= -1;
                    endIndex= -1;
                }
            }
            if(startIndex != -1){
                endIndex = 6;
                heuristicCounter += calculateHorizontalNeighborValues( player , i , startIndex , endIndex);
            }
        }
        return heuristicCounter;
    }

    private Integer checkVertical(Player player){
        Integer heuristicCounter = 0;

        for (int col = 0; col <Constant.COL_SIZE ; col++) {
            Integer neighborCounter = 0;
            Integer lasPiece = -1;
            for (int row = 0; row <Constant.ROW_SIZE ; row++) {

                if(state[row][col].equals(" ")) continue;
                if(!state[row][col].equals(player.label)) break;

                neighborCounter++;
                lasPiece = row;
            }

            if(neighborCounter == 3 && (lasPiece -neighborCounter) >= 0) heuristicCounter+= 90000;
            else if(neighborCounter == 2 && (lasPiece- neighborCounter -1) >= 0) heuristicCounter+= (lasPiece - neighborCounter) * 1000;
            else if(neighborCounter == 1 && (lasPiece- neighborCounter -2) >= 0) {
                if(col == 3) heuristicCounter+= 20;
                else if( col == 2 || col == 4) heuristicCounter+= 12;
                else if(col == 1 || col == 5) heuristicCounter+= 7;
                else heuristicCounter+= 4;
            }
        }

        return heuristicCounter;
    }

    private Integer checkDiagonal(Player player){

        Integer diagonalScore = 0;

        diagonalScore += checkRightDiagonalLine(player , 2 ,5,0,3 );
        diagonalScore += checkRightDiagonalLine(player , 1 ,5,0,4 );
        diagonalScore += checkRightDiagonalLine(player , 0 ,5,0,5 );
        diagonalScore += checkRightDiagonalLine(player , 0 ,5,1,6 );
        diagonalScore += checkRightDiagonalLine(player , 0 ,4,2,6 );
        diagonalScore += checkRightDiagonalLine(player , 0 ,3,3,6 );


        diagonalScore += checkLeftDiagonalLine(player , 3 ,0,0,3 );
        diagonalScore += checkLeftDiagonalLine(player , 4 ,0,0,4 );
        diagonalScore += checkLeftDiagonalLine(player , 5 ,0,0,5 );
        diagonalScore += checkLeftDiagonalLine(player , 5 ,0,1,6 );
        diagonalScore += checkLeftDiagonalLine(player , 5 ,1,2,6 );
        diagonalScore += checkLeftDiagonalLine(player , 5 ,2,3,6 );

        return diagonalScore;
    }

    private Integer checkRightDiagonalLine(Player player ,Integer startRowInput , Integer endRowInput , Integer startColInput  , Integer endColInput ){

        Integer lineScore = 0;
        Integer startRow = -1;
        Integer startCol = -1;

        Integer endRow = -1;
        Integer endCol = -1;

        Integer col = startColInput;
        //first Possible Diagonal
        for (int row = startRowInput; row <= endRowInput ; row++) {

            if(state[row][col].equals(player.label) && startRow == -1) {
                startRow= row;
                startCol = col;
            }

            if(!state[row][col].equals(player.label) && startRow != -1){
                endRow = row-1;
                endCol = col -1;
                lineScore += calculateDiagonalNeighborValues(player , startRow , startCol , endRow , endCol ,1 ,1);
                startCol = -1;
                startRow =-1;
            }
            col++;
        }

        if(startRow != -1){
            endRow =endRowInput;
            endCol =endColInput;
            lineScore += calculateDiagonalNeighborValues(player , startRow , startCol , endRow , endCol ,1 ,1);
        }

        return lineScore;
    }

    private Integer checkLeftDiagonalLine(Player player ,Integer startRowInput , Integer endRowInput , Integer startColInput  , Integer endColInput ){

        Integer lineScore = 0;
        Integer startRow = -1;
        Integer startCol = -1;

        Integer endRow = -1;
        Integer endCol = -1;

        Integer col = startColInput;
        //first Possible Diagonal
        for (int row = startRowInput; row >= endRowInput; row--) {

            if(state[row][col].equals(player.label) && startRow == -1) {
                startRow= row;
                startCol = col;
            }

            if(!state[row][col].equals(player.label) && startRow != -1){
                endRow = row+1;
                endCol = col -1;
                lineScore += calculateDiagonalNeighborValues(player , startRow , startCol , endRow , endCol ,-1 ,1);
                startCol = -1;
                startRow = -1;
            }
            col++;
        }

        if(startRow != -1){
            endRow =endRowInput;
            endCol =endColInput;
            lineScore += calculateDiagonalNeighborValues(player , startRow , startCol , endRow , endCol ,-1 ,1);
        }

        return lineScore;
    }

    private Integer getScoreHeuristic2(Player player){

        Integer score = 0;

        Integer verticalScore = 0;
        Integer horizontalScore = 0;
        Integer diagonalScore1 = 0;
        Integer diagonalScore2 = 0;

        //vertical Check

        for (int i = 0; i <Constant.ROW_SIZE-3 ; i++) {
            for (int j = 0; j <Constant.COL_SIZE ; j++) {
                int vScore = calculateScore(i,j, 1, 0,player);
                if (vScore == Constant.WIN_SCORE) return Constant.WIN_SCORE;
                if (vScore == -Constant.WIN_SCORE) return -Constant.WIN_SCORE;
                verticalScore += vScore;
            }
        }

        for (int i = 0; i < Constant.ROW_SIZE; i++) {
            for (int j = 0; j < Constant.COL_SIZE - 3; j++) {
                int hScore = calculateScore(i, j, 0, 1,player);
                if (hScore == Constant.WIN_SCORE) return Constant.WIN_SCORE;
                if (hScore == -Constant.WIN_SCORE) return -Constant.WIN_SCORE;
                horizontalScore += hScore;
            }
        }

        for (int i = 0; i < Constant.ROW_SIZE - 3; i++) {
            for (int j = 0; j < Constant.COL_SIZE - 3; j++) {
                int h1Ccore =calculateScore(i, j, 1, 1,player);
                if (h1Ccore == Constant.WIN_SCORE) return Constant.WIN_SCORE;
                if (h1Ccore == -Constant.WIN_SCORE) return -Constant.WIN_SCORE;
                diagonalScore1 += h1Ccore;
            }
        }

        for (int i = 3; i < Constant.ROW_SIZE; i++) {
            for (int j = 0; j <= Constant.COL_SIZE - 4; j++) {
                int h2Score = calculateScore(i, j, -1, +1,player);
                if (h2Score == Constant.WIN_SCORE) return Constant.WIN_SCORE;
                if (h2Score == -Constant.WIN_SCORE) return -Constant.WIN_SCORE;
                diagonalScore2 += h2Score;
            }

        }

        score = verticalScore + horizontalScore + diagonalScore1 + diagonalScore2;
        return score;
    }

    private Integer getScoreHeuristic1(Player player){

        Integer score = getScoreHeuristic2(player);

        if(score == Constant.WIN_SCORE ) {
            return Constant.WIN_SCORE;
        }

        if(score == -Constant.WIN_SCORE) {
            return -Constant.WIN_SCORE;
        }

        score = checkHorizontal(player);


        return  score;
    }

    private Integer getScoreHeuristic3(Player player){

        Integer score = getScoreHeuristic2(player);

        if(score == Constant.WIN_SCORE ) {
            return Constant.WIN_SCORE;
        }

        if(score == -Constant.WIN_SCORE) {
            return -Constant.WIN_SCORE;
        }


        score = checkHorizontal(player);
        score += checkVertical(player);
        score += checkDiagonal(player);

        return score;
    }

    private Integer calculateScore(Integer rowIndex , Integer colIndex , Integer deltaRow , Integer deltaCol, Player player){

        Integer playerScore = 0;
        Integer opponentScore = 0;

        for (int i = 0; i <4 ; i++) {

            if(this.state[rowIndex][colIndex].equals(player.label)){
                playerScore++;
            }else if(this.state[rowIndex][colIndex].equals(Utils.reversePlayer(player).label)){
                opponentScore++;
            }

            rowIndex+=deltaRow;
            colIndex+=deltaCol;
        }

        if(opponentScore == 4){
            return -Constant.WIN_SCORE;
        }
        else if(playerScore == 4){
            return  Constant.WIN_SCORE;
        }
        else{
            return playerScore;
        }
    }


    public boolean isGameOver(Integer depth , Integer score){

        if (depth == 0 || score == Constant.WIN_SCORE || score == -Constant.WIN_SCORE || this.isBoardFull()) {
            return true;
        }
        return false;
    }

    public boolean isBoardFull(){
        for (var i = 0; i <Constant.COL_SIZE; i++) {
            if (this.state[0][i].equals(" ")) {
                return false;
            }
        }
        return true;
    }
}
