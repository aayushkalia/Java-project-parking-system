// ExitPanel.java
import java.awt.*;
import java.awt.event.*;

public class ExitPanel extends Panel {
    private TextField vehicleField;
    private Button exitBtn;

    public ExitPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 20));
        setBackground(new Color(245,245,245));

        add(new Label("Vehicle Number:"));
        vehicleField = new TextField(15);
        add(vehicleField);

        exitBtn = new Button("Process Exit");
        add(exitBtn);

        exitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String vno = vehicleField.getText().trim();
                if (vno.isEmpty()) { showInfo("Enter vehicle number"); return; }
                double amount = SlotManager.exitVehicle(vno);
                if (amount < 0) {
                    showInfo("Vehicle not found or not parked");
                } else {
                    showInfo("Exit processed. Amount: ₹" + (int)amount);
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
        Button ok = new Button("OK"); ok.addActionListener(new ActionListener(){ public void actionPerformed(ActionEvent e){ d.setVisible(false); }});
        Panel p = new Panel(); p.add(ok); d.add(p, BorderLayout.SOUTH);
        d.setSize(320,120); d.setLocation(200,200); d.setVisible(true); d.dispose(); f.dispose();
    }
}
