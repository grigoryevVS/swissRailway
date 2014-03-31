package ru.javaschool.database.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class StationDistance implements Serializable {

    private static final long serialVersionUID = -2478846496713769074L;

    @EmbeddedId
    private StationDistanceEPK key;
    @ManyToOne
    private Station station;
    @Column
    @Temporal(TemporalType.TIME)
    private Date appearTime;

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Date getAppearTime() {
        return appearTime;
    }

    public void setAppearTime(Date appearTime) {
        this.appearTime = appearTime;
    }

    public StationDistanceEPK getKey() {
        return key;
    }

    public void setKey(StationDistanceEPK key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "StationDistance{" +
                "key=" + key +
                ", station=" + station +
                ", appearTime=" + appearTime +
                '}';
    }
}
