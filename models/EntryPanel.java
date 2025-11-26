import java.awt.*;
import java.awt.event.*;

public class EntryPanel extends Panel {
    private TextField vehicleField;
    private Choice typeChoice;
    private Button enterBtn;
    private final ParkingApplet controller; 

    // FIX: Constructor accepting the controller
    public EntryPanel(ParkingApplet controller) {
        this.controller = controller; 

        setLayout(new GridBagLayout());
        setBackground(new Color(240, 240, 240));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(15, 15, 15, 15); 
        c.anchor = GridBagConstraints.WEST; 

        Label title = new Label("Vehicle Entry Registration");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(50, 50, 50));
        c.gridx = 0; c.gridy = 0; 
        c.gridwidth = 2;
        add(title, c);
        
        Label vLbl = new Label("Vehicle Number:");
        vLbl.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        c.gridy = 2; c.gridx = 0; 
        c.gridwidth = 1; 
        add(vLbl, c);
        vehicleField = new TextField(20);
        vehicleField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        c.gridx = 1;
        add(vehicleField, c);

        Label tLbl = new Label("Vehicle Type:");
        tLbl.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        c.gridy = 3; c.gridx = 0;
        add(tLbl, c);

        typeChoice = new Choice();
        typeChoice.add("2W");
        typeChoice.add("4W");
        typeChoice.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        c.gridx = 1;
        add(typeChoice, c);
        
        enterBtn = new Button("Register Entry");
        enterBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        enterBtn.setForeground(Color.WHITE);
        enterBtn.setBackground(new Color(33, 150, 243));
        enterBtn.setPreferredSize(new Dimension(180, 40));
        
        Panel buttonPanel = new Panel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        buttonPanel.add(enterBtn);
        
        c.gridy = 4; c.gridx = 0; 
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, c);

        enterBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String vno = vehicleField.getText().trim();
                String type = typeChoice.getSelectedItem();
                if (vno.isEmpty()) { showInfo("Enter vehicle number"); return; }
                
                SlotManager.seedSlots();
                int slotId = SlotManager.findAndAssignSlot(type, vno); 
        
                if (slotId == -1) {
                    showInfo("No free slot of type " + type);
                } else {
                    showInfo("Assigned Slot: S" + slotId + " for " + vno);
                    vehicleField.setText("");
                    
                    if (controller != null) {
                        controller.switchView("SLOTS"); // Switch to Slots to see the change
                    }
                }
            }
        });
    }
    
    // Fallback constructor
    public EntryPanel() {
        this(null);
    }

    private void showInfo(String msg) {
        Frame f = new Frame();
        final Dialog d = new Dialog(f, "System Notification", true);
        d.setLayout(new BorderLayout());
        d.setBackground(new Color(255, 255, 255));

        Label msgLabel = new Label(msg, Label.CENTER);
        msgLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        msgLabel.setForeground(new Color(50, 50, 50));
        
        Panel centerPanel = new Panel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        centerPanel.add(msgLabel);
        d.add(centerPanel, BorderLayout.CENTER);

        Button ok = new Button("OK");
        ok.setFont(new Font("Segoe UI", Font.BOLD, 15));
        ok.setBackground(new Color(76, 175, 80));
        ok.setForeground(Color.WHITE);
        ok.setPreferredSize(new Dimension(80, 30));

        ok.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                d.dispose(); 
            }
        });
        Panel p = new Panel(new FlowLayout(FlowLayout.CENTER, 0, 10)); 
        p.add(ok);
        d.add(p, BorderLayout.SOUTH);
        
        d.setSize(380, 150);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - d.getWidth()) / 2;
        int y = (screenSize.height - d.getHeight()) / 2;
        d.setLocation(x, y);
        
        d.setVisible(true); 
        
        f.dispose();
    }
}