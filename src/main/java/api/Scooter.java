package api;

import jakarta.persistence.*;

@Entity
@Table(name = "scooters")
public class Scooter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String model;
    private int batteryLevel;
    private String status;
    private double pricePerMinute;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public int getBatteryLevel() { return batteryLevel; }
    public void setBatteryLevel(int batteryLevel) { this.batteryLevel = batteryLevel; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getPricePerMinute() { return pricePerMinute; }
    public void setPricePerMinute(double pricePerMinute) { this.pricePerMinute = pricePerMinute; }
}