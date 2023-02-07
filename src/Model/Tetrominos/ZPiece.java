package Model.Tetrominos;

import java.awt.Color;

import static Model.TetrisEnum.T;
import static Model.TetrisEnum.Z;

//Класс, представляющий Z фигуру
public class ZPiece extends AbstractGamePiece {
  //Конструктор по умолчанию инициализирует фигуру типа Z с красным цветом.
  public ZPiece() {
    this.color = Color.RED;
    this.type = Z;
  }

  @Override
  protected void initBlock() {
    // создает Z-образный блок
    block[1][1] = true;
    block[1][2] = true;
    block[0][0] = true;
    block[0][1] = true;
  }
}
