package ru.javaschool.serverGui;


import ru.javaschool.controller.ServerSocketImpl;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerForm extends JFrame {

    private ServerSocketImpl server;
  // public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("swissRailway");

    public ServerForm(ServerSocketImpl server) {
        super("Server SBB");
        this.server = server;
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int x = screenSize.width;
        int y = screenSize.height;

        setSize(x / 2, y / 2);
        setLocation(x / 4, y / 6);
        setVisible(true);

        ServerPanel panel = new ServerPanel();
        Container contentPane = getContentPane();
        contentPane.add(panel);
    }

    private class ServerPanel extends JPanel {
        private JButton start;
        private JButton shutDown;
        private JTextComponent info;

        public ServerPanel() {

            start = new JButton("StartServer");
            shutDown = new JButton("ShutDownServer");
            info = new JTextField(20);
            shutDown.setEnabled(false);

            start.addActionListener(new StartAction());
            shutDown.addActionListener(new ShutDownAction());

            this.add(start);
            this.add(shutDown);
            this.add(info);

        }


        class StartAction implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                Thread th = new Thread() {
                    public void run() {
                        server.startServer();
                    }
                };
                th.start();
                info.setText("Server started!");
                start.setEnabled(false);
                shutDown.setEnabled(true);
            }
        }

        private class ShutDownAction implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                server.shutDownServer();
                info.setText("Server shutDown!");
                start.setEnabled(true);
                shutDown.setEnabled(false);
            }
        }
    }

}
