package ua.in.asilichenko.quantum.service;

import ua.in.asilichenko.quantum.domain.ParticleEntangled;

/**
 * Creation date: 01.07.2022
 */
public class ParticleEntangledFactory extends ParticleFactory<ParticleEntangled> {

    @Override
    protected ParticleEntangled createParticle() {
        return new ParticleEntangled();
    }

    @Override
    protected ParticleEntangled createSibling(ParticleEntangled particle) {
        return new ParticleEntangled(particle);
    }
}
