// Slot.java
public class Slot {
    private int id;
    private String type; // "2W" or "4W"
    private boolean occupied;
    private String vehicleNo; // vehicle occupying this slot (or null)

    public Slot(int id, String type) {
        this.id = id;
        this.type = type;
        this.occupied = false;
        this.vehicleNo = null;
    }

    public int getId() { return id; }
    public String getType() { return type; }
    public boolean isOccupied() { return occupied; }
    public String getVehicleNo() { return vehicleNo; }

    public void occupy(String vehicleNo) {
        this.occupied = true;
        this.vehicleNo = vehicleNo;
    }

    public void free() {
        this.occupied = false;
        this.vehicleNo = null;
    }
}
