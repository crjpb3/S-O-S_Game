package Test;

import static org.junit.jupiter.api.Assertions.*;

import Product.SOSGame;
import Product.SOSGame.Status;
import org.junit.jupiter.api.Test;

class SOSGameTest {
  @Test
  void testBoardSize(){

    //AC 1.1.1
    SOSGame Game1 = new SOSGame(5,0);
    assertEquals(5, Game1.getBoardSize());

    //AC 1.1.2
    SOSGame Game2 = new SOSGame(-5,0);
    assertEquals(3, Game2.getBoardSize());

    //AC 1.1.3
    SOSGame Game3 = new SOSGame(5.5,0);
    assertEquals(5, Game3.getBoardSize());

    //AC 1.1.4
    SOSGame Game4 = new SOSGame(50,0);
    assertEquals(10, Game4.getBoardSize());

    //AC 1.1.5
    SOSGame Game5 = new SOSGame('z',0);
    assertEquals(3, Game5.getBoardSize());
  }

  @Test
  void testGameModeSelection(){
    //AC 2.1.1
    SOSGame Game1 = new SOSGame(3,0);
    assertEquals("Simple", Game1.getGameMode());

    //AC 2.1.2
    SOSGame Game2 = new SOSGame(3,1);
    assertEquals("General", Game2.getGameMode());
  }

  @Test
  void testGameStart(){
    SOSGame Game = new SOSGame(3,0);

    //AC 3.1.0
    assertEquals(3, Game.getBoardSize());
    assertEquals("Simple", Game.getGameMode());
    assertEquals(SOSGame.Status.PLAYING, Game.getGameStatus());
  }

  @Test
  void testPlayerMove(){
    SOSGame Game = new SOSGame(0,3);

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
    SOSGame Game = new SOSGame(3,1);

    //Player 1 scoring
    assertEquals(0,Game.getGeneralGameScore(SOSGame.Turn.PL1));//Show Player 1 score equals 0 at start
    Game.makeMove(0,0, "S");//Player 1 move
    Game.makeMove(1,0, "S");//Player 2 move
    Game.makeMove(0,1, "0");//Player 1 move
    assertEquals(0,Game.getGeneralGameScore(SOSGame.Turn.PL1));//show Player 1 score equals 0 after several moves
    Game.makeMove(2,0, "S");//Player 2 move
    Game.makeMove(0,2, "S");//Player 1 SOS completion move
    assertEquals(1,Game.getGeneralGameScore(SOSGame.Turn.PL1));//Show Player 1 score equals 1 after completing an SOS

    //Player 2 scoring
    Game.resetGame(3,1);
    assertEquals(0,Game.getGeneralGameScore(SOSGame.Turn.PL2));//Show Player 2 score equals 0 at start
    Game.makeMove(1,0, "S");//Player 1 move
    Game.makeMove(0,0, "S");//Player 2 move
    Game.makeMove(1,1, "S");//Player 1 move
    assertEquals(0,Game.getGeneralGameScore(SOSGame.Turn.PL2));//show Player 2 score equals 0 after several moves
    Game.makeMove(0,1, "O");//Player 2 move
    Game.makeMove(2,0, "S");//Player 1 move
    Game.makeMove(0,2, "S");//Player 2 SOS completion move
    assertEquals(1,Game.getGeneralGameScore(SOSGame.Turn.PL2));//Show Player 2 score equals 1 after completing an SOS

  }

  @Test
  void testPlayer1SimpleWin(){
    SOSGame Game = new SOSGame(3,0);
    assertEquals(Status.PLAYING, Game.getGameStatus());//Show no win status

    Game.makeMove(0,0,"S");//Player 1 move
    Game.makeMove(1,0,"S");//Player 2 move
    Game.makeMove(0,1,"O");//Player 1 move
    Game.makeMove(2,0,"S");//Player 2 move
    assertEquals(Status.PLAYING, Game.getGameStatus());//Show no win status after several moves
    Game.makeMove(0,2,"S");//Player 1 winning move
    assertEquals(Status.P1_WIN, Game.getGameStatus());//Show Player 1 win status
  }

