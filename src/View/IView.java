package View;

import java.awt.Color;

import Controller.ViewListener;
import Model.Tetrominos.GamePiece;

public interface IView {
  void display(GamePiece saved, Color[][] board);
  void addListener(ViewListener listener);
  void showGameOver();
}
