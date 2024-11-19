package dialog;

import com.formdev.flatlaf.FlatLightLaf;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class TestDialog extends JDialog {

    public TestDialog(JFrame parent, Map<Integer, Integer> tableData) {
        super(parent, "Select a Table", true);
        FlatLightLaf.setup();
        // Set layout for the dialog
        setLayout(new MigLayout("wrap 3", "[grow, fill]", "[]20[]"));

        JLabel instructions = new JLabel("Select a Table:");
        instructions.setFont(new Font("Arial", Font.BOLD, 16));
        add(instructions, "span, align center");

        for (Map.Entry<Integer, Integer> entry : tableData.entrySet()) {
            int tableNumber = entry.getKey();
            int seatCount = entry.getValue();

            JButton tableButton = createTableButton(tableNumber, seatCount);
            add(tableButton);
        }

        setSize(500, 400);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private JButton createTableButton(int tableNumber, int seatCount) {
        String buttonText = "Table " + tableNumber + " (" + seatCount + " seats)";

        // Calculate button width and height based on seat count
        int width = seatCount * 20; // Adjust the width multiplier as needed
        int height = 30 + (seatCount > 4 ? (seatCount - 4) * 5 : 0); // Increase height slightly for larger tables

        JButton tableButton = new JButton(buttonText);
        tableButton.setPreferredSize(new Dimension(width, height)); // Set preferred size based on calculated dimensions
        tableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(TestDialog.this,
                        "You selected Table " + tableNumber + " with " + seatCount + " seats.");
            }
        });

        return tableButton;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Sample data: Map of table number to seat count
            Map<Integer, Integer> tableData = new HashMap<>();
            tableData.put(1, 4);
            tableData.put(2, 6);
            tableData.put(3, 2);
            tableData.put(4, 8);
            tableData.put(5, 4);
            tableData.put(6, 6);
            tableData.put(7, 3);

            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            TestDialog dialog = new TestDialog(frame, tableData);
            dialog.setVisible(true);
        });
    }
}
