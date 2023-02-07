package Model.Tetrominos;

import java.awt.Color;
import java.util.Arrays;

import Model.TetrisEnum;

import static Model.TetriminoFactory.makePiece;

//Абстрактный класс для игровых фигур.
public abstract class AbstractGamePiece implements GamePiece {
  protected Color color;
  protected boolean[][] block;
  private boolean[][] workList;
  protected TetrisEnum type;
  public AbstractGamePiece() {
    block = new boolean[4][4];
    workList = new boolean[4][4];
    clearWork();
    initBlock();
  }
  private void clearWork() {
    for (boolean[] row : workList) {
      Arrays.fill(row, false);
    }
  }

  abstract protected void initBlock();

  public Color getColor() {
    return this.color;
  }

  public boolean[][] getBlock() {
    return this.block;
  }

  public void rotateR() {
    clearWork();
    int[] pivotLoc = new int[]{1, 1};
    int[][] rotate90 = new int[][]{new int[]{0, -1}, new int[]{1, 0}};

    for (int i = 0; i < block.length; i++) {
      for (int j = 0; j < block.length; j++) {
        if (block[i][j]) {
          int[] currLoc = new int[]{i, j};
          int[] newLoc = arithMatrix(pivotLoc, multMatrix(rotate90, arithMatrix(currLoc, pivotLoc,
                  true)), false);
          workList[newLoc[0]][newLoc[1]] = true;
        }
      }
    }

    for (int i = 0; i < block.length; i++) {
      System.arraycopy(workList[i], 0, block[i], 0, block.length);
    }

  }
  // предполагаем, что обе матрицы имеют одинаковую длину
  private int[] arithMatrix(int[] first, int[] second, boolean addSub) {
    int[] temp = new int[first.length];

    for (int i = 0; i < first.length; i++) {
      temp[i] = addSub ? first[i] - second[i] : first[i] + second[i];
    }

    return temp;
  }
  // матрица вращения равна 2x2
  private int[] multMatrix(int[][] rotation, int[] vector) {
    int[] temp = new int[2];

    temp[0] = rotation[0][0] * vector[0] + rotation[0][1] * vector[1];
    temp[1] = rotation[1][0] * vector[0] + rotation[1][1] * vector[1];

    return temp;
  }

  public String getBlockString() {
    StringBuilder str = new StringBuilder();
    for (int i = 0; i < block.length; i++) {
      for (int j = 0; j < block.length; j++) {
        str.append(block[i][j]);
        if (j < block.length - 1) {
          str.append(" ");
        }
      }

      if (i < block.length - 1) {
        str.append("\n");
      }
    }

    return str.toString();
  }

  public GamePiece copy() {
    GamePiece toReturn = makePiece(this.type);
    toReturn.setBlock(this.block);

    return toReturn;
  }

  public void setBlock(boolean[][] toSet) {
    this.block = toSet;
  }

}
