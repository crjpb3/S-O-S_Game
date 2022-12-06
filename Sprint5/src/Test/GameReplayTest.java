package Test;

import static org.junit.jupiter.api.Assertions.*;

import Product.SOSGame;
import Product.SOSGame.PlayerType;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

class GameReplayTest {

  @Test
  void recordMoveTest() {
    SOSGame Game = new SOSGame(3, 0, true, PlayerType.HUMAN, PlayerType.HUMAN);
    int[] moveRow = {0, 0, 0, 1, 1, 1, 2, 2, 2};
    int[] moveCol = {0, 1, 2, 0, 1, 2, 0, 1, 2};
    String[] moveChar = {"O", "S", "O", "S", "S", "O", "S", "O", "S"};

    Game.makeMove(0, 0, "O");

    Game.makeMove(0, 1, "S");
    Game.makeMove(0, 2, "O");
    Game.makeMove(1, 0, "S");
    Game.makeMove(1, 1, "S");
    Game.makeMove(1, 2, "O");
    Game.makeMove(2, 0, "S");
    Game.makeMove(2, 1, "O");
    Game.makeMove(2, 2, "S");

    try {
      File moveFile = new File("Move Log.txt");
      if (moveFile.createNewFile()) {
        Scanner fileReader = new Scanner(moveFile);

        int i = 0;
        while (fileReader.hasNext()) {
          assertEquals(String.valueOf(moveRow[i]), fileReader.next());
          assertEquals(String.valueOf(moveCol[i]), fileReader.next());
          assertEquals(moveChar[i], fileReader.next());
        }
        fileReader.close();
      }
    }
    catch(IOException e){
        System.out.println("An IO Error was encountered");
        e.printStackTrace();
    }
  }

    @Test
  void ReplayRecordedGameTest(){
    SOSGame Game = new SOSGame(3,0, true, PlayerType.HUMAN, PlayerType.HUMAN);
    try{
      File moveLogFile = new File("Move Log.txt");
      FileWriter moveLogger = new FileWriter(moveLogFile);
      //set up recorded file
    }
    catch(IOException e){
      System.out.println("An IO Error was encountered");
      e.printStackTrace();
    }
  }
}