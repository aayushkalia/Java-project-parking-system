// SlotPanel.java
import java.awt.*;
import java.awt.event.*;

public class SlotPanel extends Panel {
    private Panel grid;

    public SlotPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(245,245,245));
        Label title = new Label("Slot Management", Label.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title, BorderLayout.NORTH);

        grid = new Panel(new GridLayout(6,5,8,8));
        grid.setBackground(new Color(245,245,245));
        add(grid, BorderLayout.CENTER);

        buildGrid();
    }

    public void buildGrid() {
        grid.removeAll();
        for (final Slot s : SlotManager.getSlots()) {
            final Button b = new Button("S" + s.getId() + " (" + s.getType() + ")");
            b.setFont(new Font("Arial", Font.PLAIN, 12));
            b.setBackground(s.isOccupied() ? new Color(255,105,97) : new Color(144,238,144));
            b.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String info = "Slot " + s.getId() + " | Type: " + s.getType()
                        + " | Occupied: " + s.isOccupied();
                    if (s.isOccupied() && s.getVehicleNo() != null)
                        info += " | Vehicle: " + s.getVehicleNo();

                    if (s.isOccupied()) {
                        int opt = showConfirm("Slot occupied. Free slot?");
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
        validate();
        repaint();
    }

    // ---------- FIXED VERSION (final Dialog d) ----------
    private int showConfirm(String msg) {
        final int[] res = {1}; // 0 yes, 1 no

        Frame f = new Frame();
        final Dialog d = new Dialog(f, "Confirm", true);   // <-- FIX
        d.setLayout(new BorderLayout());
        d.add(new Label(msg), BorderLayout.CENTER);

        Panel p = new Panel();
        Button yes = new Button("Yes");
        Button no = new Button("No");

        yes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                res[0] = 0;
                d.setVisible(false);
            }
        });

        no.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                res[0] = 1;
                d.setVisible(false);
            }
        });

        p.add(yes);
        p.add(no);
        d.add(p, BorderLayout.SOUTH);

        d.setSize(300,120);
        d.setLocation(200,200);
        d.setVisible(true);

        d.dispose();
        f.dispose();
        return res[0];
    }

    // ---------- ALREADY FIXED VERSION ----------
    private void showMessage(String msg) {
        Frame f = new Frame();
        final Dialog d = new Dialog(f, "Info", true);  // kept final (correct)
        d.setLayout(new BorderLayout());
        d.add(new Label(msg), BorderLayout.CENTER);

        Button ok = new Button("OK");
        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                d.setVisible(false);
            }
        });

        Panel p = new Panel();
        p.add(ok);
        d.add(p, BorderLayout.SOUTH);

        d.setSize(350,120);
        d.setLocation(200,200);
        d.setVisible(true);

        d.dispose();
        f.dispose();
    }

    public void refresh() { buildGrid(); }
}
