import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

public class ParkingApplet extends Applet implements ActionListener {

    private CardLayout cards;
    private Panel mainPanel;
    private Panel navBar;
    private Button btnHome, btnDashboard, btnSlots, btnEntry, btnExit, btnStaff, btnReports;

    private HomePanel homePanel;
    private DashboardPanel dashboardPanel;
    private SlotPanel slotPanel;
    private EntryPanel entryPanel;
    private ExitPanel exitPanel;
    private StaffPanel staffPanel;
    private Panel reportsPanel;

    public void init() {
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(240, 240, 240));

        this.navBar = new Panel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        this.navBar.setBackground(new Color(38, 50, 56));

        this.btnHome = this.makeButton("Home");
        this.btnDashboard = this.makeButton("Dashboard");
        this.btnSlots = this.makeButton("Slots");
        this.btnEntry = this.makeButton("Entry");
        this.btnExit = this.makeButton("Exit");
        this.btnStaff = this.makeButton("Staff");
        this.btnReports = this.makeButton("Reports");

        this.navBar.add(this.btnHome);
        this.navBar.add(this.btnDashboard);
        this.navBar.add(this.btnSlots);
        this.navBar.add(this.btnEntry);
        this.navBar.add(this.btnExit);
        this.navBar.add(this.btnStaff);
        this.navBar.add(this.btnReports);

        this.add(this.navBar, BorderLayout.NORTH);

        this.cards = new CardLayout();
        this.mainPanel = new Panel(this.cards);

        SlotManager.seedSlots();

        this.homePanel = new HomePanel(this);
        this.dashboardPanel = new DashboardPanel(this);
        this.slotPanel = new SlotPanel(this);
        this.entryPanel = new EntryPanel(this);
        this.exitPanel = new ExitPanel(this);
        this.staffPanel = new StaffPanel();
        this.reportsPanel = this.buildReportsPanel();

        this.mainPanel.add(this.homePanel, "HOME");
        this.mainPanel.add(this.dashboardPanel, "DASH");
        this.mainPanel.add(this.slotPanel, "SLOTS");
        this.mainPanel.add(this.entryPanel, "ENTRY");
        this.mainPanel.add(this.exitPanel, "EXIT");
        this.mainPanel.add(this.staffPanel, "STAFF");
        this.mainPanel.add(this.reportsPanel, "REPORTS");

        this.add(this.mainPanel, BorderLayout.CENTER);

        this.cards.show(this.mainPanel, "HOME");
    }

    private Button makeButton(String text) {
        Button b = new Button(text);
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b.setBackground(new Color(255, 255, 255));
        b.setForeground(new Color(38, 50, 56));
        b.addActionListener(this);
        return b;
    }

    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == this.btnHome) {
            this.switchView("HOME");
        } else if (src == this.btnDashboard) {
            this.switchView("DASH");
        } else if (src == this.btnSlots) {
            this.switchView("SLOTS");
        } else if (src == this.btnEntry) {
            this.switchView("ENTRY");
        } else if (src == this.btnExit) {
            this.switchView("EXIT");
        } else if (src == this.btnStaff) {
            if (showPasswordDialog()) {
                 this.switchView("STAFF");
            }
        } else if (src == this.btnReports) {
            this.refreshReports();
            this.switchView("REPORTS");
        }
    }

    public void switchView(String name) {
        if (name.equals("HOME")) {
            this.cards.show(this.mainPanel, "HOME");
        } else if (name.equals("DASH")) {
            if (this.dashboardPanel != null) this.dashboardPanel.refresh();
            this.cards.show(this.mainPanel, "DASH");
        } else if (name.equals("SLOTS")) {
            if (this.slotPanel != null) this.slotPanel.refresh();
            this.cards.show(this.mainPanel, "SLOTS");
        } else {
            this.cards.show(this.mainPanel, name);
        }
    }

    private boolean showPasswordDialog() {
        final String CORRECT_PASSWORD = "admin123";
        
        Frame f = new Frame();
        final Dialog d = new Dialog(f, "Staff Login Required", true);
        d.setLayout(new BorderLayout(15, 15));
        d.setSize(320, 160);
        d.setBackground(new Color(245, 245, 245));
        
        Panel inputPanel = new Panel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.gridx = 0; c.gridy = 0;
        
        Label lbl = new Label("Password:");
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        inputPanel.add(lbl, c);
        
        c.gridx = 1;
        final TextField passField = new TextField(15);
        passField.setEchoChar('*'); 
        inputPanel.add(passField, c);
        
        d.add(inputPanel, BorderLayout.CENTER);
        
        Panel btnPanel = new Panel(new FlowLayout(FlowLayout.CENTER));
        Button loginBtn = new Button("Login");
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginBtn.setBackground(new Color(33, 150, 243));
        loginBtn.setForeground(Color.WHITE);
        
        final boolean[] isAuthenticated = {false}; 
        
        ActionListener loginAction = e -> {
            if (passField.getText().equals(CORRECT_PASSWORD)) {
                isAuthenticated[0] = true;
                d.dispose();
            } else {
                passField.setText("");
                passField.requestFocus();
            }
        };
        
        loginBtn.addActionListener(loginAction);
        passField.addActionListener(loginAction);

        btnPanel.add(loginBtn);
        d.add(btnPanel, BorderLayout.SOUTH);
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - d.getWidth()) / 2;
        int y = (screenSize.height - d.getHeight()) / 2;
        d.setLocation(x, y);

        d.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                d.dispose();
            }
        });

        d.setVisible(true); 
        f.dispose();
        
        return isAuthenticated[0];
    }

    private Panel buildReportsPanel() {
        Panel p = new Panel(new BorderLayout(20, 20));
        p.setBackground(new Color(255, 255, 255));

        Label title = new Label("System Reports", Label.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(38, 50, 56));
        p.add(title, BorderLayout.NORTH);

        Panel center = new Panel(new GridLayout(3, 1, 10, 10));
        center.setBackground(new Color(255, 255, 255));

        Font dataFont = new Font("Segoe UI", Font.PLAIN, 18);

        int total = 0, occupied = 0;
        double revenue = 0.0;
        try {
            total = SlotManager.totalSlots();
            occupied = SlotManager.occupiedSlots();
            revenue = SlotManager.getRevenue();
        } catch (NullPointerException ignored) {}

        Label totalLbl = new Label("Total slots: " + total, Label.CENTER);
        totalLbl.setFont(dataFont);

        Label occupiedLbl = new Label("Occupied: " + occupied, Label.CENTER);
        occupiedLbl.setFont(dataFont);

        Label revenueLbl = new Label("Revenue collected: Rs" + (int) revenue, Label.CENTER);
        revenueLbl.setFont(dataFont);
        revenueLbl.setForeground(new Color(56, 142, 60));

        center.add(totalLbl);
        center.add(occupiedLbl);
        center.add(revenueLbl);

        Panel padding = new Panel(new GridBagLayout());
        padding.setBackground(new Color(250, 250, 250));
        padding.add(center);

        p.add(padding, BorderLayout.CENTER);
        return p;
    }

    public void refreshReports() {
        this.mainPanel.remove(this.reportsPanel);
        this.reportsPanel = this.buildReportsPanel();
        this.mainPanel.add(this.reportsPanel, "REPORTS");
        this.mainPanel.validate();
    }
}