package ru.javaschool.database.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
@Entity
public class Schedule implements Serializable{

    private static final long serialVersionUID = -8783126071317237137L;

    @Id
    @GeneratedValue
    private long scheduleId;
    @Column( nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateTrip;
    @ManyToOne
    private Train train;
    @ManyToOne(fetch = FetchType.EAGER)
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

    @Override
    public String toString() {
        return "Schedule{" +
                "scheduleId=" + scheduleId +
                ", dateTrip=" + dateTrip +
                ", train=" + train +
                ", route=" + route +
                ", tickets=" + tickets +
                '}';
    }
}
