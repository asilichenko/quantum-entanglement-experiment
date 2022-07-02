package ua.in.asilichenko.quantum;

import ua.in.asilichenko.quantum.domain.*;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import java.util.stream.LongStream;

import static java.util.Objects.nonNull;
import static ua.in.asilichenko.quantum.domain.Axis.*;
import static ua.in.asilichenko.quantum.domain.Direction.*;

/**
 * @author Alexey Silichenko (a.silichenko@gmail.com)
 * Creation date: 02.07.2022
 */
public class CompareParticles {

    private static final int TOTAL_ATTEMPTS = 10_000_000;

    private static void countAxisChanging(AtomicReference<Axis> prevAxis, Axis axis,
                                          Axis expectedFrom, Axis expectedTo,
                                          AtomicLong counter, AtomicLong total,
                                          AtomicReference<Direction> prevDir, Direction dir) {
        if (expectedFrom == prevAxis.get() && expectedTo == axis) {
            total.incrementAndGet();
            if (prevDir.get() != dir) counter.incrementAndGet();
        }
    }

    private static void test(Supplier<Particle> particleSupplier) {
        AtomicLong aUpCnt = new AtomicLong(0);
        AtomicLong bUpCnt = new AtomicLong(0);
        AtomicLong cUpCnt = new AtomicLong(0);
        //
        AtomicLong aCnt = new AtomicLong(0);
        AtomicLong bCnt = new AtomicLong(0);
        AtomicLong cCnt = new AtomicLong(0);
// =====
        AtomicLong abCnt = new AtomicLong(0);
        AtomicLong abTotal = new AtomicLong(0);

        AtomicLong bcCnt = new AtomicLong(0);
        AtomicLong bcTotal = new AtomicLong(0);

        AtomicLong caCnt = new AtomicLong(0);
        AtomicLong caTotal = new AtomicLong(0);
        //
        AtomicLong acCnt = new AtomicLong(0);
        AtomicLong acTotal = new AtomicLong(0);

        AtomicLong cbCnt = new AtomicLong(0);
        AtomicLong cbTotal = new AtomicLong(0);

        AtomicLong baCnt = new AtomicLong(0);
        AtomicLong baTotal = new AtomicLong(0);
// =====
        AtomicReference<Axis> prevAxis = new AtomicReference<>();
        AtomicReference<Direction> prevDir = new AtomicReference<>();

        LongStream.range(0, TOTAL_ATTEMPTS).forEach(i -> {
            final Axis axis = Axis.random();
            final Direction dir = particleSupplier.get().measureSpin(axis);

            if (nonNull(prevAxis.get()) && nonNull(prevDir.get())) {
                countAxisChanging(prevAxis, axis, A, B, abCnt, abTotal, prevDir, dir);
                countAxisChanging(prevAxis, axis, B, C, bcCnt, bcTotal, prevDir, dir);
                countAxisChanging(prevAxis, axis, C, A, caCnt, caTotal, prevDir, dir);

                countAxisChanging(prevAxis, axis, A, C, acCnt, acTotal, prevDir, dir);
                countAxisChanging(prevAxis, axis, C, B, cbCnt, cbTotal, prevDir, dir);
                countAxisChanging(prevAxis, axis, B, A, baCnt, baTotal, prevDir, dir);
            }

            prevAxis.set(axis);
            prevDir.set(dir);

            if (A == axis) aCnt.incrementAndGet();
            if (B == axis) bCnt.incrementAndGet();
            if (C == axis) cCnt.incrementAndGet();

            if (UP == dir) {
                if (A == axis) aUpCnt.incrementAndGet();
                if (B == axis) bUpCnt.incrementAndGet();
                if (C == axis) cUpCnt.incrementAndGet();
            }
        });

        System.out.println(String.format("A rate:    %.2f%%", (aCnt.get() * 1d / TOTAL_ATTEMPTS * 100d)));
        System.out.println(String.format("B rate:    %.2f%%", (bCnt.get() * 1d / TOTAL_ATTEMPTS * 100d)));
        System.out.println(String.format("C rate:    %.2f%%", (cCnt.get() * 1d / TOTAL_ATTEMPTS * 100d)));
        System.out.println();
        System.out.println(String.format("A UP rate: %.2f%%", (aUpCnt.get() * 1d / aCnt.get() * 100d)));
        System.out.println(String.format("B UP rate: %.2f%%", (bUpCnt.get() * 1d / bCnt.get() * 100d)));
        System.out.println(String.format("C UP rate: %.2f%%", (cUpCnt.get() * 1d / cCnt.get() * 100d)));
        System.out.println();
        System.out.println(String.format("A -> B:    %.2f%%", (abCnt.get() * 1d / abTotal.get() * 100)));
        System.out.println(String.format("B -> C:    %.2f%%", (bcCnt.get() * 1d / bcTotal.get() * 100)));
        System.out.println(String.format("C -> A:    %.2f%%", (caCnt.get() * 1d / caTotal.get() * 100)));
        System.out.println();
        System.out.println(String.format("A -> C:    %.2f%%", (acCnt.get() * 1d / acTotal.get() * 100)));
        System.out.println(String.format("C -> B:    %.2f%%", (cbCnt.get() * 1d / cbTotal.get() * 100)));
        System.out.println(String.format("B -> A:    %.2f%%", (baCnt.get() * 1d / baTotal.get() * 100)));
    }

    public static void main(String[] args) {
        System.out.println("=======");
        System.out.println("Test particle with hidden info:");
        System.out.println("=======");
        test(() -> new ParticleHiddenInfo(Direction.random(), Direction.random(), Direction.random()));
        System.out.println("#################");
        System.out.println();

        System.out.println("Test quantum random particle:");
        System.out.println("=======");
        final Particle quantumParticle = new ParticleEntangled();
        test(() -> quantumParticle);
        System.out.println("#################");
    }
}
