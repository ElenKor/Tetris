import Controller.Controller;
import Controller.GameController;
import Model.Game;
import Model.GameModel;
import View.GameView;
import View.IView;

import java.io.IOException;

public class TetrisGame {
  public static void main(String... args) throws IOException {
    Game model = new GameModel();
    IView view = new GameView();
    Controller controller = new GameController(model, view);
    controller.play();
  }
}

