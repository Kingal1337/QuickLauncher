package quicklauncher;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;

public class AOptionPane{
    private static JButton confirmButton;
    private static boolean clickCancel;
    
    public static void showMessageDialog(String message,String title,boolean showBorder,boolean enableHTML){
        JDialog frame = new JDialog();
        frame.setModal(true);
        frame.setTitle(title);
        frame.setPreferredSize(new Dimension(300,150));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.pack(); 
        frame.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        
        JLabel question = new JLabel();
        if(enableHTML){
            question.setText("<html>"+message+"</html>");            
        }
        else{
            question.setText(message);
        }
        question.setFont(new Font("Gothic",1,12));
        question.setForeground(Color.BLACK);
        question.setPreferredSize(new Dimension(frame.getWidth()-15,(frame.getHeight()/2)-(30/2)));
        question.setBounds(5,5,frame.getWidth()-15,(frame.getHeight()/2)-(30/2));
        if(showBorder){
            question.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }
        
        confirmButton = new JButton("OK");
        confirmButton.setPreferredSize(new Dimension(75,25));
        confirmButton.setBounds(frame.getWidth()-90,frame.getHeight()-65,75,25);
        
        panel.add(question);
        panel.add(confirmButton);
        frame.add(panel);
        confirmButton.requestFocus();
        
        confirmButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }            
        });
        frame.setVisible(true);
    }
    public static String showInputDialog(String questionString,boolean showBorder,String message){
        clickCancel = false;
        JDialog frame = new JDialog();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we){
                clickCancel = true;
                frame.dispose();
            }
        });
        frame.setModal(true);
        frame.setPreferredSize(new Dimension(300,175));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.pack(); 
        frame.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        
        JLabel question = new JLabel(questionString);
        question.setFont(new Font("Gothic",1,12));
        question.setForeground(Color.BLACK);
        question.setPreferredSize(new Dimension(frame.getWidth()-15,(frame.getHeight()/2)-(30/2)));
        question.setBounds(5,5,frame.getWidth()-15,(frame.getHeight()/2)-(30/2));
        if(showBorder){
            question.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }
        
        JTextField field = new JTextField(message);
        field.setPreferredSize(new Dimension(frame.getWidth()-15,(frame.getHeight()/2)-(30/2)));
        field.setBounds(5,(frame.getHeight()/2)-(30/2)+13,frame.getWidth()-15,25);
        panel.add(field);
        
        confirmButton = new JButton("OK");
        confirmButton.setPreferredSize(new Dimension(75,25));
        confirmButton.setBounds(frame.getWidth()-85,frame.getHeight()-60,75,25);
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(75,25));
        cancelButton.setBounds(frame.getWidth()-85,frame.getHeight()-60,75,25);
        
        panel.add(question);
        panel.add(confirmButton);
        panel.add(cancelButton);
        frame.add(panel);
        confirmButton.requestFocus();
        
        confirmButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }            
        });
        cancelButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                clickCancel = true;
                frame.dispose();
            }            
        });
        frame.setVisible(true);
        if(clickCancel){
            return null;
        }
        else{
            return field.getText();            
        }
    }
    public static String showInputDialog(String questionString,String title,int numberOfCharacters,boolean showBorder,String message){
        clickCancel = false;
        JDialog frame = new JDialog();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we){
                clickCancel = true;
                frame.dispose();
            }
        });
        frame.setModal(true);
        frame.setTitle(title);
        frame.setPreferredSize(new Dimension(300,175));
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setResizable(false);
        frame.pack(); 
        frame.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        
        JLabel question = new JLabel(questionString);
        question.setFont(new Font("Gothic",1,12));
        question.setForeground(Color.BLACK);
        question.setPreferredSize(new Dimension(frame.getWidth()-15,(frame.getHeight()/2)-(30/2)));
        question.setBounds(5,5,frame.getWidth()-15,(frame.getHeight()/2)-(30/2));
        if(showBorder){
            question.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }
        
        JTextField field = new JTextField(message);
        field.setPreferredSize(new Dimension(frame.getWidth()-15,(frame.getHeight()/2)-(30/2)));
        field.setBounds(5,(frame.getHeight()/2)-(30/2)+13,frame.getWidth()-15,25);
        if(numberOfCharacters > 0){            
            field.setDocument(new JTextFieldLimit(numberOfCharacters));        
            try {
                field.getDocument().insertString(0, message, null);
            } catch (BadLocationException ex) {

            }
        }
        field.addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e) {
                
            }

            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if(key == KeyEvent.VK_ENTER){
                    frame.dispose();                    
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                
            }
            
        });
        panel.add(field);
        
        confirmButton = new JButton("OK");
        confirmButton.setPreferredSize(new Dimension(75,25));
        confirmButton.setBounds(frame.getWidth()-165,frame.getHeight()-60,75,25);
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(75,25));
        cancelButton.setBounds(frame.getWidth()-85,frame.getHeight()-60,75,25);
        
        panel.add(question);
        panel.add(confirmButton);
        panel.add(cancelButton);
        frame.add(panel);
        confirmButton.requestFocus();
        
        confirmButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }            
        });
        cancelButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                clickCancel = true;
                frame.dispose();
            }            
        });
        frame.setVisible(true);
        if(clickCancel){
            return null;
        }
        else{
            return field.getText();            
        }
    }
    
}

