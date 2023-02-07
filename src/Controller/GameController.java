package Controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import javax.swing.Timer;

import Model.Game;
import View.IView;

//Класс для подключения model, view и обработки пользовательских действий.

public class GameController implements Controller, ViewListener {
  private final Timer timer;
  private final Game model;
  private final IView view;
  private boolean start;
  public GameController(Game model, IView view) {
    Objects.requireNonNull(model);
    Objects.requireNonNull(view);
    this.model = model;
    this.view = view;
    start = false;
    view.addListener(this);
    this.timer = new Timer(600, ((e) -> {
      view.display(model.getSavedPiece(), model.getGameState());
      if (start) {
        moveDown();
      }
      if (model.getIsGameOver()) {
        try {
          model.addScoreToFile(model.getScore(), model.getName());
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
        pause();
        view.showGameOver();
      }

    }));
  }

  @Override
  public void play() {
    timer.start();
  }

  @Override
  public void moveRight() {
    model.moveX(true);
  }

  @Override
  public void moveLeft() {
    model.moveX(false);
  }

  @Override
  public void moveDown() {
    model.softDrop();
  }

  @Override
  public void drop() {
    model.hardDrop();
  }

  @Override
  public void rotate() {
    model.rotateRight();
  }

  @Override
  public void pause() {
    timer.stop();
  }

  @Override
  public void hold() {
    model.save();
  }

  @Override
  public int score() {
    return model.getScore();
  }

  @Override
  public void resume() {
    play();
  }

  @Override
  public void restart() {
    model.clear();
    timer.restart();
    model.setGameOver(false);
  }

  @Override
  public void canStart(boolean b) {
    start = b;
  }

  @Override
  public int lines() {
    return model.getLinesRemoved();
  }
  @Override
  public String about() {
    return model.info();
  }
  public void scoreFile(int score,String name) throws IOException {
     model.addScoreToFile(score,name);
  }
  public ArrayList<String> readScoreFile() throws IOException {
    return model.getScoreFile();
  }
  public void setUserName(String name){
    model.setName(name);
  };
  public String getUserName(){
   return model.getName();
  };

}

