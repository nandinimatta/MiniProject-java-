package miniproject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class miniproject extends JFrame implements ActionListener {
    JTextField sizeField;
    JTextArea outputArea;
    JButton generateButton;
    public miniproject() {
        setTitle("Auto Series Finder - Cubic");
        setSize(500,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10,10));
        JPanel top = new JPanel();
        JLabel label = new JLabel("Enter Array Size:");
        sizeField = new JTextField(5);
        generateButton = new JButton("Generate");
        generateButton.addActionListener(this);
        top.add(label);
        top.add(sizeField);
        top.add(generateButton);
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced",Font.PLAIN,14));
        JScrollPane scroll = new JScrollPane(outputArea);
        panel.add(top,BorderLayout.NORTH);
        panel.add(scroll,BorderLayout.CENTER);
        add(panel);
        setVisible(true);
    }
    public void actionPerformed(ActionEvent e) {
        try {
            int size = Integer.parseInt(sizeField.getText().trim());
            if(size <= 0){
                JOptionPane.showMessageDialog(this,"Enter a positive number!");
                return;
            }
            int[] arr = new int[size];
            for(int i=0;i<size;i++){
                arr[i] = (i+1)*(i+1)*(i+1);  
            }
            int next = (size+1)*(size+1)*(size+1);
            StringBuilder sb = new StringBuilder();
            sb.append("Series: ");
            for(int i=0;i<size;i++){
                sb.append(arr[i]).append(" ");
            }
            sb.append("?\n\n");
            sb.append("Next Number: ").append(next);
            outputArea.setText(sb.toString());
        } catch(NumberFormatException ex){
            JOptionPane.showMessageDialog(this,"Please enter a valid integer!");
        }
    }
    public static void main(String[] args) {
        new miniproject();
    }
}


