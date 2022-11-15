package Product;

import static java.lang.Math.floor;
import java.util.ArrayList;
import java.util.Objects;

public class SOSGame{
  public enum Status {PLAYING, DRAW, P1_WIN, P2_WIN}
  Status gameStatus;
  public enum Mode {SIMPLE, GENERAL}
  Mode gameMode;
  public enum PlayerType {HUMAN, COMPUTER}
  private PlayerType player1Type = PlayerType.HUMAN;
  private PlayerType player2Type = PlayerType.HUMAN;
  private int p1GeneralGameScore;
  private int p2GeneralGameScore;
  public enum Turn {PL1, PL2}
  Turn currentTurn = Turn.PL1;
  private ArrayList<ArrayList<SOSCell>> gameBoard = new ArrayList<>();
  private int unoccupiedCellsCount;
  private int boardSize;
  public <T> SOSGame(T size, int mode, PlayerType p1Type, PlayerType p2Type){
    resetGame(size, mode, p1Type, p2Type);
  }

  public <T> void resetGame(T size, int mode, PlayerType p1Type, PlayerType p2Type){
    p1GeneralGameScore = 0;
    p2GeneralGameScore = 0;

    //Destroy existing gameBoard
    for(int i = 0; i < gameBoard.size(); i++){
      for(int j = 0; j < gameBoard.get(i).size(); j++){
        gameBoard.get(i).clear();
      }
      gameBoard.clear();
    }

    setPlayerTurn(Turn.PL1);
    setPlayerTypes(p1Type,p2Type);
    setBoardSize(size);
    setUnoccupiedCellsCount(getBoardSize());
    setGameMode(mode);
    initBoard();
    setGameStatus(Status.PLAYING);
  }

  private <T> void setBoardSize(T size){
    switch (size.getClass().getSimpleName()) {
      case "Integer" -> {
        int intSize = (int) size;
        if (intSize < 3) {
          boardSize = 3;
        } else if (intSize > 10) {
          boardSize = 10;
        } else {
          boardSize = (int) size;
        }
      }
      case "Double", "Float" -> {
        double dblSize = (double) size;
        if (dblSize >= 3 && dblSize <= 11) {
          boardSize = (int) floor((double) size);
        } else {
          boardSize = 3;
        }
      }
      default -> boardSize = 3;
    }
  }

  public int getBoardSize(){
    return boardSize;
  }

  private void initBoard(){
    for(int i = 0; i < getBoardSize(); i++){
      gameBoard.add(new ArrayList<>());
      for(int j = 0; j < getBoardSize(); j++){
        gameBoard.get(i).add(new SOSCell());
      }
    }
  }

  private void setGameStatus(Status newStatus){
    switch(newStatus){
      case P1_WIN -> gameStatus = Status.P1_WIN;
      case P2_WIN -> gameStatus = Status.P2_WIN;
      case DRAW -> gameStatus = Status.DRAW;
      default -> gameStatus = Status.PLAYING;
    }
  }

  public Status getGameStatus(){ return gameStatus; }

  private void setGameMode(int mode){
    if(mode == 1){
      gameMode = Mode.GENERAL;
    }
    else{
      gameMode = Mode.SIMPLE;
    }
  }

  public String getGameMode(){
    if(gameMode == Mode.SIMPLE){
      return "Simple";
    }
    return "General";
  }

  private void setPlayerTypes(PlayerType p1Type, PlayerType p2Type){
    player1Type = p1Type;
    player2Type = p2Type;
  }

  public PlayerType getPlayerType(Turn playerTurn){
    return switch (playerTurn) {
      case PL1 -> player1Type;
      case PL2 -> player2Type;
    };
  }

  public boolean isCellEmpty(int row, int col){
    return gameBoard.get(row).get(col).isEmpty();
  }

  public String getCellContent(int row, int col){
    return gameBoard.get(row).get(col).getContent();
  }

  public int makeMove(int row, int col, String moveContent){
    if(isMoveValid(row, col)){
      gameBoard.get(row).get(col).setContent(moveContent);
      updateUnoccupiedCellsCount();

      if(getPlayerTurn() == Turn.PL1){
        gameBoard.get(row).get(col).setCellOwner(0);
      }
      else{
        gameBoard.get(row).get(col).setCellOwner(1);
      }

      if(isGameOver(row,col,moveContent,gameMode)){
        //return 1 lets the GUI know the game is over, so it can display game over notification
        return 1;
      }
      else{
        //Game is not over; change turn and continue
        changePlayerTurn();
        return 0;
      }
    }
    return -1;
  }

