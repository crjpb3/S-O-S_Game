package Product;

import static java.lang.Math.ceil;

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

    for(int i = 0; i < getBoardSize(); i++){
      for(int j = 0; j < getBoardSize(); j++){
        gameBoard.get(i).add(new SOSCell());
        gameBoard.get(i).get(j).setContent("");
      }
    }
  }

  public <T> void setBoardSize(T size){
    switch(size.getClass().getSimpleName()){
      case "Integer":
        if((int)size < 0){
          boardSize = 3;
        }
        else if((int)size > 10){
          boardSize = 10;
        }
        break;
      case "Double":
      case "Float":
        if((int)size >= 3 && (int)size <= 11){
          boardSize = (int)ceil((double)size);
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
