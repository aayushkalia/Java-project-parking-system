// StaffPanel.java  — Enhanced version
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class StaffPanel extends Panel {
    private TextField nameField;
    private Button addBtn, removeBtn, refreshBtn, clearBtn;
    private java.awt.List staffList; // AWT List so users can select entries

    public StaffPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 245, 245));
        setFont(new Font("Dialog", Font.PLAIN, 12));

        // Header
        Panel header = new Panel(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(new Color(245,245,245));
        Label lbl = new Label("Staff Management");
        lbl.setFont(new Font("Dialog", Font.BOLD, 16));
        lbl.setForeground(new Color(10, 90, 90));
        header.add(lbl);
        add(header, BorderLayout.NORTH);

        // Center: staff list (scrollable)
        staffList = new java.awt.List(10, false);
        staffList.setFont(new Font("Dialog", Font.PLAIN, 13));
        Panel center = new Panel(new BorderLayout());
        center.setBackground(new Color(245,245,245));
        center.setBorder(BorderFactoryLikeEmptyBorder(8));
        center.add(staffList, BorderLayout.CENTER);
        add(center, BorderLayout.CENTER);

        // Right: controls
        Panel controls = new Panel(new GridLayout(6, 1, 6, 6));
        controls.setBackground(new Color(245,245,245));

        nameField = new TextField(15);
        Panel inputRow = new Panel(new FlowLayout(FlowLayout.LEFT, 6, 6));
        inputRow.setBackground(new Color(245,245,245));
        inputRow.add(new Label("Name:"));
        inputRow.add(nameField);

        addBtn = new Button("Add");
        removeBtn = new Button("Remove Selected");
        refreshBtn = new Button("Refresh List");
        clearBtn = new Button("Clear Input");

        addBtn.setFont(new Font("Dialog", Font.PLAIN, 12));
        removeBtn.setFont(new Font("Dialog", Font.PLAIN, 12));
        refreshBtn.setFont(new Font("Dialog", Font.PLAIN, 12));
        clearBtn.setFont(new Font("Dialog", Font.PLAIN, 12));

        controls.add(inputRow);
        controls.add(addBtn);
        controls.add(removeBtn);
        controls.add(refreshBtn);
        controls.add(clearBtn);

        Panel rightWrapper = new Panel(new BorderLayout());
        rightWrapper.setBackground(new Color(245,245,245));
        rightWrapper.add(controls, BorderLayout.NORTH);
        add(rightWrapper, BorderLayout.EAST);

        // Wire actions
        addBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nm = nameField.getText().trim();
                if (nm.isEmpty()) {
                    showInfo("Please enter a staff name.");
                    return;
                }
                SlotManager.addStaff(nm);
                nameField.setText("");
                refreshStaffList();
                showInfo("Added: " + nm);
            }
        });

        removeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String sel = staffList.getSelectedItem();
                if (sel == null) {
                    showInfo("Please select a staff member from the list to remove.");
                    return;
                }
                boolean ok = SlotManager.removeStaff(sel);
                if (ok) {
                    refreshStaffList();
                    showInfo("Removed: " + sel);
                } else {
                    showInfo("Could not remove: " + sel);
                }
            }
        });

        refreshBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshStaffList();
            }
        });

        clearBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                nameField.setText("");
            }
        });

        // Fill nameField when list selection changes
        staffList.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                String sel = staffList.getSelectedItem();
                if (sel != null) nameField.setText(sel);
            }
        });

        // initial population
        refreshStaffList();
    }

    // Refresh the AWT List from SlotManager
    private void refreshStaffList() {
        staffList.removeAll();
        ArrayList<String> st = SlotManager.getStaffList();
        for (String s : st) staffList.add(s);
    }

    // Info dialog helper (uses final Dialog so inner classes can access it)
    private void showInfo(String msg) {
        Frame f = new Frame();
        final Dialog d = new Dialog(f, "Staff", true);
        d.setLayout(new BorderLayout());
        TextArea ta = new TextArea(msg, 6, 40, TextArea.SCROLLBARS_VERTICAL_ONLY);
        ta.setEditable(false);
        ta.setFont(new Font("Dialog", Font.PLAIN, 12));
        d.add(ta, BorderLayout.CENTER);

        Button ok = new Button("OK");
        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                d.setVisible(false);
            }
        });

        Panel p = new Panel();
        p.add(ok);
        d.add(p, BorderLayout.SOUTH);

        d.setSize(420, 200);
        d.setLocation(300, 200); // simple fixed location; adjust if needed
        d.setVisible(true);
        d.dispose();
        f.dispose();
    }

    // Small helper to mimic an empty border for AWT Panel (since AWT lacks BorderFactory)
    private Panel BorderFactoryLikeEmptyBorder(int padding) {
        Panel p = new Panel(new BorderLayout());
        p.setBackground(new Color(245,245,245));
        p.add(new Panel(){{
            setPreferredSize(new Dimension(padding, padding));
            setBackground(new Color(245,245,245));
        }}, BorderLayout.NORTH);
        p.add(new Panel(){{
            setPreferredSize(new Dimension(padding, padding));
            setBackground(new Color(245,245,245));
        }}, BorderLayout.SOUTH);
        p.add(new Panel(){{
            setPreferredSize(new Dimension(padding, padding));
            setBackground(new Color(245,245,245));
        }}, BorderLayout.WEST);
        p.add(new Panel(){{
            setPreferredSize(new Dimension(padding, padding));
            setBackground(new Color(245,245,245));
        }}, BorderLayout.EAST);
        return p;
    }
}
