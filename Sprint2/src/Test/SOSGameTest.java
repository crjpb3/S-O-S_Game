package Test;

import static org.junit.jupiter.api.Assertions.*;

import Product.SOSGame;
import Product.SOSGame.Status;
import org.junit.jupiter.api.Test;

class SOSGameTest {
  @Test
  void testBoardSize(){

    //AC 1.1
    SOSGame Game1 = new SOSGame(5,0, SOSGame.PlayerType.HUMAN, SOSGame.PlayerType.HUMAN);
    assertEquals(5, Game1.getBoardSize());

    //AC 1.2
    SOSGame Game2 = new SOSGame(-5,0, SOSGame.PlayerType.HUMAN, SOSGame.PlayerType.HUMAN);
    assertEquals(3, Game2.getBoardSize());

    //AC 1.3
    SOSGame Game3 = new SOSGame(5.5,0, SOSGame.PlayerType.HUMAN, SOSGame.PlayerType.HUMAN);
    assertEquals(5, Game3.getBoardSize());

    //AC 1.4
    SOSGame Game4 = new SOSGame(50,0, SOSGame.PlayerType.HUMAN, SOSGame.PlayerType.HUMAN);
    assertEquals(10, Game4.getBoardSize());

    //AC 1.5
    SOSGame Game5 = new SOSGame('z',0, SOSGame.PlayerType.HUMAN, SOSGame.PlayerType.HUMAN);
    assertEquals(3, Game5.getBoardSize());
  }

  @Test
  void testGameModeSelection(){
    //AC 2.1
    SOSGame Game1 = new SOSGame(3,0, SOSGame.PlayerType.HUMAN, SOSGame.PlayerType.HUMAN);
    assertEquals("Simple", Game1.getGameMode());

    //AC 2.2
    SOSGame Game2 = new SOSGame(3,1, SOSGame.PlayerType.HUMAN, SOSGame.PlayerType.HUMAN);
    assertEquals("General", Game2.getGameMode());
  }

  @Test
  void testGameStart(){
    SOSGame Game = new SOSGame(3,0, SOSGame.PlayerType.HUMAN, SOSGame.PlayerType.HUMAN);

    //AC 3.0
    assertEquals(3, Game.getBoardSize());
    assertEquals("Simple", Game.getGameMode());
    assertEquals(SOSGame.Status.PLAYING, Game.getGameStatus());
  }

  @Test
  void testPlayerMove(){
    SOSGame Game = new SOSGame(0,3, SOSGame.PlayerType.HUMAN, SOSGame.PlayerType.HUMAN);

    //AC 4 & 6 Incomplete implementation testing
    //AC 4.1 & 6.1
    assertTrue(Game.isCellEmpty(0,0));
    Game.makeMove(0,0, "S");
    assertFalse(Game.isCellEmpty(0,0));
    assertEquals(SOSGame.Turn.PL2, Game.getPlayerTurn());
    //AC 4.3 & 6.5
    assertEquals(-1, Game.makeMove(0,0, "S"));
    assertEquals(SOSGame.Turn.PL2, Game.getPlayerTurn());

    //AC 4.2 & 6.2
    Game.makeMove(0,1, "S");
    assertFalse(Game.isCellEmpty(0,1));
    assertEquals(SOSGame.Turn.PL1, Game.getPlayerTurn());
    //AC 4.3 & 6.5
    assertEquals(-1, Game.makeMove(0,1, "S"));
    assertEquals(SOSGame.Turn.PL1, Game.getPlayerTurn());

    //AC 4.4 & 6.6
    assertEquals(-1, Game.makeMove(-5,0, "S"));
    assertEquals(-1, Game.makeMove(0,5, "S"));
    assertEquals(-1, Game.makeMove(5,0, "S"));
    assertEquals(SOSGame.Turn.PL1, Game.getPlayerTurn());
  }

