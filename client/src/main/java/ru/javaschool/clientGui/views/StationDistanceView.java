package ru.javaschool.clientGui.views;


import ru.javaschool.database.entities.StationDistance;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class StationDistanceView extends AbstractTableModel {

    private final String[] columns = {"SeqNumber", "StationTitle", "AppearanceTime"};
    public List<StationDistance> distanceList;

    public StationDistanceView(List<StationDistance> distanceList) {
        this.distanceList = distanceList;
    }

    @Override
    public int getRowCount() {
        return distanceList.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        StationDistance distance = distanceList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return distance.getKey();
            case 1:
                return distance.getStation().getName();
            case 2:
                return distance.getAppearTime();
            default:
                return 0;

        }
    }
}
