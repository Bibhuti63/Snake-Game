import javax.swing.*;

public class GameFrame extends JFrame {
    GameFrame(){
//        GamePanel panel=new GamePanel();
//        this.add(panel);
//        this.setBounds(10,10,600,600);
        this.add(new GamePanel());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack(); //if we add components to JFrame this function will take JFrame and fit its all components at the frame
        this.setVisible(true);
        this.setLocationRelativeTo(null); //window to appear on middle of screen
    }
}
