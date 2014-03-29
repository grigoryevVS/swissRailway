package ru.javaschool.clientMain;


import org.apache.log4j.Logger;
import ru.javaschool.clientGui.frames.ClientFrame;

import javax.swing.*;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class);
    public static void main(String[] args) {
        logger.debug("requiem");
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ClientFrame();
            }
        });
    }
}
