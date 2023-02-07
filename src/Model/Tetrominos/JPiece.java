package Model.Tetrominos;

import java.awt.Color;

import static Model.TetrisEnum.J;
//Класс, представляющий J фигуру
public class JPiece extends AbstractGamePiece {

  //Конструктор по умолчанию инициализирует фигуру типа J с синим цветом.
  public JPiece() {
    this.color = Color.BLUE;
    this.type = J;
  }

  @Override
  protected void initBlock() {
    // создает J-образный блок
    block[0][1] = true;
    block[1][1] = true;
    block[2][1] = true;
    block[2][0] = true;
  }
}