  @Test
  void testGetGeneralGameScore(){
    SOSGame Game = new SOSGame(3,1, SOSGame.PlayerType.HUMAN, SOSGame.PlayerType.HUMAN);

    //AC 6.3
    //Player 1 scoring
    assertEquals(0,Game.getGeneralGameScore(SOSGame.Turn.PL1));//Show Player 1 score equals 0 at start
    Game.makeMove(0,0, "S");//Player 1 move
    Game.makeMove(1,0, "S");//Player 2 move
    Game.makeMove(0,1, "O");//Player 1 move
    Game.makeMove(2,0, "S");//Player 2 move
    Game.makeMove(0,2, "S");//Player 1 SOS completion move
    assertEquals(1,Game.getGeneralGameScore(SOSGame.Turn.PL1));//Show Player 1 score equals 1 after completing an SOS

    //AC 6.4
    //Player 2 scoring
    Game.resetGame(3,1, SOSGame.PlayerType.HUMAN, SOSGame.PlayerType.HUMAN);
    assertEquals(0,Game.getGeneralGameScore(SOSGame.Turn.PL2));//Show Player 2 score equals 0 at start
    Game.makeMove(1,0, "S");//Player 1 move
    Game.makeMove(0,0, "S");//Player 2 move
    Game.makeMove(1,1, "S");//Player 1 move
    Game.makeMove(0,1, "O");//Player 2 move
    Game.makeMove(2,0, "S");//Player 1 move
    Game.makeMove(0,2, "S");//Player 2 SOS completion move
    assertEquals(1,Game.getGeneralGameScore(SOSGame.Turn.PL2));//Show Player 2 score equals 1 after completing an SOS
  }

  @Test
  void testPlayer1SimpleWin(){
    //AC 5.1
    SOSGame Game = new SOSGame(3,0, SOSGame.PlayerType.HUMAN, SOSGame.PlayerType.HUMAN);
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
    //AC 5.2
    SOSGame Game = new SOSGame(3,0, SOSGame.PlayerType.HUMAN, SOSGame.PlayerType.HUMAN);
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
    //AC 5.3
    SOSGame Game = new SOSGame(3,0, SOSGame.PlayerType.HUMAN, SOSGame.PlayerType.HUMAN);
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
    //AC 7.1
    SOSGame Game = new SOSGame(3,1, SOSGame.PlayerType.HUMAN, SOSGame.PlayerType.HUMAN);
    assertEquals(SOSGame.Status.PLAYING,Game.getGameStatus());//Show game has not ended
    assertEquals(0, Game.getGeneralGameScore(SOSGame.Turn.PL1));//Show player 1 score at 0
    assertEquals(0, Game.getGeneralGameScore(SOSGame.Turn.PL2));//Show player 2 score at 0

    Game.makeMove(0,0,"S");//Player 1 move
    Game.makeMove(1,0,"S");//Player 2 move
    Game.makeMove(0,1,"O");//Player 1 move
    Game.makeMove(1,1,"S");//Player 2 move
    Game.makeMove(0,2,"S");//Player 1 move completing an SOS
    assertEquals(1, Game.getGeneralGameScore(SOSGame.Turn.PL1));//Show player 1 score at 1
    assertEquals(0, Game.getGeneralGameScore(SOSGame.Turn.PL2));//Show player 2 score at 0

    Game.makeMove(1,2,"S");//Player 2 move
    Game.makeMove(2,0,"S");//Player 1 move
    Game.makeMove(2,1,"S");//Player 2 move
    Game.makeMove(2,2,"S");//Player 1 move ending the game; all cells occupied
    assertEquals(SOSGame.Status.P1_WIN,Game.getGameStatus());//Show Player 1 win status

  }

