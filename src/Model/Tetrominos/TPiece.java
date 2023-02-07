package Model.Tetrominos;

import java.awt.Color;

import static Model.TetrisEnum.T;

//Класс, представляющий T фигуру
public class TPiece extends AbstractGamePiece{
  //Конструктор по умолчанию инициализирует фигуру типа T с пурпурным цветом.
  public TPiece() {
    this.color = Color.MAGENTA;
    this.type = T;
  }

  @Override
  protected void initBlock() {
    // создает Т-образный блок
    block[1][1] = true;
    block[1][0] = true;
    block[1][2] = true;
    block[0][1] = true;
  }
}
