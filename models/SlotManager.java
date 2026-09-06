import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SlotManager {
    private static final List<Slot> slots = new ArrayList<>();
    private static final Map<String, Vehicle> vehicles = new HashMap<>(); 
    private static final List<String> staff = new ArrayList<>();
    private static double revenue = 0.0;

    private static final double PRICE_2W = 20.0;
    private static final double PRICE_4W = 40.0;
    private static final double FEE_2W_EXIT = 10.0;
    private static final double FEE_4W_EXIT = 20.0;

    public static void seedSlots() {
        if (!slots.isEmpty()) return;
        for (int i = 1; i <= 10; i++) slots.add(new Slot(i, "2W"));
        for (int i = 11; i <= 30; i++) slots.add(new Slot(i, "4W"));
    }

    public static double getParkingPrice(String type) {
        if ("2W".equals(type)) return PRICE_2W;
        if ("4W".equals(type)) return PRICE_4W;
        return 0.0;
    }

    public static List<Slot> getSlots() { return slots; }

    public static int findAndAssignSlot(String type, String vehicleNo) {
        return slots.stream()
            .filter(s -> !s.isOccupied() && s.getType().equals(type))
            .findFirst()
            .map(s -> {
                s.occupy(vehicleNo);
                vehicles.put(vehicleNo, new Vehicle(vehicleNo, type, s.getId()));
                return s.getId();
            }).orElse(-1);
    }

    public static double exitVehicle(String vehicleNo) {
        Vehicle v = vehicles.get(vehicleNo);
        if (v == null) return -1;
        
        Slot found = slots.stream().filter(s -> s.getId() == v.getSlotId()).findFirst().orElse(null);
        if (found == null) return -1;

        double amount = "2W".equals(v.getType()) ? FEE_2W_EXIT : FEE_4W_EXIT;
        revenue += amount;
        found.free();
        vehicles.remove(vehicleNo);

        return amount;
    }

    public static Vehicle getVehicle(String vehicleNo) { return vehicles.get(vehicleNo); }
    public static int totalSlots() { return slots.size(); }
    public static int occupiedSlots() { return (int) slots.stream().filter(Slot::isOccupied).count(); }
    public static int freeSlots() { return totalSlots() - occupiedSlots(); }
    
    public static void addStaff(String name) { staff.add(name); }
    public static boolean removeStaff(String name) { return staff.remove(name); }
    public static List<String> getStaffList() { return staff; }
    public static double getRevenue() { return revenue; }

    public static boolean setSlotOccupied(int slotId, boolean occupied) {
        return slots.stream()
            .filter(s -> s.getId() == slotId)
            .findFirst()
            .map(s -> {
                if (occupied) s.occupy("MANUAL");
                else s.free();
                return true;
            }).orElse(false);
    }
}
