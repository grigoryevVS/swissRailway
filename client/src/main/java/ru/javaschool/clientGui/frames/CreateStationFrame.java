package ru.javaschool.clientGui.frames;


import ru.javaschool.clientMain.ClientSocket;
import ru.javaschool.database.entities.Station;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateStationFrame extends JFrame {

    public CreateStationFrame() {
        super("Administrator creating station!");

        setSize(560, 140);
        setVisible(true);
        setLocation(200, 200);
        setResizable(false);

        JPanel createStationPanel = new CreateStationPanel();
        this.add(createStationPanel);
    }

    private class CreateStationPanel extends JPanel {

        private JLabel setStationIdLabel;
        private JTextField setStationIdTextField;
        private JLabel setNameLabel;
        private JTextField setNameTextField;
        private JButton createStationButton;

        public CreateStationPanel() {

            setStationIdLabel = new JLabel("Unique station id: ");
            setStationIdTextField = new JTextField(6);
            setNameLabel = new JLabel("Station name: ");
            setNameTextField = new JTextField(20);
            createStationButton = new JButton("Create station");

            createStationButton.addActionListener(new CreateStationAction());

            this.add(setStationIdLabel);
            this.add(setStationIdTextField);
            this.add(setNameLabel);
            this.add(setNameTextField);
            this.add(createStationButton);

        }

        private class CreateStationAction implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {

                Station station = new Station();
                try {
                    station.setStationId(Long.parseLong(setStationIdTextField.getText()));
                    station.setName(setNameTextField.getText());

                    String response = ClientSocket.getInstance().createStation(station);
                    JOptionPane.showMessageDialog(null, response);
                    setClearFields();

                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(null, "Wrong data!");
                    setClearFields();
                }
            }

            private void setClearFields() {
                setStationIdTextField.setText("");
                setStationIdTextField.requestFocus();
                setNameTextField.setText("");
            }
        }
    }
}