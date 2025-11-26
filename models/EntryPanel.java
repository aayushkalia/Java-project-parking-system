// EntryPanel.java
import java.awt.*;
import java.awt.event.*;

public class EntryPanel extends Panel {
    private TextField vehicleField;
    private Choice typeChoice;
    private Button enterBtn;

    public EntryPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 20));
        setBackground(new Color(245,245,245));

        add(new Label("Vehicle Number:"));
        vehicleField = new TextField(15);
        add(vehicleField);

        add(new Label("Type:"));
        typeChoice = new Choice();
        typeChoice.add("2W");
        typeChoice.add("4W");
        add(typeChoice);

        enterBtn = new Button("Register Entry");
        add(enterBtn);

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
                }
            }
        });
    }

    private void showInfo(String msg) {
        Frame f = new Frame();
        final Dialog d = new Dialog(f, "Info", true);
        d.setLayout(new BorderLayout());
        d.add(new Label(msg), BorderLayout.CENTER);

        Button ok = new Button("OK");
        ok.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                d.setVisible(false);
            }
        });

        Panel p = new Panel();
        p.add(ok);
        d.add(p, BorderLayout.SOUTH);

        d.setSize(320,120);
        d.setLocation(200,200);
        d.setVisible(true);

        d.dispose();
        f.dispose();
    }

}
