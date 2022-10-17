package Test;

import static org.junit.jupiter.api.Assertions.*;

import Product.SOSGame;
import org.junit.jupiter.api.Test;

class SOSGameTest {
  @Test
  void testBoardSize(){
    //AC 1.1.1
    Product.SOSGame.setBoardSize(5);
    assertEquals(5, testBoard.getBoardSize());

    //AC 1.1.2
    Product.SOSGame.setBoardSize(-5);
    assertEquals(3, Product.SOSGame.getBoardSize());

    //AC 1.1.3
    Product.SOSGame.setBoardSize(5.5);
    assertEquals(5, Product.SOSGame.getBoardSize());

    //AC 1.1.4
    Product.SOSGame.setBoardSize(50);
    assertEquals(10, Product.SOSGame.getBoardSize());

    //AC 1.1.5
    Product.SOSGame.setBoardSize('z');
    assertEquals(3, Product.SOSGame.getBoardSize());
  }

  @Test
  void testGameModeSelection(){
    //AC 2.1.1
    Product.SOSGame.setGameMode(0);
    assertEquals("SIMPLE", Product.SOSGame.getGameMode());

    //AC 2.1.2
    Product.SOSGame.setGameMode(1);
    assertEquals("GENERAL", Product.SOSGame.getGameMode());
  }

  @Test
  void testGameStart(){
    //AC 3.1.0
    Product.SOSGame game = new SOSGame(0,3);
    assertEquals(3, Product.SOSGame.getBoardSize());
    assertEquals("SIMPLE", Product.SOSGame.getGameMode());
    assertEquals("PLAYING", Product.SOSGame.getGameStatus());

    for(int i = 0; i < Product.SOSGame.getBoardSize(); i++){
      for(int j = 0; j < Product.SOSGame.getBoardSize(); j++){
        assertFalse(Product.SOSGame.isCellOccupied(i,j));
      }
    }
  }

  @Test
  void testPlayerMove(){
    //AC 4 & 6 Incomplete implementation testing
    Product.SOSGame.setBoardSize(3);

    //AC 4.1.1 & 6.1.1
    assertFalse(Product.SOSGame.isCellOccupied(0,0));
    Product.SOSGame.makeMove(0,0);
    assertTrue(Product.SOSGame.isCellOccupied(0,0));
    //AC 4.1.3 & 6.1.5
    assertEquals(-1, Product.SOSGame.makeMove(0,0));

    //AC 4.1.2 & 6.1.2
    Product.SOSGame.makeMove(0,1);
    assertTrue(Product.SOSGame.isCellOccupied(0,1));
    //AC 4.1.3 & 6.1.5
    assertEquals(-1, Product.SOSGame.makeMove(0,1));

    //AC 4.1.4 & 6.1.6
    assertEquals(-1, Product.SOSGame.makeMove(-5,0));
    assertEquals(-1, Product.SOSGame.makeMove(0,5));
    assertEquals(-1, Product.SOSGame.makeMove(5,0));
  }
}