package Product;

import com.sun.security.jgss.GSSUtil;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
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

  private void popMoveLogLine(){
    StringBuilder remainingMoves = new StringBuilder();
    FileWriter remainingMovesTransfer;//new FileWriter for overwrite
    int itemCount = 1;
    moveReader.nextLine();

    while(moveReader.hasNext()){
      if(itemCount == 3){
        remainingMoves.append(moveReader.next());
        remainingMoves.append("\n");
        itemCount = 1;
      }
      else{
        remainingMoves.append(moveReader.next());
        remainingMoves.append(" ");
        itemCount++;
      }
    }
    moveReader.close();

    try{
      remainingMovesTransfer = new FileWriter(moveLogFile);
      remainingMovesTransfer.write(remainingMoves.toString());
      remainingMovesTransfer.close();
    }
    catch(IOException e){
      System.out.println("An IO error occurred while writing remaining moves");
      e.printStackTrace();
    }
  }

  public int[] getRecordedMove(){
    int[] moveInfo = {-1,-1,0};
    String moveToken = "";
    try{
      moveReader = new Scanner(moveLogFile);
    }
    catch(IOException e){
      System.out.println("An IO error occurred while reading recorded move from file");
      e.printStackTrace();
    }

    moveInfo[0] = Integer.parseInt(moveReader.next());
    moveInfo[1] = Integer.parseInt(moveReader.next());
    moveToken = moveReader.next();

    if(Objects.equals(moveToken, "S")){
      moveInfo[2] = 1;
    }

    popMoveLogLine();
    return moveInfo;
  }
}
