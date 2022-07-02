package ua.in.asilichenko.quantum.domain;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map;
import java.util.Random;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * A particle if spin directions would random set at the moment of measure.
 *
 * @author Alexey Silichenko (a.silichenko@gmail.com)
 */
public class ParticleEntangled extends Particle {

    private static final Random rnd = new Random(System.currentTimeMillis());

    private static final int AT_OTHER_AXIS_PROBABILITY = 75;

    private Map.Entry<Axis, Direction> spin;
    private ParticleEntangled sibling;

    public ParticleEntangled() {
    }

    public ParticleEntangled(ParticleEntangled sibling) {
        sibling.setSibling(this);
        this.sibling = sibling;
    }

    private void setSibling(ParticleEntangled sibling) {
        this.sibling = sibling;
    }

    private Map.Entry<Axis, Direction> getSpin() {
        return spin;
    }

    private void siblingMeasureEventHandler() {
        final Map.Entry<Axis, Direction> siblingSpin = sibling.getSpin();
        spin = new SimpleImmutableEntry<>(siblingSpin.getKey(), siblingSpin.getValue().opposite());
    }

    private void fireSiblingMeasureEvent() {
        if (nonNull(sibling)) sibling.siblingMeasureEventHandler();
    }

    @Override
    public Direction measureSpin(Axis axis) {
        if (isNull(spin)) {
            spin = new SimpleImmutableEntry<>(axis, Direction.random());
            fireSiblingMeasureEvent();
        } else if (spin.getKey() != axis) { // for sibling or measure again on other axis
            final Direction dir = (rnd.nextInt(100) < AT_OTHER_AXIS_PROBABILITY) ? spin.getValue().opposite() : spin.getValue();
            spin = new SimpleImmutableEntry<>(axis, dir);
        }
        return spin.getValue();
    }
}
