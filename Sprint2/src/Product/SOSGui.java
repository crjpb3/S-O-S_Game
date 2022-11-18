package Product;

import static java.lang.Math.abs;

import Product.SOSGame.PlayerType;
import Product.SOSGame.Status;
import Product.SOSGame.Turn;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class SOSGui extends JFrame implements ActionListener, MouseListener {
  SOSGame currentGame;
  private ArrayList<ArrayList<JLabel>> boardCellsList;
  private SOSGame.Turn playerTurn;
  private String p1MoveChar = "S";
  private String p2MoveChar = "S";
  private PlayerType p1Type = PlayerType.HUMAN;
  private PlayerType p2Type = PlayerType.HUMAN;
  private int gameModeSelection;

  //GUI components declarations
  private Font cellFont;

  private JPanel Board;
  private JPanel Left;
  private JPanel Right;
  private JPanel Top;
  private JPanel Bottom;
  private JButton startButton;
  private JButton resetButton;

  private JLabel gameModeLabel;
  private JRadioButton simpleGameModeOption;
  private JRadioButton generalGameModeOption;
  private ButtonGroup gameModeGroup;

  private JLabel player1SectionLabel;
  private JRadioButton player1MoveS;
  private JRadioButton player1MoveO;
  private ButtonGroup moveGroupPlayer1;
  private JRadioButton player1HumanType;
  private JRadioButton player1ComputerType;
  private ButtonGroup typeGroupPlayer1;


  private JLabel player2SectionLabel;
  private JRadioButton player2MoveS;
  private JRadioButton player2MoveO;
  private ButtonGroup moveGroupPlayer2;
  private JRadioButton player2HumanType;
  private JRadioButton player2ComputerType;
  private ButtonGroup typeGroupPlayer2;

  private JLabel boardSizeLabel;
  private JTextField boardSizeInput;
  private JLabel currentTurnLabel;

  //Game Over option pane
  JOptionPane gameOver = new JOptionPane();

  public SOSGui(){
    boardCellsList = new ArrayList<>();
    initGUI();
    resetBoard(3);
    createPreviewBoard();//One time function to display board/game representation; "main screen"
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if(e.getSource() == startButton) {
      startGame();
    }
    else if(e.getSource() == simpleGameModeOption){
      gameModeSelection = 0;
    }
    else if (e.getSource() == generalGameModeOption){
      gameModeSelection = 1;
    }
    else if(e.getSource() == player1HumanType){
      p1Type = PlayerType.HUMAN;
    }
    else if(e.getSource() == player1ComputerType){
      p1Type = PlayerType.COMPUTER;
    }
    else if(e.getSource() == player2HumanType){
      p2Type = PlayerType.HUMAN;
    }
    else if(e.getSource() == player2ComputerType){
      p2Type = PlayerType.COMPUTER;
    }
    else if(e.getSource() == player1MoveS){
      p1MoveChar = "S";
    }
    else if(e.getSource() == player2MoveS){
      p2MoveChar = "S";
    }
    else if(e.getSource() == player1MoveO){
      p1MoveChar = "O";
    }
    else if(e.getSource() == player2MoveO){
      p2MoveChar = "O";
    }
    else if(e.getSource() == resetButton){
      startGame();
      resetBoard(currentGame.getBoardSize());
    }
  }
  @Override
  public void mouseClicked(MouseEvent e) {
    //Making a move
    if((e.getSource() == currentTurnLabel) && (currentGame.getPlayerType(playerTurn) == PlayerType.COMPUTER)){
      int[] moveInformation = currentGame.computerMove();
      int moveX = moveInformation[0];
      int moveY = moveInformation[1];
      String moveToken = currentGame.getCellContent(moveX,moveY);
      int moveInt = moveInformation[2];

      if(moveInt == 0){
        boardCellsList.get(moveX).get(moveY).setText(moveToken);
        drawSOSLine(moveX,moveY);
      }
      else if(moveInt == 1){
        boardCellsList.get(moveX).get(moveY).setText(moveToken);
        drawSOSLine(moveX,moveY);
        handleGameOVer();
      }
      changePlayerTurn();
      return;
    }

    for(int i = 0; i < boardCellsList.size(); i++){
      for(int j = 0; j < boardCellsList.get(i).size(); j++){
        if(e.getSource() == boardCellsList.get(i).get(j)){
          int moveIntReturn;
          if(currentGame.getPlayerTurn() == SOSGame.Turn.PL1) {
            moveIntReturn = currentGame.makeMove(i, j, p1MoveChar);
            if(moveIntReturn == 0){
              boardCellsList.get(i).get(j).setText(p1MoveChar);
              drawSOSLine(i,j);
            }
            else if(moveIntReturn == 1){
              boardCellsList.get(i).get(j).setText(p1MoveChar);
              drawSOSLine(i,j);
              handleGameOVer();
            }
          }
          else{
            moveIntReturn = currentGame.makeMove(i, j, p2MoveChar);
            if(moveIntReturn == 0){
              boardCellsList.get(i).get(j).setText(p2MoveChar);
              drawSOSLine(i,j);
            }
            else if(moveIntReturn == 1){
              boardCellsList.get(i).get(j).setText(p2MoveChar);
              drawSOSLine(i,j);
              handleGameOVer();
            }
          }
        }
      }
    }
    changePlayerTurn();
  }

  private void panelsComponentsSetup(){
    //Top panel components
    startButton = new JButton("Start");
    startButton.addActionListener(this);
    resetButton = new JButton("New Game");
    resetButton.addActionListener(this);
    gameModeLabel = new JLabel("Game Mode:");
    simpleGameModeOption = new JRadioButton("Simple", true);
    simpleGameModeOption.addActionListener(this);
    generalGameModeOption = new JRadioButton("General                    ", false);
    generalGameModeOption.addActionListener(this);
    gameModeGroup = new ButtonGroup();
    gameModeGroup.add(simpleGameModeOption);
    gameModeGroup.add(generalGameModeOption);

    boardSizeLabel = new JLabel("Board Size:");
    boardSizeInput = new JTextField("3",2);
    
    //Left panel Components
    player1SectionLabel = new JLabel("<html><font color=blue>Player 1</font></html>");
    player1HumanType = new JRadioButton("Human", true);
    player1HumanType.addActionListener(this);
    player1MoveS = new JRadioButton("S", true);
    player1MoveS.addActionListener(this);
    player1MoveO = new JRadioButton("O", false);
    player1MoveO.addActionListener(this);
    player1ComputerType = new JRadioButton("Computer", false);
    player1ComputerType.addActionListener(this);

    typeGroupPlayer1 = new ButtonGroup();
    typeGroupPlayer1.add(player1HumanType);
    typeGroupPlayer1.add(player1ComputerType);

    moveGroupPlayer1 = new ButtonGroup();
    moveGroupPlayer1.add(player1MoveS);
    moveGroupPlayer1.add(player1MoveO);

    player1SectionLabel.setHorizontalAlignment(JLabel.CENTER);
    player1HumanType.setHorizontalAlignment(JLabel.CENTER);
    player1MoveS.setHorizontalAlignment(JRadioButton.CENTER);
    player1MoveO.setHorizontalAlignment(JRadioButton.CENTER);
    player1ComputerType.setHorizontalAlignment(JLabel.CENTER);
    
    //Right panel components
    player2SectionLabel = new JLabel("<html><font color=red>Player 2</font></html>");
    player2HumanType = new JRadioButton("Human", true);
    player2HumanType.addActionListener(this);
    player2MoveS = new JRadioButton("S", true);
    player2MoveS.addActionListener(this);
    player2MoveO = new JRadioButton("O", false);
    player2MoveO.addActionListener(this);
    player2ComputerType = new JRadioButton("Computer", false);
    player2ComputerType.addActionListener(this);

    typeGroupPlayer2 = new ButtonGroup();
    typeGroupPlayer2.add(player2HumanType);
    typeGroupPlayer2.add(player2ComputerType);

    moveGroupPlayer2 = new ButtonGroup();
    moveGroupPlayer2.add(player2MoveS);
    moveGroupPlayer2.add(player2MoveO);

    player2SectionLabel.setHorizontalAlignment(JLabel.CENTER);
    player2HumanType.setHorizontalAlignment(JLabel.CENTER);
    player2MoveS.setHorizontalAlignment(JRadioButton.CENTER);
    player2MoveO.setHorizontalAlignment(JRadioButton.CENTER);
    player2ComputerType.setHorizontalAlignment(JLabel.CENTER);
    
    //Bottom panel components
    Font turnLabelFont = new Font(null, Font.PLAIN, 25);
    currentTurnLabel = new JLabel("<html>Turn: <font color=blue>Player 1</font></html>");
    currentTurnLabel.setFont(turnLabelFont);
    currentTurnLabel.addMouseListener(this);
  }

  private void panelsSetup(){
    Board = new JPanel();
    Board.setPreferredSize(new Dimension(100,100));

    Top = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 35));
    Top.setPreferredSize(new Dimension(800,100));
    Top.add(gameModeLabel);
    Top.add(simpleGameModeOption);
    Top.add(generalGameModeOption);
    Top.add(boardSizeLabel);
    Top.add(boardSizeInput);
    Top.add(startButton);

    Left = new JPanel(new GridLayout(5,1));
    Left.setPreferredSize(new Dimension(100,700));
    Left.add(player1SectionLabel);
    Left.add(player1HumanType);
    Left.add(player1MoveS);
    Left.add(player1MoveO);
    Left.add(player1ComputerType);

    Right = new JPanel(new GridLayout(5,1));
    Right.setPreferredSize(new Dimension(100,700));
    Right.add(player2SectionLabel);
    Right.add(player2HumanType);
    Right.add(player2MoveS);
    Right.add(player2MoveO);
    Right.add(player2ComputerType);

    Bottom = new JPanel(new FlowLayout(FlowLayout.CENTER,0,35));
    Bottom.setPreferredSize(new Dimension(800,100));
  }

  private void frameSetup(){
    this.setTitle("SOS Game");
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setSize(800,800);
    this.setResizable(true);
    this.setVisible(true);
    this.setLayout(new BorderLayout());

    this.add(Board, BorderLayout.CENTER);
    this.add(Left, BorderLayout.WEST);
    this.add(Right, BorderLayout.EAST);
    this.add(Top, BorderLayout.NORTH);
    this.add(Bottom, BorderLayout.SOUTH);
  }

  private void initGUI(){
    panelsComponentsSetup();
    panelsSetup();
    frameSetup();
  }

  private void destroyBoard(){
    for(int i = 0; i < boardCellsList.size(); i++){
      for(int j = 0; j < boardCellsList.get(i).size(); j++){
        Board.remove(boardCellsList.get(i).get(j));
      }
    }

    for(int i = 0; i < boardCellsList.size(); i++){
      for(int j = 0; j < boardCellsList.get(i).size(); j++){
        boardCellsList.get(i).clear();
      }
      boardCellsList.clear();
    }
  }

  private void resetBoard(int boardSize){
    //Initialize cell list
    destroyBoard();
    Board.setLayout(new GridLayout(boardSize,boardSize, 0,0));
    cellFont = new Font(Font.SERIF, Font.BOLD, 50);

    for(int i = 0; i < boardSize; i++){
      boardCellsList.add(new ArrayList<>());
      for(int j = 0; j < boardSize; j++){
        boardCellsList.get(i).add(new JLabel("", SwingConstants.CENTER));
        boardCellsList.get(i).get(j).setOpaque(true);
        boardCellsList.get(i).get(j).setFont(cellFont);
        boardCellsList.get(i).get(j).setBorder(BorderFactory.createLineBorder(Color.black, 2));
        boardCellsList.get(i).get(j).addMouseListener(this);
      }
    }

    //Populate board panel
    for(int i = boardSize - 1; i >=0; i--){
      for(int j = boardSize - 1; j >= 0; j--){
        Board.add(boardCellsList.get(i).get(j), SwingConstants.CENTER);
      }
    }
    Board.updateUI();
  }

  private void startGame(){
    try {
      currentGame = new SOSGame(Integer.parseInt(boardSizeInput.getText()), gameModeSelection, p1Type, p2Type);
    }
    catch(NumberFormatException e1){
      try{
        currentGame = new SOSGame(Double.parseDouble(boardSizeInput.getText()), gameModeSelection, p1Type, p2Type);
      }
      catch(NumberFormatException e2){
       try{
         currentGame = new SOSGame(Float.parseFloat(boardSizeInput.getText()), gameModeSelection, p1Type, p2Type);
       }
       catch(NumberFormatException e3){
         currentGame = new SOSGame(boardSizeInput.getText(), gameModeSelection, p1Type, p2Type);
       }
      }
    }

    playerTurn = currentGame.getPlayerTurn();
    currentTurnLabel.setText("<html>Turn: <font color=blue>Player 1</font></html>");
    boardSizeInput.setText(Integer.toString(currentGame.getBoardSize()));

    Top.remove(startButton);
    Top.add(resetButton);
    Top.updateUI();
    Bottom.add(currentTurnLabel);
    Bottom.updateUI();

    resetBoard(currentGame.getBoardSize());

    if((currentGame.getPlayerType(Turn.PL1) == PlayerType.COMPUTER) || (currentGame.getPlayerType(Turn.PL2) == PlayerType.COMPUTER)){
      JOptionPane.showMessageDialog(Board,"Click the player turn label to have the computer player(s) make their move!", "Computer Player Information", JOptionPane.INFORMATION_MESSAGE);
    }
  }

  private void createPreviewBoard(){//May remove this in favor of a different start setup
    resetBoard(3);

    boardCellsList.get(0).get(0).setText("S");
    boardCellsList.get(0).get(0).setForeground(Color.RED);
    boardCellsList.get(1).get(1).setText("O");
    boardCellsList.get(1).get(1).setForeground(Color.decode("#8f00f5"));//Purple hex color #8f00f5
    boardCellsList.get(2).get(2).setText("S");
    boardCellsList.get(2).get(2).setForeground(Color.RED);
    boardCellsList.get(0).get(2).setText("S");
    boardCellsList.get(0).get(2).setForeground(Color.BLUE);
    boardCellsList.get(2).get(0).setText("S");
    boardCellsList.get(2).get(0).setForeground(Color.BLUE);
  }

  private void paint(Graphics g, Color lineColor, int x1, int y1, int x2 , int y2){
    Graphics2D g2d = (Graphics2D)g;
    g2d.setPaint(lineColor);
    g2d.setStroke(new BasicStroke(5));
    g2d.drawLine(x1,y1,x2,y2);
  }
  
  private void drawSOSLine(int row, int col){
    int beginRow = currentGame.getBeginRowIndex(row,col);
    int beginCol = currentGame.getBeginColIndex(row,col);
    int endRow = currentGame.getEndRowIndex(row,col);
    int endCol = currentGame.getEndColIndex(row,col);

    if(currentGame.getBeginRowIndex(row,col) > -1){
      if(currentGame.getCellOwnerID(row,col) == 0){
        //blue line for player 1
        paint(
            Board.getGraphics(), Color.BLUE,
            boardCellsList.get(beginRow).get(beginCol).getX() + boardCellsList.get(beginRow).get(beginCol).getWidth()/2,
            abs(boardCellsList.get(beginRow).get(beginCol).getY() + boardCellsList.get(beginRow).get(beginCol).getHeight()/2),
            boardCellsList.get(endRow).get(endCol).getX() + boardCellsList.get(endRow).get(endCol).getWidth()/2,
            abs(boardCellsList.get(endRow).get(endCol).getY() + boardCellsList.get(endRow).get(endCol).getHeight()/2)
        );
      }
      else if(currentGame.getCellOwnerID(row,col) == 1){
        //red line for player 2
        paint(
            Board.getGraphics(), Color.RED,
            boardCellsList.get(beginRow).get(beginCol).getX() + boardCellsList.get(beginRow).get(beginCol).getWidth()/2,
            abs(boardCellsList.get(beginRow).get(beginCol).getY() + boardCellsList.get(beginRow).get(beginCol).getHeight()/2),
            boardCellsList.get(endRow).get(endCol).getX() + boardCellsList.get(endRow).get(endCol).getWidth()/2,
            abs(boardCellsList.get(endRow).get(endCol).getY() + boardCellsList.get(endRow).get(endCol).getHeight()/2)
        );
      }
    }
  }

  private void handleGameOVer(){
    //Check game status for game over and display appropriate notification
    String[] gameOverOptionsList = {"Exit Game","New Game"};
    int gameOverOptionSelection = 2;
    if(currentGame.getGameStatus() == Status.DRAW){
      gameOverOptionSelection = JOptionPane.showOptionDialog(this, "The game is a draw!", "DRAW", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, gameOverOptionsList, 0);
    }
    else if(currentGame.getGameStatus() == Status.P1_WIN){
      gameOverOptionSelection = JOptionPane.showOptionDialog(this, "<html><font color=blue>Player 1</font> wins!</html>", "WINNER", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, gameOverOptionsList, 0);
    }
    else if(currentGame.getGameStatus() == Status.P2_WIN){
      gameOverOptionSelection = JOptionPane.showOptionDialog(this, "<html><font color=red>Player 2</font> wins!</html>", "WINNER", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, gameOverOptionsList, 0);
    }

    //Check user's game over choice
    if(gameOverOptionSelection == 0){
      System.exit(0);
    }
    else if(gameOverOptionSelection == 1){
      Top.remove(resetButton);
      Top.add(startButton);
      Top.updateUI();
      Bottom.remove(currentTurnLabel);
      Bottom.updateUI();
      createPreviewBoard();
    }
  }

  private void changePlayerTurn(){
    playerTurn = currentGame.getPlayerTurn();
    if(playerTurn == Turn.PL1){
      currentTurnLabel.setText("<html>Turn: <font color=blue>Player 1</font></html>");
    }
    else{
      currentTurnLabel.setText("<html>Turn: <font color=red>Player 2</font></html>");
    }
  }

  //Unused MouseListener methods; required overrides for MouseListener Interface
  @Override
  public void mousePressed(MouseEvent e) {}

  @Override
  public void mouseReleased(MouseEvent e) {}

  @Override
  public void mouseEntered(MouseEvent e) {}

  @Override
  public void mouseExited(MouseEvent e) {}
}