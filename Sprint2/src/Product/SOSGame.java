package Product;

import static java.lang.Math.floor;

import java.util.ArrayList;
import java.util.Objects;

public class SOSGame{
  public enum Status {PLAYING, DRAW, P1_WIN, P2_WIN}
  Status gameStatus;
  public enum Mode {SIMPLE, GENERAL}
  Mode gameMode;
  private int p1GeneralGameScore = 0;
  private int p2GeneralGameScore = 0;
  public enum Turn {PL1, PL2}
  Turn currentTurn = Turn.PL1;
  private ArrayList<ArrayList<SOSCell>> gameBoard = new ArrayList<>();
  private int boardSize;
  public <T> SOSGame(T size, int mode){
    resetGame(size, mode);
  }

  public <T> void resetGame(T size, int mode){
    p1GeneralGameScore = 0;
    p2GeneralGameScore = 0;
    setBoardSize(size);
    setGameMode(mode);
    initBoard();
    setGameStatus(Status.PLAYING);
  }

  private <T> void setBoardSize(T size){
    switch(size.getClass().getSimpleName()){
      case "Integer":
        int intSize = (int)size;
        if(intSize < 3){
          boardSize = 3;
        }
        else if(intSize > 10){
          boardSize = 10;
        }
        else{
          boardSize = (int)size;
        }
        break;
      case "Double":
      case "Float":
        double dblSize = (double)size;
        if(dblSize >= 3 && dblSize <= 11){
          boardSize = (int)floor((double)size);
        }
        else{
          boardSize = 3;
        }
        break;
      default:
        boardSize = 3;
        break;
    }
  }

  public int getBoardSize(){
    return boardSize;
  }

  private void initBoard(){
    for(int i = 0; i < getBoardSize(); i++){
      gameBoard.add(new ArrayList<>());
      for(int j = 0; j < getBoardSize(); j++){
        gameBoard.get(i).add(new SOSCell());
      }
    }
  }

  private void setGameStatus(Status newStatus){
   //To Do
  }

  public Status getGameStatus(){
    //To Do
    return Status.PLAYING;//temporary placeholder return
  }

  private void setGameMode(int mode){
    if(mode == 1){
      gameMode = Mode.GENERAL;
    }
    else{
      gameMode = Mode.SIMPLE;
    }
  }

  public String getGameMode(){
    if(gameMode == Mode.SIMPLE){
      return "Simple";
    }
    return "General";
  }

  public boolean isCellEmpty(int row, int col){
    return gameBoard.get(row).get(col).isEmpty();
  }

  public int makeMove(int row, int col, String moveContent){
    if(isMoveValid(row, col)){
      gameBoard.get(row).get(col).setContent(moveContent);

      if(isSOSFormed(row,col,moveContent)){
        System.out.println("SOS Made in Simple Game");
        isGameOver(gameMode);
      }
      else{
        changePlayerTurn();
      }
      return 0;
    }
    return -1;
  }

  private boolean isMoveValid(int row, int col){
    if((row >= 0 && col >= 0) && (row < getBoardSize() && col < getBoardSize())){
      return isCellEmpty(row, col);
    }
    return false;
  }

  private void changePlayerTurn(){
    if(currentTurn == Turn.PL1){
      currentTurn = Turn.PL2;
      return;
    }
    currentTurn = Turn.PL1;
  }

  public String getPlayerTurn(){
    if(currentTurn == Turn.PL1){
      return "Player 1";
    }
    return "Player 2";
  }

  private void updateGeneralGameScore(Turn playerTurn){
    if(playerTurn == Turn.PL1){
      p1GeneralGameScore++;
    }
    else{
      p2GeneralGameScore++;
    }
  }

  public int getGeneralGameScore(Turn playerTurn){
    switch(playerTurn){
      case PL1:
        return p1GeneralGameScore;
      case PL2:
        return p2GeneralGameScore;
      default:
        return -1;
    }
  }

