package ua.in.asilichenko.quantum.domain;

import java.util.Random;

/**
 * Spin direction
 */
public enum Direction {

    UP,
    DOWN;

    private static final Random rnd = new Random(System.currentTimeMillis());

    public static Direction random() {
        final Direction[] values = Direction.values();
        return values[rnd.nextInt(values.length)];
    }

    public Direction opposite() {
        return UP.equals(this) ? DOWN : UP;
    }
}
