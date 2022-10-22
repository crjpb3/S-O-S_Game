package Product;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class SOSGui extends JFrame implements ActionListener, MouseListener {
  private ArrayList<ArrayList<JLabel>> boardCellsList;
  private Font cellFont;
  private JPanel Board;
  private JPanel Left;
  private JPanel Right;
  private JPanel Top;
  private JPanel Bottom;
  private JButton startButton;

  //Options items
  private JLabel gameModeLabel;
  private JRadioButton simpleGameModeOption;
  private JRadioButton generalGameModeOption;

  private JLabel player1SectionLabel;
  private JRadioButton player1MoveS;
  private JRadioButton player1MoveO;

  private JLabel player2SectionLabel;
  private JRadioButton player2MoveS;
  private JRadioButton player2MoveO;

  private JLabel boardSizeLabel;
  private JTextField boardSizeInput;

  private JLabel currentTurnLabel;

  public SOSGui(){
    boardCellsList = new ArrayList<>();

    this.setTitle("SOS Game");
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setSize(800,800);
    this.setLayout(new BorderLayout(10,10));

    startButton = new JButton("Start");
    startButton.addActionListener(this);

    cellFont = new Font(Font.SERIF, Font.BOLD, 25);

    Board = new JPanel();
    Board.setPreferredSize(new Dimension(100,100));

    Left = new JPanel();
    Left.setPreferredSize(new Dimension(100,700));
    Left.setBackground(Color.RED);

    Right = new JPanel();
    Right.setPreferredSize(new Dimension(100,700));
    Right.setBackground(Color.BLUE);

    Top = new JPanel();
    Top.setPreferredSize(new Dimension(800,100));
    Top.setBackground(Color.black);

    Bottom = new JPanel();
    Bottom.setPreferredSize(new Dimension(800,100));
    Bottom.setBackground(Color.YELLOW);
    Bottom.add(startButton);

    //createBoardGUI(3);

    this.add(Board, BorderLayout.CENTER);
    this.add(Left, BorderLayout.WEST);
    this.add(Right, BorderLayout.EAST);
    this.add(Top, BorderLayout.NORTH);
    this.add(Bottom, BorderLayout.SOUTH);

    this.setVisible(true);

    createPreviewBoard(); //One time function to display board/game representation
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    //Start button clicked
    if(e.getSource() == startButton) {
      //Board.setLayout(new GridLayout(boardCellsList.size(), boardCellsList.size()));
      startButton.setVisible(false);
      resetBoard(4);
    }
  }
  @Override
  public void mouseClicked(MouseEvent e) {
    //Move event
    for(int i = 0; i < boardCellsList.size(); i++){
      for(int j = 0; j < boardCellsList.get(i).size(); j++){
        if(e.getSource() == boardCellsList.get(i).get(j)){
          boardCellsList.get(i).get(j).setText(String.valueOf(i) + ',' + j);
        }
      }
    }
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
  }

  private void startGame(){
    //SOSGame currentGame = new SOSGame(userSize, userMode);
  }

  private void createPreviewBoard(){
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
