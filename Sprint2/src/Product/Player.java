package Product;

import Product.SOSGame.PlayerType;

public class Player {
  private SOSGame.PlayerType PlayerType = SOSGame.PlayerType.HUMAN;
  private int[] previousMove = {-1,-1};
  private int playerScore = 0;

  public Player(SOSGame.PlayerType type){
    this.PlayerType = type;
  }

  public void resetScore(){
    this.playerScore = 0;
  }

  public void updateScore(){
    this.playerScore++;
  }

  public int getScore(){
    return this.playerScore;
  }

  public SOSGame.PlayerType getPlayerType(){
    return this.PlayerType;
  }

  public void setPreviousMove(int[] previousMove) {
    this.previousMove = previousMove;
  }

  public int[] getPreviousMove(){
    return this.previousMove;
  }
}
