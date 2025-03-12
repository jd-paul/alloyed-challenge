package com.project.paul.maven;

import java.util.ArrayList;
import java.util.List;

import com.project.paul.maven.AlloyOptimisation.Alloy;
import com.project.paul.maven.AlloyOptimisation.Chromium;
import com.project.paul.maven.AlloyOptimisation.Cobalt;
import com.project.paul.maven.AlloyOptimisation.Molybdenum;
import com.project.paul.maven.AlloyOptimisation.Nickel;
import com.project.paul.maven.AlloyOptimisation.Niobium;

/**
 * AlloyOptimisation is the main container for all elements, alloys, and
 * computation logic.
 * - It contains an element interface and various alloy element classes (Cr, Co, Nb, Mo, Ni).
 * - It has an Alloy class that represents a composition with atomic percentages.
 * - AlloyComputer (inside this class) finds the best alloy configuration based on constraints.
 */

public class AlloyOptimisation {

    public static void main(String[] args) {
        System.out.println("\n--- Testing of Table 3 ---\n");

        AlloyOptimisation optimisation = new AlloyOptimisation();

        AlloyOptimisation.Element chromium = optimisation.new Chromium();
        AlloyOptimisation.Element cobalt = optimisation.new Cobalt();
        AlloyOptimisation.Element niobium = optimisation.new Niobium();
        AlloyOptimisation.Element molybdenum = optimisation.new Molybdenum();
        AlloyOptimisation.Element nickel = optimisation.new Nickel(); // Base element (balance)

        // **Manual Testing - Table 3 Fixed Compositions**

        // Test Case 1: Expected Creep: 1.226E18
        Alloy alloy1 = new AlloyOptimisation().new Alloy();
        alloy1.addElement(chromium, 15.0);
        alloy1.addElement(cobalt, 10.0);
        alloy1.addElement(niobium, 1.0);
        alloy1.addElement(molybdenum, 2.0);
        alloy1.addElement(nickel, 100.0 - (15.0 + 10.0 + 1.0 + 2.0));

        double calculatedCreep1 = alloy1.getCreepResistance();
        System.out.printf("First alloy - Creep Resistance: %.3E (Expected: 1.226E18)\n", calculatedCreep1);

        // Test Case 2: Expected Creep: 5.519E17
        Alloy alloy2 = new AlloyOptimisation().new Alloy();
        alloy2.addElement(chromium, 20.0);
        alloy2.addElement(cobalt, 0.0);
        alloy2.addElement(niobium, 0.0);
        alloy2.addElement(molybdenum, 1.5);
        alloy2.addElement(nickel, 100.0 - (20.0 + 0.0 + 0.0 + 1.5));

        double calculatedCreep2 = alloy2.getCreepResistance();
        System.out.printf("Second alloy - Creep Resistance: %.3E (Expected: 5.519E17)\n", calculatedCreep2);

        // Test Case 3: Expected Creep: 2.820E18
        Alloy alloy3 = new AlloyOptimisation().new Alloy();
        alloy3.addElement(chromium, 22.0);
        alloy3.addElement(cobalt, 25.0);
        alloy3.addElement(niobium, 1.5);
        alloy3.addElement(molybdenum, 6.0);
        alloy3.addElement(nickel, 100.0 - (22.0 + 25.0 + 1.5 + 6.0));

        double calculatedCreep3 = alloy3.getCreepResistance();
        System.out.printf("Third alloy -> Creep Resistance: %.3E (Expected: 2.820E18)\n", calculatedCreep3);
    }

    // --- Alloy Data Structure ---
    public interface Element {
        double getCreepCoefficient();
        double getCostPerKg();
        double getMinAtPercent();
        double getMaxAtPercent();
        double getStepSize();
        String getName();
    }

    // --- Alloy Types ---
    public class Chromium implements Element {
        @Override public double getCreepCoefficient() { return 2.0911350E+16; }
        @Override public double getCostPerKg() { return 14.0; }
        @Override public double getMinAtPercent() { return 14.5; }
        @Override public double getMaxAtPercent() { return 22.0; }
        @Override public double getStepSize() { return 0.5; }
        @Override public String getName() { return "Cr"; }
    }

