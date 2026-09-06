import java.awt.*;

public class HomePanel extends Panel {

    public HomePanel(final ParkingApplet applet) {
        this.setLayout(new GridBagLayout());
        this.setBackground(new Color(245, 245, 245));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(20, 20, 20, 20);

        Label title = new Label("Parking Management System", Label.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(new Color(38, 50, 56));
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        this.add(title, c);

        c.gridwidth = 1;
        c.gridy = 1;

        Button dashBtn = createTileButton("Dashboard", new Color(33, 150, 243));
        Button slotsBtn = createTileButton("Slots", new Color(76, 175, 80));
        Button entryBtn = createTileButton("Entry", new Color(255, 193, 7));
        Button exitBtn = createTileButton("Exit", new Color(244, 67, 54));
        Button staffBtn = createTileButton("Staff", new Color(156, 39, 176));
        Button reportsBtn = createTileButton("Reports", new Color(0, 150, 136));

        Button[] buttons = {dashBtn, slotsBtn, entryBtn, exitBtn, staffBtn, reportsBtn};

        for (Button b : buttons) {
            b.addActionListener(e -> {
                String label = b.getLabel();
                switch (label) {
                    case "Dashboard": applet.switchView("DASH"); break;
                    case "Slots": applet.switchView("SLOTS"); break;
                    case "Entry": applet.switchView("ENTRY"); break;
                    case "Exit": applet.switchView("EXIT"); break;
                    case "Staff": applet.switchView("STAFF"); break;
                    case "Reports":
                        applet.refreshReports();
                        applet.switchView("REPORTS");
                        break;
                }
            });
        }

        c.gridx = 0; c.gridy = 2; this.add(dashBtn, c);
        c.gridx = 1; this.add(slotsBtn, c);
        c.gridx = 0; c.gridy = 3; this.add(entryBtn, c);
        c.gridx = 1; this.add(exitBtn, c);
        c.gridx = 0; c.gridy = 4; this.add(staffBtn, c);
        c.gridx = 1; this.add(reportsBtn, c);
    }

    private Button createTileButton(String text, Color bg) {
        Button b = new Button(text);
        b.setFont(new Font("Segoe UI", Font.BOLD, 18));
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setPreferredSize(new Dimension(200, 80));
        return b;
    }
}
