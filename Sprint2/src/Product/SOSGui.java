package Product;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class SOSGui extends JFrame implements ActionListener {
  JPanel Board = new JPanel();
  JPanel Left = new JPanel();
  JPanel Right = new JPanel();
  JPanel Top = new JPanel();
  JPanel Bottom = new JPanel();

  public SOSGui(){
    this.setTitle("SOS Game");
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setSize(800,800);
    //this.setResizable(false);
    this.setLayout(new BorderLayout(10,10));

    Board.setLayout(new GridLayout(3,3));
    Board.setPreferredSize(new Dimension(100,100));

    Left.setPreferredSize(new Dimension(100,700));
    Left.setBackground(Color.RED);

    Right.setPreferredSize(new Dimension(100,700));
    Right.setBackground(Color.BLUE);

    Top.setPreferredSize(new Dimension(800,100));
    Top.setBackground(Color.black);

    Bottom.setPreferredSize(new Dimension(800,100));
    Bottom.setBackground(Color.YELLOW);

    this.add(Board, BorderLayout.CENTER);
    this.add(Left, BorderLayout.WEST);
    this.add(Right, BorderLayout.EAST);
    this.add(Top, BorderLayout.NORTH);
    this.add(Bottom, BorderLayout.SOUTH);

    Board.add(new JLabel("Test", SwingConstants.CENTER));
    Board.add(new JLabel("Test", SwingConstants.CENTER));
    Board.add(new JLabel("Test", SwingConstants.CENTER));
    Board.add(new JLabel("Test", SwingConstants.CENTER));
    Board.add(new JLabel("Test", SwingConstants.CENTER));
    Board.add(new JLabel("Test", SwingConstants.CENTER));
    Board.add(new JLabel("Test", SwingConstants.CENTER));
    Board.add(new JLabel("Test", SwingConstants.CENTER));
    Board.add(new JLabel("Test", SwingConstants.CENTER));

    this.setVisible(true);
  }

  public void paint(Graphics g){
    Graphics2D g2D = (Graphics2D) g;
    paintComponents(this.getGraphics());
    g2D.drawLine(190,215,190,250);
    //g2D.drawString("S",200,200);
  }

  @Override
  public void actionPerformed(ActionEvent e) {

  }
}
