package ua.in.asilichenko.quantum.service;

import ua.in.asilichenko.quantum.domain.Pair;
import ua.in.asilichenko.quantum.domain.Particle;

/**
 * Creation date: 01.07.2022
 */
public abstract class ParticleFactory<P extends Particle> {

    public Pair makePair() {
        final P particleA = createParticle();
        final P particleB = createSibling(particleA);
        return new Pair(particleA, particleB);
    }

    protected abstract P createParticle();

    protected abstract P createSibling(P particle);
}
