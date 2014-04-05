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

        private JLabel setNameLabel;
        private JTextField setNameTextField;
        private JButton createStationButton;

        public CreateStationPanel() {

            setNameLabel = new JLabel("Station name: ");
            setNameTextField = new JTextField(20);
            createStationButton = new JButton("Create station");

            createStationButton.addActionListener(new CreateStationAction());

            this.add(setNameLabel);
            this.add(setNameTextField);
            this.add(createStationButton);

        }

        private class CreateStationAction implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (setNameTextField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Input data!");
                } else {
                    Station station = new Station();
                    station.setName(setNameTextField.getText());

                    String response = ClientSocket.getInstance().createStation(station);
                    JOptionPane.showMessageDialog(null, response);
                }
                setClearFields();
            }

            private void setClearFields() {
                setNameTextField.setText("");
                setNameTextField.requestFocus();
            }
        }
    }
}