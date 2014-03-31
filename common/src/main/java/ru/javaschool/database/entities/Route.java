package ru.javaschool.database.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Route implements Serializable{

    private static final long serialVersionUID = 4245604106003040914L;

    @Id
    @GeneratedValue
    private long routeId;
    @Column
    private String title;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy="key.route")
    private List<StationDistance> stationDistances;

    public long getRouteId() {
        return routeId;
    }

    public void setRouteId(long routeId) {
        this.routeId = routeId;
    }

    public List<StationDistance> getStationDistances() {
        return stationDistances;
    }

    public void setStationDistances(List<StationDistance> stationDistances) {
        this.stationDistances = stationDistances;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Route{" +
                "routeId=" + routeId +
                ", title='" + title + '\'' +
                ", stationDistances=" + stationDistances +
                '}';
    }
}
