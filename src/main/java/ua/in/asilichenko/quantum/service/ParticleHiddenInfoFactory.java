package ua.in.asilichenko.quantum.service;

import ua.in.asilichenko.quantum.domain.Direction;
import ua.in.asilichenko.quantum.domain.ParticleHiddenInfo;

import java.util.Random;

/**
 * Creation date: 01.07.2022
 */
public class ParticleHiddenInfoFactory extends ParticleFactory<ParticleHiddenInfo> {

    private static final Random rnd = new Random(System.currentTimeMillis());

    private boolean notAllDirectionsSame = false;

    /**
     * Use full recombination or only cases with at least one different spin direction
     *
     * @param notAllDirectionsSame if false - result will be 66.7% otherwise - 5/9 (55.6%)
     */
    public void setNotAllDirectionsSame(boolean notAllDirectionsSame) {
        this.notAllDirectionsSame = notAllDirectionsSame;
    }

    @Override
    protected ParticleHiddenInfo createParticle() {
        final Direction[] arr = new Direction[]{Direction.random(), Direction.random(), Direction.random()};

        if (notAllDirectionsSame && arr[0] == arr[1] && arr[1] == arr[2]) {
            arr[rnd.nextInt(3)] = arr[0].opposite();
        }

        return new ParticleHiddenInfo(arr[0], arr[1], arr[2]);
    }

    @Override
    protected ParticleHiddenInfo createSibling(ParticleHiddenInfo particle) {
        return new ParticleHiddenInfo(particle);
    }
}
