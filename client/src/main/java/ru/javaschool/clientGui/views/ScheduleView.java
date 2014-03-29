package ru.javaschool.clientGui.views;


import ru.javaschool.database.entities.Schedule;
import ru.javaschool.database.entities.StationDistance;

import javax.swing.table.AbstractTableModel;
import java.util.List;


public class ScheduleView extends AbstractTableModel {
    private static final long serialVersionUID = -3983669871349074849L;

    private final String[] colNames = {"Id", "RouteNumber", "StationFrom", "StationTo", "DateTrip", "DepartureTime", "TrainId"};

    enum ScheduleColumn {
        ID, ROUTE_ID, STATION_FROM, STATION_TO,
        DATE_TRIP, TIME_DEPART, TRAIN_ID
    }

    List<Schedule> scheduleList;

    public ScheduleView(List<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
    }

    @Override
    public String getColumnName(int column) {
        return colNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public int getRowCount() {
        return scheduleList.size();
    }

    @Override
    public int getColumnCount() {
        return 7;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Schedule schedule = scheduleList.get(rowIndex);
        ScheduleColumn scheduleColumn = ScheduleColumn.values()[columnIndex];
        List<StationDistance> stationDistanceList = schedule.getRoute().getStationDistances();

        switch (scheduleColumn){
            case ID: return schedule.getScheduleId();
            case ROUTE_ID: return schedule.getRoute().getRouteId();
            case STATION_FROM: return stationDistanceList.get(0).getStation().getName();
            case STATION_TO: return stationDistanceList.get(stationDistanceList.size() - 1).getStation().getName();
            case DATE_TRIP: return schedule.getDateTrip();
            case TIME_DEPART: return schedule.getDepartureTime();
            case TRAIN_ID: return schedule.getRoute().getTrain().getTrainId();
        }
        return null;
    }
}
