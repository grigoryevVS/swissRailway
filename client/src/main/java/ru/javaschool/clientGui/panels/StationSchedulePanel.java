package ru.javaschool.clientGui.panels;


import ru.javaschool.clientGui.panels.StandartPanel;
import ru.javaschool.database.criteria.ScheduleConstraints;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StationSchedulePanel extends StandartPanel {

    private static final long serialVersionUID = -2571062697354965885L;

    public StationSchedulePanel(){
        super(new BorderLayout());
        this.addConditionPanel();
        this.addButtonPanel();
        this.addViewPanel();
    }

    @Override
    void addConditionPanel(){

        JPanel conditionPanel = new JPanel(new FlowLayout());
        this.add(conditionPanel, BorderLayout.NORTH);

        JLabel stationLabel = new JLabel("Station ");
        JTextField stationTextField = new JTextField(16);
        stationLabel.setLabelFor(stationTextField);

        conditionPanel.add(stationLabel);
        conditionPanel.add(stationTextField);
    }

    @Override
    void addButtonPanel(){

        JPanel east = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weighty = 2.0;
        GridLayout gridLayout = new GridLayout(4,1);
        gridLayout.setHgap(4);
        gridLayout.setVgap(30);
        JPanel buttonPanel = new JPanel(gridLayout);
        east.add(buttonPanel);
        this.add(east, BorderLayout.WEST);

        JButton setConstraintsButton = new JButton("Set constraints");
        JButton getStandardScheduleButton = new JButton("Get schedule");

        setConstraintsButton.addActionListener(new SetConstraintsAction());

        buttonPanel.add(setConstraintsButton);
        buttonPanel.add(getStandardScheduleButton);
    }

    @Override
    void addViewPanel() {

    }

    private class SetConstraintsAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            ScheduleConstraints constraints = new ScheduleConstraints();
        }
    }
}