    public class Cobalt implements Element {
        @Override public double getCreepCoefficient() { return 7.2380280E+16; }
        @Override public double getCostPerKg() { return 80.5; }
        @Override public double getMinAtPercent() { return 0.0; }
        @Override public double getMaxAtPercent() { return 25.0; }
        @Override public double getStepSize() { return 1.0; }
        @Override public String getName() { return "Co"; }
    }

    public class Niobium implements Element {
        @Override public double getCreepCoefficient() { return 1.0352738E+16; }
        @Override public double getCostPerKg() { return 42.5; }
        @Override public double getMinAtPercent() { return 0.0; }
        @Override public double getMaxAtPercent() { return 1.5; }
        @Override public double getStepSize() { return 0.1; }
        @Override public String getName() { return "Nb"; }
    }

    public class Molybdenum implements Element {
        @Override public double getCreepCoefficient() { return 8.9124547E+16; }
        @Override public double getCostPerKg() { return 16.0; }
        @Override public double getMinAtPercent() { return 1.5; }
        @Override public double getMaxAtPercent() { return 6.0; }
        @Override public double getStepSize() { return 0.5; }
        @Override public String getName() { return "Mo"; }
    }

    public class Nickel implements Element {
        @Override public double getCreepCoefficient() { return 0.0; }
        @Override public double getCostPerKg() { return 8.9; }
        @Override public double getMinAtPercent() { return 0.0; }
        @Override public double getMaxAtPercent() { return 100.0; }
        @Override public double getStepSize() { return 0.0; }
        @Override public String getName() { return "Ni"; }
    }

    // --- Alloy Representation ---
    public class Alloy {
        private final List<Element> elements = new ArrayList<>();
        private final List<Double> percentages = new ArrayList<>();

        public void addElement(Element element, double percent) {
            elements.add(element);
            percentages.add(percent);
        }

        public double getCreepResistance() {
            double total = 0.0;
            for (int i = 0; i < elements.size(); i++) {
                total += elements.get(i).getCreepCoefficient() * percentages.get(i);
            }
            return total;
        }

        public double getCost() {
            double total = 0.0;
            for (int i = 0; i < elements.size(); i++) {
                total += (elements.get(i).getCostPerKg() * percentages.get(i)) / 100.0;
            }
            return total;
        }

        public String getCompositionSummary() {
            StringBuilder sb = new StringBuilder("Alloy Composition: ");
            for (int i = 0; i < elements.size(); i++) {
                sb.append(String.format("%s: %.1f%%, ", elements.get(i).getName(), percentages.get(i)));
            }
            return sb.substring(0, sb.length() - 2);
        }
    }

    // --- Alloy Computation ---
    public class AlloyComputer {
        private double bestResistance = 0.0;
        private Alloy bestAlloy = null;

        public Alloy findBestAlloy(List<Element> elements, Element baseElement, double maxCost) {
            elements.remove(baseElement); // Remove base element from iteration
            double[] percentages = new double[elements.size()];
            computeBestAlloy(elements, baseElement, percentages, 0, 0.0, maxCost);
            return bestAlloy;
        }

        private void computeBestAlloy(List<Element> elements, Element baseElement,
                                      double[] percentages, int index, double sum, double maxCost) {
            if (index == elements.size()) {
                double basePercentage = 100.0 - sum;
                if (basePercentage < 0) return;

                Alloy candidate = new Alloy();
                for (int i = 0; i < elements.size(); i++) {
                    candidate.addElement(elements.get(i), percentages[i]);
                }
                candidate.addElement(baseElement, basePercentage);

                double cost = candidate.getCost();
                double creep = candidate.getCreepResistance();

                if (cost <= maxCost) {
                    System.out.printf("🔍 Candidate Alloy -> %s | Creep: %.3E | Cost: £%.2f\n",
                            candidate.getCompositionSummary(), creep, cost);
                }

                if (cost <= maxCost && creep > bestResistance) {
                    bestResistance = creep;
                    bestAlloy = candidate;
                }
                return;
            }

            Element element = elements.get(index);
            for (double p = element.getMinAtPercent(); p <= element.getMaxAtPercent(); p += element.getStepSize()) {
                if (sum + p > 100.0) break;
                percentages[index] = p;
                computeBestAlloy(elements, baseElement, percentages, index + 1, sum + p, maxCost);
            }
        }
    }
}
