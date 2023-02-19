import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Random;

public class panel extends JPanel implements ActionListener
{
    static int width = 1200;
    static int height = 600;
    static int unit = 50;
    Timer timer;
    static int delay = 160;
    Random random;

    int fx,fy;
    int body=1;
    char dir = 'R';
    int score;
    boolean flag = false;
    int xsnake[]=new int[288];
    int ysnake[]=new int[288];
    panel()
    {
        this.setPreferredSize(new Dimension(width,height));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new mykey());

        random = new Random();
        gamestart();
    }

    public void gamestart()
    {
        flag=true;
        spawnfood();
        timer = new Timer(delay,this);
        timer.start();
    }
    public void spawnfood()
    {
        fx = random.nextInt((int) width/unit)*unit;
        fy = random.nextInt((int) height/unit)*unit;

    }
    public void paintComponent(Graphics graphic)
    {
        super.paintComponent(graphic);
        draw(graphic);
    }
    public void draw(Graphics graphic)
    {
        if(flag==true)
        {
            graphic.setColor(Color.orange);
            graphic.fillOval(fx,fy,unit,unit);

            for(int i=0;i<body;i++)
            {
                if(i==0)
                {
                    graphic.setColor(Color.red);
                    graphic.fillRect(xsnake[i],ysnake[i],unit,unit);
                }
                else
                {
                    graphic.setColor(Color.green);
                    graphic.fillRect(xsnake[i],ysnake[i],unit,unit);
                }
            }
            graphic.setColor(Color.red);
            graphic.setFont(new Font("comic sans",Font.BOLD,40));
            FontMetrics fme = getFontMetrics(graphic.getFont());
            graphic.drawString("score:"+score,(width-fme.stringWidth("Score:"+score))/2,graphic.getFont().getSize());
        }
        else {
            gameover(graphic);
        }
    }
    public void gameover(Graphics graphic)
    {
        graphic.setColor(Color.CYAN);
        graphic.setFont(new Font("comic sans",Font.BOLD,40));
        FontMetrics fme = getFontMetrics(graphic.getFont());
        graphic.drawString("score:"+score,(width-fme.stringWidth("score:"+score))/2,graphic.getFont().getSize());

        graphic.setColor(Color.red);
        graphic.setFont(new Font("comic sans",Font.BOLD,80));
        FontMetrics fme1 = getFontMetrics(graphic.getFont());
        graphic.drawString("GAME OVER"+score,(width-fme1.stringWidth("GAME OVER"))/2,height/2);

        graphic.setColor(Color.CYAN);
        graphic.setFont(new Font("comic sans",Font.BOLD,40));
        FontMetrics fme2 = getFontMetrics(graphic.getFont());
        graphic.drawString("Press  R to replay",(width-fme2.stringWidth("Press R to replay"))/2,height/2-150);
    }
    public void move()
    {
        for(int i=body;i>0;i--)
        {
            xsnake[i]=xsnake[i-1];
            ysnake[i]=ysnake[i-1];
        }
        switch(dir)
        {
            case 'R' :
                xsnake[0]=xsnake[0]+unit;
                break;
            case 'L' :
                xsnake[0]=xsnake[0]-unit;
                break;
            case 'U' :
                ysnake[0]=ysnake[0]-unit;
                break;
            case 'D' :
                ysnake[0]=ysnake[0]+unit;
                break;
        }
    }
    public void check()
    {
        if(xsnake[0]<0){
            flag=false;
        }
        else if(xsnake[0]>width){
            flag=false;
        }
        else if(ysnake[0]>height){
            flag=false;
        }
        else if(ysnake[0]<0){
            flag=false;
        }
        for(int i=body;i>0;i--){
            if((xsnake[0]==xsnake[i]) && (ysnake[0]==ysnake[i])){
                flag=false;
            }
        }
        if(flag==false){
            timer.stop();
        }
    }
    public void eat(){
        if((xsnake[0]==fx) && ysnake[0]==fy){
            body++;
            score++;spawnfood();
        }
    }
    public class mykey extends KeyAdapter
    {

        public void keyPressed(KeyEvent e)
        {
            switch(e.getKeyCode()){
                case KeyEvent.VK_UP:
                    if(dir!= 'D'){
                        dir = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(dir!= 'U'){
                        dir = 'D';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(dir!='L'){
                        dir = 'R';
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    if(dir!='R'){
                        dir = 'L';
                    }
                    break;
                case KeyEvent.VK_R:
                    if(!flag){
                        score = 0;
                        body = 3;
                        dir = 'R';
                        Arrays.fill(xsnake, 0);
                        Arrays.fill(ysnake, 0);
                        gamestart();
                    }
                    break;
            }
        }
    }
    public void actionPerformed(ActionEvent e)
    {
            if(flag){
                move();
                eat();
                check();
            }
            repaint();
    }
}
