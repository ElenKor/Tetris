package Model.Tetrominos;

import java.awt.Color;

import static Model.TetrisEnum.O;

//Класс, представляющий O фигуру
public class OPiece extends AbstractGamePiece {
  //Конструктор по умолчанию для фигуры O с желтым цветом.
  public OPiece() {
    this.color = Color.YELLOW;
    this.type = O;
  }

  protected void initBlock() {
    // создает О-образный блок
    block[2][1] = true;
    block[2][2] = true;
    block[3][1] = true;
    block[3][2] = true;
  }
  // вращение фигуры просто "вернет" то же самое состояние
  @Override
  public void rotateR() {
  }
}
