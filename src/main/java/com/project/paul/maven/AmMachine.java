package com.project.paul.maven;


/**
 * AmMachine is an abstract class representing different machine types.
 * The decorator pattern is used here to add features dynamically
 * without modifying existing classes.
 */
public abstract class AmMachine {
    protected String description = "Unknown Machine";

    public String getDescription() {
        return description;
    }

    public abstract double cost();

    // --- Machine Types ---
    public static class LowPowerMachine extends AmMachine {
        public LowPowerMachine() {
            description = "Low Power Machine (200W)";
        }
        public double cost() {
            return 450000;
        }
    }

    public static class MediumPowerMachine extends AmMachine {
        public MediumPowerMachine() {
            description = "Medium Power Machine (400W)";
        }
        public double cost() {
            return 550000;
        }
    }

    public static class HighPowerMachine extends AmMachine {
        public HighPowerMachine() {
            description = "High Power Machine (500W)";
        }
        public double cost() {
            return 750000;
        }
    }

    // --- Decorater Pattern ---
    public static abstract class MachineFeature extends AmMachine {
        protected AmMachine machine;
        public MachineFeature(AmMachine machine) {
            this.machine = machine;
        }
        public abstract String getDescription();
    }

    public static class QuadLaser extends MachineFeature {
        public QuadLaser(AmMachine machine) {
            super(machine);
        }
        public String getDescription() {
            return machine.getDescription() + ", Quad Laser";
        }
        public double cost() {
            return machine.cost() + 225000;
        }
    }

    public static class ThermalImagingCamera extends MachineFeature {
        public ThermalImagingCamera(AmMachine machine) {
            super(machine);
        }
        public String getDescription() {
            return machine.getDescription() + ", Thermal Imaging Camera";
        }
        public double cost() {
            return machine.cost() + 54000;
        }
    }

    public static class PhotoDiodes extends MachineFeature {
        public PhotoDiodes(AmMachine machine) {
            super(machine);
        }
        public String getDescription() {
            return machine.getDescription() + ", PhotoDiodes";
        }
        public double cost() {
            return machine.cost() + 63000;
        }
    }

    public static class ReducedBuildVolume extends MachineFeature {
        public ReducedBuildVolume(AmMachine machine) {
            super(machine);
        }
        public String getDescription() {
            return machine.getDescription() + ", Reduced Build Volume";
        }
        public double cost() {
            return machine.cost() + 75000;
        }
    }

    public static class PowderRecirculationSystem extends MachineFeature {
        public PowderRecirculationSystem(AmMachine machine) {
            super(machine);
        }
        public String getDescription() {
            return machine.getDescription() + ", Powder Recirculation System";
        }
        public double cost() {
            return machine.cost() + 82000;
        }
    }
}
