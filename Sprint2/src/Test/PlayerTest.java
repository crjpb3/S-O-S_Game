package Test;

import static org.junit.jupiter.api.Assertions.*;

import Product.Player;
import Product.SOSGame;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PlayerTest {
  static Player TestPlayer;
  int[] defaultPlayerMove = {-1,-1};
  @BeforeAll
  static void setUp(){
    TestPlayer = new Player(SOSGame.PlayerType.COMPUTER);
  }

  @Test
  void testPlayerScoring() {
    //Tests updateScore(), getScore(), and resetScore()
    TestPlayer.updateScore();
    assertEquals(1,TestPlayer.getScore());
    TestPlayer.resetScore();
    assertEquals(0,TestPlayer.getScore());
  }

  @Test
  void testGetPlayerType() {
    Player ComputerPlayer = new Player(SOSGame.PlayerType.COMPUTER);
    Player HumanPlayer = new Player(SOSGame.PlayerType.HUMAN);

    assertEquals(SOSGame.PlayerType.COMPUTER, ComputerPlayer.getPlayerType());
    assertEquals(SOSGame.PlayerType.HUMAN, HumanPlayer.getPlayerType());
  }

  @Test
  void testPreviousMove() {
    int[] newPlayerMove = {2,0};

    assertEquals(true, Arrays.equals(defaultPlayerMove, TestPlayer.getPreviousMove()));

    TestPlayer.setPreviousMove(newPlayerMove);
    assertEquals(true,Arrays.equals(newPlayerMove,TestPlayer.getPreviousMove()));
  }
}