package Product;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;

public class Gui extends JFrame implements ActionListener{
    JPanel top_panel;
    JPanel bot_panel;
    JLabel result;
    JButton button;
    JTextField text1;
    JTextField text2;
    public Gui() {
        this.setTitle("Addition Calc");
        this.setSize(400,200);
        this.setLayout(new FlowLayout());
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        top_panel = new JPanel();
        bot_panel = new JPanel();
        JPanel vertical_panel = new JPanel();
        JPanel horizontal_panel = new JPanel();
        JLabel num1 = new JLabel("Num 1: ");
        text1 = new JTextField();
        JLabel num2 = new JLabel("Num 2: ");
        text2 = new JTextField();
        result = new JLabel("");
        result.setFont(new Font("Arial", Font.PLAIN,50));
        result.setSize(400,50);
        button = new JButton("Add!");
        button.addActionListener(this);
        JCheckBox check1 = new JCheckBox();
        JCheckBox check2 = new JCheckBox();
        JRadioButton radio1 = new JRadioButton("Useless button 1");
        JRadioButton radio2 = new JRadioButton("Useless button 2");

        top_panel.setPreferredSize(new Dimension(400,50));
        bot_panel.setPreferredSize(new Dimension(400,75));
        bot_panel.setBackground(Color.DARK_GRAY);
        vertical_panel.setPreferredSize(new Dimension(5,50));
        horizontal_panel.setPreferredSize(new Dimension(400,5));
        horizontal_panel.setBackground(Color.gray);
        vertical_panel.setBackground(Color.gray);

        num1.setPreferredSize(new Dimension(50,25));
        text1.setPreferredSize(new Dimension(50,25));
        num2.setPreferredSize(new Dimension(50,25));
        text2.setPreferredSize(new Dimension(50,25));
        button.setPreferredSize(new Dimension(60,25));
        result.setForeground(Color.WHITE);

        this.add(top_panel);
        this.add(radio1);
        this.add(radio2);
        this.add(horizontal_panel);
        this.add(bot_panel);
        top_panel.add(vertical_panel);
        top_panel.add(check1);
        top_panel.add(num1);
        top_panel.add(text1);
        top_panel.add(check2);
        top_panel.add(num2);
        top_panel.add(text2);
        top_panel.add(button);

        this.setVisible(true);
    }

    public int add(int num1, int num2){
        return num1 + num2;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button){
            int sum = add(Integer.parseInt(text1.getText()), Integer.parseInt(text2.getText()));
            result.setText(String.valueOf(sum));
            bot_panel.add(result);
            bot_panel.revalidate();
            bot_panel.repaint();
        }
    }
}
