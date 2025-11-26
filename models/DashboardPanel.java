import java.awt.*;
import java.awt.event.*;

public class DashboardPanel extends Panel {
    private final ParkingApplet controller;
    private static final String FAILED_DATA = "N/A";

    // Inner utility class for styled data cards
    class CardPanel extends Panel {
        public CardPanel(String title, String data, Color bg, ActionListener listener) {
            setLayout(new BorderLayout(5, 5));
            setBackground(bg);
            setPreferredSize(new Dimension(180, 100));

            Label titleLbl = new Label(title, Label.CENTER);
            titleLbl.setFont(new Font("Segoe UI", Font.BOLD, 15));
            titleLbl.setForeground(Color.WHITE);

            Label dataLbl = new Label(data, Label.CENTER);
            dataLbl.setFont(new Font("Segoe UI", Font.BOLD, 28));
            dataLbl.setForeground(Color.WHITE);

            add(titleLbl, BorderLayout.NORTH);
            add(dataLbl, BorderLayout.CENTER);

            if (listener != null) {
                Button invisibleBtn = new Button();
                invisibleBtn.setPreferredSize(new Dimension(1,1));
                add(invisibleBtn, BorderLayout.SOUTH);
                invisibleBtn.addActionListener(listener);
            }
        }
    }

    public void refresh() {
        removeAll();
        buildUI();
        validate();
        repaint();
    }
    
    // FIX: Constructor accepting the controller
    public DashboardPanel(ParkingApplet controller) {
        this.controller = controller;
        setLayout(new BorderLayout());
        setBackground(new Color(250, 250, 250));
        buildUI();
    }
    
    // Fallback constructor for safety if needed
    public DashboardPanel() {
        this(null);
    }

    private void buildUI() {
        Panel header = new Panel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        header.setBackground(new Color(250, 250, 250));
        Label title = new Label("Parking Management Overview", Label.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(new Color(38, 50, 56));
        header.add(title);
        add(header, BorderLayout.NORTH);

        Panel cards = new Panel(new GridBagLayout());
        cards.setBackground(new Color(250, 250, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.anchor = GridBagConstraints.CENTER;

        // FIX: Wrap SlotManager calls in try-catch to prevent constructor failure
        int totalSlots = 0; 
        int occupiedSlots = 0;
        double revenue = 0.0;
        
        try {
             totalSlots = SlotManager.totalSlots(); 
             occupiedSlots = SlotManager.occupiedSlots();
             revenue = SlotManager.getRevenue();
        } catch (NullPointerException e) {
             // Data stays 0, handled gracefully below
        }

        Panel statusCard = new CardPanel(
            "Available / Total Slots",
            (totalSlots == 0 && occupiedSlots == 0) ? FAILED_DATA : ((totalSlots - occupiedSlots) + " / " + totalSlots),
            new Color(33, 150, 243),
            e -> { if (controller != null) controller.switchView("SLOTS"); }
        );
        gbc.gridx = 0;
        gbc.gridy = 0;
        cards.add(statusCard, gbc);

        Panel occupiedCard = new CardPanel(
            "Occupied Vehicles",
            (occupiedSlots == 0 && totalSlots == 0) ? FAILED_DATA : String.valueOf(occupiedSlots),
            new Color(255, 193, 7),
            e -> { /* Placeholder for future vehicle list view */ }
        );
        gbc.gridx = 1;
        cards.add(occupiedCard, gbc);

        Panel revenueCard = new CardPanel(
            "Total Revenue (Rs)",
            (revenue == 0.0 && totalSlots == 0) ? FAILED_DATA : String.valueOf((int)revenue),
            new Color(56, 142, 60),
            e -> { if (controller != null) controller.switchView("REPORTS"); }
        );
        gbc.gridx = 2;
        cards.add(revenueCard, gbc);
        
        add(cards, BorderLayout.CENTER);
    }
}