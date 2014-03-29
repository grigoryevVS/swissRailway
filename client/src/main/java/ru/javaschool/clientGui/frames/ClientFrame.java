package ru.javaschool.clientGui.frames;

import ru.javaschool.clientGui.panels.SchedulePanel;
import ru.javaschool.clientGui.panels.StationSchedulePanel;

import javax.swing.*;
import java.awt.*;

public class ClientFrame extends JFrame {

    private static final long serialVersionUID = 86535267019383466L;
    public static JTabbedPane bookmark;
    public static boolean registered = false;

    public ClientFrame() {
        super("T-Systems task");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int x = screenSize.width;
        int y = screenSize.height;

        setSize(x/3*2, y/3*2);
        setVisible(true);
        setLocation(x/5,y/5);

        bookmark = new JTabbedPane();

        bookmark.addTab("Schedule", new SchedulePanel());
        bookmark.addTab("Station schedule", new StationSchedulePanel());

        this.add(bookmark, BorderLayout.CENTER);     // maybe i will do it borderLayout.center!


    }
}
