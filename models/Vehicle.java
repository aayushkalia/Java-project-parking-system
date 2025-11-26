// Vehicle.java
public class Vehicle {
    private String vehicleNo;
    private String type; // "2W" or "4W"
    private long entryTime; // millis
    private int slotId;

    public Vehicle(String vehicleNo, String type, int slotId) {
        this.vehicleNo = vehicleNo;
        this.type = type;
        this.entryTime = System.currentTimeMillis();
        this.slotId = slotId;
    }

    public String getVehicleNo() { return vehicleNo; }
    public String getType() { return type; }
    public long getEntryTime() { return entryTime; }
    public int getSlotId() { return slotId; }
}
