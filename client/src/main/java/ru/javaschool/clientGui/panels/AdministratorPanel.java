package ru.javaschool.clientGui.panels;

import ru.javaschool.clientGui.frames.CreateStationFrame;
import ru.javaschool.clientGui.frames.CreateTrainFrame;
import ru.javaschool.clientGui.views.TrainView;
import ru.javaschool.clientMain.ClientSocket;
import ru.javaschool.database.entities.Train;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class AdministratorPanel extends StandartPanel {

    private JTable trainTable;
    private TableModel trainView;
    private JPanel viewPanel;

    public AdministratorPanel() {
        super(new BorderLayout());
        this.addConditionPanel();
        this.addButtonPanel();
        this.addViewPanel();
    }

    @Override
    void addConditionPanel() {
        JPanel conditionPanel = new JPanel(new FlowLayout());
        this.add(conditionPanel, BorderLayout.NORTH);
        conditionPanel.setBackground(Color.RED);
    }

    @Override
    void addButtonPanel() {
        JPanel east = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weighty = 1.0;
        GridLayout gridLayout = new GridLayout(4, 1);
        gridLayout.setHgap(4);
        gridLayout.setVgap(30);
        JPanel buttonPanel = new JPanel(gridLayout);
        east.add(buttonPanel);
        this.add(east, BorderLayout.WEST);

        JButton createNewStationButton = new JButton("CreateStation");
        JButton createNewTrainButton = new JButton("CreateTrain");
        JButton getAllTrainsButton = new JButton("Get all trains");

        getAllTrainsButton.addActionListener(new GetAllTrainsAction());
        createNewTrainButton.addActionListener(new CreateTrainAction());
        createNewStationButton.addActionListener(new CreateStationAction());

        buttonPanel.add(createNewStationButton);
        buttonPanel.add(createNewTrainButton);
        buttonPanel.add(getAllTrainsButton);
    }

    @Override
    void addViewPanel() {
        viewPanel = new JPanel();
        this.add(viewPanel, BorderLayout.CENTER);

        trainView = new TrainView(new ArrayList<Train>());
        trainTable = new JTable(trainView);
        trainTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        trainTable.setFillsViewportHeight(true);
        trainTable.setMaximumSize(viewPanel.getSize());
        trainTable.setUpdateSelectionOnSort(true);
        JScrollPane scrollPaneTrain = new JScrollPane(trainTable);
        viewPanel.add(scrollPaneTrain);
        viewPanel.setVisible(false);
    }

    private class GetAllTrainsAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                trainTable.setModel(new TrainView(ClientSocket.getInstance().getAllTrains()));

                viewPanel.setVisible(true);

                if (trainTable.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Sorry,there are no trains");
                }
            } catch (Exception exc) {
                JOptionPane.showMessageDialog(null, "Sorry, server is not available!");
            }
        }
    }

    private class CreateTrainAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            new CreateTrainFrame();
        }
    }

    private class CreateStationAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new CreateStationFrame();
        }
    }
}
