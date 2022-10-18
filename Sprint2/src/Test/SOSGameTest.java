package Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class SOSGameTest {
  @Test
  void testBoardSize(){
    Product.SOSGame Game = new Product.SOSGame();

    //AC 1.1.1
    Game.setBoardSize(5);
    assertEquals(5, Game.getBoardSize());

    //AC 1.1.2
    Game.setBoardSize(-5);
    assertEquals(3, Game.getBoardSize());

    //AC 1.1.3
    Game.setBoardSize(5.5);
    assertEquals(5, Game.getBoardSize());

    //AC 1.1.4
    Game.setBoardSize(50);
    assertEquals(10, Game.getBoardSize());

    //AC 1.1.5
    Game.setBoardSize('z');
    assertEquals(3, Game.getBoardSize());
  }

  @Test
  void testGameModeSelection(){
    Product.SOSGame Game = new Product.SOSGame();

    //AC 2.1.1
    Game.setGameMode(0);
    assertEquals("Simple", Game.getGameMode());

    //AC 2.1.2
    Game.setGameMode(1);
    assertEquals("General", Game.getGameMode());
  }

  @Test
  void testGameStart(){
    Product.SOSGame Game = new Product.SOSGame();

    //AC 3.1.0
    Game.setBoardSize(3);
    Game.setGameMode(0);
    assertEquals(3, Game.getBoardSize());
    assertEquals("Simple", Game.getGameMode());
    assertEquals("PLAYING", Game.getGameStatus());

    for(int i = 0; i < Game.getBoardSize(); i++){
      for(int j = 0; j < Game.getBoardSize(); j++){
        assertFalse(Game.isCellOccupied(i,j));
      }
    }
  }

  @Test
  void testPlayerMove(){
    Product.SOSGame Game = new Product.SOSGame();

    //AC 4 & 6 Incomplete implementation testing
    Game.setBoardSize(3);

    //AC 4.1.1 & 6.1.1
    assertFalse(Game.isCellOccupied(0,0));
    Game.makeMove(0,0);
    assertTrue(Game.isCellOccupied(0,0));
    assertTrue("Player 2", Game.getPlayerTurn());
    //AC 4.1.3 & 6.1.5
    assertEquals(-1, Game.makeMove(0,0));
    assertTrue("Player 2", Game.getPlayerTurn());

    //AC 4.1.2 & 6.1.2
    Game.makeMove(0,1);
    assertTrue(Game.isCellOccupied(0,1));
    assertTrue("Player 1", Game.getPlayerTurn());
    //AC 4.1.3 & 6.1.5
    assertEquals(-1, Game.makeMove(0,1));
    assertTrue("Player 1", Game.getPlayerTurn());

    //AC 4.1.4 & 6.1.6
    assertEquals(-1, Game.makeMove(-5,0));
    assertEquals(-1, Game.makeMove(0,5));
    assertEquals(-1, Game.makeMove(5,0));
    assertTrue("Player 1", Game.getPlayerTurn());
  }
}