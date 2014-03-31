package ru.javaschool.clientGui.frames;


import ru.javaschool.clientGui.views.StationDistanceView;
import ru.javaschool.clientMain.ClientSocket;
import ru.javaschool.database.entities.Route;
import ru.javaschool.database.entities.Station;
import ru.javaschool.database.entities.StationDistance;
import ru.javaschool.database.entities.StationDistanceEPK;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class CreateRouteFrame extends JFrame {


    private JComboBox<?> stationBox;
    private JTextField stationTitleTextField;
    private JTextField appearanceTimeTextField;
    private Route route = new Route();
    private StationDistanceView distanceView;

    public CreateRouteFrame() {
        super("Creating a new route");

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int x = screenSize.width;
        int y = screenSize.height;

        setSize(x / 3 * 2, y / 3 * 2);
        setVisible(true);
        setLocation(x / 5, y / 5);

        JPanel routePanel = new JPanel();



        distanceView = new StationDistanceView(new ArrayList<StationDistance>());
        JPanel viewPanel = new JPanel();
        JTable stationTable = new JTable(distanceView);
        JScrollPane scrollPane = new JScrollPane(stationTable);
        viewPanel.add(scrollPane);
        this.add(viewPanel, BorderLayout.SOUTH);

        JPanel stationPanel = new JPanel();
        java.util.List<Station> stationList = ClientSocket.getInstance().getAllStations();
        stationBox = new JComboBox<Object>(stationList.toArray());
        JLabel sequenceNumberLabel = new JLabel("Sequence number: ");
        JTextField sequenceNumberTextField = new JTextField(3);
        sequenceNumberLabel.setLabelFor(sequenceNumberTextField);
        JLabel timeLabel = new JLabel("Appearance time: ");
        appearanceTimeTextField = new JTextField(15);
        timeLabel.setLabelFor(appearanceTimeTextField);

        JButton createRouteButton = new JButton("Create: ");
        createRouteButton.addActionListener(new CreateRouteAction());

        JButton saveResultButton = new JButton("Save result");
        saveResultButton.addActionListener(new SaveResultAction());
        JLabel stationTitleLabel = new JLabel("Station title: ");
        stationTitleTextField = new JTextField(20);
        stationTitleLabel.setLabelFor(stationTitleTextField);
        stationPanel.add(stationTitleLabel);
        stationPanel.add(stationTitleTextField);

        stationPanel.add(stationBox);
        stationPanel.add(sequenceNumberLabel);
        stationPanel.add(sequenceNumberTextField);
        stationPanel.add(timeLabel);
        stationPanel.add(appearanceTimeTextField);
        stationPanel.add(createRouteButton);
        stationPanel.add(saveResultButton);
        this.add(stationPanel);
    }

    class CreateRouteAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int sequenceNumber = distanceView.getRowCount();
            StationDistance distance = new StationDistance();
            distance.setKey(new StationDistanceEPK());
            distance.getKey().setSequenceNumber(sequenceNumber + 1);
            Station station = (Station)stationBox.getSelectedItem();
            distance.setStation(station);
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            try {
                distance.setAppearTime(dateFormat.parse(appearanceTimeTextField.getText()));
            }
            catch (ParseException exception) {
                JOptionPane.showMessageDialog(null, "Wrong time format");
                return;
            }
            distanceView.distanceList.add(distance);
            distanceView.fireTableDataChanged();
        }
    }

    private class SaveResultAction implements ActionListener {

        public SaveResultAction() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            route.setTitle(stationTitleTextField.getText());
            route.setStationDistances(distanceView.distanceList);
            String result = ClientSocket.getInstance().createRoute(route);
            JOptionPane.showMessageDialog(null, result);
        }
    }
}
