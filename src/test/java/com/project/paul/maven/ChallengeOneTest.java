package com.project.paul.maven;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * ChallengeOneTest - Unit test for AmMachine
 * - Ensures that the price is correctly calculated through decorators.
 */

public class ChallengeOneTest {

    @Test
    public void testMachineDescription() {
        System.out.println("\n--- Challenge one test - Obtain price using decoraters =====\n");

        AmMachine machine = new AmMachine.MediumPowerMachine();
        machine = new AmMachine.ReducedBuildVolume(machine);
        machine = new AmMachine.QuadLaser(machine);
        machine = new AmMachine.PowderRecirculationSystem(machine);

        String description = machine.getDescription();
        System.out.println("A machine with: " + description);

        assertTrue(description.contains("Medium Power Machine"));
        assertTrue(description.contains("Reduced Build Volume"));
        assertTrue(description.contains("Quad Laser"));
        assertTrue(description.contains("Powder Recirculation System"));
    }

    @Test
    public void testMachineCost() {
        AmMachine machine = new AmMachine.MediumPowerMachine();
        machine = new AmMachine.ReducedBuildVolume(machine);
        machine = new AmMachine.QuadLaser(machine);
        machine = new AmMachine.PowderRecirculationSystem(machine);

        double cost = machine.cost();
        System.out.println("Final price: £" + cost);

        assertTrue(cost == 932000);
    }
}