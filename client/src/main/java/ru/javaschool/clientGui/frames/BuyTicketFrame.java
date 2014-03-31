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

            Passenger passenger;

            @Override
            public void actionPerformed(ActionEvent e) {

                Pattern p = Pattern.compile("\\d", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
                Matcher m = p.matcher(firstNameTextField.getText());

                if (firstNameTextField.getText().equals("") || firstNameTextField.getText().charAt(0) == ' ' || m.find()) {
                    JOptionPane.showMessageDialog(null, "FirstName must be specified correctly!");
                    firstNameTextField.setText("");
                    firstNameTextField.requestFocus();
                } else {
                    passenger.setFirstName(firstNameTextField.getText());
                }
                if (lastNameTextField.getText().equals("") || lastNameTextField.getText().charAt(0) == ' ' || m.find()) {
                    JOptionPane.showMessageDialog(null, "LastName must be specified!");
                    lastNameTextField.setText("");
                    lastNameTextField.requestFocus();
                } else {
                    passenger.setLastName(lastNameTextField.getText());
                }
                if (birthDateTextField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "BirthDate must be specified!");
                    birthDateTextField.requestFocus();
                } else {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                    try {
                        passenger.setBirthDate(dateFormat.parse(birthDateTextField.getText()));
                    } catch (ParseException ex) {
                        JOptionPane.showMessageDialog(null, "Wrong dateFormat! It should be DD.MM.YYYY");
                        birthDateTextField.requestFocus();
                        ex.printStackTrace();
                    }
                }
                long scheduleId = (Long) scheduleTable.getValueAt(scheduleTable.getSelectedRow(), 0);
                Response response = ClientSocket.getInstance().buyTicket(scheduleId, passenger);
                if (response.getIsProblem()) {
                    JOptionPane.showMessageDialog(null, "Error: " + response.getTitle());
                }
                else {
                    JOptionPane.showMessageDialog(null, "Congratulation!");
                }
            }
        }
    }
}

