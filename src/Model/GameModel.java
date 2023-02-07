package Model;

import java.awt.Point;
import java.awt.Color;
import java.io.*;
import java.util.*;

import java.util.stream.IntStream;

import Model.Tetrominos.GamePiece;

import static Model.TetriminoFactory.makePiece;
import static Model.TetrisEnum.I;
import static Model.TetrisEnum.J;
import static Model.TetrisEnum.O;
import static Model.TetrisEnum.S;
import static Model.TetrisEnum.Z;
import static Model.TetrisEnum.L;
import static Model.TetrisEnum.T;

//Реализации операций игрового интерфейса. Одна игровая модель - это одна сессия игры в Тетрис.
public class GameModel implements Game {
  private int score;
  private final int col;
  private final int row;
  private Color[][] board;
  private Color[][] tempBoard;
  private final GamePiece[] bucket;
  private GamePiece[] workBucket;
  private GamePiece currentPiece;
  private GamePiece savedPiece;
  private Point currPosition;
  private boolean gameOver;
  private boolean canHold;
  private int linesRemoved;
  private int i=0;
  private String name="no name";
  private static final String Separator = "\t";

  public GameModel() {
    score = 0;
    linesRemoved = 0;
    col = 10;
    row = 18;
    bucket = new GamePiece[7];
    workBucket = new GamePiece[0];
    board = new Color[row][col];
    tempBoard = new Color[row][col];
    currPosition = new Point(3, -4);
    gameOver = false;
    canHold = true;
    initBlockBucket();
    currentPiece = randomPiece();
    initBoard();
  }

  private void initBoard() {
    Arrays.stream(board).forEach(r -> Arrays.fill(r, Color.BLACK));
  }

  private void initBlockBucket() {
    TetrisEnum[] list = new TetrisEnum[]{I, J, O, S, Z, T, L};

    IntStream.range(0, 7).forEach(i -> bucket[i] = makePiece(list[i]));
  }

  private GamePiece randomPiece() {
    Random ran = new Random();

    if (workBucket.length == 0) workBucket = Arrays.copyOf(bucket, 7);

    int index = ran.nextInt(workBucket.length);
    GamePiece toReturn = workBucket[index];
    List<GamePiece> temp = new ArrayList<>(Arrays.asList(workBucket));
    temp.remove(index);
    workBucket = temp.toArray(new GamePiece[workBucket.length - 1]);

    return toReturn;
  }

  @Override
  public void rotateRight() {
    this.currentPiece.rotateR();

    if (attachable(0, 1)) wallTest();

    if (attachable(0, 1)) {
      currentPiece.rotateR();
      currentPiece.rotateR();
      currentPiece.rotateR();
    }
  }

  private void wallTest() {
    if (!attachable(1, 0) && attachable(0, 0)) {
      currPosition.x += 1;
      if (attachable(0, 0)) currPosition.x -= 1;
    }

    if (!attachable(-1, 0) && attachable(0, 0)) {
      currPosition.x -= 1;
      if (attachable(0, 0)) currPosition.x += 1;
    }
  }

  @Override
  public int getScore() {
    return this.score;
  }

  @Override
  public Color[][] getGameState() {
    IntStream.range(0, board.length).forEach(i -> tempBoard[i]
            = Arrays.copyOf(board[i], 10));

    attachToThis(false);
    return this.tempBoard;
  }

  @Override
  public GamePiece getSavedPiece() {
    return this.savedPiece;
  }

  @Override
  public void save() {
    if (canHold) {
      GamePiece storeCurr = currentPiece.copy();

      if (savedPiece == null) {
        currentPiece = randomPiece();
        currPosition.setLocation(3, -4);
      } else {
        currentPiece = savedPiece.copy();
      }

      savedPiece = storeCurr;
      canHold = false;
    }
  }

  @Override
  public void moveX(boolean dir) {
    int x = dir ? 1 : -1;

    if (!attachable(x, 0)) {
      currPosition.x += x;
    }

  }

  @Override
  public void softDrop() {
    currPosition.y += 1;

    if (attachable(0, 1)) installAndReset();
  }

  @Override
  public void hardDrop() {
    while (!attachable(0, 1)) {
      currPosition.y += 1;
    }

    installAndReset();
  }

