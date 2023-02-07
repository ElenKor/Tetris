package View;

import java.awt.Color;

import Model.Tetrominos.GamePiece;

public interface IDrawing {
  void draw(GamePiece saved, Color[][] board);
}
