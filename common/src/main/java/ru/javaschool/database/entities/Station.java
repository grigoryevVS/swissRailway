package ru.javaschool.database.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Station implements Serializable{

    private static final long serialVersionUID = -8507927853458358682L;

    @Id
    private long stationId;
    @Column(nullable = false, length = 30)
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "station")
    private List<StationDistance> stationDistances;


    public long getStationId() {
        return stationId;
    }

    public void setStationId(long stationId) {
        this.stationId = stationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<StationDistance> getStationDistances() {
        return stationDistances;
    }

    public void setStationDistances(List<StationDistance> stationDistances) {
        this.stationDistances = stationDistances;
    }

    @Override
    public String toString() {
        return "Station{" +
                "name='" + name + '\'' +
                ", stationId=" + stationId +
                '}';
    }
}
