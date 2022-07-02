package ua.in.asilichenko.quantum.domain;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * A particle if spin directions would preset.
 *
 * @author Alexey Silichenko (a.silichenko@gmail.com)
 */
public class ParticleHiddenInfo extends Particle {

    private final Map<Axis, Direction> spinMap;

    public ParticleHiddenInfo(Direction a, Direction b, Direction c) {
        spinMap = ImmutableMap.of(Axis.A, a, Axis.B, b, Axis.C, c);
    }

    public ParticleHiddenInfo(ParticleHiddenInfo sibling) {
        final Map<Axis, Direction> siblingSpinMap = sibling.getSpinMap();
        this.spinMap = ImmutableMap.of(
                Axis.A, siblingSpinMap.get(Axis.A).opposite(),
                Axis.B, siblingSpinMap.get(Axis.B).opposite(),
                Axis.C, siblingSpinMap.get(Axis.C).opposite()
        );
    }

    private Map<Axis, Direction> getSpinMap() {
        return spinMap;
    }

    @Override
    public Direction measureSpin(Axis axis) {
        return spinMap.get(axis);
    }
}
