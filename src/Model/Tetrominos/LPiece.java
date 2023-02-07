package Model.Tetrominos;

import java.awt.Color;

import static Model.TetrisEnum.L;

//Класс, представляющий L фигуру
public class LPiece extends AbstractGamePiece {
  //Конструктор по умолчанию инициализирует фигуру типа L с оранжевым цветом.
  public LPiece() {
    this.color = Color.ORANGE;
    this.type = L;
  }

  @Override
  protected void initBlock() {
    // создает L-образный блок
    block[0][1] = true;
    block[1][1] = true;
    block[2][1] = true;
    block[2][2] = true;
  }
}
