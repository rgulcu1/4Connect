import javax.swing.*;
        import java.awt.*;

public class MainFrame extends JFrame {

    Container content = this.getContentPane();

    public MainFrame(Constant.GameType gameType){
        super("Connect 4");
        setSize(700,750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        setResizable(false);

        setContentPane(new GamePage(gameType));
        setVisible(true);

    }
}
