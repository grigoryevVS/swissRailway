package ru.javaschool.database.criteria;


import java.io.Serializable;
import java.util.Date;

public class ScheduleConstraints implements Serializable{

    private static final long serialVersionUID = 8416702862541712658L;
    public Date date;
    public String stationFromName;
    public String StationToName;
}
