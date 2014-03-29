package ru.javaschool.database.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
public class StationDistance implements Serializable {

    private static final long serialVersionUID = -2478846496713769074L;

    @Id
    private long stationDistanceId;
    @Column(nullable = false)
    private int sequenceNumber;
    @ManyToOne
    private Station station;
    @ManyToOne
    private Route route;

    public long getStationDistanceId() {
        return stationDistanceId;
    }

    public void setStationDistanceId(long stationDistanceId) {
        this.stationDistanceId = stationDistanceId;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    @Override
    public String toString() {
        return "StationDistance{" +
                "stationDistanceId=" + stationDistanceId +
                ", sequenceNumber=" + sequenceNumber +
                ", station=" + station +
                '}';
    }
}
