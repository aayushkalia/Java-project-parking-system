public class Vehicle {
    private final String vehicleNo;
    private final String type; 
    private final long entryTime; 
    private final int slotId;

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
