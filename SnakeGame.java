import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

public class SnakeGame extends JPanel implements ActionListener {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;
    private static final int DOT_SIZE = 10;
    private static final int ALL_DOTS = (WIDTH * HEIGHT) / (DOT_SIZE * DOT_SIZE);
    private static final int RAND_POS = WIDTH / DOT_SIZE;

    private final int[] x = new int[ALL_DOTS];
    private final int[] y = new int[ALL_DOTS];

    private int dots;
    private int appleX;
    private int appleY;
    private int score;

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;

    private Timer timer;
    private JLabel scoreLabel;
    private Image buffer;
    private Graphics bufferGraphics;

    public SnakeGame() {
        initGame();
    }

    private void initGame() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.black);
        setFocusable(true);
        setLayout(null);

        addKeyListener(new TAdapter());
        setFocusable(true);
        requestFocusInWindow();

        Border border = BorderFactory.createLineBorder(Color.white);
        setBorder(border);

        initSnake();
        locateApple();

        score = 0;
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setForeground(Color.white);
        scoreLabel.setFont(new Font("Helvetica", Font.BOLD, 16));
        scoreLabel.setBounds(10, 10, 100, 20);
        add(scoreLabel);

        timer = new Timer(100, this);
        timer.start();
    }

    private void initSnake() {
        dots = 3;

        for (int z = 0; z < dots; z++) {
            x[z] = 50 - z * DOT_SIZE;
            y[z] = 50;
        }
    }

    private void locateApple() {
        int r = (int) (Math.random() * RAND_POS);
        appleX = r * DOT_SIZE;

        r = (int) (Math.random() * RAND_POS);
        appleY = r * DOT_SIZE;
    }

    private void checkApple() {
        if ((x[0] == appleX) && (y[0] == appleY)) {
            dots++;
            score++;
            scoreLabel.setText("Score: " + score);
            locateApple();
        }
    }

    private void move() {
        for (int z = dots; z > 0; z--) {
            x[z] = x[z - 1];
            y[z] = y[z - 1];
        }

        if (leftDirection) {
            x[0] -= DOT_SIZE;
        }

        if (rightDirection) {
            x[0] += DOT_SIZE;
        }

        if (upDirection) {
            y[0] -= DOT_SIZE;
        }

        if (downDirection) {
            y[0] += DOT_SIZE;
        }
    }

    private void checkCollision() {
        for (int z = dots; z > 0; z--) {
            if ((z > 3) && (x[0] == x[z]) && (y[0] == y[z])) {
                inGame = false;
            }
        }

        if (y[0] >= HEIGHT) {
            inGame = false;
        }

        if (y[0] < 0) {
            inGame = false;
        }

        if (x[0] >= WIDTH) {
            inGame = false;
        }

        if (x[0] < 0) {
            inGame = false;
        }

        if (!inGame) {
            timer.stop();
            showGameOverDialog();
        }
    }

    private void showGameOverDialog() {
        int option = JOptionPane.showConfirmDialog(this, "Game Over! Do you want to play again?", "Game Over", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            restartGame();
        } else {
            System.exit(0);
        }
    }

    private void restartGame() {
        initSnake();
        locateApple();
        score = 0;
        scoreLabel.setText("Score: " + score);
        inGame = true;
        timer.start();
    }

    private void draw(Graphics g) {
        if (inGame) {
            if (buffer == null) {
                buffer = createImage(WIDTH, HEIGHT);
                bufferGraphics = buffer.getGraphics();
            }

            bufferGraphics.setColor(Color.black);
            bufferGraphics.fillRect(0, 0, WIDTH, HEIGHT);

            bufferGraphics.setColor(Color.red);
            bufferGraphics.fillOval(appleX, appleY, DOT_SIZE, DOT_SIZE);

            for (int z = 0; z < dots; z++) {
                if (z == 0) {
                    bufferGraphics.setColor(Color.green);
                } else {
                    bufferGraphics.setColor(Color.white);
                }
                bufferGraphics.fillRect(x[z], y[z], DOT_SIZE, DOT_SIZE);
            }

            g.drawImage(buffer, 0, 0, null);
            Toolkit.getDefaultToolkit().sync();
        } else {
            gameOver(g);
        }
    }

    private void gameOver(Graphics g) {
        String msg = "Game Over";
        Font smallFont = new Font("Helvetica", Font.BOLD, 20);
        FontMetrics metrics = g.getFontMetrics(smallFont);

        int msgX = (WIDTH - metrics.stringWidth(msg)) / 2;
        int msgY = HEIGHT / 2;

        g.setColor(Color.white);
        g.setFont(smallFont);
        g.drawString(msg, msgX, msgY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkCollision();
            move();
        }

        repaint();
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if ((key == KeyEvent.VK_UP) && (!downDirection)) {
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Snake");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);

            SnakeGame game = new SnakeGame();
            frame.getContentPane().add(game);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
