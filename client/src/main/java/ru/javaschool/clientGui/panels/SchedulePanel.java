package ru.javaschool.clientGui.panels;


import ru.javaschool.clientGui.frames.AuthorizationFrame;
import ru.javaschool.clientGui.frames.BuyTicketFrame;
import ru.javaschool.clientGui.frames.ClientFrame;
import ru.javaschool.clientGui.frames.RegisteredPassengersFrame;
import ru.javaschool.clientGui.views.ScheduleView;
import ru.javaschool.clientMain.ClientSocket;
import ru.javaschool.database.entities.Schedule;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SchedulePanel extends StandartPanel {

    private JTable scheduleTable;
    private TableModel scheduleView;
    public static JButton getRegisteredPassengersButton;

    public SchedulePanel() {
        super(new BorderLayout());
        this.addConditionPanel();
        this.addButtonPanel();
        this.addViewPanel();
    }

    @Override
    void addConditionPanel() {

        JPanel conditionPanel = new JPanel(new FlowLayout());
        this.add(conditionPanel, BorderLayout.NORTH);

        JLabel from = new JLabel("FROM");
        JTextField fromTextField = new JTextField(14);
        from.setLabelFor(fromTextField);
        JLabel to = new JLabel("TO");
        JTextField toTextField = new JTextField(14);
        to.setLabelFor(toTextField);
        JLabel dateTrip = new JLabel("Date trip");
        JTextField dateTripTextField = new JTextField(8);
        dateTrip.setLabelFor(dateTripTextField);

        conditionPanel.add(from);
        conditionPanel.add(fromTextField);
        conditionPanel.add(to);
        conditionPanel.add(toTextField);
        conditionPanel.add(dateTrip);
        conditionPanel.add(dateTripTextField);
    }

    @Override
    void addButtonPanel() {

        JPanel east = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weighty = 1.0;
        GridLayout gridLayout = new GridLayout(6, 1);
        gridLayout.setHgap(4);
        gridLayout.setVgap(30);
        JPanel buttonPanel = new JPanel(gridLayout);
        east.add(buttonPanel);
        this.add(east, BorderLayout.WEST);

        JButton getAllScheduleButton = new JButton("GetSchedule");
        JButton getRevisedScheduleButton = new JButton("GetRevisedSchedule");
        JButton buyTicketButton = new JButton("buyTicket");
        JButton authorizationButton = new JButton("Authorization");
        JButton logOutButton = new JButton("Log out");
        getRegisteredPassengersButton = new JButton("Get registered passengers");
        if (!ClientFrame.registered) {
            getRegisteredPassengersButton.setEnabled(false);
        }

        getAllScheduleButton.addActionListener(new GetAllScheduleAction());
        getRevisedScheduleButton.addActionListener(new getRevisedScheduleAction());
        buyTicketButton.addActionListener(new BuyTicketAction());
        getRegisteredPassengersButton.addActionListener(new PassengerListAction());
        authorizationButton.addActionListener(new AuthorizationAction());
        logOutButton.addActionListener(new LogOutAction());


        buttonPanel.add(getAllScheduleButton);
        buttonPanel.add(getRevisedScheduleButton);
        buttonPanel.add(buyTicketButton);
        buttonPanel.add(authorizationButton);
        buttonPanel.add(logOutButton);
        buttonPanel.add(getRegisteredPassengersButton);
    }

    @Override
    void addViewPanel() {
        JPanel viewPanel = new JPanel();
        scheduleView = new ScheduleView(new ArrayList<Schedule>());
        this.add(viewPanel, BorderLayout.CENTER);
        scheduleTable = new JTable(scheduleView);
        scheduleTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        scheduleTable.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(scheduleTable);
        viewPanel.add(scrollPane);

    }

    private class AuthorizationAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (ClientFrame.registered) {
                JOptionPane.showMessageDialog(null, "You 've been already registered!");
            } else {
                new AuthorizationFrame();
            }
        }
    }

    private class BuyTicketAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new BuyTicketFrame();
        }
    }

    private class PassengerListAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (scheduleTable.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null, "Please, select concrete row in the scheduleList!");
            } else {
                new RegisteredPassengersFrame(getActualSchedule());
            }
        }

        private Schedule getActualSchedule() {
            Schedule actualSchedule = (Schedule) scheduleView.getValueAt(scheduleTable.getSelectedRow(), scheduleTable.getSelectedColumn());
            return actualSchedule;
        }

    }

    private class GetAllScheduleAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                scheduleTable.setModel(new ScheduleView(ClientSocket.getInstance().getAllSchedule()));

                if (scheduleTable.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Sorry,schedule is empty!");
                }
            } catch (Exception exc) {
                JOptionPane.showMessageDialog(null, "Sorry, server is not available!");
            }
        }
    }

    private class getRevisedScheduleAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class LogOutAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!ClientFrame.registered) {
                JOptionPane.showMessageDialog(null, "You haven't been registered yet!");
            } else {
                int answer = JOptionPane.showConfirmDialog(null, "Do you really want to log out?");
                if (answer == 0) {
                    ClientFrame.registered = false;
                    ClientFrame.bookmark.removeTabAt(2);
                    SchedulePanel.getRegisteredPassengersButton.setEnabled(false);
                }
            }
        }
    }
}
