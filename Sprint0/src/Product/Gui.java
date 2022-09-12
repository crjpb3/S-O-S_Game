package Product;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui extends JFrame implements ActionListener{
    JLabel result;
    JButton button;
    Gui() {
        //JFrame frame = new JFrame();
        JLabel num1 = new JLabel("Num 1: ");
        JLabel num2 = new JLabel("Num 2: ");
        result = new JLabel("Result: ");
        button = new JButton("Calculate!");
        button.addActionListener(this);

        this.setSize(300,300);
        this.setLayout(null);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        num1.setBounds(10,10,50,25);
        num2.setBounds(10,30,50,25);
        button.setBounds(25, 50,100,25);

        this.add(num1);
        this.add(num2);
        this.add(button);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button){
            System.out.println("Click!");
            result.setBounds(25,75,75,50);
            this.add(result);
            this.repaint();
        }
    }
}
