package ru.javaschool.database.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Station implements Serializable{

    private static final long serialVersionUID = -8507927853458358682L;

    @Id
    private long stationId;
    @Column(nullable = false, length = 30)
    private String name;

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

    @Override
    public String toString() {
        return "Station{" +
                "name='" + name + '\'' +
                ", stationId=" + stationId +
                '}';
    }
}