  private int computerChooseX(){
    return (int)(Math.random() * boardSize);
  }

  private int computerChooseY(){
    return (int)(Math.random() * boardSize);
  }

  private String computerChooseToken(){
    int randInt = (int)(Math.random() * 2);

    if(randInt == 0){
      return "S";
    }

    return"O";
  }

  public int[] computerMove(){
    int[] moveInformation = {-1,-1,-1};//{x-coordinate, y-coordinate, makeMove return}
    int xCoord = computerChooseX();
    int yCoord = computerChooseY();
    String moveToken = computerChooseToken();

    while(!gameBoard.get(xCoord).get(yCoord).isEmpty()){
      xCoord = computerChooseX();
      yCoord = computerChooseY();
    }

    moveInformation[0] = xCoord;
    moveInformation[1] = yCoord;
    moveInformation[2] = makeMove(xCoord,yCoord,moveToken);

    return moveInformation;
  }

  private boolean isMoveValid(int row, int col){
    if((row >= 0 && col >= 0) && (row < getBoardSize() && col < getBoardSize())){
      return isCellEmpty(row, col);
    }
    return false;
  }

  private void setPlayerTurn(Turn playerTurn){
    currentTurn = playerTurn;
  }

  private void changePlayerTurn(){
    if(getPlayerTurn() == Turn.PL1){
      currentTurn = Turn.PL2;
      return;
    }
    currentTurn = Turn.PL1;
  }

  public Turn getPlayerTurn(){
    return currentTurn;
  }

  private void updateGeneralGameScore(Turn playerTurn){
    switch(playerTurn){
      case PL1 -> p1GeneralGameScore++;
      case PL2 -> p2GeneralGameScore++;
    }
  }

  public int getGeneralGameScore(Turn playerTurn){
    return switch (playerTurn) {
      case PL1 -> p1GeneralGameScore;
      case PL2 -> p2GeneralGameScore;
    };
  }

  private boolean isSOSFormed(int row, int col, String moveContent){
    //Check if an SOS was formed by the current move
    int minRowIndex;
    int maxRowIndex;
    int minColIndex;
    int maxColIndex;
    boolean isFormed = false;

    switch (moveContent) {
      case "S" -> {//Check row+/-2 cells subarray on all sides of the cell
        minRowIndex = row - 2;
        maxRowIndex = row + 2;
        minColIndex = col - 2;
        maxColIndex = col + 2;

        //Set subarray min/max row indices
        if ((row - 2) < 0) {
          minRowIndex = row;
        }
        if ((row + 2) > getBoardSize() - 1) {
          maxRowIndex = row;
        }

        //set subarray min/max column indices
        if ((col - 2) < 0) {
          minColIndex = col;
        }
        if ((col + 2) > getBoardSize() - 1) {
          maxColIndex = col;
        }

        //Starting and ending indices are adjusted in the loops, so it doesn't iterate over cells unnecessarily
        for (int i = minRowIndex; i <= maxRowIndex; i++) {
          for (int j = minColIndex; j <= maxColIndex; j++) {
            //Check surrounding cells for "O" content, then check 1 cell beyond for "S" content

            if (Objects.equals(gameBoard.get(i).get(j).getContent(), "O")) {
              //Create variables for determining which extended cell to check
              int extendedRowIndex = i + (i - row);
              int extendedColIndex = j + (j - col);

              if ((extendedRowIndex >= minRowIndex) && (extendedRowIndex <= maxRowIndex) && (
                  extendedColIndex >= minColIndex) && (extendedColIndex <= maxColIndex)
                  && Objects.equals(
                  gameBoard.get(extendedRowIndex).get(extendedColIndex).getContent(), "S")) {
                gameBoard.get(row).get(col).setBeginIndexOfSOS(row, col);
                gameBoard.get(row).get(col).setEndIndexOfSOS(extendedRowIndex, extendedColIndex);
                updateGeneralGameScore(getPlayerTurn());
                isFormed = true;
              }
            }
          }
        }
      }
      case "O" -> {//Check row+/-1 cells subarray around the cell
        minRowIndex = row - 1;
        minColIndex = col - 1;
        maxRowIndex = row + 1;
        maxColIndex = col + 1;


        //If an "O" is placed in a corner, it is impossible to have formed an SOS
        if (((row == 0) && (col == 0)) || (row == 0) && (col == getBoardSize() - 1) || (
            (row == getBoardSize() - 1) && (col == 0)) || ((row == getBoardSize() - 1) && (col
            == getBoardSize() - 1))) {
          return false;
        }

        //Set valid indices for sub rows
        if ((row - 1) < 0) {
          minRowIndex = row;
        }

        //Set valid indices for sub columns
        if ((col - 1) < 0) {
          minColIndex = col;
        } else if ((col + 1) > getBoardSize() - 1) {
          maxColIndex = col;
        }

        for (int i = minRowIndex; i <= row; i++) {
          for (int j = minColIndex; j <= maxColIndex; j++) {//Check surrounding cells for "S" content
            if (Objects.equals(gameBoard.get(i).get(j).getContent(), "S")) {
              int oppCellRow = 0;
              int oppCellCol = 0;

              switch (row - i) {//Determine cell row to check
                case -1:
                  if ((row - 1) > 0) {
                    oppCellRow = row - 1;
                  }
                  break;
                case 0:
                  oppCellRow = row;
                  break;
                case 1:
                  if ((row + 1) >= getBoardSize()) {
                    oppCellRow = getBoardSize() - 1;
                  } else {
                    oppCellRow = row + 1;
                  }
                  break;
                default:
                  break;
              }

              switch (col - j) {//Determine cell column to check
                case -1:
                  if ((col - 1) > 0) {
                    oppCellCol = col - 1;
                  }
                  break;
                case 0:
                  oppCellCol = col;
                  break;
                case 1:
                  if ((col + 1) >= getBoardSize()) {
                    oppCellCol = getBoardSize() - 1;
                  } else {
                    oppCellCol = col + 1;
                  }
                  break;
                default:
                  break;
              }

              if (Objects.equals(gameBoard.get(oppCellRow).get(oppCellCol).getContent(), "S")) {
                gameBoard.get(row).get(col).setBeginIndexOfSOS(i, j);
                gameBoard.get(row).get(col).setEndIndexOfSOS(oppCellRow, oppCellCol);
                updateGeneralGameScore(getPlayerTurn());
                isFormed = true;
              }
            }
          }
          maxColIndex -= 2;
        }
      }
    }
    return isFormed;
  }

