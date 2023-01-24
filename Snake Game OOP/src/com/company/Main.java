package com.company;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Random;
import java.util.Scanner;

////interface PreviousButton
//{
//    public void gb();
//}

class StartPage implements ActionListener
{
    //class Objects ----------------------------------------------------------------------------------------------------
    JFrame frame1 = new JFrame();
    JPanel panel = new JPanel();
    JLabel label = new JLabel();
    JButton start = new JButton("START");
    JButton high_score = new JButton("HIGH SCORE");
    JButton help = new JButton("HELP");
    JButton exit = new JButton("EXIT");

    StartPage()
    {

        //StartPage Panel ----------------------------------------------------------------------------------------------
        label.setLayout(null);
        ImageIcon ic = new ImageIcon("SNEK2.png");
        label.setIcon(ic);

        //Customization of Buttons -------------------------------------------------------------------------------------

        start.setFocusable(false);
        high_score.setFocusable(false);
        help.setFocusable(false);
        exit.setFocusable(false);
        start.setBackground(new Color(255, 133, 0));
        high_score.setBackground(new Color(255, 133, 0));
        help.setBackground(new Color(255, 133, 0));
        exit.setBackground(new Color(255, 133, 0));
        start.setBorder(BorderFactory.createEtchedBorder());
        high_score.setBorder(BorderFactory.createEtchedBorder());
        help.setBorder(BorderFactory.createEtchedBorder());
        exit.setBorder(BorderFactory.createEtchedBorder());
        start.setFont(new Font("Comic Sans", Font.BOLD, 13));
        high_score.setFont(new Font("Comic Sans", Font.BOLD, 13));
        help.setFont(new Font("Comic Sans", Font.BOLD, 13));
        exit.setFont(new Font("Comic Sans", Font.BOLD, 13));
        start.setBounds(51, 466, 84, 50);
        high_score.setBounds(184, 466, 84, 50);
        help.setBounds(332, 466, 84, 50);
        exit.setBounds(478, 466, 84, 50);

        label.add(start);
        label.add(high_score);
        label.add(help);
        label.add(exit);
        panel.add(label);

        //StarPage Frame -----------------------------------------------------------------------------------------------
        frame1.setTitle("Snek.exe");
        frame1.setResizable(false);
        frame1.setSize(600, 600);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setLocationRelativeTo(null);
        frame1.add(label);


        //Adding Action Listener ---------------------------------------------------------------------------------------

        start.addActionListener(this);
        help.addActionListener(this);
        exit.addActionListener(this);
        high_score.addActionListener(this);

        panel.setVisible(true);
        frame1.setVisible(true);
    }

    //Action Performed--------------------------------------------------------------------------------------------------

    @Override
    public void actionPerformed(ActionEvent e)
    {
        // FOR HELP---------------------------------
        if (e.getSource() == help) {
            frame1.dispose();
            new Help();
        }
        // FOR EXIT---------------------------------
        if (e.getSource() == exit) {
            frame1.dispose();
            System.exit(0);
        }
        // FOR HIGH SCORE---------------------------
        if (e.getSource() == high_score) {
            frame1.dispose();
            new Highscore();
        }
        // FOR START--------------------------------
        if (e.getSource() == start) {
            frame1.dispose();
            new UserName();
            //new Playpanel();
        }
    }
}
class Playpanel
{
    JFrame frame = new JFrame();
//    public static int temp = 0 ;

