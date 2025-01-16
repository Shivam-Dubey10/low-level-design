public interface WeightInPonds {
    public double returnWeight();
}

public class WeightMachine implements WeightInPonds {
    @override
    public double returnWeight() {
        return 28.00;
    }
}

public interface WeightInKg {
    public double getWeightInKg();
}

public class WeightMachineKg implements WeightInKg {
    WeightInPonds weightInPounds;
    public WeightMachineKg(WeightInPonds weightInPounds) {
        this.weightInPounds = weightInPounds;
    }
    @override
    public double getWeightInKg() {
        int poundWt = weightInPounds.returnWeight();
        return poundWt*(.45);
    }
}