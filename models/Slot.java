public class Slot {
    private final int id;
    private final String type; // "2W" or "4W"
    private boolean occupied;
    private String vehicleNo;
    private long entryTime;

    public Slot(int id, String type) {
        this.id = id;
        this.type = type;
        this.occupied = false;
        this.vehicleNo = null;
        this.entryTime = 0;
    }

    public int getId() { return id; }
    public String getType() { return type; }
    public boolean isOccupied() { return occupied; }
    public String getVehicleNo() { return vehicleNo; }
    public long getEntryTime() { return entryTime; }

    public void occupy(String vehicleNo) {
        this.occupied = true;
        this.vehicleNo = vehicleNo;
        this.entryTime = System.currentTimeMillis();
    }

    public void free() {
        this.occupied = false;
        this.vehicleNo = null;
        this.entryTime = 0;
    }
}