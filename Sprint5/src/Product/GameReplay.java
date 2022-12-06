package Product;

import com.sun.security.jgss.GSSUtil;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class GameReplay {
  File moveLogFile = new File("Move Log.txt");
  FileWriter moveRecorder;
  Scanner moveReader;

  public static void resetMoveLogFile(){
    try{
      File logFile = new File("Move Log.txt");
      FileWriter resetWriter = new FileWriter(logFile);
      resetWriter.write("");
      resetWriter.close();
    }
    catch(IOException e){
      System.out.println("An IO error occurred while trying to reset the \"Move Log.txt\" file");
      e.printStackTrace();
    }
  }

  public GameReplay(){
    try{
      moveLogFile.createNewFile();
    }
    catch(IOException e){
      System.out.println("An IO error occurred while trying to create the \"Move Log.txt\" file");
      e.printStackTrace();
    }

//    try{
//      moveReader = new Scanner(moveLogFile);
//    }
//    catch(IOException e){
//      System.out.println("An IO error occurred while trying to set up the File Reader");
//      e.printStackTrace();
//    }
  }

  public void recordMove(Player currentPlayer, ArrayList<ArrayList<SOSCell>> currentGameBoard){
    int moveRow = currentPlayer.getPreviousMove()[0];
    int moveCol = currentPlayer.getPreviousMove()[1];
    String moveToken = currentGameBoard.get(moveRow).get(moveCol).getContent();

    try{
      moveRecorder = new FileWriter(moveLogFile, true);
    }
    catch(IOException e){
      System.out.println("An IO error occurred while trying to set up the File Writer");
      e.printStackTrace();
    }

    try{
      moveRecorder.write(String.valueOf(moveRow));
      moveRecorder.write(" ");
      moveRecorder.write(String.valueOf(moveCol));
      moveRecorder.write(" ");
      moveRecorder.write(moveToken);
      moveRecorder.write("\n");
      moveRecorder.close();
    }
    catch(IOException e){
      System.out.println("An IO error occurred while trying to record the game");
      e.printStackTrace();
    }
  }

  public void replayRecordedGame(){

  }
}