  @Test
  void testPlayer2SimpleWin(){
    SOSGame Game = new SOSGame(3,0);
    assertEquals(Status.PLAYING, Game.getGameStatus());//Show no win status

    Game.makeMove(1,0,"S");//Player 1 move
    Game.makeMove(0,0,"S");//Player 2 move
    Game.makeMove(2,0,"S");//Player 1 move
    Game.makeMove(0,1,"O");//Player 2 move
    assertEquals(Status.PLAYING, Game.getGameStatus());//Show no win status after several moves

    Game.makeMove(1,1,"S");//Player 1 move
    Game.makeMove(0,2,"S");//Player 2 winning move
    assertEquals(Status.P2_WIN, Game.getGameStatus());//Show Player 2 win status
  }

  @Test
  void testSimpleDraw(){
    SOSGame Game = new SOSGame(3,0);
    assertEquals(SOSGame.Status.PLAYING, Game.getGameStatus());
    //Fill all cells without creating an SOS
    for(int i = 0; i < Game.getBoardSize(); i++){
      for(int j = 0; j < Game.getBoardSize(); j++){
        Game.makeMove(i,j,"S");
      }
    }
    assertEquals(SOSGame.Status.DRAW, Game.getGameStatus());
  }

  @Test
  void testPlayer1GeneralWin(){
    SOSGame Game = new SOSGame(3,1);
    assertEquals(SOSGame.Status.PLAYING,Game.getGameStatus());//Show game has not ended
    assertEquals(0, Game.getGeneralGameScore(SOSGame.Turn.PL1));//Show player 1 score at 0
    assertEquals(0, Game.getGeneralGameScore(SOSGame.Turn.PL2));//Show player 2 score at 0

    Game.makeMove(0,0,"S");//Player 1 move
    Game.makeMove(1,0,"S");//Player 2 move
    Game.makeMove(0,1,"O");//Player 1 move
    Game.makeMove(1,1,"S");//Player 2 move
    Game.makeMove(0,2,"S");//Player 1 move completing an SOS
    assertEquals(0, Game.getGeneralGameScore(SOSGame.Turn.PL1));//Show player 1 score at 1
    assertEquals(0, Game.getGeneralGameScore(SOSGame.Turn.PL2));//Show player 2 score at 0

    Game.makeMove(1,2,"S");//Player 2 move
    Game.makeMove(2,0,"S");//Player 1 move
    Game.makeMove(2,1,"S");//Player 2 move
    Game.makeMove(2,2,"S");//Player 1 move ending the game; all cells occupied
    assertEquals(SOSGame.Status.P1_WIN,Game.getGameStatus());//Show Player 1 win status

  }

  @Test
  void testPlayer2GeneralWin(){
    SOSGame Game = new SOSGame(3,1);
    assertEquals(SOSGame.Status.PLAYING,Game.getGameStatus());//Show game has not ended
    assertEquals(0, Game.getGeneralGameScore(SOSGame.Turn.PL1));//Show player 1 score at 0
    assertEquals(0, Game.getGeneralGameScore(SOSGame.Turn.PL2));//Show player 2 score at 0

    Game.makeMove(1,0,"S");//Player 1 move
    Game.makeMove(0,0,"S");//Player 2 move
    Game.makeMove(1,1,"O");//Player 1 move
    Game.makeMove(0,1,"S");//Player 2 move
    Game.makeMove(1,2,"S");//Player 1 move
    Game.makeMove(0,2,"S");//Player 2 move
    assertEquals(0, Game.getGeneralGameScore(SOSGame.Turn.PL1));//Show player 1 score at 0
    assertEquals(0, Game.getGeneralGameScore(SOSGame.Turn.PL2));//Show player 2 score at 1

    Game.makeMove(2,0,"S");//Player 1 move
    Game.makeMove(2,1,"S");//Player 2 move
    Game.makeMove(2,2,"S");//Player 1 move ending the game; all cells occupied
    assertEquals(SOSGame.Status.P2_WIN,Game.getGameStatus());//Show Player 2 win status
  }

  @Test
  void testGeneralDraw(){
    SOSGame Game = new SOSGame(3,1);
    assertEquals(SOSGame.Status.PLAYING, Game.getGameStatus());
    //Fill all cells without creating an SOS
    for(int i = 0; i < Game.getBoardSize(); i++){
      for(int j = 0; j < Game.getBoardSize(); j++){
        Game.makeMove(i,j,"S");
      }
    }
    assertEquals(SOSGame.Status.DRAW, Game.getGameStatus());
  }
}