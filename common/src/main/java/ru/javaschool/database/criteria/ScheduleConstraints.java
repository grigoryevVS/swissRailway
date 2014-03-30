package ru.javaschool.database.criteria;


import java.io.Serializable;
import java.util.Date;

public class ScheduleConstraints implements Serializable{

    private static final long serialVersionUID = 8416702862541712658L;
    private Date date;
    private String stationFromName;
    private String StationToName;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStationFromName() {
        return stationFromName;
    }

    public void setStationFromName(String stationFromName) {
        this.stationFromName = stationFromName;
    }

    public String getStationToName() {
        return StationToName;
    }

    public void setStationToName(String stationToName) {
        StationToName = stationToName;
    }
}
