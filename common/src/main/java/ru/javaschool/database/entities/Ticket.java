package ru.javaschool.database.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Ticket implements Serializable{
    private static final long serialVersionUID = 7332983214543490707L;

    @Id
    @GeneratedValue
    private long ticketId;
    @ManyToOne
    private Passenger passenger;
    @ManyToOne(fetch = FetchType.EAGER)
    private Schedule schedule;

    public long getTicketId() {
        return ticketId;
    }

    public void setTicketId(long ticketId) {
        this.ticketId = ticketId;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", passenger=" + passenger.getFirstName() +
                ", passenger=" + passenger.getLastName() +
                ", schedule=" + schedule.getDateTrip() +
                '}';
    }
}
