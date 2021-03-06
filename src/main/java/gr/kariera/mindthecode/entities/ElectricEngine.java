package gr.kariera.mindthecode.entities;

import javax.persistence.Entity;

@Entity
public class ElectricEngine extends Engine {
    private double switchFrequency;

    public ElectricEngine() {

    }

    public ElectricEngine(String serialNumber, int size, Car car, double switchFrequency) {
        super(serialNumber, size, car);
        this.switchFrequency = switchFrequency;
    }

    public double getSwitchFrequency() {
        return switchFrequency;
    }

    public void setSwitchFrequency(double switchFrequency) {
        this.switchFrequency = switchFrequency;
    }
}