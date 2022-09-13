package Product;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui extends JFrame implements ActionListener{
    JPanel top_panel;
    JPanel bot_panel;
    JLabel result;
    JButton button;
    JTextField text1;
    JTextField text2;
    Gui() {
        top_panel = new JPanel();
        bot_panel = new JPanel();
        JLabel num1 = new JLabel("Num 1: ");
        JLabel num2 = new JLabel("Num 2: ");
        result = new JLabel("");
        button = new JButton("Calculate!");
        button.addActionListener(this);
        text1 = new JTextField();
        text2 = new JTextField();

        this.setSize(400,200);
        this.setLayout(null);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        top_panel.setBounds(0,0,400,50);
        top_panel.setLayout(null);

        bot_panel.setBounds(0,50,400,150);
        bot_panel.setLayout(null);
        bot_panel.setBackground(Color.DARK_GRAY);

        num1.setBounds(10,5,50,25);
        num2.setBounds(125,5,50,25);
        button.setBounds(250, 5,100,25);
        text1.setBounds(55,5,50,25);
        text2.setBounds(170,5,50,25);

        result.setForeground(Color.WHITE);

        this.add(top_panel);
        this.add(bot_panel);
        top_panel.add(num1);
        top_panel.add(num2);
        top_panel.add(button);
        top_panel.add(text1);
        top_panel.add(text2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button){
            int sum = Integer.parseInt(text1.getText()) + Integer.parseInt(text2.getText());
            result.setText(String.valueOf(sum));
            result.setBounds(100,50,100,75);
            bot_panel.add(result);
            bot_panel.repaint();
        }
    }
}
