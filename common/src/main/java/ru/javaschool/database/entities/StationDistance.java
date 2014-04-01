package ru.javaschool.database.entities;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;

@Entity
@IdClass(StationDistanceEPK.class)
public class StationDistance implements Serializable {

    private static final long serialVersionUID = -2478846496713769074L;

    @Id
    @ManyToOne
    @JoinColumn(name = "routeId")
    private Route route;
    @Id
    @Column
    @Min(1)
    private int sequenceNumber;
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

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    @Override
    public String toString() {
        return
                "key=" + route
               ;
    }
}
