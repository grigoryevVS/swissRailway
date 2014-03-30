package ru.javaschool.clientGui.panels;


import ru.javaschool.clientGui.views.ScheduleView;
import ru.javaschool.clientGui.views.StationScheduleView;
import ru.javaschool.clientMain.ClientSocket;
import ru.javaschool.database.criteria.ScheduleConstraints;
import ru.javaschool.database.entities.Schedule;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StationSchedulePanel extends StandartPanel {

    private static final long serialVersionUID = -2571062697354965885L;

    private JTable scheduleTable;
    private ScheduleView scheduleView;
    private JTextField stationTextField;

    public StationSchedulePanel() {
        super(new BorderLayout());
        this.addConditionPanel();
        this.addButtonPanel();
        this.addViewPanel();
    }

    @Override
    void addConditionPanel() {

        JPanel conditionPanel = new JPanel(new FlowLayout());
        this.add(conditionPanel, BorderLayout.NORTH);

        JLabel stationLabel = new JLabel("Station ");
        stationTextField = new JTextField(16);
        stationLabel.setLabelFor(stationTextField);

        conditionPanel.add(stationLabel);
        conditionPanel.add(stationTextField);
    }

    @Override
    void addButtonPanel() {

        JPanel east = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weighty = 2.0;
        GridLayout gridLayout = new GridLayout(4, 1);
        gridLayout.setHgap(4);
        gridLayout.setVgap(30);
        JPanel buttonPanel = new JPanel(gridLayout);
        east.add(buttonPanel);
        this.add(east, BorderLayout.WEST);

        JButton setConstraintsButton = new JButton("Set constraints");
        JButton getStandardScheduleButton = new JButton("Get schedule");
        JButton buyTicketButton = new JButton("Buy ticket");

        setConstraintsButton.addActionListener(new SetConstraintsAction());
        buyTicketButton.addActionListener(new BuyTicketAction(scheduleTable));

        buttonPanel.add(setConstraintsButton);
        buttonPanel.add(getStandardScheduleButton);
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

    private class SetConstraintsAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ScheduleConstraints constraints = new ScheduleConstraints();
            constraints.setDate(null);
            constraints.setStationToName("");
            if (!stationTextField.getText().equals("")) {
                constraints.setStationFromName(stationTextField.getText());
            } else {
                constraints.setStationFromName("");
            }
            scheduleTable.setModel
                    (new StationScheduleView(ClientSocket.getInstance().getStationByName
                            (constraints.getStationFromName()),
                            ClientSocket.getInstance().getRevisedSchedule(constraints)));
        }
    }
}
