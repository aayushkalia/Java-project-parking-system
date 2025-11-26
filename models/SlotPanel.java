import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SlotPanel extends Panel {
    private Panel grid;
    private final ParkingApplet controller; 

    public SlotPanel(ParkingApplet controller) {
        this.controller = controller;
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(240, 240, 240)); 

        Label title = new Label("Parking Slot Status", Label.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(50, 50, 50));
        add(title, BorderLayout.NORTH);

        Panel gridContainer = new Panel(new FlowLayout(FlowLayout.CENTER, 0, 20)); 
        gridContainer.setBackground(new Color(240, 240, 240));

        grid = new Panel(new GridLayout(6, 5, 12, 12));
        grid.setBackground(new Color(240, 240, 240));
        
        grid.setPreferredSize(new Dimension(650, 450)); 
        gridContainer.add(grid);
        
        add(gridContainer, BorderLayout.CENTER);

        buildGrid();
    }
    
    public SlotPanel() {
        this(null);
    }

    public void buildGrid() {
        grid.removeAll();
        
        try {
            for (final Slot s : SlotManager.getSlots()) {
                
                String buttonText;

                double price = SlotManager.getParkingPrice(s.getType()); 
                
                if (s.isOccupied()) {
                   
                    buttonText = "S" + s.getId() + " | " + s.getVehicleNo() + " (Rs " + (int)price + ")";
                } else {
                   
                    buttonText = "S" + s.getId() + " (" + s.getType() + ") Rs " + (int)price;
                }
                
                final Button b = new Button(buttonText);
                
                // Adjust font size slightly if text is long
                b.setFont(new Font("Segoe UI", Font.BOLD, s.isOccupied() ? 12 : 13)); 
                b.setForeground(Color.WHITE);
                
                b.setBackground(s.isOccupied() ? new Color(220, 53, 69) : new Color(40, 167, 69)); 
                
                b.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String info = "Slot " + s.getId() + " | Type: " + s.getType()
                            + " | Status: " + (s.isOccupied() ? "OCCUPIED" : "FREE");
                        
                        // Add vehicle info and price to the dialog
                        if (s.isOccupied() && s.getVehicleNo() != null)
                            info += "\nVehicle: " + s.getVehicleNo();
                        info += "\nRate per hour: Rs " + (int)price;

                        if (s.isOccupied()) {
                            int opt = showConfirm("Free slot S" + s.getId() + "? \n" + s.getVehicleNo());
                            if (opt == 0) {
                                s.free();
                                buildGrid();
                            }
                        } else {
                            showMessage(info);
                        }
                    }
                });
                grid.add(b);
            }
        } catch (NullPointerException e) {
            Label errorLbl = new Label("Parking Data Unavailable. Check SlotManager initialization.", Label.CENTER);
            errorLbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
            errorLbl.setForeground(new Color(183, 28, 28));
            grid.setLayout(new GridLayout(1, 1));
            grid.add(errorLbl);
        }
        
        validate();
        repaint();
    }

    private int showConfirm(String msg) {
        final int[] res = {1}; 

        Frame f = new Frame();
        final Dialog d = new Dialog(f, "Confirm Action", true); 
        d.setLayout(new BorderLayout(15, 15));
        d.setBackground(new Color(255, 255, 255)); 

        Label msgLabel = new Label(msg, Label.CENTER);
        msgLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        d.add(msgLabel, BorderLayout.CENTER);

        Panel p = new Panel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        p.setBackground(new Color(255, 255, 255));
        
        Button yes = new Button("Yes, Free Slot");
        yes.setFont(new Font("Segoe UI", Font.BOLD, 14));
        yes.setForeground(Color.WHITE);
        yes.setBackground(new Color(255, 193, 7)); 
        
        Button no = new Button("No, Cancel");
        no.setFont(new Font("Segoe UI", Font.BOLD, 14));
        no.setForeground(Color.WHITE);
        no.setBackground(new Color(108, 117, 125)); 

        yes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                res[0] = 0;
                d.dispose();
            }
        });

        no.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                res[0] = 1;
                d.dispose();
            }
        });

        p.add(yes);
        p.add(no);
        d.add(p, BorderLayout.SOUTH);

        d.setSize(350,150);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - d.getWidth()) / 2;
        int y = (screenSize.height - d.getHeight()) / 2;
        d.setLocation(x, y);
        d.setVisible(true);

        d.dispose();
        f.dispose();
        return res[0];
    }

    private void showMessage(String msg) {
        Frame f = new Frame();
        final Dialog d = new Dialog(f, "Slot Info", true); 
        d.setLayout(new BorderLayout(15, 15));
        d.setBackground(new Color(255, 255, 255));

        Label msgLabel = new Label(msg, Label.CENTER);
        msgLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        d.add(msgLabel, BorderLayout.CENTER);

        Button ok = new Button("OK");
        ok.setFont(new Font("Segoe UI", Font.BOLD, 14));
        ok.setForeground(Color.WHITE);
        ok.setBackground(new Color(33, 150, 243)); 
        ok.setPreferredSize(new Dimension(80, 30));

        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                d.dispose();
            }
        });

        Panel p = new Panel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        p.setBackground(new Color(255, 255, 255));
        p.add(ok);
        d.add(p, BorderLayout.SOUTH);

        d.setSize(380,150);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - d.getWidth()) / 2;
        int y = (screenSize.height - d.getHeight()) / 2;
        d.setLocation(x, y);
        d.setVisible(true);

        d.dispose();
        f.dispose();
    }

    public void refresh() { buildGrid(); }
}