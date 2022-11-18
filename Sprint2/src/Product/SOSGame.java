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
  public enum Turn {PL1, PL2}
  Turn currentTurn = Turn.PL1;
  Player Player1;
  Player Player2;
  private ArrayList<ArrayList<SOSCell>> gameBoard = new ArrayList<>();
  private int unoccupiedCellsCount;
  private int boardSize;
  public <T> SOSGame(T size, int mode, PlayerType p1Type, PlayerType p2Type){
    resetGame(size, mode, p1Type, p2Type);
  }

  public <T> void resetGame(T size, int mode, PlayerType p1Type, PlayerType p2Type){
    Player1 = new Player(p1Type);
    Player2 = new Player(p2Type);
    Player1.resetScore();
    Player2.resetScore();

    //Destroy existing gameBoard
    for(int i = 0; i < gameBoard.size(); i++){
      for(int j = 0; j < gameBoard.get(i).size(); j++){
        gameBoard.get(i).clear();
      }
      gameBoard.clear();
    }

    setPlayerTurn(Turn.PL1);
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

  public PlayerType getPlayerType(Turn playerTurn){
    return switch (playerTurn) {
      case PL1 -> Player1.getPlayerType();
      case PL2 -> Player2.getPlayerType();
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
      int[] playerMove = {row,col};
      gameBoard.get(row).get(col).setContent(moveContent);
      updateUnoccupiedCellsCount();

      if(getPlayerTurn() == Turn.PL1){
        Player1.setPreviousMove(playerMove);
        gameBoard.get(row).get(col).setCellOwner(0);
      }
      else{
        Player2.setPreviousMove(playerMove);
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

  private String computerChooseToken(){
    int randInt = (int)(Math.random() * 2);

    if(randInt == 0){
      return "S";
    }

    return"O";
  }

  public int[] computerMove(){
    int[] moveInformation = {-1,-1,-1};//{x-coordinate, y-coordinate, makeMove return int}
    int decisionInt = ((int)(Math.random() * 99) % 3);
    String compToken = computerChooseToken();
    int[] opponentPrevMoveCoords = {(int)(Math.random() * getBoardSize()),(int)(Math.random() * getBoardSize())};
    String opponentPrevToken = "";

    switch (getPlayerTurn()) {
      case PL1 -> {
        opponentPrevMoveCoords = Player2.getPreviousMove();

        if((opponentPrevMoveCoords[0] == -1) || (opponentPrevMoveCoords[1] == -1)){
          //Make a random move
          moveInformation[0] = (int) (Math.random() * getBoardSize());
          moveInformation[1] = (int) (Math.random() * getBoardSize());
          while (!gameBoard.get(moveInformation[0]).get(moveInformation[1]).isEmpty()) {
            moveInformation[0] = (int) (Math.random() * getBoardSize());
            moveInformation[1] = (int) (Math.random() * getBoardSize());
          }
          moveInformation[2] = makeMove(moveInformation[0], moveInformation[1], compToken);
          return moveInformation;
        }

        opponentPrevToken = gameBoard.get(opponentPrevMoveCoords[0]).get(opponentPrevMoveCoords[1]).getContent();
      }
      case PL2 -> {
        opponentPrevMoveCoords = Player1.getPreviousMove();

        if((opponentPrevMoveCoords[0] == -1) || (opponentPrevMoveCoords[1] == -1)){
          //Make a random move
          moveInformation[0] = (int) (Math.random() * getBoardSize());
          moveInformation[1] = (int) (Math.random() * getBoardSize());
          while (!gameBoard.get(moveInformation[0]).get(moveInformation[1]).isEmpty()) {
            moveInformation[0] = (int) (Math.random() * getBoardSize());
            moveInformation[1] = (int) (Math.random() * getBoardSize());
          }
          moveInformation[2] = makeMove(moveInformation[0], moveInformation[1], compToken);
          return moveInformation;
        }

        opponentPrevToken = gameBoard.get(opponentPrevMoveCoords[0]).get(opponentPrevMoveCoords[1]).getContent();
      }
    }

    switch (decisionInt) {
      case 0 -> {
        moveInformation = compCompleteSOSMove(opponentPrevMoveCoords, opponentPrevToken);

        //Check token information returned from compCompleteSOSMove
        if (moveInformation[2] == 0) {
          compToken = "O";
        }
        else {
          compToken = "S";
        }
        moveInformation[2] = makeMove(moveInformation[0], moveInformation[1], compToken);
      }
      case 1, 2 -> {
        //Make a random move
        moveInformation[0] = (int) (Math.random() * getBoardSize());
        moveInformation[1] = (int) (Math.random() * getBoardSize());
        while (!gameBoard.get(moveInformation[0]).get(moveInformation[1]).isEmpty()) {
          moveInformation[0] = (int) (Math.random() * getBoardSize());
          moveInformation[1] = (int) (Math.random() * getBoardSize());
        }
        moveInformation[2] = makeMove(moveInformation[0], moveInformation[1], compToken);
      }
    }
    return moveInformation;
  }
  
  private int[] compCompleteSOSMove(int[] moveToCheck, String placedToken){
    int[] prevMove = moveToCheck;
    int[] moveToMake = {-1,-1,0};//mmoveToMake[2] indicates which token to place, zero for "O" and 1 for "S"
    String prevToken = placedToken;
    int minRowIndex;
    int maxRowIndex;
    int minColIndex;
    int maxColIndex;

    switch (prevToken) {
      case "S" -> {
        //Set up subarray
        minRowIndex = prevMove[0] - 1;
        maxRowIndex = prevMove[0] + 1;
        minColIndex = prevMove[1] - 1;
        maxColIndex = prevMove[1] + 1;

        //Adjust bounds if token placed near edge of board
        if ((prevMove[0] - 2) < 0) {
          minRowIndex = prevMove[0];
        }
        if ((prevMove[0] + 2) > getBoardSize() - 1) {
          maxRowIndex = prevMove[0];
        }
        if ((prevMove[1] - 2) < 0) {
          minColIndex = prevMove[1];
        }
        if ((prevMove[1] + 2) > getBoardSize() - 1) {
          maxColIndex = prevMove[1];
        }
        for (int i = minRowIndex; i <= maxRowIndex; i++) {
          for (int j = minColIndex; j <= maxColIndex; j++) {
            if ((i == prevMove[0]) && (j == prevMove[1])) {
              continue;
            }

            //Calculate extended cell to check
            int extendedRowIndex = i + (i - prevMove[0]);
            int extendedColIndex = j + (j - prevMove[1]);

            switch (gameBoard.get(i).get(j).getContent()) {
              case "":
                if (Objects.equals("S",
                    gameBoard.get(extendedRowIndex).get(extendedColIndex).getContent())) {
                  moveToMake[0] = i;
                  moveToMake[1] = j;
                  moveToMake[2] = 0;
                }
                break;
              case "O":
                if (Objects.equals("",
                    gameBoard.get(extendedRowIndex).get(extendedColIndex).getContent())) {
                  moveToMake[0] = extendedRowIndex;
                  moveToMake[1] = extendedColIndex;
                  moveToMake[2] = 1;
                }
                break;
              default:
                //Make a random move
                moveToMake[0] = (int) (Math.random() * getBoardSize());
                moveToMake[1] = (int) (Math.random() * getBoardSize());
                String token = computerChooseToken();
                while (!gameBoard.get(moveToMake[0]).get(moveToMake[1]).isEmpty()) {
                  moveToMake[0] = (int) (Math.random() * getBoardSize());
                  moveToMake[1] = (int) (Math.random() * getBoardSize());
                }

                if(token.equals("S")){
                  moveToMake[2] = 1;
                }
                else{
                  moveToMake[2] = 0;
                }
                break;
            }
          }
        }
      }
      case "O" -> {
        //Set up subarray
        minRowIndex = prevMove[0] - 1;
        maxRowIndex = prevMove[0] + 1;
        minColIndex = prevMove[1] - 1;
        maxColIndex = prevMove[1] + 1;
        int oppRowIndex = prevMove[0];
        int oppColIndex = prevMove[1];

        //Adjust bounds if token placed at edge of board
        if (minRowIndex < 0) {
          minRowIndex = 0;
        }
        if (minColIndex < 0) {
          minColIndex = 0;
        }
        if (maxRowIndex >= getBoardSize()) {
          maxRowIndex = (getBoardSize() - 1);
        }
        if (maxColIndex >= getBoardSize()) {
          maxColIndex = (getBoardSize() - 1);
        }
        
        for (int i = minRowIndex; i <= maxRowIndex; i++) {
          for (int j = minColIndex; j <= maxColIndex; j++) {
            if ((i == prevMove[0]) && (j == prevMove[1])) {
              continue;
            }

            //Set opposite indices to check
            switch (i - prevMove[0]) {
              case -1 -> oppRowIndex = i + 2;
              case 0 -> oppRowIndex = i;
              case 1 -> oppRowIndex = i - 2;
            }
            switch (j - prevMove[1]) {
              case -1 -> oppColIndex = j + 2;
              case 0 -> oppColIndex = j;
              case 1 -> oppColIndex = j - 2;
            }

            if((oppRowIndex < 0) || (oppRowIndex >= getBoardSize()) || (oppColIndex < 0) || (oppColIndex >= getBoardSize())){
              continue;
            }

            if (Objects.equals("S", gameBoard.get(i).get(j).getContent()) && Objects.equals("",
                gameBoard.get(oppRowIndex).get(oppColIndex).getContent())) {
              moveToMake[0] = oppRowIndex;
              moveToMake[1] = oppColIndex;
              moveToMake[2] = 1;
            }
            else if (Objects.equals("S", gameBoard.get(oppRowIndex).get(oppColIndex).getContent())
                && Objects.equals("", gameBoard.get(i).get(j).getContent())) {
              moveToMake[0] = i;
              moveToMake[1] = j;
              moveToMake[2] = 1;
            }
            else{
              //Make a random move
              moveToMake[0] = (int) (Math.random() * getBoardSize());
              moveToMake[1] = (int) (Math.random() * getBoardSize());
              String token = computerChooseToken();

              while (!gameBoard.get(moveToMake[0]).get(moveToMake[1]).isEmpty()) {
                moveToMake[0] = (int) (Math.random() * getBoardSize());
                moveToMake[1] = (int) (Math.random() * getBoardSize());
              }

              if(token.equals("S")){
                moveToMake[2] = 1;
              }
              else{
                moveToMake[2] = 0;
              }
            }
          }
        }
      }
    }
    return moveToMake;
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
      case PL1 -> Player1.updateScore();
      case PL2 -> Player2.updateScore();
    }
  }

  public int getGeneralGameScore(Turn playerTurn){
    return switch (playerTurn) {
      case PL1 -> Player1.getScore();
      case PL2 -> Player2.getScore();
    };
  }

//ISSUE: SOS not in a straight line can sometimes form, must be an error while checking for SOS, need to investigate
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