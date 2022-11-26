import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.KeyAdapter;
//import java.awt.event.KeyEvent;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH=600;
    static final int SCREEN_HEIGHT=600;
    static final int UNIT_SIZE=25; //size of objects
    static final int GAME_UNITS=(SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE; //how many objects can feet on the screen
    static final int DELAY=120; //higher is the delay slower is the game
    //2 arrays will hold all the coordinates for all the parts of snake including head of the snake
    final int x[]=new int[GAME_UNITS]; //this will hold all the x coordinates of snake
    final int y[]=new int[GAME_UNITS]; //this will hold all the x coordinates of snake

    //initial body part of the snake
    int bodyParts=3;

    int applesEaten=0;
    int appleX;//x coordinate of apples location which will random each time
    int appleY;;//y coordinate of apples location which will random each time

    char direction='R'; //initialize the game towards right direction //r-right,l=left,u=up,d=down

    boolean running=false;

    Timer timer;  //instance of Timer class

    Random random; //instance of Random class


    GamePanel(){
        random=new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT)); //preferred size of the panel
        this.setBackground(Color.BLACK);
        this.setFocusable(true); //add focousibility
        this.addKeyListener(new MyKeyAdapter()); //add keylistner

        startGame();

    }

    public void startGame(){
        newApple();//to create a new apple on the screen
        running=true; //
        timer=new Timer(DELAY,this); //to detect how fast the game is running
        timer.start();

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g); //calling draw method to create greed structure

    }

    public void draw(Graphics g){
        if(running){
            /*
            //loop for creating a greed which is optional
            for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++){
                g.drawLine(i*UNIT_SIZE,0,i*UNIT_SIZE,SCREEN_HEIGHT); //vertical line
                g.drawLine(0,i*UNIT_SIZE,SCREEN_WIDTH,i*UNIT_SIZE); //horizontal line
            }
             */

            g.setColor(Color.RED); //setting color of apple
            g.fillOval(appleX,appleY,UNIT_SIZE,UNIT_SIZE); //Draws the outline of a circular or elliptical arc covering the specified rectangle.

            //create Full Body of Snake
            for(int i=0;i<bodyParts;i++){
                if(i==0){ //head of the snake
                    g.setColor(Color.green);
                    g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
                }
                else{ //body of snake excluding head
                    //it will give green color as the RGB valu is fixed
//                    g.setColor(new Color(45,180,0)); //added customized color with RGB value of color
                    //colour full body part
                    g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                    g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
                }
            }
            //show the score on the top of screen
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free",Font.BOLD,40));
            //allign the text in center of the screen
            FontMetrics metrics=getFontMetrics(g.getFont());
            int x=(SCREEN_WIDTH-metrics.stringWidth("Score: "+applesEaten))/2;
            int y=g.getFont().getSize();
            g.drawString("Score: "+applesEaten,x,y);
        }
        else{
            gameOver(g);
        }

    }

    public void newApple(){
        appleX=random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY= random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;


    }

    public void move(){
        //loop to shifting the bodyparts of the snake
        for(int i=bodyParts;i>0;i--){
            x[i]=x[i-1]; //shifting all the coordinate to previous spot
            y[i]=y[i-1];
        }

        //to switch direction
        switch (direction){
            case 'U':
                y[0]=y[0]-UNIT_SIZE;
                break;
            case 'D':
                y[0]=y[0]+UNIT_SIZE;
                break;
            case 'L':
                x[0]=x[0]-UNIT_SIZE;
                break;
            case 'R':
                x[0]=x[0]+UNIT_SIZE;
                break;
        }

    }

    public void checkApple(){
        //if snake head touches the apple
        if((x[0]==appleX)&&(y[0])==appleY){
            bodyParts++; //snake body will grow
            applesEaten++; //score increases
            newApple();//new apple is created
        }

    }

    public void checkCollision(){
        //checks if head collids with body
        for(int i=bodyParts;i>0;i--){
            if((x[0]==x[i])&&y[0]==y[i]){ //head collided with the body
                running=false; //stop the game

            }
        }

        //checks if head touches left border
        if(x[0]<0) running=false;
        //checks if head touches right border
        if(x[0]>SCREEN_WIDTH) running=false;
        //checks if head touches top border
        if(y[0]<0)running=false;
        //checks if head touches bottom border
        if(y[0]>SCREEN_HEIGHT)running=false;


        //if not running i.e running=false
        if(!running){
            timer.stop();
        }


    }

    public void gameOver(Graphics g){

        //Game over text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        //allign the text in center of the screen
        FontMetrics metrics=getFontMetrics(g.getFont());
        int x=(SCREEN_WIDTH-metrics.stringWidth("Game Over"))/2;
        int y=SCREEN_HEIGHT/2;
        g.drawString("Game Over",x,y);


        //Show Score also
        g.setColor(Color.green);
        g.setFont(new Font("Ink Free",Font.BOLD,40));
        //allign the text in center of the screen
        FontMetrics metrics1=getFontMetrics(g.getFont());
        int a=(SCREEN_WIDTH-metrics1.stringWidth("Score: "+applesEaten))/2;
        int b=SCREEN_HEIGHT/4*3;
        g.drawString("Score: "+applesEaten,a,b);


    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(running){
            move();//move the snake
            checkApple();//
            checkCollision();

        }
        //if the game is no longer running
        repaint();

    }

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction!='R') direction='L';
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction!='L') direction='R';
                    break;
                case KeyEvent.VK_UP:
                    if(direction!='D') direction='U';
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction!='U') direction='D';
                    break;
            }

        }
    }
}
