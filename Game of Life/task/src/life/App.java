package life;

import life.entity.GameField;

import java.io.IOException;
import java.util.Deque;

public class App extends TextInterface implements Runnable {
    private GameField field;

    @Override
    public void run() {
        String input;
        do {
            input = readLine();
        }
        while (!isCorrect("initial", input));

        String size = applyRule("size", input);
        String seed = applyRule("seed", input);
        String numOfGenerations = applyRule("generations", input);
        Generator generator = seed.length() == 0 ?
                new Generator(Integer.parseInt(size)) :
                new Generator(Integer.parseInt(size), Long.parseLong(seed));
        generator.generate(numOfGenerations.length() == 0 ? 10 : Integer.parseInt(numOfGenerations));
//        printField(generator.getLastGeneration());
//        System.out.println();
        printAll(generator.generations);
    }

    public void printField(GameField field) {
        for (char[] row : field.get()) {
            for (char ch : row) {
                System.out.print(ch);
            }
            System.out.println();
        }
    }

    public void printAll(Deque<GameField> generations) {
        System.out.println(generations.size());
        int i = 0;
        while (!generations.isEmpty()) {
            clear();
            System.out.println("Generation: #" + i++);
            System.out.println("Alive: " + generations.peekFirst().aliveNum());
            printField(generations.pollFirst());
            sleep(1000);
        }
    }

    private void clear() {
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        }
        catch (IOException | InterruptedException e) {}
    }

    private void sleep(long mills) {
        try {
            Thread.sleep(mills);
        } catch (IllegalArgumentException | InterruptedException exception) {
            exception.printStackTrace();
        }
    }
}
