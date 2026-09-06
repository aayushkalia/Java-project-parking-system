import java.awt.*;

public class ExitPanel extends Panel {
    private final TextField vehicleField;
    private final Button exitBtn;
    private final ParkingApplet controller; 

    public ExitPanel(ParkingApplet controller) {
        this.controller = controller;

        setLayout(new GridBagLayout());
        setBackground(new Color(240, 240, 240));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(15, 15, 15, 15);
        c.anchor = GridBagConstraints.WEST;

        Label title = new Label("Vehicle Exit Payment");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(50, 50, 50));
        c.gridx = 0; c.gridy = 0;
        c.gridwidth = 2;
        add(title, c);

        Label vLbl = new Label("Enter Vehicle Number:");
        vLbl.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        c.gridy = 1; c.gridx = 0;
        c.gridwidth = 1;
        add(vLbl, c);

        vehicleField = new TextField(20);
        vehicleField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        c.gridx = 1;
        add(vehicleField, c);

        exitBtn = new Button("Process Exit & Pay");
        exitBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        exitBtn.setForeground(Color.WHITE);
        exitBtn.setBackground(new Color(183, 28, 28));
        exitBtn.setPreferredSize(new Dimension(180, 40));

        c.gridy = 2; c.gridx = 0;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        add(exitBtn, c);

        exitBtn.addActionListener(e -> {
            String vno = vehicleField.getText().trim();
            if (vno.isEmpty()) { showInfo("Enter vehicle number"); return; }
            
            double amount = SlotManager.exitVehicle(vno);
            
            if (amount < 0) {
                showInfo("Vehicle not found or not parked");
            } else {
                showInfo("Exit processed.\nAmount Due: Rs" + (int)amount);
                vehicleField.setText("");
                if (controller != null) {
                    controller.switchView("HOME"); 
                }
            }
        });
    }
    
    public ExitPanel() {
        this(null);
    }

    private void showInfo(String msg) {
        Frame f = new Frame();
        Dialog d = new Dialog(f, "System Notification", true);
        d.setLayout(new BorderLayout(15, 15));
        d.setBackground(new Color(255, 255, 255));

        Label msgLabel = new Label(msg, Label.CENTER);
        msgLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        d.add(msgLabel, BorderLayout.CENTER);

        Button ok = new Button("OK"); 
        ok.setFont(new Font("Segoe UI", Font.BOLD, 15));
        ok.setBackground(new Color(33, 150, 243));
        ok.setForeground(Color.WHITE);
        ok.setPreferredSize(new Dimension(80, 30));
        ok.addActionListener(e -> d.dispose());

        Panel p = new Panel(new FlowLayout(FlowLayout.CENTER, 0, 10)); 
        p.setBackground(new Color(255, 255, 255));
        p.add(ok); 
        d.add(p, BorderLayout.SOUTH);

        d.setSize(380,150);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        d.setLocation((screenSize.width - d.getWidth()) / 2, (screenSize.height - d.getHeight()) / 2);
        d.setVisible(true); 
        f.dispose();
    }
}
