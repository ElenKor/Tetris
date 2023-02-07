package Model;

import Model.Tetrominos.GamePiece;
import Model.Tetrominos.IPiece;
import Model.Tetrominos.JPiece;
import Model.Tetrominos.LPiece;
import Model.Tetrominos.OPiece;
import Model.Tetrominos.SPiece;
import Model.Tetrominos.TPiece;
import Model.Tetrominos.ZPiece;

///Фабрика для создания фигур с заданным идентификатором enum.
public class TetriminoFactory {
  public static GamePiece makePiece(TetrisEnum e) {
    switch (e) {
      case I: return new IPiece();
      case J: return new JPiece();
      case O: return new OPiece();
      case L: return new LPiece();
      case S: return new SPiece();
      case Z: return new ZPiece();
      case T: return new TPiece();
      default:
        throw new IllegalArgumentException("Invalid Piece");
    }
  }
}
