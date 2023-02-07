package Model.Tetrominos;

import java.awt.Color;

import static Model.TetrisEnum.S;

//Класс, представляющий S фигуру

public class SPiece extends AbstractGamePiece{
  //Конструктор по умолчанию инициализирует фигуру типа S с зеленым цветом.
  public SPiece() {
    this.color = Color.GREEN;
    this.type = S;
  }
  @Override
  protected void initBlock() {
    // создает S-образный блок
    block[1][1] = true;
    block[1][0] = true;
    block[0][1] = true;
    block[0][2] = true;
  }
}
