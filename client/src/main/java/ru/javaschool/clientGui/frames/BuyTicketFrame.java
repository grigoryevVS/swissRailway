package ru.javaschool.clientGui.frames;

import ru.javaschool.clientMain.ClientSocket;
import ru.javaschool.database.criteria.Response;
import ru.javaschool.database.entities.Passenger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class BuyTicketFrame extends JFrame {

    private JTable scheduleTable;

    public BuyTicketFrame(JTable scheduleTable) {
        super("Buy ticket");
        setSize(800, 200);
        setVisible(true);
        setLocation(300, 300);

        JPanel buyTicketPanel = new BuyTicketPanel();
        this.add(buyTicketPanel);
        this.scheduleTable = scheduleTable;
    }

    private class BuyTicketPanel extends JPanel {

        JTextField firstNameTextField;
        JTextField lastNameTextField;
        JTextField birthDateTextField;

        public BuyTicketPanel() {
            JLabel firstNameLabel = new JLabel("First name: ");
            firstNameTextField = new JTextField(15);
            JLabel lastNameLabel = new JLabel("Last name: ");
            lastNameTextField = new JTextField(15);
            JLabel birthDateLabel = new JLabel("Birth date: ");
            birthDateTextField = new JTextField(15);
            JButton buyTicketButton = new JButton("Buy ticket");
            buyTicketButton.addActionListener(new BuyTicketAction());

            this.add(firstNameLabel);
            this.add(firstNameTextField);
            this.add(lastNameLabel);
            this.add(lastNameTextField);
            this.add(birthDateLabel);
            this.add(birthDateTextField);
            this.add(buyTicketButton);
        }

        private class BuyTicketAction implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {

                Passenger passenger = new Passenger();
                Pattern p = Pattern.compile("\\d", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
                Matcher m = p.matcher(firstNameTextField.getText());
                if (firstNameTextField.getText().equals("") || m.find()) {
                    JOptionPane.showMessageDialog(null, "FirstName must be specified correctly!");
                    clearFields();
                } else if (lastNameTextField.getText().equals("") || m.find()) {
                    JOptionPane.showMessageDialog(null, "LastName must be specified!");
                    clearFields();
                } else if (birthDateTextField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "BirthDate must be specified!");
                    clearFields();
                } else {
                    try {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                        passenger.setBirthDate(dateFormat.parse(birthDateTextField.getText()));
                        passenger.setFirstName(firstNameTextField.getText());
                        passenger.setLastName(lastNameTextField.getText());
                        buyInit(passenger);
                    } catch (ParseException ex) {
                        JOptionPane.showMessageDialog(null, "Wrong dateFormat! It should be DD.MM.YYYY");
                        clearFields();
                    }
                }
            }

            private void clearFields(){
                firstNameTextField.setText("");
                lastNameTextField.setText("");
                birthDateTextField.setText("");
                firstNameTextField.requestFocus();
            }

            private void buyInit(Passenger passenger){
                long scheduleId = (Long) scheduleTable.getValueAt(scheduleTable.getSelectedRow(), 0);
                Response response = ClientSocket.getInstance().buyTicket(scheduleId, passenger);
                if (response == null || response.getIsProblem()) {
                    JOptionPane.showMessageDialog(null, "Error!");
                } else {
                    JOptionPane.showMessageDialog(null, "Congratulation!");
                }
            }
        }
    }
}

