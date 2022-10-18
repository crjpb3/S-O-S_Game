package Product;

import java.util.ArrayList;
import Product.SOSCell;

enum Status {PLAYING,OVER}

public class SOSGame{
  private ArrayList<SOSCell> gameBoard = new ArrayList<SOSCell>();
  Status gameStatus;
  public SOSGame(){

  }

  public <T> void setBoardSize(T size){

  }

  public int getBoardSize(){

  }

  public void setGameMode(int mode){

  }

  public String getGameMode(){

  }

  public void setGameStatus(int statusCode){
    if(statusCode == 1){
      gameStatus = Status.PLAYING;
    }
    else{
      gameStatus = Status.OVER;
    }

  }

  public String getGameStatus(){

  }

  public boolean isCellOccupied(int row, int col){

  }

  public int makeMove(int row, int col){

  }

  public String getPlayerTurn(){

  }

}
