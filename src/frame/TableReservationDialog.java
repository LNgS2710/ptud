package frame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TableReservationDialog extends JDialog {
    private int numTables = 10; // Total number of tables
    private boolean[] tableStatus; // true if reserved, false if free

    public TableReservationDialog(JFrame parentFrame) {
        super(parentFrame, "Table Reservation", true);
        tableStatus = new boolean[numTables];

        // Panel for buttons
        JPanel tablePanel = new JPanel(new GridLayout(2, 5, 10, 10)); // Grid layout with 2 rows and 5 columns
        JButton[] tableButtons = new JButton[numTables];

        for (int i = 0; i < numTables; i++) {
            tableButtons[i] = new JButton("Table " + (i + 1));
            tableButtons[i].setBackground(Color.GREEN); // Free tables are green

            int tableIndex = i; // Capture the table index
            tableButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!tableStatus[tableIndex]) {
                        tableStatus[tableIndex] = true;
                        tableButtons[tableIndex].setBackground(Color.RED); // Reserved tables turn red
                        JOptionPane.showMessageDialog(TableReservationDialog.this, "Table " + (tableIndex + 1) + " Reserved!");
                    } else {
                        tableStatus[tableIndex] = false;
                        tableButtons[tableIndex].setBackground(Color.GREEN); // Free the table
                        JOptionPane.showMessageDialog(TableReservationDialog.this, "Table " + (tableIndex + 1) + " Freed!");
                    }
                }
            });

            tablePanel.add(tableButtons[i]);
        }

        // Add panel to dialog
        add(tablePanel, BorderLayout.CENTER);
        setSize(500, 300);
        setLocationRelativeTo(parentFrame); // Center on parent
    }

    public static void main(String[] args) {
        // Create a frame to hold the dialog
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Button to open the reservation dialog
        JButton openDialogButton = new JButton("Open Reservation System");
        openDialogButton.addActionListener(e -> {
            TableReservationDialog reservationDialog = new TableReservationDialog(frame);
            reservationDialog.setVisible(true);
        });

        frame.add(openDialogButton, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
