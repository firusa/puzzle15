package se.flyingpenguin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PuzzleUserInterface extends JPanel {
    private static final int frameSize = 540;
    private static final int margin = 30;
    private static final Color tileColor = new Color(50, 189, 124);
    private static final Color backgroundColor = Color.WHITE;
    private static final Color borderColor = Color.BLACK;
    private static final Font font = new Font(Font.SANS_SERIF, Font.BOLD, 65);
    private static final Dimension frameDimension = new Dimension(frameSize, frameSize);
    private static final int gridSize = frameSize - margin * 2;
    private static final int tileSize = gridSize / 4;
    private static final String title = "Game of 15";
    private static boolean gameStarted = false;

    public static void drawFrame() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle(title);
            frame.setResizable(false);
            frame.add(new PuzzleUserInterface(), BorderLayout.CENTER);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    public PuzzleUserInterface() {
        setPreferredSize(frameDimension);
        setBackground(backgroundColor);
        setForeground(tileColor);
        setFont(font);
        addMouseListener(getMouseAdapter());
    }

    private MouseAdapter getMouseAdapter() {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseClick) {
                if (gameStarted) {
                    // get position of the click
                    int xWithinGrid = mouseClick.getX() - margin;
                    int yWithinGrid = mouseClick.getY() - margin;

                    // check if click is in the grid
                    if (xWithinGrid < 0 || xWithinGrid > gridSize || yWithinGrid < 0 || yWithinGrid > gridSize) {
                        return;
                    }

                    // get position in the grid
                    int clickRow = xWithinGrid / tileSize;
                    int clickColumn = yWithinGrid / tileSize;
                    Puzzle15.moveTile(clickColumn, clickRow);
                } else {
                    Puzzle15.newGame();
                    gameStarted = true;
                }

                // we repaint panel
                repaint();
            }
        };
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (!gameStarted) {
            drawStartMessage(g2D);
        } else if (Puzzle15.isSolved()) {
            gameStarted = false;
            drawEndMessage(g2D);
        } else {
            drawGrid(g2D);
        }
    }

    private void drawGrid(Graphics2D g) {
        for (int r = 0; r < 4; r++) { //r is row
            for (int c = 0; c < 4; c++) { //c is column
                // we convert in coords on the UI
                int x = margin + c * tileSize;
                int y = margin + r * tileSize;

                // check special case for blank tile
                if (Puzzle15.tilesGrid[r][c] == 0) {
                    continue;
                }

                // for other tiles
                g.setColor(getForeground());
                g.fillRect(x, y, tileSize, tileSize);
                g.setColor(borderColor);
                g.drawRect(x, y, tileSize, tileSize);
                g.setColor(backgroundColor);
                drawCenteredString(g, String.valueOf(Puzzle15.tilesGrid[r][c]), x, y);
            }
        }
    }

    private void drawCenteredString(Graphics2D g, String s, int x, int y) {
        // center string s for the given tile (x,y)
        FontMetrics fm = g.getFontMetrics();
        int asc = fm.getAscent();
        int desc = fm.getDescent();
        g.drawString(s, x + (tileSize - fm.stringWidth(s)) / 2,
                y + (asc + (tileSize - (asc + desc)) / 2));
    }

    private void drawStartMessage(Graphics2D g) {
        drawMessage(g, "Click to start new game");
    }

    private void drawEndMessage(Graphics2D g) {
        drawMessage(g, "Congratulations you won!");
    }

    private void drawMessage(Graphics2D g, String message) {
        g.setFont(getFont().deriveFont(Font.BOLD, 18));
        g.setColor(tileColor);
        g.drawString(message, (getWidth() - g.getFontMetrics().stringWidth(message)) / 2,
                getHeight() - margin);
    }
}
