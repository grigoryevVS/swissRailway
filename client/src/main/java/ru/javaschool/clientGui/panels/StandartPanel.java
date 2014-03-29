package ru.javaschool.clientGui.panels;


import javax.swing.*;
import java.awt.*;

public abstract class StandartPanel extends JPanel{

    StandartPanel(LayoutManager l){
      this.setLayout(l);
    }

    abstract void addConditionPanel();

    abstract void addButtonPanel();

    abstract void addViewPanel();
}
