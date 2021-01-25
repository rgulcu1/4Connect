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


    private Integer checkHorizontal( Player player){
        Integer heuristicCounter = 0;
        for (int i = 0; i <6 ; i++) {
            Integer startIndex=-1;
            Integer endIndex=-1;

            for (int j = 0; j <7 ; j++) {
                if(player.label.equals(state[i][j]) && startIndex== -1) startIndex = j;

                if(!player.label.equals(state[i][j]) && startIndex != -1){
                    endIndex = j-1;
                    if((endIndex -startIndex + 1 ) == 3){
                        heuristicCounter += 500;
                    }
                    if((endIndex -startIndex + 1 ) == 2){
                        heuristicCounter += 50;
                    }
                    startIndex= -1;
                    endIndex= -1;
                }
            }
            if(startIndex != -1){
                endIndex = 6;
                if((endIndex -startIndex + 1 ) == 3){
                    heuristicCounter += 500;
                }
                if((endIndex -startIndex + 1 ) == 2){
                    heuristicCounter += 50;
                }
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

            if(neighborCounter == 3 && (lasPiece -neighborCounter) >= 0) heuristicCounter+= 500;
            else if(neighborCounter == 2 && (lasPiece- neighborCounter -1) >= 0) heuristicCounter+=  50;

        }

        return heuristicCounter;
    }

    private Integer getScoreHeuristic3(Player player){

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

        Integer score = getScoreHeuristic3(player);

        if(score == Constant.WIN_SCORE ) {
            return Constant.WIN_SCORE;
        }

        if(score == -Constant.WIN_SCORE) {
            return -Constant.WIN_SCORE;
        }

        score = checkHorizontal(player);
        score += checkVertical(player);
        return  score;
    }

    private Integer getScoreHeuristic2(Player player){

        Integer score = getScoreHeuristic3(player);

        if(score == Constant.WIN_SCORE ) {
            return Constant.WIN_SCORE;
        }

        if(score == -Constant.WIN_SCORE) {
            return -Constant.WIN_SCORE;
        }
        score = 0;


        for (int i = 0; i <Constant.ROW_SIZE ; i++) {
            for (int j = 0; j <Constant.COL_SIZE ; j++) {
                if(this.state[i][j].equals(player.label)){
                    score += Constant.CELL_WEIGHTS[i][j];
                }
            }
        }
        return score;

    }

    private Integer calculateScore(Integer rowIndex , Integer colIndex , Integer deltaRow , Integer deltaCol, Player player){

        Integer playerScore = 0;
        Integer opponentScore = 0;
        Integer neighBorCounter = 0;
        Integer maxNeighBor = 0;

        for (int i = 0; i <4 ; i++) {

            if(this.state[rowIndex][colIndex].equals(player.label)){
                playerScore++;
                neighBorCounter++;

            }else if(this.state[rowIndex][colIndex].equals(Utils.reversePlayer(player).label)){
                opponentScore++;
                maxNeighBor = Math.max(neighBorCounter,maxNeighBor);
                neighBorCounter = 0;

            }else{
                maxNeighBor = Math.max(neighBorCounter,maxNeighBor);

                neighBorCounter = 0;
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
            if(maxNeighBor ==3){
               if (opponentScore == 0) return playerScore + 50;
               else return playerScore + 20;
            }else if(maxNeighBor == 2){
                if (opponentScore == 0) return playerScore + 10;
                else if(opponentScore == 1) return  playerScore + 3;
                 else return playerScore + 1;
            }else{
                return playerScore;
            }
        }
    }


    public boolean isGameOver(Integer depth , Integer score){

        if (depth == 0 || score == Constant.WIN_SCORE || score == -Constant.WIN_SCORE || this.isBoardFull()) {
            return true;
        }
        return false;
    }

    public boolean isBoardFull(){
        for (int i = 0; i <Constant.COL_SIZE; i++) {
            if (this.state[0][i].equals(" ")) {
                return false;
            }
        }
        return true;
    }
}
