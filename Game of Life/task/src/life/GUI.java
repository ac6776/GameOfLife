package life;

import life.entity.GameField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    GameField currentField;
    int width;
    int height;
    int speed = 100;
    boolean play = false;
    boolean reset = false;
    ActionListener listener;
    JToggleButton toggleButton;
    JButton resetButton;

    public GUI(int width, int height) {
        super("Game of Life");
        this.width = width;
        this.height = height;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);

        init();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void init() {
        Font font = new Font("Comic Sans MS", Font.PLAIN, 12);

        JLabel generalLabel = new JLabel();
        generalLabel.setFont(font);
        generalLabel.setName("GenerationLabel");
        generalLabel.setText("Generation #0");

        JLabel aliveLabel = new JLabel();
        aliveLabel.setFont(font);
        aliveLabel.setName("AliveLabel");
        aliveLabel.setText("Alive: 0");

        toggleButton = new JToggleButton("Play/Stop");
        toggleButton.setName("PlayToggleButton");

        resetButton = new JButton("Reset");
        resetButton.setName("ResetButton");

        JLabel speedMode = new JLabel();
        speedMode.setText("Speed mode: 5");

        JSlider slider = new JSlider(1, 10, 5);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(1);

        JLabel drawing = new JLabel();
        drawing.setName("Drawing");
        drawing.setSize(new Dimension(300, 300));


        JPanel right = new JPanel();
        GroupLayout layout = new GroupLayout(right);
        right.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(toggleButton)
                                .addComponent(resetButton))
                        .addComponent(generalLabel)
                        .addComponent(aliveLabel)
                        .addComponent(speedMode)
                        .addComponent(slider));
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup()
                                .addComponent(toggleButton)
                                .addComponent(resetButton))
                        .addComponent(generalLabel)
                        .addComponent(aliveLabel)
                        .addComponent(speedMode)
                        .addComponent(slider));

        right.setSize(200, 480);
        right.add(toggleButton);
        right.add(resetButton);
        right.add(generalLabel);
        right.add(aliveLabel);
        right.add(speedMode);
        right.add(slider);

        JPanel left = new JPanel();
        left.setBackground(Color.WHITE);
        left.add(drawing);

        add(right);
        add(left);

        slider.addChangeListener(event -> {
            int val = ((JSlider)event.getSource()).getValue();
            speedMode.setText("Speed mode: " + val);
            speed = 1000 / 5 * val;
        });
    }

    public void paint(Graphics g) {
        super.paint(g);
        int paddingLeft = 200;
        int margin = 0;
        int vPort = Math.min(640 - 200, 480);
        int vGap = height - vPort;
        int num = 10;
        int size = vPort / num;

        if (currentField != null) {
            for (int i = vGap, y = 0; i < vGap + vPort; i += size, y++) {
                for (int j = paddingLeft, x = 0; j < vPort + paddingLeft; j += size, x++) {
                    g.drawRect(j, i, size, size);
                    if (currentField.get()[y][x] == Generator.ALIVE) {
                        g.fillRect(j, i, size, size);
                    }
                }
            }
        }
    }

    public void drawGen(GameField field, int genNum) {
        var generalLabel = (JLabel)((JPanel)getContentPane().getComponent(0)).getComponent(2);
        var aliveLabel = (JLabel)((JPanel)getContentPane().getComponent(0)).getComponent(3);
        generalLabel.setText("Generation #" + genNum);
        aliveLabel.setText("Alive: " + field.aliveNum());
        currentField = field;
        repaint();
    }
}
