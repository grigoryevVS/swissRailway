package ru.javaschool.clientGui.views;


import ru.javaschool.database.entities.Schedule;
import ru.javaschool.database.entities.Station;
import ru.javaschool.database.entities.StationDistance;

import javax.swing.table.AbstractTableModel;
import java.text.SimpleDateFormat;
import java.util.List;

public class StationScheduleView extends AbstractTableModel{

    enum StationScheduleColumn {
        ID, ROUTE_TITLE, TRAIN_ID, STATION_TO,
        DATETIME_DEPART
    }
    private final String[] columns = {"Id", "RouteTitle", "TrainId", "StationTo",
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
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Schedule schedule = scheduleList.get(rowIndex);
        StationScheduleColumn scheduleColumn = StationScheduleColumn.values()[columnIndex];
        List<StationDistance> stationDistanceList = schedule.getRoute().getStationDistances();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        switch (scheduleColumn){
            case ID: return schedule.getScheduleId();
            case ROUTE_TITLE: return schedule.getRoute().getTitle();
            case TRAIN_ID: return schedule.getTrain().getTrainId();
            case STATION_TO: return stationDistanceList.get(stationDistanceList.size() - 1).getStation().getName();
            case DATETIME_DEPART:  return dateFormat.format(schedule.getDepartTime(station));
            default: return null;
        }
    }
    /*
    case DATETIME_PASS:
				return station != null ? Global.dateTimeFormat.format(schedule.getStationDateTime(station)) : "";
			case DATETIME_ARRIVAL: return Global.dateTimeFormat.format(schedule.getFinalStationDateTime());
     */

    @Override
    public String getColumnName(int c) {
        return columns[c];
    }

    @Override
    public Class<?> getColumnClass(int c) {
        return String.class;
    }
}
