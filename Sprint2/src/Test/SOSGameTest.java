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
    assertEquals("Simple", Game1.getGameMode());

    //AC 2.1.2
    Product.SOSGame Game2 = new Product.SOSGame(3,1);
    assertEquals("General", Game2.getGameMode());
  }

  @Test
  void testGameStart(){
    Product.SOSGame Game = new Product.SOSGame(3,0);

    //AC 3.1.0
    assertEquals(3, Game.getBoardSize());
    assertEquals("Simple", Game.getGameMode());
    assertEquals(SOSGame.Status.PLAYING, Game.getGameStatus());
  }

  @Test
  void testPlayerMove(){
    Product.SOSGame Game = new Product.SOSGame(0,3);

    //AC 4 & 6 Incomplete implementation testing
    //AC 4.1.1 & 6.1.1
    assertTrue(Game.isCellEmpty(0,0));
    Game.makeMove(0,0, "S");
    assertFalse(Game.isCellEmpty(0,0));
    assertEquals("Player 2", Game.getPlayerTurn());
    //AC 4.1.3 & 6.1.5
    assertEquals(-1, Game.makeMove(0,0, "S"));
    assertEquals("Player 2", Game.getPlayerTurn());

    //AC 4.1.2 & 6.1.2
    Game.makeMove(0,1, "S");
    assertFalse(Game.isCellEmpty(0,1));
    assertEquals("Player 1", Game.getPlayerTurn());
    //AC 4.1.3 & 6.1.5
    assertEquals(-1, Game.makeMove(0,1, "S"));
    assertEquals("Player 1", Game.getPlayerTurn());

    //AC 4.1.4 & 6.1.6
    assertEquals(-1, Game.makeMove(-5,0, "S"));
    assertEquals(-1, Game.makeMove(0,5, "S"));
    assertEquals(-1, Game.makeMove(5,0, "S"));
    assertEquals("Player 1", Game.getPlayerTurn());
  }

  @Test
  void testGetGeneralGameScore(){

  }

  @Test
  void testPlayer1SimpleWin(){

  }

  @Test
  void testPlayer2SimpleWin(){

  }

  @Test
  void testSimpleDraw(){

  }

  @Test
  void testPlayer1GeneralWin(){

  }

  @Test
  void testPlayer2GeneralWin(){

  }

  @Test
  void testGeneralDraw(){

  }
}