  @Test
  void testPlayer2GeneralWin(){
    //AC 7.2
    SOSGame Game = new SOSGame(3,1, SOSGame.PlayerType.HUMAN, SOSGame.PlayerType.HUMAN);
    assertEquals(SOSGame.Status.PLAYING,Game.getGameStatus());//Show game has not ended
    assertEquals(0, Game.getGeneralGameScore(SOSGame.Turn.PL1));//Show player 1 score at 0
    assertEquals(0, Game.getGeneralGameScore(SOSGame.Turn.PL2));//Show player 2 score at 0

    Game.makeMove(1,0,"S");//Player 1 move
    Game.makeMove(0,0,"S");//Player 2 move
    Game.makeMove(1,1,"S");//Player 1 move
    Game.makeMove(0,1,"O");//Player 2 move
    Game.makeMove(1,2,"S");//Player 1 move
    Game.makeMove(0,2,"S");//Player 2 move
    assertEquals(0, Game.getGeneralGameScore(SOSGame.Turn.PL1));//Show player 1 score at 0
    assertEquals(1, Game.getGeneralGameScore(SOSGame.Turn.PL2));//Show player 2 score at 1

    Game.makeMove(2,0,"S");//Player 1 move
    Game.makeMove(2,1,"S");//Player 2 move
    Game.makeMove(2,2,"S");//Player 1 move ending the game; all cells occupied
    assertEquals(SOSGame.Status.P2_WIN,Game.getGameStatus());//Show Player 2 win status
  }

  @Test
  void testGeneralDraw(){
    //AC 7.3
    SOSGame Game = new SOSGame(3,1, SOSGame.PlayerType.HUMAN, SOSGame.PlayerType.HUMAN);
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
  void testPlayerTypeSelection(){
    SOSGame Game = new SOSGame(3,0,SOSGame.PlayerType.HUMAN,SOSGame.PlayerType.HUMAN);
    assertEquals(SOSGame.PlayerType.HUMAN, Game.getPlayerType(SOSGame.Turn.PL1));
    assertEquals(SOSGame.PlayerType.HUMAN, Game.getPlayerType(SOSGame.Turn.PL2));

    Game.resetGame(3,0,SOSGame.PlayerType.COMPUTER,SOSGame.PlayerType.HUMAN);
    assertEquals(SOSGame.PlayerType.COMPUTER, Game.getPlayerType(SOSGame.Turn.PL1));
    assertEquals(SOSGame.PlayerType.HUMAN, Game.getPlayerType(SOSGame.Turn.PL2));

    Game.resetGame(3,0,SOSGame.PlayerType.HUMAN,SOSGame.PlayerType.COMPUTER);
    assertEquals(SOSGame.PlayerType.HUMAN, Game.getPlayerType(SOSGame.Turn.PL1));
    assertEquals(SOSGame.PlayerType.COMPUTER, Game.getPlayerType(SOSGame.Turn.PL2));

    Game.resetGame(3,0,SOSGame.PlayerType.COMPUTER,SOSGame.PlayerType.COMPUTER);
    assertEquals(SOSGame.PlayerType.COMPUTER, Game.getPlayerType(SOSGame.Turn.PL1));
    assertEquals(SOSGame.PlayerType.COMPUTER, Game.getPlayerType(SOSGame.Turn.PL2));
  }

  @Test
  void testComputerMove(){
    SOSGame Game = new SOSGame(3,0,SOSGame.PlayerType.HUMAN,SOSGame.PlayerType.HUMAN);
    int x = Game.computerChooseX();
    int y = Game.computerChooseY();
    String token = Game.computerChooseToken();

    Game.makeMove(x,y,token);
    assertFalse(Game.isCellEmpty(x,y));
    assertEquals(token, Game.getCellContent(x,y));
  }
  @Test
  void test1ComputerPlayerSimple(){
    SOSGame Game = new SOSGame(3,0,SOSGame.PlayerType.HUMAN,SOSGame.PlayerType.COMPUTER);
  }

  @Test
  void test1ComputerPlayerGeneral(){
    SOSGame Game = new SOSGame(3,1,SOSGame.PlayerType.HUMAN,SOSGame.PlayerType.COMPUTER);
  }

  @Test
  void test2ComputerPlayersSimple(){
    SOSGame Game = new SOSGame(3,0,SOSGame.PlayerType.COMPUTER,SOSGame.PlayerType.COMPUTER);
  }

  @Test
  void test2ComputerPlayersGeneral(){
    SOSGame Game = new SOSGame(3,1,SOSGame.PlayerType.COMPUTER,SOSGame.PlayerType.COMPUTER);
  }
}