package ru.javaschool.database.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
@Entity
public class Schedule implements Serializable{

    private static final long serialVersionUID = -8783126071317237137L;

    @Id
    private long scheduleId;
    @Column( nullable = false)
    private Date dateTrip;
    @Column(nullable = false)
    private int departureTime;
    @ManyToOne
    private Route route;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy="schedule")
    private List<Ticket> tickets;

    public long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Date getDateTrip() {
        return dateTrip;
    }

    public void setDateTrip(Date dateTrip) {
        this.dateTrip = dateTrip;
    }

    public int getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(int departureTime) {
        this.departureTime = departureTime;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    @Override                                   // need to be modified
    public String toString() {
        return "Schedule{" +
                "route=" + route +
                ", scheduleId=" + scheduleId +
                ", dateTrip=" + dateTrip +
                ", departureTime=" + departureTime +
                '}';
    }
}
