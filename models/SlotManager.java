
import java.util.ArrayList;
import java.util.HashMap;

public class SlotManager {
    private static ArrayList<Slot> slots = new ArrayList<Slot>();
    private static HashMap<String, Vehicle> vehicles = new HashMap<String, Vehicle>(); // by vehicleNo
    private static ArrayList<String> staff = new ArrayList<String>();
    private static double revenue = 0.0;
    public static void seedSlots() {
        if (!slots.isEmpty()) return;
       
        for (int i = 1; i <= 10; i++) slots.add(new Slot(i, "2W"));
        for (int i = 11; i <= 30; i++) slots.add(new Slot(i, "4W"));
    }
public static double getParkingPrice(String type) {
    if (type.equals("2W")) {
       
        return 20.0;
    } else if (type.equals("4W")) {
       
        return 40.0;
    }
    
    return 0.0;
}
    public static ArrayList<Slot> getSlots() { return slots; }

    public static int findAndAssignSlot(String type, String vehicleNo) {
        for (Slot s : slots) {
            if (!s.isOccupied() && s.getType().equals(type)) {
                s.occupy(vehicleNo);
                Vehicle v = new Vehicle(vehicleNo, type, s.getId());
                vehicles.put(vehicleNo, v);
                return s.getId();
            }
        }
        return -1;
    }

    public static double exitVehicle(String vehicleNo) {
        Vehicle v = vehicles.get(vehicleNo);
        if (v == null) return -1;
        int slotId = v.getSlotId();
        Slot found = null;
        for (Slot s : slots) if (s.getId() == slotId) { found = s; break; }
        if (found == null) return -1;

        double amount = v.getType().equals("2W") ? 10.0 : 20.0;

        
        revenue += amount;
        found.free();
        vehicles.remove(vehicleNo);

        return amount;
    }

    public static Vehicle getVehicle(String vehicleNo) { return vehicles.get(vehicleNo); }

    public static int totalSlots() { return slots.size(); }
    public static int occupiedSlots() {
        int c = 0;
        for (Slot s : slots) if (s.isOccupied()) c++;
        return c;
    }
    public static int freeSlots() { return totalSlots() - occupiedSlots(); }

    public static void addStaff(String name) { staff.add(name); }
    public static boolean removeStaff(String name) { return staff.remove(name); }
    public static ArrayList<String> getStaffList() { return staff; }

    public static double getRevenue() { return revenue; }

    // helper: assign slot manually (admin): set occupied true w/o vehicle
    public static boolean setSlotOccupied(int slotId, boolean occupied) {
        for (Slot s : slots) {
            if (s.getId() == slotId) {
                if (occupied) s.occupy("MANUAL");
                else s.free();
                return true;
            }
        }
        return false;
    }
}