    Playpanel()
    {   frame.setUndecorated(true);
        frame.add(new Panel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Snek.exe");
    }

}
class Panel extends JPanel implements ActionListener
{

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/(UNIT_SIZE*UNIT_SIZE);
    static final int DELAY = 75;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 6;
    static int circlesEaten;
    int circlex;
    int circley;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    Panel()
    {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new Mykeyadapter());
        startGame();
    }
    public void startGame()
    {
        newcircle();
        running = true ;
        timer = new Timer(DELAY,this);
        timer.start();
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Image img = Toolkit.getDefaultToolkit().getImage("levelone.png");
        g.drawImage(img,0,0,null);
        draw(g);
    }
    public void draw(Graphics g) {
        if (running) {
//            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
//                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
//                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
//            }
            g.setColor(Color.orange);
            g.fillOval(circlex, circley, UNIT_SIZE, UNIT_SIZE);
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(new Color(199,11,11));
                    g.fillOval(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(199, 11, 11));
                    g.fillOval(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial",Font.PLAIN,30));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("SCORE: "+circlesEaten,(SCREEN_WIDTH - metrics.stringWidth("SCORE: "+circlesEaten))/2, g.getFont().getSize());

        } else {
            gameover(g);
        }
    }
    public void newcircle(){
        circlex = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        circley = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }
    public void move()
    {
        for(int i = bodyParts ; i>0 ; i--)
        {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch(direction)
        {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }
    public void checkcircle()
    {
        if((x[0] == circlex) && (y[0] == circley))
        {
            bodyParts++;
            circlesEaten++;
            newcircle();
        }
    }
    public void checkcollisions()
    {
        // check if head collides with body
        for(int i = bodyParts; i>0; i--)
        {
            if((x[0] == x[i])&&(y[0] == y[i]))
            {
                running = false;
            }
        }
        if(x[0] < 0)
        {
            running = false ;
        }
        if(x[0] > SCREEN_WIDTH)
        {
            running = false ;
        }
        if(y[0] < 0)
        {
            running = false ;
        }
        if(y[0] > SCREEN_HEIGHT)
        {
            running = false ;
        }
        if(!running)
        {
            timer.stop();
        }
    }
    public void gameover(Graphics g)
    {
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter("Scores.txt",true));
            int higs;
            higs=circlesEaten;
            bw.write(higs+"\n");
            bw.close();
        }catch(IOException exception){
            exception.printStackTrace();
        }
        new RestartGame();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running)
        {
            move();
            checkcircle();
            checkcollisions();
        }
        repaint();
    }
    public class Mykeyadapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e)
        {
            switch (e.getKeyCode())
            {
                case KeyEvent.VK_LEFT :
                    if(direction != 'R')
                    {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L')
                    {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D')
                    {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN :
                    if(direction != 'U')
                    {
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
class RestartGame implements ActionListener
{
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    JLabel label = new JLabel();
    JButton exit = new JButton("Exit");
    JButton score = new JButton("Score: "+Panel.circlesEaten);
    RestartGame()
    {  frame.setTitle("Snek.exe");
        label.setLayout(null);
        ImageIcon image = new ImageIcon("GAMEOVER.png");
        label.setIcon(image);
        exit.setFocusable(false);
        exit.setBackground(new Color(255, 133, 0));
        exit.setBorder(BorderFactory.createEtchedBorder());
        exit.setFont(new Font("Comic Sans", Font.BOLD, 13));
        score.setFocusable(false);
        score.setBackground(new Color(255, 133, 0));
        score.setBorder(BorderFactory.createEtchedBorder());
        score.setFont(new Font("Comic Sans", Font.BOLD, 13));
        score.setFocusable(false);
        exit.setBounds(150,200,100,40);
        score.setBounds(150,280,100,40);

        label.add(score);
        label.add(exit);
        panel.add(label);

        frame.setResizable(false);
        frame.setSize(400,400);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(label);

        exit.addActionListener(this);

        panel.setVisible(true);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == exit)
        {
            frame.dispose();
            System.exit(0);
        }
    }
}
class Help implements ActionListener
{
    JFrame frame = new JFrame();
    JLabel background = new JLabel();
    JPanel panel = new JPanel();
    JButton back = new JButton("back");


    Help()
    {
        //Adding Background Image---------------------------------------------------------------------------------------

        background.setLayout(null);
        ImageIcon image = new ImageIcon("Controls.png");
        background.setIcon(image);

        //Customization of Buttons -------------------------------------------------------------------------------------

        back.setFocusable(false);
        back.setBackground(new Color(255, 133, 0));
        back.setBorder(BorderFactory.createEtchedBorder());
        back.setFont(new Font("Comic Sans", Font.BOLD, 13));
        back.setBounds(10,10,70,30);

        //Help Panel----------------------------------------------------------------------------------------------------


        background.add(back);
        panel.add(background);

        //Help Frame----------------------------------------------------------------------------------------------------

        frame.setResizable(false);
        frame.setSize(680, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(background);
        frame.setTitle("Snek.exe");

        panel.setVisible(true);
        frame.setVisible(true);

        back.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==back)
        {
            frame.setVisible(false);
            new StartPage();
        }
    }

}
class Highscore extends JFrame implements ActionListener
{
    //Class Objects-----------------------------------------------------------------------------------------------------


    JPanel Panel = new JPanel();
    JLabel label = new JLabel();
    JLabel name1 = new JLabel();
    JLabel name2 = new JLabel();
    JLabel name3 = new JLabel();
    JButton back = new JButton("back");
    JButton data = new JButton();
    void printmsg(){
        System.out.println("The File Is Not Created Yet!");
    }
    Highscore()
    {
        //Adding Background Image---------------------------------------------------------------------------------------

        label.setLayout(null);
        ImageIcon image = new ImageIcon("LEADER.png");
        label.setIcon(image);


        //Customization of Buttons--------------------------------------------------------------------------------------

        back.setFocusable(false);
        back.setBackground(new Color(255, 133, 0));
        back.setBorder(BorderFactory.createEtchedBorder());
        back.setFont(new Font("Comic Sans", Font.BOLD, 13));
        back.setBounds(10,10,70,30);
        back.setBounds(10,10,70,30);
        data.setFocusable(false);
        data.setBackground(new Color(0, 0, 0));
        data.setBorder(BorderFactory.createEtchedBorder());
        data.setFont(new Font("Comic Sans", Font.BOLD, 30));
        data.setBounds(37,140,508,280);

        label.add(back);
        Panel.add(label);

        //HighScore Frame-----------------------------------------------------------------------------------------------
        //this.getContentPane().setBackground( Color.BLACK );
        this.setResizable(false);
        this.setSize(600, 600);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.add(label);
        this.setTitle("Snek.exe");
        //mera code-----------------------------------------------------------------------------------------------------
        try{
            int points[]=new int[3];
            BufferedReader br1 = new BufferedReader(new FileReader("Scores.txt"));
            Scanner scan1 = new Scanner(br1);
            for(int i=0;i<points.length;i++)
            {
                points[i]=0;
            }
            for(int i=0;i<points.length;i++)
            {
                points[i]=scan1.nextInt();
            }
            br1.close();
            String ok[]=new String[3];
            BufferedReader br = new BufferedReader(new FileReader("username1.txt"));
            Scanner scan = new Scanner(br);
            for(int i=0;i<ok.length;i++)
            {
                ok[i]="No player";
            }
            for(int i=0;i<ok.length;i++)
            {
                ok[i]= scan.nextLine();
            }

            data.setText("<html><b><u></u></b><br/><br/>"+ok[0]+"<---------------------->       "+points[0]+"<br/>"+ok[1]+"<---------------------->       "+points[1]+"<br/>"+ok[2]+"<---------------------->       "+points[2]+"</html>");
            label.setFont(new Font("Comic Sans",Font.BOLD,90));
            data.setEnabled(false);
            label.setHorizontalAlignment(label.CENTER);
            label.add(data);

            //label.setBorder(new EmptyBorder(0,10,0,0));
            br.close();
        } catch(FileNotFoundException p){
            printmsg();
        } catch(IOException e){
            e.printStackTrace();
        }


        //Adding Action Listener----------------------------------------------------------------------------------------

        back.addActionListener(this);

        Panel.setVisible(true);
        this.setVisible(true);
    }

    //Action Performed--------------------------------------------------------------------------------------------------

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == back) {
            this.dispose();
            new StartPage();
        }
    }
}
class UserName extends JFrame  implements ActionListener
{
    JLabel background = new JLabel();
    JPanel panel = new JPanel();
    JButton back = new JButton("back");
    JButton play = new JButton("Login");
    JPanel usernamep = new JPanel();
    JButton tick = new JButton("✔");
    JButton confirm = new JButton();
    JTextField text = new JFormattedTextField();
    static  String useri;
    static int userint=0;
    UserName()
    {
        //Adding Background Image---------------------------------------------------------------------------------------

        background.setLayout(null);
        ImageIcon image = new ImageIcon("login.png");
        background.setIcon(image);

        //Customization of Buttons -------------------------------------------------------------------------------------

        back.setFocusable(false);
        back.setBackground(new Color(255, 133, 0));
        back.setBorder(BorderFactory.createEtchedBorder());
        back.setFont(new Font("Comic Sans", Font.BOLD, 13));
        back.setBounds(15,10,70,30);
        play.setBounds(220,500,150,30);
        play.setFocusable(false);
        play.setBackground(new Color(255, 133, 0));
        play.setBorder(BorderFactory.createEtchedBorder());
        play.setFont(new Font("Comic Sans", Font.BOLD, 13));
        tick.setFocusable(false);
        tick.setBackground(new Color(255, 133, 0));
        tick.setBorder(BorderFactory.createEtchedBorder());
        tick.setFont(new Font("Comic Sans", Font.BOLD, 13));
        tick.setBounds(520,420,40,30);
        confirm.setFocusable(false);
        confirm.setBackground(new Color(255, 133, 0));
        confirm.setBorder(BorderFactory.createEtchedBorder());
        confirm.setFont(new Font("Comic Sans", Font.BOLD, 13));
        confirm.setBounds(450,500,100,30);

        //Help Panel----------------------------------------------------------------------------------------------------

        text.setLayout(null);
        text.setBounds(288,420,200,30);
        background.add(text);
        background.add(play);
        background.add(back);
        background.add(tick);
        background.add(confirm);
        panel.add(background);

        //Help Frame----------------------------------------------------------------------------------------------------
        this.setTitle("Snek.exe");
        this.setResizable(true);
        this.setSize(600, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        //this.add(background);
        this.add(panel);
        panel.setVisible(true);
        this.setVisible(true);
        play.addActionListener(this);
        back.addActionListener(this);
        tick.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==back)
        {
            this.dispose();
            new StartPage();
        }
        if(e.getSource()==play)
        {
            this.dispose();

            new Playpanel();
        }if(e.getSource()==tick){
            userint=userint+1;
            confirm.setText("Sucesss..!");
            try{

                BufferedWriter bw = new BufferedWriter(new FileWriter("username1.txt",true));
                useri=text.getText();

                bw.write(useri+"\n");
                bw.close();
            }catch(IOException exception){
                exception.printStackTrace();
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        new StartPage();
    }
}