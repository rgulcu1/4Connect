
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
