package life;

import java.awt.event.ActionListener;

public class GameOfLife extends GUI {
    private final Generator generator;
    private Thread thread;

    public GameOfLife() {
        super(640, 480);
        generator = new Generator(10);
        generator.generate(10);
        start();

        ActionListener toggle = ev -> {
            if (thread.getState() == Thread.State.NEW) {
                thread.start();
                play = true;
            } else if (thread.getState() == Thread.State.TERMINATED) {
                start();
                thread.start();
                play = true;
            } else if (thread.isAlive()) {
                play = !play;
            }
        };
        toggleButton.addActionListener(toggle);

        ActionListener reset = ev -> {
            if (thread.isAlive()) {
                thread.interrupt();
            }
            if (toggleButton.isSelected()) {
                toggleButton.setSelected(false);
            }
            start();
            thread.start();
            play = false;
        };
        resetButton.addActionListener(reset);
    }

    private void start() {
        thread = new Thread(() -> {
            int i = 0;
            for (var gen : generator.generations) {
                drawGen(gen, i++);
                try {
                    Thread.sleep(speed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
                while (!play) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
                }
            }
        });
    }
}
