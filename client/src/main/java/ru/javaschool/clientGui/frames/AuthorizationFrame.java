package ru.javaschool.clientGui.frames;

import ru.javaschool.clientGui.panels.AdministratorPanel;
import ru.javaschool.clientGui.panels.SchedulePanel;
import ru.javaschool.clientMain.ClientSocket;
import ru.javaschool.database.entities.EmployeeData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AuthorizationFrame extends JFrame {

    public AuthorizationFrame() {
        super("Authorization");

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int x = screenSize.width;
        int y = screenSize.height;

        setSize(340, 180);
        setVisible(true);
        setResizable(false);
        setLocation(x / 3, y / 3);

        this.add(new AuthorizationPanel());
    }

    class AuthorizationPanel extends JPanel {

        private JTextField loginField;
        private JLabel password;
        private JPasswordField passwordField;
        private JButton logIn;

        public AuthorizationPanel() {

            JLabel login = new JLabel("Login: ");
            loginField = new JTextField(20);
            login.setLabelFor(loginField);
            password = new JLabel("Password: ");
            passwordField = new JPasswordField(18);
            password.setLabelFor(passwordField);
            logIn = new JButton("Log in");

            this.add(login);
            this.add(loginField);
            this.add(password);
            this.add(passwordField);
            this.add(logIn);

            logIn.addActionListener(new AuthorizeAction());
        }

//        public boolean isEmployee() {
//            return isEmployee;
//        }

        class AuthorizeAction implements ActionListener {

            //EmployeeService es = new EmployeeService();
            EmployeeData ed = new EmployeeData();

            @Override
            public void actionPerformed(ActionEvent e) {
                char[] pass = passwordField.getPassword();
                String password = new String(pass);
//                if (loginField.getText().equals("")) {
//                    JOptionPane.showMessageDialog(null, "Wrong! input login");
//                } else if (password.equals("")) {
//                    JOptionPane.showMessageDialog(null, "Wrong! Input password");
                // }
                ed.setLogin(loginField.getText());
                ed.setPassword(password);
                try {
                    if (!ClientSocket.getInstance().checkLoginAvailable(ed)) {
                        JOptionPane.showMessageDialog(null, "Wrong login/password");
                        clearFields();

                    } else {
                        ClientFrame.registered = true;
                        ClientFrame.bookmark.addTab("Administration", new AdministratorPanel());
                        SchedulePanel.getRegisteredPassengersButton.setVisible(true);
                        JOptionPane.showMessageDialog(null, "Hello, " + ed.getLogin());
                        AuthorizationFrame.super.dispose();
                    }
                } catch (NullPointerException ex) {
                    JOptionPane.showMessageDialog(null, "Server is not available");
                    clearFields();
                }
            }

            private void clearFields() {
                loginField.setText("");
                loginField.requestFocus();
                passwordField.setText("");
            }

//                if(es.checkExist(login, password)){
//                ClientForm c = new ClientForm();
//                c.bookmark.addTab("Administration", new AdministratorPanel());
//                } else{
//                    JOptionPane.showMessageDialog(null, "Wrong login/password");
//                }

        }
    }
}

