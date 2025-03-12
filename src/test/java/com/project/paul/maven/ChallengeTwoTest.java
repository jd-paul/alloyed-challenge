package com.project.paul.maven;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

/**
 * ChallengeTwoTest - Unit test for AlloyOptimization
 * - Ensures that the best alloy is found correctly
 * - Verifies that the creep resistance & cost are within expected values
 */

public class ChallengeTwoTest {

    @Test
    public void testFindBestAlloy() {
        System.out.println("\n--- Challenge two test - Find an alloy with the most creep resistance under £18/kg ---\n");

        // Initialize AlloyOptimisation container
        AlloyOptimisation optimisation = new AlloyOptimisation();

        // Define elements (excluding Nickel since it's the base element)
        List<AlloyOptimisation.Element> elements = new ArrayList<>();
        elements.add(optimisation.new Chromium());
        elements.add(optimisation.new Cobalt());
        elements.add(optimisation.new Niobium());
        elements.add(optimisation.new Molybdenum());

        // Define base element (Nickel takes remaining percentage)
        AlloyOptimisation.Element baseElement = optimisation.new Nickel();

        // Run alloy optimization search
        AlloyOptimisation.AlloyComputer computer = optimisation.new AlloyComputer();
        AlloyOptimisation.Alloy bestAlloy = computer.findBestAlloy(elements, baseElement, 18.0);

        // Ensure best alloy is found
        assertNotNull("No valid alloy found within cost limit", bestAlloy);

        // Retrieve actual values
        double calculatedCreepResistance = bestAlloy.getCreepResistance();
        double calculatedCost = bestAlloy.getCost();

        // Print results for debugging
        System.out.println("Best Alloy Found:");
        System.out.println(bestAlloy.getCompositionSummary());
        System.out.printf("Creep Resistance: %.5E (Expected: 1.72999E18)\n", calculatedCreepResistance);
        System.out.printf("Cost: £%.2f (Should be ≤ 18.0)\n", calculatedCost);

        // Assertions (direct values instead of variables)
        assertEquals("Creep resistance does not match!", 1.72999E18, calculatedCreepResistance, 1E16);
        assertEquals("Cost exceeds limit!", 18.0, calculatedCost, 0.1);
    }
}