  private boolean isSOSFormed(int row, int col, String moveContent){
    //Check if an SOS was formed by the current move
    int minRowIndex = 0;
    int maxRowIndex = getBoardSize() - 1;
    int minColIndex = 0;
    int maxColIndex = getBoardSize() - 1;

    switch(moveContent){
      case "S"://Check row+/-2 cells subarray on all sides of the cell
        //Set subarray min/max row indices
        if((row - 2) > 0){
          minRowIndex = row - 2;
        }
        if((row + 2) < getBoardSize() - 1){
          maxRowIndex = row + 2;
        }

        //set subarray min/max column indices
        if((col - 2) > 0){
          minColIndex = col - 2;
        }
        if((col + 2) < getBoardSize() - 1){
          maxColIndex = col + 2;
        }

        //Starting and ending indices are adjusted in the loops, so it doesn't iterate over cells unnecessarily
        for(int i = minRowIndex; i <= maxRowIndex; i++) {
          for (int j = minColIndex; j <= maxColIndex; j++) {
            //Check surrounding cells for "O" content, then check 1 cell beyond for "S" content

            if(Objects.equals(gameBoard.get(i).get(j).getContent(), "O")){
              //Create variables for determining which extended cell to check
              int extendedRowIndex = i + (i - row);
              int extendedColIndex = j + (j - col);

              if((extendedRowIndex >= minRowIndex) && (extendedRowIndex <= maxRowIndex) && (extendedColIndex >= minColIndex) && (extendedColIndex <= maxColIndex) && Objects.equals(gameBoard.get(extendedRowIndex).get(extendedColIndex).getContent(), "S")){
                return true;
              }
            }
          }
        }
        break;
      case "O"://Check row+/-1 cells subarray around the cell
        //If an "O" is placed in a corner, it is impossible to have formed an SOS
        if(((row == 0)&&(col == 0))||(row == 0)&&(col == getBoardSize() - 1)||((row == getBoardSize() - 1)&&(col == 0))||((row == getBoardSize() - 1)&&(col == getBoardSize() - 1))){
          return false;
        }

        //Set valid indices for sub rows
        if((row - 1) > minRowIndex){
          minRowIndex = row - 1;
        }
        else if((row + 1) < maxRowIndex){
          maxRowIndex = row + 1;
        }

        //Set valid indices for sub columns
        if((col - 1) > minColIndex){
          minRowIndex = col - 1;
        }
        else if((col + 1) < maxColIndex){
          maxColIndex = col;
        }

        for(int i = minRowIndex; i <= maxRowIndex; i++){
          for(int j = minColIndex; j <= maxColIndex; j++){//Check surrounding cells for "S" content
            if(Objects.equals(gameBoard.get(i).get(j).getContent(), "S")){
              int oppCellRow = 0;
              int oppCellCol = 0;

              switch(row - i){//Determine cell row to check
                case -1:
                  if((row - 1) > 0){
                    oppCellRow = row - 1;
                  }
                  break;
                case 0:
                  oppCellRow = row;
                  break;
                case 1:
                  if((row + 1) >= getBoardSize()){
                    oppCellRow = getBoardSize() - 1;
                  }
                  else{
                    oppCellRow = row + 1;
                  }
                  break;
                default:
                  break;
              }

              switch(col - j){//Determine cell column to check
                case -1:
                  if((col - 1) > 0){
                    oppCellCol = col - 1;
                  }
                  break;
                case 0:
                  oppCellCol = col;
                  break;
                case 1:
                  if((col + 1) >= getBoardSize()){
                    oppCellCol = getBoardSize() - 1;
                  }
                  else{
                    oppCellCol = col + 1;
                  }
                  break;
                default:
                  break;
              }

              if(Objects.equals(gameBoard.get(oppCellRow).get(oppCellCol).getContent(), "S")){
                return true;
              }
            }
          }
        }
        break;
      default:
        break;
    }
    return false;
  }

  private void isGameOver(Mode currentMode){
    //To Do
    //Check if the game is over
    //This function may change or be removed entirely
    if(gameMode == Mode.SIMPLE){

    }
  }
}