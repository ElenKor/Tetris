package Model.Tetrominos;
import java.awt.Color;
import java.util.Arrays;

import static Model.TetrisEnum.I;
//Класс, представляющий I фигурy

public class IPiece extends AbstractGamePiece {
  //Конструктор по умолчанию инициализирует фигуру типа I с голубым цветом.
  public IPiece() {
    color = Color.CYAN;
    type = I;
  }
  @Override
  protected void initBlock() {
    // creates the I-shaped block
    block[0][1] = true;
    block[1][1] = true;
    block[2][1] = true;
    block[3][1] = true;
  }

  @Override
  public void rotateR() {
    boolean[][] tempList = new boolean[4][4];
    for (int i = 0; i < block.length; i++) {
      tempList[i] = Arrays.copyOf(block[i], 4);
    }

    for (int i = 0; i < block.length; i++)
      for (int j = 0; j < block.length; j++)
        block[j][i] = tempList[i][3 - j];
  }

}
