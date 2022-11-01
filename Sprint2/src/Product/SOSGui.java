package Product;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class SOSGui extends JFrame implements ActionListener, MouseListener {
  SOSGame currentGame;
  private ArrayList<ArrayList<JLabel>> boardCellsList;
  private String playerTurn;
  private String p1MoveChar = "S";
  private String p2MoveChar = "S";
  private int gameModeSelection;

  //Panels declarations
  private Font cellFont;
  private JPanel Board;
  private JPanel Left;
  private JPanel Right;
  private JPanel Top;
  private JPanel Bottom;
  private JButton startButton;
  private JButton resetButton;

  //Options items declarations
  private JLabel gameModeLabel;
  private JRadioButton simpleGameModeOption;
  private JRadioButton generalGameModeOption;
  private ButtonGroup gameModeGroup;

  private JLabel player1SectionLabel;
  private JRadioButton player1MoveS;
  private JRadioButton player1MoveO;
  private ButtonGroup moveGroupPlayer1;


  private JLabel player2SectionLabel;
  private JRadioButton player2MoveS;
  private JRadioButton player2MoveO;
  private ButtonGroup moveGroupPlayer2;

  private JLabel boardSizeLabel;
  private JTextField boardSizeInput;

  private JLabel currentTurnLabel;

  public SOSGui(){
    boardCellsList = new ArrayList<>();
    initGUI();
    resetBoard(3);
    createPreviewBoard(); //One time function to display board/game representation; "main screen"
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if(e.getSource() == startButton) {
      //Pass gameModeSelection, boardSize for start game; set playerTurn to "Player 1"
      startGame();
      Bottom.remove(startButton);
      Bottom.add(resetButton);
      Bottom.add(currentTurnLabel);
      resetBoard(currentGame.getBoardSize());
    }
    else if(e.getSource() == simpleGameModeOption){
      gameModeSelection = 0;
    }
    else if (e.getSource() == generalGameModeOption){
      gameModeSelection = 1;
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
    //Making a move event
    for(int i = 0; i < boardCellsList.size(); i++){
      for(int j = 0; j < boardCellsList.get(i).size(); j++){
        if(e.getSource() == boardCellsList.get(i).get(j)){
          if(Objects.equals(playerTurn, "Player 1")) {
            currentGame.makeMove(i, j, p1MoveChar);
            if(currentGame.getBeginRowIndex(i,j) > -1){
              if(currentGame.getCellOwnerID(i,j) == 0){
                //blue line for player 1
              }
              else if(currentGame.getCellOwnerID(i,j) == 1){
                //red line for player 2
              }
              int beginRow = currentGame.getBeginRowIndex(i,j);
              int beginCol = currentGame.getBeginColIndex(i,j);
              int endRow = currentGame.getEndRowIndex(i,j);
              int endCol = currentGame.getEndColIndex(i,j);
            }
            boardCellsList.get(i).get(j).setText(p1MoveChar);
          }
          else{
            currentGame.makeMove(i, j, p2MoveChar);
            boardCellsList.get(i).get(j).setText(p2MoveChar);
          }
          playerTurn = currentGame.getPlayerTurn();
          currentTurnLabel.setText("Turn: " + playerTurn);
        }
      }
    }
  }

  private void panelsComponentsSetup(){
    //Top panel components
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
    player1SectionLabel = new JLabel("Player 1");
    player1MoveS = new JRadioButton("S", true);
    player1MoveS.addActionListener(this);
    player1MoveO = new JRadioButton("O", false);
    player1MoveO.addActionListener(this);
    moveGroupPlayer1 = new ButtonGroup();
    moveGroupPlayer1.add(player1MoveS);
    moveGroupPlayer1.add(player1MoveO);
    
    //Right panel components
    player2SectionLabel = new JLabel("Player 2");
    player2MoveS = new JRadioButton("S", true);
    player2MoveS.addActionListener(this);
    player2MoveO = new JRadioButton("O", false);
    player2MoveO.addActionListener(this);
    moveGroupPlayer2 = new ButtonGroup();
    moveGroupPlayer2.add(player2MoveS);
    moveGroupPlayer2.add(player2MoveO);
    
    //Bottom panel components
    startButton = new JButton("Start");
    startButton.addActionListener(this);
    resetButton = new JButton("New Game");
    resetButton.addActionListener(this);

    currentTurnLabel = new JLabel();
  }

  private void panelsSetup(){
    Board = new JPanel();
    Board.setPreferredSize(new Dimension(100,100));

    Top = new JPanel(new FlowLayout());
    Top.setPreferredSize(new Dimension(800,100));
    Top.add(gameModeLabel);
    Top.add(simpleGameModeOption);
    Top.add(generalGameModeOption);
    Top.add(boardSizeLabel);
    Top.add(boardSizeInput);

    Left = new JPanel(new GridLayout(3,1));
    Left.setPreferredSize(new Dimension(100,700));
    Left.add(player1SectionLabel);
    Left.add(player1MoveS);
    Left.add(player1MoveO);

    Right = new JPanel(new GridLayout(3,1));
    Right.setPreferredSize(new Dimension(100,700));
    Right.add(player2SectionLabel);
    Right.add(player2MoveS);
    Right.add(player2MoveO);

    Bottom = new JPanel();
    Bottom.setPreferredSize(new Dimension(800,100));
    Bottom.add(startButton);
  }

  private void frameSetup(){
    this.setTitle("SOS Game");
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setSize(800,800);
    this.setVisible(true);
    this.setLayout(new BorderLayout(10,10));

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
    //resetBoard(boardSize);
  }

  private void destroyBoard(int boardSize){
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
    destroyBoard(boardSize);
    Board.setLayout(new GridLayout(boardSize,boardSize, 0,0));
    cellFont = new Font(Font.SERIF, Font.BOLD, 25);

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
      currentGame = new SOSGame(Integer.parseInt(boardSizeInput.getText()), gameModeSelection);
    }
    catch(NumberFormatException e1){
      try{
        currentGame = new SOSGame(Double.parseDouble(boardSizeInput.getText()), gameModeSelection);
      }
      catch(NumberFormatException e2){
       try{
         currentGame = new SOSGame(Float.parseFloat(boardSizeInput.getText()), gameModeSelection);
       }
       catch(NumberFormatException e3){
         currentGame = new SOSGame(boardSizeInput.getText(), gameModeSelection);
       }
      }
    }
    playerTurn = currentGame.getPlayerTurn();
    currentTurnLabel.setText("Turn: " + playerTurn);
    boardSizeInput.setText(Integer.toString(currentGame.getBoardSize()));
  }

  private void createPreviewBoard(){
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

  //Unused MouseListener methods; required overrides for MouseListener implementation
  @Override
  public void mousePressed(MouseEvent e) {}

  @Override
  public void mouseReleased(MouseEvent e) {}

  @Override
  public void mouseEntered(MouseEvent e) {}

  @Override
  public void mouseExited(MouseEvent e) {}
}