package View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

import Controller.ViewListener;
import Model.Tetrominos.GamePiece;

public class GameView extends JFrame implements IView, ActionListener {
  private final Drawing panel;
  private ViewListener listener;
  private final JPanel scoreboard;
  private final JLabel points;
  private final JLabel finalPoint;
  private final JPanel optionPanel;
  private boolean canPress;
  private boolean canHear;
  private Clip clip;
  private final CardLayout cardPanels;
  private final Container c;
  private final JPanel gameOverPanel;
  private final JPanel startPanel;
  private final JButton soundButton;
  private final JLabel name;
  private  String userName;


   //Конструктор представления игры по умолчанию, который инициализирует компоненты для видимости пользователем.

  public GameView() throws IOException {
    super();
    clip = null;
    panel = new Drawing();
    panel.setBackground(new Color(204, 239, 205));
    cardPanels = new CardLayout();
    optionPanel = new JPanel();
    scoreboard = new JPanel();
    startPanel = new JPanel();
    gameOverPanel = new JPanel();
    soundButton = new JButton();
    points = new JLabel("0");
    finalPoint = new JLabel("0");
    name = new JLabel("No name");
    c = getContentPane();
    c.setLayout(cardPanels);
    initScoreboard();
    initStartPanel();
    JPanel panel2 = new JPanel(new BorderLayout());
    panel2.add(panel, BorderLayout.CENTER);
    panel2.add(scoreboard, BorderLayout.SOUTH);
    panel2.add(optionPanel, BorderLayout.EAST);
    c.add(startPanel);
    c.add(panel2);
    c.add(gameOverPanel);


    setSize(550, 600);
    setTitle("Тетрис");
    setIconImage(new ImageIcon("/Users/elina/Downloads/TetrisGame-master/resources/tetris-icon.png").getImage());
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setResizable(false);

    InputMap buttonMap = (InputMap) UIManager.get("Button.focusInputMap");
    buttonMap.put(KeyStroke.getKeyStroke("pressed SPACE"), "none");
    buttonMap.put(KeyStroke.getKeyStroke("released SPACE"), "none");

    InputMap im = panel2.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
    ActionMap am = panel2.getActionMap();

    im.put(KeyStroke.getKeyStroke("DOWN"), "Down");
    im.put(KeyStroke.getKeyStroke("RIGHT"), "Move Right");
    im.put(KeyStroke.getKeyStroke("LEFT"), "Move Left");
    im.put(KeyStroke.getKeyStroke("UP"), "Rotate");
    im.put(KeyStroke.getKeyStroke("C"), "Drop");
    im.put(KeyStroke.getKeyStroke("X"), "Hold");

    am.put("Down", new TetrisAction("Down"));
    am.put("Move Right", new TetrisAction("Move Right"));
    am.put("Move Left", new TetrisAction("Move Left"));
    am.put("Rotate", new TetrisAction("Rotate"));
    am.put("Drop", new TetrisAction("Drop"));
    am.put("Hold", new TetrisAction("Hold"));

    File file = new File("/Users/elina/Downloads/TetrisGame-master/resources/tetris-themesong.wav");
    try {
      AudioInputStream audio = AudioSystem.getAudioInputStream(file);
      clip = AudioSystem.getClip();
      clip.open(audio);
      clip.loop(Clip.LOOP_CONTINUOUSLY);
    } catch (Exception e) {
    }
    initGameOverPanel();
    initOptionPanel();
    canPress = false;
    canHear = true;
    setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String selectSound = "/Users/elina/Downloads/TetrisGame-master/resources/selection.wav";
    switch (e.getActionCommand()) {
      case "Pause":
        listener.pause();
        canPress = false;
        openSound(selectSound);
        break;
      case "Resume":
        listener.resume();
        canPress = true;
        openSound(selectSound);
        break;
      case "New Game":
        listener.restart();
        canPress = true;
        openSound(selectSound);
        break;
      case "About":
        listener.pause();
        String text = listener.about();
        JOptionPane.showMessageDialog(optionPanel.getComponent(0),text);
        canPress = true;
        openSound(selectSound);
        listener.resume();
        break;
      case "Sound":
        canHear = !canHear;
        String path = canHear ? "/Users/elina/Downloads/TetrisGame-master/resources/speaker.png" : "/Users/elina/Downloads/TetrisGame-master/resources/no-sound.png";
        soundButton.setIcon(new ImageIcon(path));
        openSound(selectSound);
        playSound();
        break;
      case "Restart":
        cardPanels.next(c);
        listener.canStart(false);
        listener.restart();
        openSound(selectSound);
        break;
      case "Start":
        cardPanels.next(c);
        canPress = true;
        listener.canStart(true);
        openSound(selectSound);
        break;
      case "ShowScore":
        cardPanels.next(c);
        try {
          ArrayList<String> text1 =listener.readScoreFile();
          StringBuilder builder = new StringBuilder("<html>");
          builder.append("<H1>");
          builder.append("Таблица Рекордов");
          builder.append("</H1>");
          builder.append("<h2>Результат     Игрок</h2>");
          for (int i = 0; i < text1.size(); i++) {
            builder.append("<pre>");
            builder.append(text1.get(i));
            builder.append("</pre>");
          }
          JOptionPane.showMessageDialog(optionPanel.getComponent(0),builder.toString());
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
        openSound(selectSound);
        listener.restart();
        break;
    }
  }
  private class TetrisAction extends AbstractAction {
    String command;
    TetrisAction(String command) {
      this.command = command;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
      if (canPress) {
        switch (command) {
          case "Down":
            listener.moveDown();
            removedLineSound();
            updatePanel();
            break;
          case "Move Right":
            listener.moveRight();
            updatePanel();

            break;
          case "Move Left":
            listener.moveLeft();
            updatePanel();
            break;
          case "Rotate":
            listener.rotate();
            updatePanel();
            break;
          case "Drop":
            listener.drop();
            updatePanel();
            openSound("/Users/elina/Downloads/TetrisGame-master/resources/fall.wav");
            removedLineSound();
            break;
          case "Hold":
            listener.hold();
            updatePanel();
            break;
        }
      }
    }
  }
  @Override
  public void display(GamePiece saved, Color[][] board) {
    panel.draw(saved, board);
  }
  @Override
  public void addListener(ViewListener listener) {
    this.listener = listener;
  }
  @Override
  public void showGameOver() {
    clip.stop();
    cardPanels.next(c);
    canPress = false;
    openSound("/Users/elina/Downloads/TetrisGame-master/resources/gameover.wav");
  }
  private void initScoreboard() {
    points.setFont(new Font("Impact", Font.BOLD, 18));
    scoreboard.add(name);
    scoreboard.add(points);
    scoreboard.setBackground(new Color(238, 232, 113));
  }

  private void initOptionPanel() {
    optionPanel.setBackground(new Color(204, 239, 205));
    optionPanel.setLayout(new GridLayout(2, 1));
    JPanel buttonPanel = new JPanel(new GridLayout(5, 1));

    JButton pauseButton = new JButton("Пауза");
    pauseButton.setActionCommand("Pause");
    pauseButton.addActionListener(this);

    JButton resumeButton = new JButton("Продолжить");
    resumeButton.setActionCommand("Resume");
    resumeButton.addActionListener(this);

    JButton restartButton = new JButton("Новая Игра");
    restartButton.setActionCommand("New Game");
    restartButton.addActionListener(this);

    JButton InfoButton = new JButton("Об игре");
    InfoButton.setActionCommand("About");
    InfoButton.addActionListener(this);

    ImageIcon sound = new ImageIcon("/Users/elina/Downloads/TetrisGame-master/resources/speaker.png");
    soundButton.setIcon(sound);
    soundButton.setActionCommand("Sound");
    soundButton.addActionListener(this);

    JPanel panelInfo = new JPanel(new GridLayout(1, 1));
    panelInfo.setBackground(new Color(204, 239, 205));

    buttonPanel.add(pauseButton);
    buttonPanel.add(resumeButton);
    buttonPanel.add(restartButton);
    buttonPanel.add(soundButton);
    buttonPanel.add(InfoButton);
    optionPanel.add(panelInfo);
    optionPanel.add(buttonPanel);

  }

  private void updatePanel()  {
    panel.repaint();
    points.setText(Integer.toString(listener.score()));
    finalPoint.setText(Integer.toString(listener.score()));
  }

  private void playSound() {
    if (canHear) {
      clip.start();
    } else {
      clip.stop();
    }
  }

  private void initGameOverPanel() throws IOException {
    gameOverPanel.setBackground(Color.PINK);
    gameOverPanel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    JLabel gmLogo = new JLabel();
    gmLogo.setIcon(new ImageIcon("/Users/elina/Downloads/TetrisGame-master/resources/gameover.jpeg"));
    JLabel finalScore = new JLabel("Результат:");
    finalScore.setForeground(Color.RED);
    finalScore.setFont(new Font("Impact", Font.BOLD, 18));
    finalPoint.setFont(new Font("Impact", Font.BOLD, 18));
    finalPoint.setForeground(Color.RED);
    JButton restart = new JButton("Начать новую игру");
    restart.setActionCommand("Restart");
    restart.addActionListener(this);
    JButton show = new JButton("Таблица рекордов");
    show.setActionCommand("ShowScore");
    show.addActionListener(this);
    gbc.gridx = 1;
    gbc.gridy = 0;
    gameOverPanel.add(gmLogo, gbc);
    gbc.gridy = 1;
    gameOverPanel.add(finalScore, gbc);
    gbc.gridy = 2;
    gameOverPanel.add(finalPoint, gbc);
    gbc.gridy = 3;
    gameOverPanel.add(restart, gbc);
    gbc.gridy = 4;
    gameOverPanel.add(show, gbc);

  }

  private void initStartPanel() {
    startPanel.setBackground(Color.PINK);
    startPanel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    JLabel logo = new JLabel();
    logo.setIcon(new ImageIcon("/Users/elina/Downloads/TetrisGame-master/resources/logo7.png"));
    JButton start = new JButton("Начать игру");
    start.setActionCommand("Start");
    start.addActionListener(this);
    JTextField bigField;
    // Создание текстовых полей
    bigField = new JTextField("Ваше имя", 15);
    // Установка команды действия для поля ввода текста.
    bigField.setActionCommand("myTF");
    // Добавление приемников событий от поля ввода.
    bigField.addActionListener(this);
    // Настройка шрифта
    bigField.setFont(new Font("Dialog", Font.PLAIN, 14));
    bigField.setHorizontalAlignment(JTextField.RIGHT);
    // создать кнопку ok
    JButton jbtnRev = new JButton("Ok") ;
    // Добавление приемников событий от поля ввода и кнопки.
    jbtnRev.addActionListener(this) ;

  // Обработка событий от кнопки и поля ввода текста.
    // Слушатель окончания ввода
    jbtnRev.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // Отображение введенного текста
        if(e.getActionCommand().equals("Ok")) {
          // Нажатие кнопки Ok.
          userName = bigField.getText();
          name.setText(bigField.getText());
          listener.setUserName(bigField.getText());
        } else {

        }
      }
    });
    gbc.gridx = 1;
    gbc.gridy = 0;
    startPanel.add(logo, gbc);
    gbc.gridy = 1;
    startPanel.add(start, gbc);
    gbc.gridy = 2;
    startPanel.add(bigField, gbc);
    gbc.gridy = 3;
    startPanel.add(jbtnRev, gbc);
     gbc.gridy = 4;
  }
  private void openSound(String path) {
    File pathFile = new File(path);
    try {
      AudioInputStream input = AudioSystem.getAudioInputStream(pathFile);
      Clip clip = AudioSystem.getClip();
      clip.open(input);
      clip.start();
    } catch (Exception e) {
      //ничего не делать, так как звук не будет воспроизводиться, если файл не найден
    }
  }

  private void removedLineSound() {
    if (listener.lines() == 4) {
      openSound("/Users/elina/Downloads/TetrisGame-master/resources/clear.wav");
    } else if (listener.lines() != 0) {
      openSound("/Users/elina/Downloads/TetrisGame-master/resources/line.wav");
    }
  }
}
