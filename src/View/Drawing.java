package View;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import Model.Tetrominos.GamePiece;

//Класс, представляющий рисуемую панель игры Тетрис.
public class Drawing extends JPanel implements IDrawing {
  private GamePiece saved;
  private Color[][] board;
  private final int cubeSide;
  private final int cubeDistance;
  private final int boardX;
  private final int boardY;
  private final int holdX;
  public Drawing() {
    super();
    cubeSide = 10;
    cubeDistance = 2;
    boardX = 150;
    boardY = 50;
    holdX = 50;
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.setColor(Color.BLACK);
    g.fillRect(25, 25, 90, 90);
    if (saved != null) {
      g.setColor(saved.getColor());
      for (int i = 0; i < saved.getBlock().length; i++) {
        for (int j = 0; j < saved.getBlock().length; j++) {
          if (saved.getBlock()[i][j]) {
            g.fillRect(holdX + j * cubeSide, boardY + i * cubeSide, cubeSide, cubeSide);
          }
        }
      }
    }

    // масштабируется в два раза больше обычного размера платы
    if (board != null) {
      for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board[i].length; j++) {
          g.setColor(board[i][j]);
          g.fillRect(boardX + (cubeDistance + cubeSide) * j  * 2,
                  boardY + i * 2 *(cubeDistance + cubeSide),
                  cubeSide * 2,
                  cubeSide * 2);
        }
      }
    }

  }

  @Override
  public void draw(GamePiece saved, Color[][] board) {
    this.saved = saved;
    this.board = board;
    repaint();
  }
}
