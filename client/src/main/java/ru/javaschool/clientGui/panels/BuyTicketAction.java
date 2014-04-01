package ru.javaschool.clientGui.panels;


import ru.javaschool.clientGui.frames.BuyTicketFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BuyTicketAction implements ActionListener {

    private JTable scheduleTable;
    Component parent;

    public BuyTicketAction(JTable scheduleTable, Component parent) {
        this.scheduleTable = scheduleTable;
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (scheduleTable.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(parent, "Choose concrete schedule!");
        } else {
            new BuyTicketFrame(scheduleTable);
        }
    }
}
