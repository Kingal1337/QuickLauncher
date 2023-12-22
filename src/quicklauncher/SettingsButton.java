package quicklauncher;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

public class SettingsButton extends ImageButton{
    private JButton confirmButton;
    private boolean forStartUpButton;
    public SettingsButton(String name,ImageIcon imageIcon,ToolTip tooltip) {
        super(name,imageIcon,tooltip);
    }

    @Override
    public void action() {
        QuickLauncher.mainPane.setSettings(settingsFrame(QuickLauncher.mainPane.getSettings()));
        QuickLauncher.mainPane.updateSettings(QuickLauncher.mainPane.getSettings());
    }
    
    public Settings settingsFrame(Settings setting){
        JDialog frame = new JDialog();
        frame.setModal(true);
        frame.setTitle("Settings");
        frame.setPreferredSize(new Dimension(200,300));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.pack(); 
        frame.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        
        panel.setBackground(Color.decode("#111111"));
        
        JLabel sideButtonLabel = new JLabel("Side Of Panel",SwingConstants.LEFT);
        sideButtonLabel.setForeground(Color.WHITE);
        sideButtonLabel.setFont(new Font("Gothic",1,12));
        sideButtonLabel.setPreferredSize(new Dimension(150,30));
        sideButtonLabel.setBounds((200/2)-(150/2),10,150,15);
        
        JToggleButton SideButton = new JToggleButton();
        if(setting.getPreferredDirection() == 0){
            SideButton.setSelected(true);
            SideButton.setText("Current : Left Side");
        }
        else if(setting.getPreferredDirection() == 1){
            SideButton.setSelected(false);
            SideButton.setText("Current : Right Side");
        }
        else{
            setting.setPreferredDirection(0);
            SideButton.setSelected(true);            
        }
        SideButton.setOpaque(true);
        SideButton.setBackground(new Color(0.0f,0.0f,0.0f,0.0f));
        SideButton.setBorder(BorderFactory.createEmptyBorder());
        SideButton.setFocusPainted(false); 
        SideButton.setForeground(Color.GRAY);
        SideButton.setFont(new Font("Gothic",1,12));
        SideButton.setPreferredSize(new Dimension(150,30));
        SideButton.setBounds((200/2)-(150/2),30,150,30);
        SideButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {     
                SideButton.setOpaque(true);
                SideButton.setBackground(Color.LIGHT_GRAY);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                SideButton.setOpaque(false);
            }
        });
        SideButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ev) {
                if (ev.getStateChange() == ItemEvent.SELECTED) {
                    setting.setPreferredDirection(0);
                    SideButton.setText("Current : Left Side");
                } else if (ev.getStateChange() == ItemEvent.DESELECTED) {
                    setting.setPreferredDirection(1);
                    SideButton.setText("Current : Right Side");
                }
            }
        });
                
        JLabel scrollingSpeedLabel = new JLabel("Scrolling Speed",SwingConstants.LEFT);
        scrollingSpeedLabel.setForeground(Color.WHITE);
        scrollingSpeedLabel.setFont(new Font("Gothic",1,12));
        scrollingSpeedLabel.setPreferredSize(new Dimension(150,30));
        scrollingSpeedLabel.setBounds((200/2)-(150/2),70,150,15);
        
        JComboBox scrollingSpeed = new JComboBox();
        scrollingSpeed.setPreferredSize(new Dimension(150,30));        
        scrollingSpeed.setBounds((200/2)-(150/2),90,150,30);
        scrollingSpeed.addItem("Default");
        scrollingSpeed.addItem("Fast");
        scrollingSpeed.addItem("Super");
        scrollingSpeed.addActionListener(new java.awt.event.ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = scrollingSpeed.getSelectedIndex();
                if(index == 0){
                    setting.setScrollSpeed(5);
                }
                else if(index == 1){
                    setting.setScrollSpeed(10);
                }
                else if(index == 2){
                    setting.setScrollSpeed(15);
                }
            }
        });
        
        if(setting.getScrollSpeed() == 5){
            scrollingSpeed.setSelectedIndex(0);            
        }
        if(setting.getScrollSpeed() == 10){
            scrollingSpeed.setSelectedIndex(1);            
        }
        if(setting.getScrollSpeed() == 15){
            scrollingSpeed.setSelectedIndex(2);
        }
        
        JLabel allowStartUpLabel = new JLabel("Open On Startup",SwingConstants.LEFT);
        allowStartUpLabel.setForeground(Color.WHITE);
        allowStartUpLabel.setFont(new Font("Gothic",1,12));
        allowStartUpLabel.setPreferredSize(new Dimension(150,30));
        allowStartUpLabel.setBounds((200/2)-(150/2),130,150,15);
        
        JToggleButton allowStartUp = new JToggleButton();
        if(System.getProperty("os.name").toLowerCase().startsWith("win")){
            if(setting.isStartUp()== 0){
                allowStartUp.setSelected(false);
                allowStartUp.setText("Current : Dont Open");
            }
            else if(setting.isStartUp() == 1){
                allowStartUp.setSelected(true);
                allowStartUp.setText("Current : Open");
            }
            else{
                setting.setStartUp(0);
                allowStartUp.setSelected(false);          
                allowStartUp.setText("Current : Dont Open");  
            }
        }
        else{
            setting.setStartUp(0);
            allowStartUp.setSelected(false); 
            allowStartUp.setText("Current : Dont Open");
        }
        allowStartUp.setOpaque(true);
        allowStartUp.setBackground(new Color(0.0f,0.0f,0.0f,0.0f));
        allowStartUp.setBorder(BorderFactory.createEmptyBorder());
        allowStartUp.setFocusPainted(false); 
        allowStartUp.setForeground(Color.GRAY);
        allowStartUp.setFont(new Font("Gothic",1,12));
        allowStartUp.setPreferredSize(new Dimension(150,30));
        allowStartUp.setBounds((200/2)-(150/2),150,150,30);
        allowStartUp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {     
                allowStartUp.setOpaque(true);
                allowStartUp.setBackground(Color.LIGHT_GRAY);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                allowStartUp.setOpaque(false);
            }
        });
        allowStartUp.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ev) {
                if(System.getProperty("os.name").toLowerCase().startsWith("win")){
                    System.out.println("Hello");
                    if (ev.getStateChange() == ItemEvent.SELECTED) {
                        setting.setStartUp(1);
                        allowStartUp.setText("Current : Open");
                    } else if (ev.getStateChange() == ItemEvent.DESELECTED) {
                        setting.setStartUp(0);
                        allowStartUp.setText("Current : Dont Open");
                    }
                }
                else{
                    if (ev.getStateChange() == ItemEvent.SELECTED) {
                        setting.setStartUp(0);
                        AOptionPane.showMessageDialog("Error<br>Cannot add app to startup<br>Reason : OS not supported", "Error", true, true);
                        allowStartUp.setText("Current : Dont Open");
                    } else if (ev.getStateChange() == ItemEvent.DESELECTED) {
                        
                    }
                }
            }
        });
                
        JButton exitButton = new JButton("Exit Program");
        exitButton.setPreferredSize(new Dimension(150,30));
        exitButton.setBounds((200/2)-(150/2),190,150,30);
        exitButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }            
        });
        
        JLabel credits = new JLabel("<html>Created By<br>Alan Tsui</html>",SwingConstants.CENTER);
        credits.setForeground(Color.WHITE);
        credits.setPreferredSize(new Dimension(75,25));
        credits.setBounds(20,frame.getHeight()-65,75,25);
        
        confirmButton = new JButton("OK");
        confirmButton.setPreferredSize(new Dimension(75,25));
        confirmButton.setBounds(frame.getWidth()-90,frame.getHeight()-65,75,25);
        confirmButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }            
        });
        
        panel.add(sideButtonLabel);
        panel.add(scrollingSpeedLabel);
        panel.add(SideButton);
        panel.add(scrollingSpeed);
        panel.add(allowStartUpLabel);
        panel.add(allowStartUp);
        panel.add(exitButton);
        panel.add(confirmButton);
        panel.add(credits);
        frame.add(panel);
        confirmButton.requestFocus();
        
        frame.setVisible(true);
        return setting;
    }
}
