package Product;

import static java.lang.Math.floor;

import java.util.ArrayList;

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
      changePlayerTurn();
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
    }
    else{
      currentTurn = Turn.PL1;
    }
  }

  public String getPlayerTurn(){
    if(currentTurn == Turn.PL1){
      return "Player 1";
    }
    return "Player 2";
  }

  private void updateGeneralGameScore(Turn playerTurn){
    //To Do
  }

  public int getGeneralGameScore(Turn playerTurn){
    //To Do
    //Using Turn playerTurn as a parameter to choose which player score we will return
    return p1GeneralGameScore;//temporary placeholder return
  }

  private void checkWinConditions(){

  }
}