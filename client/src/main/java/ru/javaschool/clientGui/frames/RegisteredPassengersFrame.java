package ru.javaschool.clientGui.frames;


import ru.javaschool.clientGui.views.PassengerView;
import ru.javaschool.clientMain.ClientSocket;
import ru.javaschool.database.entities.Passenger;
import ru.javaschool.database.entities.Schedule;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;


public class RegisteredPassengersFrame extends JFrame {

    private TableModel passengerView;
    private JTable passengerTable;
    private Schedule schedule;

    public RegisteredPassengersFrame(Schedule actualSchedule) {

        super("Administrator creating station!");

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int x = screenSize.width;
        int y = screenSize.height;

        setSize(x / 3 * 2, y / 3 * 2);
        setVisible(true);
        setLocation(x / 5, y / 5);
        setResizable(false);

        this.add(new RegisteredPassengersPanel());

        schedule = actualSchedule;
    }

    private class RegisteredPassengersPanel extends JPanel {

        public RegisteredPassengersPanel() {

            JButton getScheduleButton = new JButton("Get all schedule");
            JButton getRevisedScheduleButton = new JButton("Get revised schedule");
            JButton getRegisteredPassengersButton = new JButton("Get registered passengers");

            JPanel east = new JPanel();
            this.add(east, BorderLayout.EAST);
            east.add(getScheduleButton);
            east.add(getRevisedScheduleButton);
            east.add(getRegisteredPassengersButton);

            try {
                java.util.List<Passenger> passengerList = ClientSocket.getInstance().getRegisteredPassengers(schedule);
                passengerView = new PassengerView(passengerList);
                if (passengerView.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Sorry,there are no registered passengers on this train!");
                } else {
                    passengerTable = new JTable(passengerView);
                    passengerTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                    passengerTable.setFillsViewportHeight(true);
                    JScrollPane scrollPanePass = new JScrollPane(passengerTable);
                    this.add(scrollPanePass);
                }
            } catch (Exception exc) {
                JOptionPane.showMessageDialog(null, "Sorry, server is not available!");
            }
        }
    }
}
