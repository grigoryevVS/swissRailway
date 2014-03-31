package ru.javaschool.clientGui.views;


import ru.javaschool.database.entities.Schedule;
import ru.javaschool.database.entities.Station;
import ru.javaschool.database.entities.StationDistance;

import javax.swing.table.AbstractTableModel;
import java.text.SimpleDateFormat;
import java.util.List;

public class StationScheduleView extends AbstractTableModel{

    enum StationScheduleColumn {
        ID, TRAIN_ID, STATION_TO,
        DATE_TIME
    }
    private final String[] columns = {"Id", "TrainId", "StationTo",
           "Residence time"};
    private Station station;
    private List<Schedule> scheduleList;



    public StationScheduleView(Station station, List<Schedule> scheduleList) {
        this.station = station;
        this.scheduleList = scheduleList;
    }

    @Override
    public int getRowCount() {
        return scheduleList.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Schedule schedule = scheduleList.get(rowIndex);
        StationScheduleColumn scheduleColumn = StationScheduleColumn.values()[columnIndex];
        List<StationDistance> stationDistanceList = schedule.getRoute().getStationDistances();

        // output of data in new format, with date and time!
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        switch (scheduleColumn){
            case ID: return schedule.getScheduleId();
            case TRAIN_ID: return schedule.getTrain().getTrainId();
            case STATION_TO: return stationDistanceList.get(stationDistanceList.size() - 1).getStation().getName();
            case DATE_TIME: return schedule.getDateTrip();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int c) {
        return columns[c];
    }

    @Override
    public Class<?> getColumnClass(int c) {
        return String.class;
    }
}
