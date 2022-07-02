package ua.in.asilichenko.quantum.domain;

/**
 * Pair of two particles created at the same time
 */
public class Pair {

    private final Particle particleA;
    private final Particle particleB;

    public Pair(Particle particleA, Particle particleB) {
        this.particleA = particleA;
        this.particleB = particleB;
    }

    public Particle getParticleA() {
        return particleA;
    }

    public Particle getParticleB() {
        return particleB;
    }
}
