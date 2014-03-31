package ru.javaschool.clientGui.panels;

import ru.javaschool.clientGui.frames.CreateRouteFrame;
import ru.javaschool.clientGui.frames.CreateStationFrame;
import ru.javaschool.clientGui.frames.CreateTrainFrame;
import ru.javaschool.clientGui.views.TrainView;
import ru.javaschool.clientMain.ClientSocket;
import ru.javaschool.database.entities.Route;
import ru.javaschool.database.entities.Schedule;
import ru.javaschool.database.entities.Train;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class AdministratorPanel extends StandartPanel {

    private JTable trainTable;
    //private JTable stationTable = new StationView().se;
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
  //      JButton getAllStationsButton = new JButton("Get all stations");
        JButton createNewRouteButton = new JButton("CreateRoute");
        JButton createNewScheduleButton = new JButton("CreateSchedule");

        getAllTrainsButton.addActionListener(new GetAllTrainsAction());
//        getAllStationsButton.addActionListener(new GetAllStations());
        createNewTrainButton.addActionListener(new CreateTrainAction());
        createNewStationButton.addActionListener(new CreateStationAction());
        createNewRouteButton.addActionListener(new CreateRouteAction());
        createNewScheduleButton.addActionListener(new CreateScheduleAction());

        buttonPanel.add(createNewStationButton);
        buttonPanel.add(createNewTrainButton);
        buttonPanel.add(getAllTrainsButton);
        buttonPanel.add(createNewRouteButton);
        buttonPanel.add(createNewScheduleButton);
    }

    @Override
    void addViewPanel() {
        viewPanel = new JPanel();
        this.add(viewPanel, BorderLayout.CENTER);

        TableModel trainView = new TrainView(new ArrayList<Train>());
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

        private JTable trainTable;

        private CreateTrainAction() {
        }

        private CreateTrainAction(JTable trainTable) {
            this.trainTable = trainTable;
        }

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

    private class CreateRouteAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            new CreateRouteFrame();
        }
    }

    private class CreateScheduleAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JPanel panel = new JPanel();
            java.util.List<Route> routeList = ClientSocket.getInstance().getAllRoutes();
            java.util.List<Train> trainList = ClientSocket.getInstance().getAllTrains();
            JComboBox<?> comboBoxTrain = new JComboBox<Object>(trainList.toArray());
            JComboBox<?> comboBoxRoute = new JComboBox<Object>(routeList.toArray());
            panel.add(comboBoxRoute);
            panel.add(comboBoxTrain);

            panel.add(new JLabel("Date departure:"));
            JTextField dateRelease = new JTextField(10);
            panel.add(dateRelease);

            int result = JOptionPane.showConfirmDialog(null, panel,
                    "Add schedule information", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                Schedule schedule = new Schedule();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                try {
                    schedule.setDateTrip(dateFormat.parse(dateRelease.getText()));
                } catch (ParseException exception) {
                    JOptionPane.showMessageDialog(null, "Wrong date format");
                    return;
                }
                Route r = (Route) comboBoxRoute.getSelectedItem();
                Train t = (Train) comboBoxTrain.getSelectedItem();
                schedule.setRoute(r);
                schedule.setTrain(t);
                String message = ClientSocket.getInstance().createSchedule(schedule);
                JOptionPane.showMessageDialog(null, message);
            }
        }
    }

//    private class GetAllStations implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            try {
//                trainTable.setVisible(false);
//                trainTable.setModel(new TrainView(ClientSocket.getInstance().getAllStations()));
//
//                viewPanel.setVisible(true);
//
//                if (trainTable.getRowCount() == 0) {
//                    JOptionPane.showMessageDialog(null, "Sorry,there are no trains");
//                }
//            } catch (Exception exc) {
//                JOptionPane.showMessageDialog(null, "Sorry, server is not available!");
//            }
//        }
//    }
}

