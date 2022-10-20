package Product;

import static java.lang.Math.floor;

import java.util.ArrayList;

public class SOSGame{
  public enum Status {PLAYING,OVER}
  public enum Mode {SIMPLE, GENERAL}
  public ArrayList<ArrayList<SOSCell>> gameBoard = new ArrayList<ArrayList<SOSCell>>();
  Status gameStatus;
  Mode gameMode;
  int boardSize;
  public <T> SOSGame(T size, int mode){
    setBoardSize(size);
    setGameMode(mode);
    setGameStatus(1);

    for(int i = 0; i < getBoardSize(); i++){
      gameBoard.add(new ArrayList<SOSCell>());
      for(int j = 0; j < getBoardSize(); j++){
        gameBoard.get(i).add(new SOSCell());
      }
    }
  }

  public <T> void setBoardSize(T size){
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

  public void setGameMode(int mode){
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

  public void setGameStatus(int statusCode){
    if(statusCode == 1){
      gameStatus = Status.PLAYING;
    }
    else{
      gameStatus = Status.OVER;
    }

  }

  public Status getGameStatus(){
    return gameStatus;
  }

  public boolean isCellOccupied(int row, int col){
    return false;
  }

  public int makeMove(int row, int col){
    return 0;
  }

  public void setPlayerTurn(){

  }

  public String getPlayerTurn(){
    return "";
  }

}
