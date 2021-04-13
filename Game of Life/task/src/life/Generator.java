package life;

import life.entity.GameField;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;

public class Generator {
    final Deque<GameField> generations;
    private final Random random;
    private final int size;
    public static final char ALIVE = 'O';
    public static final char DEAD = ' ';

    public Generator(int size) {
        this.size = size;
        random = new Random();
        generations = new ArrayDeque<>();
    }

    public Generator(int size, long seed) {
        this(size);
        random.setSeed(seed);
    }

    private GameField next() {
        if (generations.isEmpty()) {
            return initField(size);
        }
        return nextField(generations.peekLast());
    }

    private GameField nextField(GameField current) {
//        GameField newField = new GameField(size);
        char[][] newArr = new char[size][size];
        for (int i = 0; i < current.get().length; i++) {
            for (int j = 0; j < current.get().length; j++) {
                int numOfAlive = countAliveNeighbors(j, i, current);
                if (current.get()[i][j] == ALIVE) {
                    newArr[i][j] = (numOfAlive < 2 || numOfAlive > 3) ? DEAD : ALIVE;
                } else {
                    newArr[i][j] = numOfAlive == 3 ? ALIVE : DEAD;
                }
            }
        }
        return new GameField(newArr);
    }

    private int countAliveNeighbors(int x, int y, GameField field) {
        int counter = 0;
        int[][] directions = { //format {y, x}
                {-1, -1}, {-1, 0},  {-1, 1},
                {0, 1},
                {1, 1}, {1, 0}, {1, -1},
                {0, -1}};
        for (int i = 0; i < directions.length; i++) {
            int neighborX = x + directions[i][1];
            int neighborY = y + directions[i][0];
            if (getNeighbor(neighborX, neighborY, field) == ALIVE) {
                counter++;
            }
        }
        return counter;
    }

    private char getNeighbor(int x, int y, GameField field) {
        int size = field.get().length;
        int validX = getValidCoordinate(x, size);
        int validY = getValidCoordinate(y, size);
        return field.get()[validY][validX];
    }

    private int getValidCoordinate(int XorY, int size) {
        if (XorY < 0) {
            return size + XorY;
        }
        if (XorY >= size) {
           return XorY - size;
        }
        return XorY;
    }

    private GameField initField(int size) {
        GameField field = new GameField(size);
        for (int i = 0; i < field.get().length; i++) {
            for (int j = 0; j < field.get()[i].length; j++) {
                field.get()[i][j] = random.nextBoolean() ? ALIVE : DEAD;
            }
        }
        return field;
    }

    public void generate(int quantity) {
        for (int i = 0; i < quantity + 1; i++) {
            generations.offerLast(next());
        }
    }

    public GameField getLastGeneration() {
        return generations.peekLast();
    }
}
