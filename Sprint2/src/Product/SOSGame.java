package Product;

import java.util.ArrayList;

enum Status {PLAYING,OVER}

public class SOSGame{
  private ArrayList<SOSCell> gameBoard = new ArrayList<SOSCell>();
  Status gameStatus;
  public <T> SOSGame(T size, int mode){

  }

  private <T> void setBoardSize(T size){

  }

  public int getBoardSize(){
    return 0;
  }

  private void setGameMode(int mode){

  }

  public String getGameMode(){
    return "";
  }

  private void setGameStatus(int statusCode){
    if(statusCode == 1){
      gameStatus = Status.PLAYING;
    }
    else{
      gameStatus = Status.OVER;
    }

  }

  public String getGameStatus(){
    return "";
  }

  public boolean isCellOccupied(int row, int col){
    return false;
  }

  public int makeMove(int row, int col){
    return 0;
  }

  private void setPlayerTurn(){

  }

  public String getPlayerTurn(){
    return "";
  }

}
