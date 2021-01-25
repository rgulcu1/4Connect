import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;


public class GamePage extends JPanel implements MouseMotionListener, MouseListener, KeyListener {

    boolean isMouseEnter = false;
    boolean isGameOver = false;
    boolean isHumanPlay = false;
    boolean isHumanvsAi = false;
    boolean isAİvsAi = false;
    Integer x;
    Player player = Constant.player1;
    Connect4 c4 = new Connect4();
    Node boardNode = c4.boardNode;

    public GamePage(Constant.GameType gameType) {
        addMouseMotionListener(this);
        addMouseListener(this);
        this.setFocusable(true);
        this.requestFocus();
        addKeyListener(this);
        addResetButton();
        setLayout(null);

        switch (gameType){
            case HUMANvsHUMAN:
                playHumanvsHuman();
                break;
            case HUMANvsAI:
                playHumanvsAi();
                break;
            case AIvsAI:
                playAivsAi();
                break;
        }
    }

    private void addResetButton() {
        JButton resetbtn = new JButton("Reset");
        resetbtn.setBounds(600, 30, 70, 50);

        resetbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isMouseEnter = false;
                isGameOver = false;
                isHumanPlay = true;
                player = Constant.player1;
                c4.boardNode = new Node(Constant.INITIAL_BOARD);
                boardNode = c4.boardNode;
                setFocusable(true);
                requestFocus();
            }
        });
        this.add(resetbtn);
    }

    private void playHumanvsHuman() {

        JLabel ai1Info = new JLabel("("+player.colorName+") " + "Human");
        ai1Info.setBounds(50,10,200,20);


        Player oppenent = Utils.reversePlayer(player);
        JLabel ai2Info = new JLabel("("+oppenent.colorName+") " + "Human");
        ai2Info.setBounds(400,10,200,20);

        isHumanPlay = true;
    }

    private void playHumanvsAi() {

        JLabel ai1Info = new JLabel("("+player.colorName+") " + "Human");
        ai1Info.setBounds(50,10,200,20);


        Player oppenent = Utils.reversePlayer(player);
        JLabel ai2Info = new JLabel("("+oppenent.colorName+") " + oppenent.heuristic.name() + " Depth : " +oppenent.depth);
        ai2Info.setBounds(400,10,200,20);

        add(ai1Info);
        add(ai2Info);

        isHumanvsAi = true;
        isHumanPlay = true;
    }

    public void playAivsAi() {
        isAİvsAi = true;

        System.out.println(player.name);
        System.out.println(player.depth);
        System.out.println(player.heuristic.name());
        System.out.println(player.colorName);
        JLabel ai1Info = new JLabel("("+player.colorName+") " + player.heuristic.name() + " Depth : " +player.depth);
        ai1Info.setBounds(50,50,200,20);


        Player oppenent = Utils.reversePlayer(player);
        JLabel ai2Info = new JLabel("("+oppenent.colorName+") " + oppenent.heuristic.name() + " Depth : " +oppenent.depth);
        ai2Info.setBounds(400,50,200,20);

        add(ai1Info);
        add(ai2Info);
    }

    private void playAi(){

        if (!isHumanPlay && !isGameOver) {

            repaint();
            c4.generateAiDecision(player);
            checkGameOver();
            player = Utils.reversePlayer(player);

            if(!isAİvsAi) isHumanPlay = true;
        }
        repaint();
    }

    private void move(Integer colChoice) {

        Integer rowIndex = c4.move(boardNode.getState(), colChoice, player);
        if (rowIndex == -1) {
            return;
        }

        if (checkGameOver()) return;
        player = Utils.reversePlayer(player);

        repaint();

        if (isHumanvsAi){
            isHumanPlay = false;
        }
    }

    private boolean checkGameOver() {

        if (boardNode.getScore(player) == Constant.WIN_SCORE) {
            JOptionPane.showMessageDialog(null, player.colorName + " is win the game!!");
            isGameOver = true;
            return true;
        }

        if (boardNode.isBoardFull()) {
            JOptionPane.showMessageDialog(null, "Tie");
            isGameOver = true;
            return true;
        }
        return false;
    }


    private void printCell(Graphics g, Integer x, Integer y, String piece) {
        Graphics2D g2d = (Graphics2D) g;
        Area a1 = new Area(new Rectangle2D.Double(x, y, 100, 100));
        g2d.setColor(new Color(0, 0, 170));
        g2d.fill(a1);

        Ellipse2D cell = new Ellipse2D.Double(x + 10, y + 10, 80, 80);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(10));
        g2d.drawOval(x + 10, y + 10, 80, 80);


        if (piece.equals(Constant.player1.label)) {
            g2d.setColor(Color.ORANGE);
            g2d.fillOval(x + 10, y + 10, 80, 80);
        } else if (piece.equals(Constant.player2.label)) {
            g2d.setColor(Color.RED);
            g2d.fillOval(x + 10, y + 10, 80, 80);
        } else {
            g2d.setColor(this.getBackground());
            g2d.fillOval(x + 10, y + 10, 80, 80);
        }
    }

    private void printBoard(Graphics g) {
        Integer marginTop = this.getSize().height - Constant.ROW_SIZE * 100;
        for (int i = 0; i < Constant.ROW_SIZE; i++) {
            for (int j = 0; j < Constant.COL_SIZE; j++) {
                printCell(g, j * 100, i * 100 + marginTop, boardNode.getState()[i][j]);
            }
        }
     }

    private void printNextMove(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        if (isHumanPlay && isMouseEnter && !isGameOver) {
            Integer y = this.getSize().height - (Constant.ROW_SIZE + 1) * 100;

            g2d.setColor(player.playerColor);
            g2d.fillOval(x + 10, y + 10, 80, 80);
        }
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        printBoard(g);
        printNextMove(g);
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Integer xPoisiton = e.getX();

        if (xPoisiton <= 100 && xPoisiton > 0) xPoisiton = 0;
        if (xPoisiton <= 200 && xPoisiton > 100) xPoisiton = 100;
        if (xPoisiton <= 300 && xPoisiton > 200) xPoisiton = 200;
        if (xPoisiton <= 400 && xPoisiton > 300) xPoisiton = 300;
        if (xPoisiton <= 500 && xPoisiton > 400) xPoisiton = 400;
        if (xPoisiton <= 600 && xPoisiton > 500) xPoisiton = 500;
        if (xPoisiton <= 700 && xPoisiton > 600) xPoisiton = 600;

        if (xPoisiton != x) {
            x = xPoisiton;
            repaint();
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {

        if (isHumanPlay && !isGameOver) {
            Integer xPoisiton = e.getX();
            Integer colChoice = null;
            if (xPoisiton <= 100 && xPoisiton > 0) colChoice = 0;
            if (xPoisiton <= 200 && xPoisiton > 100) colChoice = 1;
            if (xPoisiton <= 300 && xPoisiton > 200) colChoice = 2;
            if (xPoisiton <= 400 && xPoisiton > 300) colChoice = 3;
            if (xPoisiton <= 500 && xPoisiton > 400) colChoice = 4;
            if (xPoisiton <= 600 && xPoisiton > 500) colChoice = 5;
            if (xPoisiton <= 700 && xPoisiton > 600) colChoice = 6;

            move(colChoice);
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        isMouseEnter = true;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        isMouseEnter = false;
    }


    @Override
    public void keyTyped(KeyEvent e) {


    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER ){
            playAi();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {


    }
}
