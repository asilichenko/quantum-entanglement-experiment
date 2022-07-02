package ua.in.asilichenko.quantum.domain;

import java.util.Random;

/**
 * Measurement axis
 */
public enum Axis {

    A, B, C;

    private static final Random rnd = new Random(System.currentTimeMillis());

    public static Axis random() {
        final Axis[] values = values();
        return values[rnd.nextInt(values.length)];
    }
}
