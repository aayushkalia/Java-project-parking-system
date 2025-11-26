// ParkingApplet.java
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

public class ParkingApplet extends Applet implements ActionListener {
    private CardLayout cards;
    private Panel mainPanel;
    private Panel navBar;
    private Button btnHome, btnDashboard, btnSlots, btnEntry, btnExit, btnStaff, btnReports;

    // panels (one per page)
    private DashboardPanel dashboardPanel;
    private SlotPanel slotPanel;
    private EntryPanel entryPanel;
    private ExitPanel exitPanel;
    private StaffPanel staffPanel;
    private Panel reportsPanel;

    public void init() {
        setLayout(new BorderLayout());
        setBackground(new Color(240,240,240));

        // NAVBAR
        navBar = new Panel(new FlowLayout(FlowLayout.LEFT,8,6));
        navBar.setBackground(new Color(20,120,120));
        btnHome = makeButton("Home");
        btnDashboard = makeButton("Dashboard");
        btnSlots = makeButton("Slots");
        btnEntry = makeButton("Entry");
        btnExit = makeButton("Exit");
        btnStaff = makeButton("Staff");
        btnReports = makeButton("Reports");

        navBar.add(btnHome); navBar.add(btnDashboard); navBar.add(btnSlots);
        navBar.add(btnEntry); navBar.add(btnExit); navBar.add(btnStaff);
        navBar.add(btnReports);

        add(navBar, BorderLayout.NORTH);

        // MAIN CARDS
        cards = new CardLayout();
        mainPanel = new Panel(cards);

        // ensure slots seeded
        SlotManager.seedSlots();

        dashboardPanel = new DashboardPanel();
        slotPanel = new SlotPanel();
        entryPanel = new EntryPanel();
        exitPanel = new ExitPanel();
        staffPanel = new StaffPanel();
        reportsPanel = buildReportsPanel();

        mainPanel.add(dashboardPanel, "HOME");
        mainPanel.add(dashboardPanel, "DASH"); // same dashboard
        mainPanel.add(slotPanel, "SLOTS");
        mainPanel.add(entryPanel, "ENTRY");
        mainPanel.add(exitPanel, "EXIT");
        mainPanel.add(staffPanel, "STAFF");
        mainPanel.add(reportsPanel, "REPORTS");

        add(mainPanel, BorderLayout.CENTER);

        // show home
        cards.show(mainPanel, "HOME");
    }

    private Button makeButton(String text) {
        Button b = new Button(text);
        b.setFont(new Font("Arial", Font.PLAIN, 12));
        b.setBackground(Color.white);
        b.setForeground(new Color(10,90,90));
        b.addActionListener(this);
        return b;
    }

    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == btnHome || src == btnDashboard) {
            dashboardPanel.refresh();
            cards.show(mainPanel, "HOME");
        } else if (src == btnSlots) {
            slotPanel.refresh();
            cards.show(mainPanel, "SLOTS");
        } else if (src == btnEntry) {
            cards.show(mainPanel, "ENTRY");
        } else if (src == btnExit) {
            cards.show(mainPanel, "EXIT");
        } else if (src == btnStaff) {
            cards.show(mainPanel, "STAFF");
        } else if (src == btnReports) {
            refreshReports();
            cards.show(mainPanel, "REPORTS");
        }
    }

    private Panel buildReportsPanel() {
        Panel p = new Panel(new BorderLayout());
        p.setBackground(new Color(250,250,250));
        Label title = new Label("Reports", Label.CENTER); title.setFont(new Font("Arial", Font.BOLD, 18));
        p.add(title, BorderLayout.NORTH);

        Panel center = new Panel(new GridLayout(3,1));
        center.add(new Label("Total slots: " + SlotManager.totalSlots()));
        center.add(new Label("Occupied: " + SlotManager.occupiedSlots()));
        center.add(new Label("Revenue collected: ₹" + (int)SlotManager.getRevenue()));
        p.add(center, BorderLayout.CENTER);
        return p;
    }

    private void refreshReports() {
        // rebuild reports panel quickly by replacing card (simpler: just update dashboard and reports)
        // For simplicity, recreate and replace REPORTS card
        mainPanel.remove(mainPanel.getComponentCount()-1); // last assumed reports
        Panel reportsPanel = buildReportsPanel();
        mainPanel.add(reportsPanel, "REPORTS");
    }
}
