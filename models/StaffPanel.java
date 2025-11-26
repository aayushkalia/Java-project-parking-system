// StaffPanel.java — Polished AWT UI, same functionality
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class StaffPanel extends Panel {
    private TextField nameField;
    private Button addBtn, removeBtn, refreshBtn, clearBtn;
    private java.awt.List staffList; // interactive AWT List

    // colors / fonts
    private static final Color BG = new Color(250, 251, 252);
    private static final Color CARD = new Color(236, 246, 244);
    private static final Color ACCENT = new Color(6, 92, 92);
    private static final Color BTN_BG = new Color(20, 160, 160);
    private static final Font TITLE_FONT = new Font("Dialog", Font.BOLD, 18);
    private static final Font LABEL_FONT = new Font("Dialog", Font.PLAIN, 13);
    private static final Font BTN_FONT = new Font("Dialog", Font.PLAIN, 13);

    public StaffPanel() {
        setLayout(new BorderLayout(12, 12));
        setBackground(BG);
        setFont(new Font("Dialog", Font.PLAIN, 12));

        // top: title area
        add(buildHeader(), BorderLayout.NORTH);

        // center: main content with list (left) and controls (right)
        Panel content = new Panel(new BorderLayout(10, 10));
        content.setBackground(BG);
        content.add(buildListCard(), BorderLayout.CENTER);
        content.add(buildControlsCard(), BorderLayout.EAST);
        add(content, BorderLayout.CENTER);

        // bottom: small legend / tips
        add(buildFooter(), BorderLayout.SOUTH);

        // initial population
        refreshStaffList();
    }

    private Panel buildHeader() {
        Panel header = new Panel(new BorderLayout());
        header.setBackground(BG);

        Label title = new Label("Staff Management");
        title.setFont(TITLE_FONT);
        title.setForeground(ACCENT);
        title.setAlignment(Label.LEFT);
        header.add(wrapWithPadding(title, 8), BorderLayout.WEST);

        // subtitle
        Label sub = new Label("Add, remove or view staff members");
        sub.setFont(new Font("Dialog", Font.PLAIN, 12));
        sub.setForeground(Color.DARK_GRAY);
        header.add(wrapWithPadding(sub, 8), BorderLayout.SOUTH);

        return header;
    }

    private Panel buildListCard() {
        Panel card = new Panel(new BorderLayout());
        card.setBackground(CARD);
        card.setPreferredSize(new Dimension(480, 240));
        card.add(wrapWithPadding(buildListHeader(), 8), BorderLayout.NORTH);

        staffList = new java.awt.List(10, false);
        staffList.setFont(LABEL_FONT);
        staffList.setBackground(Color.white);
        staffList.setForeground(Color.black);

        // when user selects an item, put it into the input field
        staffList.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                String sel = staffList.getSelectedItem();
                if (sel != null) nameField.setText(sel);
            }
        });

        Panel listWrap = new Panel(new BorderLayout());
        listWrap.setBackground(CARD);
        listWrap.add(staffList, BorderLayout.CENTER);
        card.add(wrapWithPadding(listWrap, 8), BorderLayout.CENTER);

        return wrapWithMargin(card, 6);
    }

    private Panel buildListHeader() {
        Panel p = new Panel(new FlowLayout(FlowLayout.LEFT));
        p.setBackground(CARD);
        Label lbl = new Label("Current Staff");
        lbl.setFont(new Font("Dialog", Font.BOLD, 14));
        lbl.setForeground(ACCENT);
        p.add(lbl);
        return p;
    }

    private Panel buildControlsCard() {
        Panel card = new Panel(new BorderLayout());
        card.setBackground(BG);
        Panel controls = new Panel(new GridLayout(6, 1, 8, 8));
        controls.setBackground(BG);

        // input row
        Panel inputRow = new Panel(new FlowLayout(FlowLayout.LEFT, 6, 6));
        inputRow.setBackground(BG);
        Label nameLbl = new Label("Name:");
        nameLbl.setFont(LABEL_FONT);
        nameLbl.setForeground(Color.DARK_GRAY);
        nameField = new TextField(18);
        nameField.setFont(LABEL_FONT);
        inputRow.add(nameLbl);
        inputRow.add(nameField);

        // buttons
        addBtn = styledButton("Add");
        removeBtn = styledButton("Remove Selected");
        refreshBtn = styledButton("Refresh List");
        clearBtn = styledButton("Clear Input");

        controls.add(inputRow);
        controls.add(addBtn);
        controls.add(removeBtn);
        controls.add(refreshBtn);
        controls.add(clearBtn);

        // small spacer to balance layout
        controls.add(new Panel() {{ setBackground(BG); }});

        card.add(controls, BorderLayout.NORTH);

        // wire actions
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
                    showInfo("Please select a staff member to remove.");
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

        return wrapWithMargin(card, 6);
    }

    private Panel buildFooter() {
        Panel footer = new Panel(new FlowLayout(FlowLayout.LEFT));
        footer.setBackground(BG);
        Label tip = new Label("Tip: Select a name to quickly load it into the input field for editing or removal.");
        tip.setFont(new Font("Dialog", Font.ITALIC, 12));
        tip.setForeground(Color.GRAY);
        footer.add(tip);
        return wrapWithPadding(footer, 6);
    }

    // Helper: styled button
    private Button styledButton(String text) {
        Button b = new Button(text);
        b.setFont(BTN_FONT);
        b.setBackground(BTN_BG);
        b.setForeground(Color.white);
        return b;
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
        d.setLayout(new BorderLayout(8, 8));

        TextArea ta = new TextArea(msg, 6, 40, TextArea.SCROLLBARS_VERTICAL_ONLY);
        ta.setEditable(false);
        ta.setFont(new Font("Dialog", Font.PLAIN, 12));
        ta.setBackground(Color.white);
        d.add(wrapWithPadding(ta, 8), BorderLayout.CENTER);

        Panel p = new Panel(new FlowLayout(FlowLayout.CENTER));
        p.setBackground(Color.white);
        Button ok = new Button("OK");
        ok.setBackground(BTN_BG);
        ok.setForeground(Color.white);
        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                d.setVisible(false);
            }
        });
        p.add(ok);
        d.add(wrapWithPadding(p, 6), BorderLayout.SOUTH);

        d.setSize(420, 200);
        d.setLocation(320, 220);
        d.setVisible(true);
        d.dispose();
        f.dispose();
    }

    // Small layout helpers to add uniform padding/margins
    private Panel wrapWithPadding(Component c, int pad) {
        Panel p = new Panel(new BorderLayout());
        p.setBackground(BG);
        Panel top = new Panel(); top.setBackground(BG); top.setPreferredSize(new Dimension(1, pad));
        Panel bottom = new Panel(); bottom.setBackground(BG); bottom.setPreferredSize(new Dimension(1, pad));
        Panel left = new Panel(); left.setBackground(BG); left.setPreferredSize(new Dimension(pad, 1));
        Panel right = new Panel(); right.setBackground(BG); right.setPreferredSize(new Dimension(pad, 1));
        p.add(top, BorderLayout.NORTH);
        p.add(bottom, BorderLayout.SOUTH);
        p.add(left, BorderLayout.WEST);
        p.add(right, BorderLayout.EAST);
        p.add(c, BorderLayout.CENTER);
        return p;
    }

    private Panel wrapWithMargin(Component c, int margin) {
        Panel outer = new Panel(new BorderLayout());
        outer.setBackground(BG);
        outer.add(wrapWithPadding((Component) c, margin), BorderLayout.CENTER);
        return outer;
    }
}