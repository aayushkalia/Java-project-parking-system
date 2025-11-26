// DashboardPanel.java
import java.awt.*;
import java.awt.event.*;

public class DashboardPanel extends Panel {
    private Label totalLabel, occLabel, freeLabel, revenueLabel;

    public DashboardPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(245,245,245));

        Label title = new Label("Parking Dashboard", Label.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        Panel center = new Panel(new GridLayout(2,2,10,10));
        center.setBackground(new Color(250,250,250));
        totalLabel = new Label();
        occLabel = new Label();
        freeLabel = new Label();
        revenueLabel = new Label();

        center.add(wrap("Total Slots", totalLabel));
        center.add(wrap("Occupied", occLabel));
        center.add(wrap("Free", freeLabel));
        center.add(wrap("Revenue", revenueLabel));

        add(center, BorderLayout.CENTER);

        // seed data if needed
        SlotManager.seedSlots();
        refresh();
    }

    private Panel wrap(String title, Label value) {
        Panel p = new Panel(new BorderLayout());
        Label t = new Label(title, Label.CENTER);
        t.setFont(new Font("Arial", Font.BOLD, 14));
        value.setAlignment(Label.CENTER);
        value.setFont(new Font("Arial", Font.PLAIN, 18));
        p.add(t, BorderLayout.NORTH);
        p.add(value, BorderLayout.CENTER);
        return p;
    }

    // call this when switching back to dashboard
    public void refresh() {
        totalLabel.setText(" " + SlotManager.totalSlots());
        occLabel.setText(" " + SlotManager.occupiedSlots());
        freeLabel.setText(" " + SlotManager.freeSlots());
        revenueLabel.setText(" ₹" + SlotManager.getRevenue());
    }
}
