package ru.javaschool.database.entities;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.LocalTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Schedule implements Serializable {

    private static final long serialVersionUID = -8783126071317237137L;

    @Id
    private long scheduleId;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateTrip;
    @ManyToOne
    private Train train;
    @ManyToOne(fetch = FetchType.EAGER)
    private Route route;

    public long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Date getDateTrip() {
        return dateTrip;
    }

    public void setDateTrip(Date dateTrip) {
        this.dateTrip = dateTrip;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public Date getDepartTime(Station station) {
        long stationId = 0;
        if (station != null)
            stationId = station.getStationId();
        DateTime departDate = new DateTime(dateTrip);
        Date arriveTime = null;
        for (StationDistance distance : route.getStationDistances()) {
            if (arriveTime != null) {
                long difference = distance.getAppearTime().getTime() - arriveTime.getTime();
                if (difference < 0)
                    difference += 24 * 3600 * 1000;
                departDate = departDate.plus(new Duration(difference));
            }
            else {
                LocalTime departTime = new LocalTime(distance.getAppearTime());
                departDate = departDate.plus(departTime.getMillisOfDay());
            }
            arriveTime = distance.getAppearTime();
            if (distance.getStation().getStationId() == stationId)
                break;
        }
        return departDate.toDate();
    }
}
