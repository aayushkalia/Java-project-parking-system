// StaffPanel.java
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class StaffPanel extends Panel {
    private TextField nameField;
    private Button addBtn, removeBtn, listBtn;

    public StaffPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 20));
        setBackground(new Color(245,245,245));

        add(new Label("Staff Name:"));
        nameField = new TextField(15);
        add(nameField);

        addBtn = new Button("Add");
        removeBtn = new Button("Remove");
        listBtn = new Button("List");

        add(addBtn); add(removeBtn); add(listBtn);

        addBtn.addActionListener(new ActionListener(){ public void actionPerformed(ActionEvent e){
            String nm = nameField.getText().trim();
            if (nm.isEmpty()) { showInfo("Enter name"); return; }
            SlotManager.addStaff(nm);
            showInfo("Added " + nm);
            nameField.setText("");
        }});

        removeBtn.addActionListener(new ActionListener(){ public void actionPerformed(ActionEvent e){
            String nm = nameField.getText().trim();
            if (nm.isEmpty()) { showInfo("Enter name"); return; }
            boolean ok = SlotManager.removeStaff(nm);
            showInfo(ok ? "Removed " + nm : "Not found");
            nameField.setText("");
        }});

        listBtn.addActionListener(new ActionListener(){ public void actionPerformed(ActionEvent e){
            ArrayList<String> st = SlotManager.getStaffList();
            StringBuilder sb = new StringBuilder();
            for (int i=0;i<st.size();i++) sb.append((i+1)+". "+st.get(i)+"\n");
            if (st.isEmpty()) sb.append("No staff added");
            showInfo(sb.toString());
        }});
    }

    private void showInfo(String msg) {
        Frame f = new Frame();
        final Dialog d = new Dialog(f, "Staff", true);

        // Dialog d = new Dialog(f, "Staff", true);
        d.setLayout(new BorderLayout());
        TextArea ta = new TextArea(msg, 8, 40, TextArea.SCROLLBARS_VERTICAL_ONLY);
        ta.setEditable(false);
        d.add(ta, BorderLayout.CENTER);
        Button ok = new Button("OK"); ok.addActionListener(new ActionListener(){ public void actionPerformed(ActionEvent e){ d.setVisible(false); }});
        Panel p = new Panel(); p.add(ok); d.add(p, BorderLayout.SOUTH);
        d.setSize(420,240); d.setLocation(200,200); d.setVisible(true); d.dispose(); f.dispose();
    }
}
