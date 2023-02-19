import javax.swing.*;

public class frame extends JFrame
{
    frame()
    {
        this.add(new panel());
        this.setTitle("Snake_game");
        this.setResizable(false);
        this.pack();   // layout maneger
        this.setVisible(true);
    }



}
