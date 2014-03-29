package ru.javaschool.clientGui.views;


import ru.javaschool.database.entities.Train;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TrainView extends AbstractTableModel {

    private List<Train> trainList;

    public TrainView(List<Train> trainList) {
        super();
        this.trainList = trainList;
    }

    @Override
    public String getColumnName(int column) {
        if (column == 0) {
            return "Train number";
        } else if (column == 1) {
            return "Name";
        } else {
            return "Capacity";
        }

    }

    @Override
    public int getRowCount() {
        return trainList.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Train train = trainList.get(rowIndex);
        if (columnIndex == 0) {
            return train.getTrainId();
        } else if (columnIndex == 1) {
            return train.getName();
        } else {
            return train.getNumberOfSeats();
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 0) {
            return Integer.class;
        } else if (columnIndex == 1) {
            return String.class;
        } else {
            return Integer.class;
        }
    }
}
