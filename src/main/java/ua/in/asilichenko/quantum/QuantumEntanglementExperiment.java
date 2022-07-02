package ua.in.asilichenko.quantum;

import ua.in.asilichenko.quantum.domain.Axis;
import ua.in.asilichenko.quantum.domain.Direction;
import ua.in.asilichenko.quantum.domain.Pair;
import ua.in.asilichenko.quantum.service.ParticleEntangledFactory;
import ua.in.asilichenko.quantum.service.ParticleFactory;
import ua.in.asilichenko.quantum.service.ParticleHiddenInfoFactory;

import java.util.stream.LongStream;

/**
 * Expected:
 * Entangled Quantum particles: 50.00%
 * Hidden information not less than: 55.56% (5/9)
 *
 * @author Alexey Silichenko (a.silichenko@gmail.com)
 * Creation date: 02.07.2022
 */
public class QuantumEntanglementExperiment {

    private static final int ATTEMPT_CNT = 10_000_000;

    private static boolean test(ParticleFactory particleFactory) {
        final Pair pair = particleFactory.makePair();

        final Direction dirPartA = pair.getParticleA().measureSpin(Axis.random());
        final Direction dirPartB = pair.getParticleB().measureSpin(Axis.random());

        return dirPartA != dirPartB;
    }

    public static void main(String[] args) {
        final ParticleEntangledFactory particleEntangledFactory = new ParticleEntangledFactory();
        final long count1 = LongStream.range(0, ATTEMPT_CNT).filter(i -> test(particleEntangledFactory)).count();
        final double res1 = count1 * 1d / ATTEMPT_CNT * 100d;

        System.out.println(String.format("Entangled Quantum Particles:    %.2f%%", res1));

        final ParticleHiddenInfoFactory particleHiddenInfoFactory = new ParticleHiddenInfoFactory();
        final long count2 = LongStream.range(0, ATTEMPT_CNT).filter(i -> test(particleHiddenInfoFactory)).count();
        final double res2 = count2 * 1d / ATTEMPT_CNT * 100d;

        System.out.println(String.format("Hidden Information full:        %.2f%%", res2));

        particleHiddenInfoFactory.setNotAllDirectionsSame(true);
        final long count3 = LongStream.range(0, ATTEMPT_CNT).filter(i -> test(particleHiddenInfoFactory)).count();
        final double res3 = count3 * 1d / ATTEMPT_CNT * 100d;

        System.out.println(String.format("Hidden Information decrease %% : %.2f%%", res3));
    }
}