  private void installAndReset() {
    attachToThis(true);
    removeLines();
    currentPiece = randomPiece();
    currPosition.setLocation(3, -4);

    if (!canHold) canHold = true;
  }

  @Override
  public boolean getIsGameOver()  {
    return this.gameOver;
  }

  @Override
  public void clear() {
    savedPiece = null;
    initBoard();
    workBucket = Arrays.copyOf(bucket, 7);
    currentPiece = randomPiece();
    canHold = true;
    score = 0;
    currPosition.setLocation(3, -4);
  }

  private void attachToThis(boolean b) {
    boolean[][] piece = currentPiece.getBlock();

    Color[][] well = b ? this.board : this.tempBoard;

    for (int i = 0; i < piece.length; i++) {
      for (int j = 0; j < piece.length; j++) {

        if (b && piece[i][j] && currPosition.y + i < 0) {
          gameOver = true;
          return;
        }

        if (piece[i][j] && currPosition.y + i >= 0) {
          well[currPosition.y + i][currPosition.x + j] = currentPiece.getColor();
        }
      }
    }

  }

  private boolean attachable(int x, int y) {
    boolean[][] piece = currentPiece.getBlock();
    int yTest = currPosition.y + y;
    int xTest = currPosition.x + x;

    for (int i = 0; i < piece.length; i++) {
      for (int j = 0; j < piece.length; j++) {
        int to_x = j + xTest;
        int to_y = i + yTest;
        if (piece[i][j]) {
          if (0 > to_x || to_x >= col || to_y >= row ||
                  to_y >= 0 && !board[to_y][to_x].equals(Color.BLACK)) {
            return true;
          }
        }
      }
    }
    return false;
  }

  private void removeLines() {
    linesRemoved = 0;
    for (int i = 0; i < board.length; i++) {
      if (canRemove(board[i])) {
        linesRemoved++;
        removeAndUpdate(i);
      }
    }

    addPoints(linesRemoved);
  }

  private boolean canRemove(Color[] row) {
    for (Color x : row) {
      if (Color.BLACK.equals(x)) {
        return false;
      }
    }

    return true;
  }

  private void removeAndUpdate(int row) {
    Color[] blank = new Color[col];
    Arrays.fill(blank, Color.BLACK);

    for (int i = row; i >= 0; i--) {
      board[i] = i == 0 ? blank : Arrays.copyOf(board[i - 1], col);
    }
  }

  private void addPoints(int num) {
    switch (num) {
      case 1:
        score += 100;
        break;
      case 2:
        score += 300;
        break;
      case 3:
        score += 500;
        break;
      case 4:
        score += 800;
        break;
    }
  }

  @Override
  public int getLinesRemoved() {
    return this.linesRemoved;
  }

  @Override
  public void setGameOver(boolean b) {
    this.gameOver = b;
  }

  public String info() {
    return ("Название: Тетрис\nСоздатель: Коргачева Элина ИКПИ-01\n" +
            "Команды:\nВверх - поворот фигуры\n" +
            "Вниз - ускорение падения вниз\n" +
            "Вправо - перенос фигуры враво\n" +
            "Влево -перенос фигуры влево\n" +
            "X - сохранить фигуру/взять сохраненную фигуру\n" +
            "С-перенос фигуры вниз");
  }

  public void addScoreToFile(int score,String name) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter("score.txt", true));
    i++;
    String text = score + "\t " + name + "\n" ;
    writer.write(text);
    writer.close();
  }
  public ArrayList<String>  getScoreFile() throws IOException {
    File f = new File("/Users/elina/Downloads/TetrisGame-master/score.txt");
    BufferedReader fin = new BufferedReader(new FileReader(f));
    ArrayList<String> text = new ArrayList<>();
    String line;
    // размер по умолчанию 10
    while ((line=fin.readLine()) != null)
      text.add(line);
    Collections.sort(text,Collections.reverseOrder());
    ArrayList < String > intList = new ArrayList <>();
    for ( int i = 0 ; i < 5 ; i ++) {
      intList . add ( text.get(i));
    }
    fin.close();
    return intList;
  }

  public void setName(String name){
    this.name=name;
  };
  public String getName(){
    return this.name;
  };
}
