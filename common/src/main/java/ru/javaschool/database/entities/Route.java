package ru.javaschool.database.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Route implements Serializable{

    private static final long serialVersionUID = 4245604106003040914L;

    @Id
    private long routeId;                           //  it is a direction one way, not the whole route
    @ManyToOne
    private Train train;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy="route")
    private List<StationDistance> stationDistances;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy="route")
    private List<Schedule> schedules;

    public long getRouteId() {
        return routeId;
    }

    public void setRouteId(long routeId) {
        this.routeId = routeId;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public List<StationDistance> getStationDistances() {
        return stationDistances;
    }

    public void setStationDistances(List<StationDistance> stationDistances) {
        this.stationDistances = stationDistances;
    }

//    public String getDepartureStationName(){
//        stationDistances.
//    }

    //public void getDeparture

    @Override
    public String toString() {
        return "Route{" +
                "routeId=" + routeId +
                ", train=" + train +
                '}';
    }
}
