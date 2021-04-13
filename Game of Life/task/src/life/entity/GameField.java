package life.entity;

import static life.Generator.ALIVE;

public class GameField {
    private char[][] field;

    public GameField(int size) {
        field = new char[size][size];
    }

    public GameField(char[][] field) {
        this.field = field;
    }

    public char[][] get() {
        return field;
    }

    public int aliveNum() {
        int counter = 0;
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j] == ALIVE) {
                    counter++;
                }
            }
        }
        return counter;
    }
}
