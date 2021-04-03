import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {

    public int ApllX,ApllY;
    public int R,G,B;
    public int[] x = new int[Config.ALL_DOTS];
    public int[] y = new int[Config.ALL_DOTS];
    public static  int dots,score=0;
    private Timer timer;
    public static boolean left,r,up=true,down,gameOver=true,menu = true,durak=false;

    JButton b1 = new JButton("Новая Игра");
    eHendler hendler = new eHendler();
    Color c1;


    public GameField(){
        setBackground(Color.BLACK);
        initGame();
        addKeyListener(new KeyField());
        setFocusable(true);
    }

    public void initGame(){
        dots = 3;
        for(int i = 0;i<dots;i++){
            x[i] = (Config.DOT_SIZE*dots)-i*Config.DOT_SIZE;
            y[i] = (Config.DOT_SIZE*dots);
        }
        timer = new Timer(100,this);
        timer.start();
        createAplle();
        R= new Random().nextInt(236)+20;
        G= new Random().nextInt(236)+20;
        B= new Random().nextInt(236)+20;
        b1.setBounds(50, 250,200,40);
        add(b1);
        b1.addActionListener(hendler);
    }


    public void createAplle(){
        ApllX = new Random().nextInt(Config.SIZE/Config.DOT_SIZE)*Config.DOT_SIZE;
        ApllY = new Random().nextInt(Config.SIZE/Config.DOT_SIZE)*Config.DOT_SIZE;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, Config.SIZE * Config.SIZE + 5, Config.SIZE * Config.SIZE + 5);
        if(!gameOver && !menu){
            remove(b1);
            c1 = new Color(R,G,B);
            g2d.setPaint(c1);
            g2d.fillOval(ApllX,ApllY,Config.DOT_SIZE,Config.DOT_SIZE);
            for (int i = 0; i < dots; i++) {
                if(i==0)g2d.setPaint(Color.GREEN);
                else g2d.setPaint(Color.BLUE);
                g2d.fillOval(x[i],y[i],Config.DOT_SIZE,Config.DOT_SIZE);
            }
            print(""+score,20,20,14,g);
            if(dots==(Config.ALL_DOTS*Config.ALL_DOTS)-1){
                gameOver = true;
            }
        } else if(dots==(Config.ALL_DOTS*Config.ALL_DOTS)-1){
            gameOver = true;
            print("А вы я так погляжу вы мазохис " + score + " ОЧКОв",Config.SIZE/2-200,Config.SIZE/2,20,g);
        }
        else if (dots<Config.ALL_DOTS-1 && !menu && !durak) {
            print("А все, ГГ \nНажми Х",Config.SIZE/2-100,Config.SIZE/2,20,g);
        }
        if(durak && gameOver){
            print("Та английскую Х",Config.SIZE/2-100,Config.SIZE/2,30,g);
        }
    }


    public void print(String str,int x,int y,int size,Graphics g){
        Font f = new Font("Arial", Font.BOLD, size);
        g.setColor(Color.white);
        g.setFont(f);
        g.drawString(str,x,y);
    }

    public void move(){
        for (int i = dots; i >0 ; i--) {
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        if(left && !r && !down && !up)x[0]-=Config.DOT_SIZE;
        if(r && !left && !down && !up)x[0]+=Config.DOT_SIZE;
        if(up && !down && !left && !r)y[0]-=Config.DOT_SIZE;
        if(down && !up && !left && !r)y[0]+=Config.DOT_SIZE;
    }

    public void GenerRGBColorAplle(){
        R= new Random().nextInt(236)+20;
        G= new Random().nextInt(236)+20;
        B= new Random().nextInt(236)+20;
    }


    public void checkAplle(){
        if(x[0]==ApllX && y[0]==ApllY){
            dots++;
            score++;
            createAplle();
            GenerRGBColorAplle();
        }
    }
    public void checkCollis(){
        for (int i = dots; i > 0 ; i--) {
            if ( i > 2 && x[0] == x[i] && y[0] == y[i]) {
                gameOver = true;
            }
        }
        //Чтобы когда он прикасаеться к полю то пигибал

//        if(x[0]<0 || x[0]>Config.SIZE-Config.DOT_SIZE || y[0]<0 || y[0]>Config.SIZE){
//            gameOver = true;
//        }

        // Чтобы он перемещался на другую сторону

        if(x[0]<0){
            x[0]=Config.SIZE;
            left=true;
            r=false;
            up = false;
            down = false;
        }if(x[0]>Config.SIZE){
            x[0]=0;
            left=false;
            r=true;
            up = false;
            down = false;
        }if(y[0]<0){
            y[0]=Config.SIZE;
            up=true;
            down=false;
            left = false;
            r = false;
        }if(y[0]>Config.SIZE){
            y[0]=0;
            up=false;
            down=true;
            left = false;
            r = false;
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!gameOver){
            checkAplle();
            checkCollis();
            move();
        }
        repaint();
    }

    public class eHendler implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            try {
                if (e.getSource() == b1) {
                    menu = false;
                    gameOver = false;
                }
            }catch (Exception ex){
                JOptionPane.showMessageDialog(null,"Что-то пошло не так!");
            }
        }

    }

    class KeyField extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && !r){
                left = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_RIGHT && !left){
                r = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_UP && !down){
                up = true;
                left = false;
                r = false;
            }
            if(key == KeyEvent.VK_DOWN && !up){
                down = true;
                left = false;
                r = false;
            }
            if(key == KeyEvent.VK_X ){
                gameOver = false;
                durak=false;
                timer.stop();
                initGame();
                score=0;
            }
            if(key == KeyEvent.VK_OPEN_BRACKET && gameOver){
                durak=true;
            }
            if(gameOver){
                if(key == KeyEvent.VK_ENTER){
                    if(gameOver==true && menu == true) {
                        gameOver = false;
                        menu = false;
                    }
                    gameOver = false;
                    durak=false;
                    timer.stop();
                    initGame();
                    score=0;
                }
            }
        }
    }
}


