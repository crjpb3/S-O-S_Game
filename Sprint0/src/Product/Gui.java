package Product;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
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
        this.setTitle("Addition Calc");
        this.setSize(400,170);
        this.setLayout(new FlowLayout());
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        top_panel = new JPanel();
        bot_panel = new JPanel();
        JLabel num1 = new JLabel("Num 1: ");
        text1 = new JTextField();
        JLabel num2 = new JLabel("Num 2: ");
        text2 = new JTextField();
        result = new JLabel("");
        result.setFont(new Font("Arial", Font.PLAIN,50));
        result.setSize(400,50);
        button = new JButton("Calculate!");
        button.addActionListener(this);

        top_panel.setPreferredSize(new Dimension(400,50));
        bot_panel.setPreferredSize(new Dimension(400,75));
        bot_panel.setBackground(Color.DARK_GRAY);

        num1.setPreferredSize(new Dimension(50,25));
        text1.setPreferredSize(new Dimension(50,25));
        num2.setPreferredSize(new Dimension(50,25));
        text2.setPreferredSize(new Dimension(50,25));
        button.setPreferredSize(new Dimension(100,25));
        result.setForeground(Color.WHITE);

        this.add(top_panel);
        this.add(bot_panel);
        top_panel.add(num1);
        top_panel.add(text1);
        top_panel.add(num2);
        top_panel.add(text2);
        top_panel.add(button);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button){
            int sum = Integer.parseInt(text1.getText()) + Integer.parseInt(text2.getText());
            result.setText(String.valueOf(sum));
            bot_panel.add(result);
            bot_panel.revalidate();
            bot_panel.repaint();
        }
    }
}
