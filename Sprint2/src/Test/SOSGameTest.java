package Test;

import static org.junit.jupiter.api.Assertions.*;
import Product.SOSGame;
import org.junit.jupiter.api.Test;

class SOSGameTest {
  @Test
  void testBoardSize(){

    //AC 1.1.1
    Product.SOSGame Game1 = new Product.SOSGame(5,0);
    assertEquals(5, Game1.getBoardSize());

    //AC 1.1.2
    Product.SOSGame Game2 = new Product.SOSGame(-5,0);
    assertEquals(3, Game2.getBoardSize());

    //AC 1.1.3
    Product.SOSGame Game3 = new Product.SOSGame(5.5,0);
    assertEquals(5, Game3.getBoardSize());

    //AC 1.1.4
    Product.SOSGame Game4 = new Product.SOSGame(50,0);
    assertEquals(10, Game4.getBoardSize());

    //AC 1.1.5
    Product.SOSGame Game5 = new Product.SOSGame('z',0);
    assertEquals(3, Game5.getBoardSize());
  }

  @Test
  void testGameModeSelection(){
    //AC 2.1.1
    Product.SOSGame Game1 = new Product.SOSGame(3,0);
    assertEquals(SOSGame.Mode.SIMPLE, Game1.getGameMode());

    //AC 2.1.2
    Product.SOSGame Game2 = new Product.SOSGame(3,1);
    assertEquals(SOSGame.Mode.GENERAL, Game2.getGameMode());
  }

  @Test
  void testGameStart(){
    Product.SOSGame Game = new Product.SOSGame(3,0);

    //AC 3.1.0
    assertEquals(3, Game.getBoardSize());
    assertEquals(SOSGame.Mode.SIMPLE, Game.getGameMode());
    //assertEquals(SOSGame.Status.PLAYING, Game.getGameStatus());
  }

  @Test
  void testPlayerMove(){
    Product.SOSGame Game = new Product.SOSGame(0,3);

    //AC 4 & 6 Incomplete implementation testing
    //AC 4.1.1 & 6.1.1
    assertFalse(Game.isCellEmpty(0,0));
    Game.makeMove(0,0);
    assertTrue(Game.isCellEmpty(0,0));
    assertEquals(SOSGame.Turn.PL2, Game.getPlayerTurn());
    //AC 4.1.3 & 6.1.5
    assertEquals(-1, Game.makeMove(0,0));
    assertEquals(SOSGame.Turn.PL2, Game.getPlayerTurn());

    //AC 4.1.2 & 6.1.2
    Game.makeMove(0,1);
    assertTrue(Game.isCellEmpty(0,1));
    assertEquals(SOSGame.Turn.PL1, Game.getPlayerTurn());
    //AC 4.1.3 & 6.1.5
    assertEquals(-1, Game.makeMove(0,1));
    assertEquals(SOSGame.Turn.PL1, Game.getPlayerTurn());

    //AC 4.1.4 & 6.1.6
    assertEquals(-1, Game.makeMove(-5,0));
    assertEquals(-1, Game.makeMove(0,5));
    assertEquals(-1, Game.makeMove(5,0));
    assertEquals(SOSGame.Turn.PL1, Game.getPlayerTurn());
  }
}