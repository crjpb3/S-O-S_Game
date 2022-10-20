package Product;

import static java.lang.Math.floor;

import java.util.ArrayList;

public class SOSGame{
  //public enum Status {PLAYING, DRAW, P1_WIN, P2_WIN}
  public enum Mode {SIMPLE, GENERAL}
  public enum Turn{PL1, PL2}
  private ArrayList<ArrayList<SOSCell>> gameBoard = new ArrayList<ArrayList<SOSCell>>();
  //Status gameStatus;
  Mode gameMode;

  Turn currentTurn;
  private int boardSize;
  public <T> SOSGame(T size, int mode){
    resetGame(size, mode);
  }

  public <T> void resetGame(T size, int mode){
    setBoardSize(size);
    setGameMode(mode);
    initBoard();
    //setGameStatus(Status.PLAYING);
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
      gameBoard.add(new ArrayList<SOSCell>());
      for(int j = 0; j < getBoardSize(); j++){
        gameBoard.get(i).add(new SOSCell());
      }
    }
  }

  private void setGameMode(int mode){
    if(mode == 1){
      gameMode = Mode.GENERAL;
    }
    else{
      gameMode = Mode.SIMPLE;
    }
  }

  public Mode getGameMode(){
    return gameMode;
  }

/*
  private void setGameStatus(Status status){
    gameStatus = status;
  }


  public Status getGameStatus(){
    return gameStatus;
  }
*/

  public boolean isCellEmpty(int row, int col){
    return true;
  }

  public int makeMove(int row, int col){
    return 0;
  }

  private void setPlayerTurn(){
    if(currentTurn == Turn.PL1){
      currentTurn = Turn.PL2;
    }
    else{
      currentTurn = Turn.PL1;
    }
  }

  public Turn getPlayerTurn(){
    return currentTurn;
  }

}
