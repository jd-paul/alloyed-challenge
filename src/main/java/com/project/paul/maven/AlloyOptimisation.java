package com.project.paul.maven;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.project.paul.maven.AlloyOptimisation.Alloy;
import com.project.paul.maven.AlloyOptimisation.Chromium;

/**
 * AlloyOptimisation is an abstract class representing different machine types.
 * 
 * Creep resistance of an alloy = Summation of all materials
 * (Creep coefficient * atomic percentages)
 * Cost of an alloy = Summation of all materials
 * (Cost per kg * atomic percentages) / 100
 */

public class AlloyOptimisation {

    // --- Alloy Data Structure ---
    public interface Element {
        double getCreepCoefficient(); // alpha_i

        double getCostPerKg(); // c_i

        String getName();
    }

    // --- Alloy Types ---
    public class Chromium implements Element {
        @Override
        public double getCreepCoefficient() {
            return 2.0911350E+16;
        }

        @Override
        public double getCostPerKg() {
            return 14.0;
        }

        @Override
        public String getName() {
            return "Cr";
        }
    }

    public class Cobalt implements Element {
        @Override
        public double getCreepCoefficient() {
            return 7.2380280E+16;
        }

        @Override
        public double getCostPerKg() {
            return 80.5;
        }

        @Override
        public String getName() {
            return "Co";
        }
    }

    public class Niobium implements Element {
        @Override
        public double getCreepCoefficient() {
            return 1.0352738E+16;
        }

        @Override
        public double getCostPerKg() {
            return 42.5;
        }

        @Override
        public String getName() {
            return "Nb";
        }
    }

    public class Molybdenum implements Element {
        @Override
        public double getCreepCoefficient() {
            return 8.9124547E+16;
        }

        @Override
        public double getCostPerKg() {
            return 16.0;
        }

        @Override
        public String getName() {
            return "Mo";
        }
    }

    // Nickel is unique since it doesn't contribute to creep resistance
    public class Nickel implements Element {
        @Override
        public double getCreepCoefficient() {
            return 0.0; // Nickel does not contribute to creep resistance
        }

        @Override
        public double getCostPerKg() {
            return 8.9;
        }

        @Override
        public String getName() {
            return "Ni";
        }
    }

    /**
     * Alloy is a class representing a metal alloy.
     * It contains a map of elements and their atomic percentages.
     * It can calculate the creep resistance and cost of the alloy.
     */
    public class Alloy {
        // Map element -> atomic percent, or a small container class
        private Map<Element, Double> composition = new HashMap<>();

        public void addElement(Element element, double percent) {
            composition.put(element, percent);
        }

        public double getCreepResistance() {
            double total = 0.0;
            for (Map.Entry<Element, Double> entry : composition.entrySet()) {
                Element e = entry.getKey();
                double x = entry.getValue(); // atomic percent
                // Nickel’s coefficient is 0 anyway, so no harm in adding
                total += e.getCreepCoefficient() * x;
            }
            return total;
        }

        public double getCost() {
            double total = 0.0;
            for (Map.Entry<Element, Double> entry : composition.entrySet()) {
                Element e = entry.getKey();
                double x = entry.getValue();
                total += (e.getCostPerKg() * x) / 100.0;
            }
            return total;
        }
    }

    /**
     * AlloyComputer is a class that will compute through each step passed in. It
     * will perform a brute-force search to find the alloy with the highest creep
     * resistance while avoiding the cost going over £18/kg.
     */
    public class AlloyComputer {

        public Alloy findBestAlloy(double stepCr, double stepCo, double stepNb, double stepMo) {
            Alloy bestAlloy = null;
            double bestResistance = 0.0;

            for (double cr = 14.5; cr <= 22.0; cr += stepCr) {
                for (double co = 0.0; co <= 25.0; co += stepCo) {
                    for (double nb = 0.0; nb <= 1.5; nb += stepNb) {
                        for (double mo = 1.5; mo <= 6.0; mo += stepMo) {
                            double sum = cr + co + nb + mo;
                            if (sum <= 100.0) {
                                double ni = 100.0 - sum;

                                Alloy candidate = new Alloy();
                                candidate.addElement(new Chromium(), cr);
                                candidate.addElement(new Cobalt(), co);
                                candidate.addElement(new Niobium(), nb);
                                candidate.addElement(new Molybdenum(), mo);
                                candidate.addElement(new Nickel(), ni);

                                double cost = candidate.getCost();
                                double creep = candidate.getCreepResistance();

                                if (cost <= 18.0 && creep > bestResistance) {
                                    bestResistance = creep;
                                    bestAlloy = candidate;
                                }
                            }
                        }
                    }
                }
            }
            return bestAlloy;
        }

        public List<Alloy> computeAllAlloys(double stepCr, double stepCo, double stepNb, double stepMo) {
            List<Alloy> alloys = new ArrayList<>();

            for (double cr = 14.5; cr <= 22.0; cr += stepCr) {
                for (double co = 0.0; co <= 25.0; co += stepCo) {
                    for (double nb = 0.0; nb <= 1.5; nb += stepNb) {
                        for (double mo = 1.5; mo <= 6.0; mo += stepMo) {
                            double sum = cr + co + nb + mo;
                            if (sum <= 100.0) {
                                double ni = 100.0 - sum;

                                Alloy candidate = new Alloy();
                                candidate.addElement(new Chromium(), cr);
                                candidate.addElement(new Cobalt(), co);
                                candidate.addElement(new Niobium(), nb);
                                candidate.addElement(new Molybdenum(), mo);
                                candidate.addElement(new Nickel(), ni);

                                if (candidate.getCost() <= 18.0) {
                                    alloys.add(candidate);
                                }
                            }
                        }
                    }
                }
            }
            return alloys;
        }
    }

}