  private boolean isGameOver(int row, int col, String moveContent, Mode gameMode){
    boolean isSOS = isSOSFormed(row,col,moveContent);

    if(isSOS && (gameMode == Mode.SIMPLE)){
      switch (getPlayerTurn()) {
        case PL1 -> setGameStatus(Status.P1_WIN);
        case PL2 -> setGameStatus(Status.P2_WIN);
      }
      return true;
    }
    else if(!isSOS && (Objects.equals(getGameMode(), "Simple"))){
      if(getUnoccupiedCellsCount() == 0){
        setGameStatus(Status.DRAW);
        return true;
      }
    }

    //Checks for General game over and appropriate status update
    if(getUnoccupiedCellsCount() == 0){
      if(getGeneralGameScore(Turn.PL1) > getGeneralGameScore(Turn.PL2)){
        setGameStatus(Status.P1_WIN);
        return true;
      }
      else if(getGeneralGameScore(Turn.PL2) > getGeneralGameScore(Turn.PL1)){
        setGameStatus(Status.P2_WIN);
        return true;
      }
      else{
        setGameStatus(Status.DRAW);
        return true;
      }
    }
    return false;
  }

  private void setUnoccupiedCellsCount(int boardSize){
    unoccupiedCellsCount = boardSize*boardSize;
  }

  private int getUnoccupiedCellsCount(){
    return unoccupiedCellsCount;
  }

  private void updateUnoccupiedCellsCount(){
    unoccupiedCellsCount--;
  }

  public int getCellOwnerID(int row, int col){
    return gameBoard.get(row).get(col).getCellOwner();
  }

  public int getBeginRowIndex(int row, int col){
    return gameBoard.get(row).get(col).getBeginRowIndex();
  }

  public int getEndRowIndex(int row, int col){
    return gameBoard.get(row).get(col).getEndRowIndex();
  }

  public int getBeginColIndex(int row, int col){
    return gameBoard.get(row).get(col).getBeginColIndex();
  }

  public int getEndColIndex(int row, int col){
    return gameBoard.get(row).get(col).getEndColIndex();
  }
}