package ru.javaschool.clientGui.frames;

import ru.javaschool.clientMain.ClientSocket;
import ru.javaschool.database.entities.Train;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateTrainFrame extends JFrame {

    public CreateTrainFrame() {
        super("Administrator creating trains!");

        setSize(700, 140);
        setVisible(true);
        setLocation(200, 200);
        setResizable(false);

        JPanel createTrainPanel = new CreateTrainPanel();
        this.add(createTrainPanel);
    }



    static class CreateTrainPanel extends JPanel {

        private JLabel setNameLabel;
        private JTextField setNameTextField;
        private JLabel setNumberOfSeatsLabel;
        private JTextField setNumberOfSeatsTextField;
        private JButton createTrainButton;

        public CreateTrainPanel() {

            setNameLabel = new JLabel("Train name: ");
            setNameTextField = new JTextField(20);
            setNumberOfSeatsLabel = new JLabel("Trains capacity: ");
            setNumberOfSeatsTextField = new JTextField(10);
            createTrainButton = new JButton("Create train");

            createTrainButton.addActionListener(new CreateTrainAction());

            this.add(setNameLabel);
            this.add(setNameTextField);
            this.add(setNumberOfSeatsLabel);
            this.add(setNumberOfSeatsTextField);
            this.add(createTrainButton);

        }

        private class CreateTrainAction implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                Train train = new Train();
                try {
                    train.setName(setNameTextField.getText());
                    train.setNumberOfSeats(Integer.parseInt(setNumberOfSeatsTextField.getText()));

                    String response = ClientSocket.getInstance().createTrain(train);
                    JOptionPane.showMessageDialog(null, response);
                    setClearFields();
                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(null, "Wrong data!");
                    setClearFields();
                }

            }

            private void setClearFields() {
                setNameTextField.setText("");
                setNumberOfSeatsTextField.setText("");
                setNameTextField.requestFocus();
            }
        }
    }
}
