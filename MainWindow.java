import javax.swing.*;

public class MainWindow extends JFrame {
    public MainWindow(){
        super(Config.Title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Config.SIZE+30,Config.SIZE+55);
        setLocation(400,100);
        add(new GameField());
        setResizable(false);
        setVisible(true);
    }
    public static void main(String[] args){
        MainWindow mw = new MainWindow();
    }
}
