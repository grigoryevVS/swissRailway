package ru.javaschool.dao;


import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.LocalTime;
import ru.javaschool.database.entities.Passenger;
import ru.javaschool.database.entities.Schedule;
import ru.javaschool.database.entities.Ticket;

import javax.persistence.Query;
import java.util.List;

public class TicketDao extends GenericDaoHiberImpl<Ticket, Long> {
    public TicketDao() {
        super(Ticket.class);
    }


    public String buyTicket(Schedule s, Passenger p) {

        // check, that this passenger already bougth ticket on this train!
        Query existQuery = getEm().createQuery ("select t from Ticket t where t.passenger =:passenger and t.schedule =:schedule", Ticket.class);
        existQuery.setParameter("passenger", p);
        existQuery.setParameter("schedule", s);

        List<Ticket> ticketList = existQuery.getResultList();
        if (!ticketList.isEmpty()) {
            return "Sorry, but this passenger already bought ticket on this train!";
        }

        // check, that train is already full, and there is no tickets on it!
        Query querySeats = getEm().createQuery("select count(t) from Ticket t where t.schedule =:schedule");
        querySeats.setParameter("schedule", s);

        int soldTickets = ((Long) querySeats.getSingleResult()).intValue();
        int trainCapacity = s.getTrain().getNumberOfSeats();
        if (soldTickets >= trainCapacity) {
            return "Sorry, this train is already full!";
        }

        // check, that this train will depart in 10 minutes!
        //DateTime
        Duration interval = new Duration(600 * 1000L);
        DateTime departureTime = new DateTime(s.getDateTrip());
        DateTime currentTime = new DateTime();
        LocalTime departTime = new LocalTime(s.getRoute().getStationDistances().get(0).getAppearTime());
        departureTime = departureTime.plus(departTime.getMillisOfDay());
        Duration duration = new Duration(currentTime, departureTime);
        if (duration.isShorterThan(interval)) {
            return "Sorry, but there is only 10 minutes before departure, you can't buy ticket!";
        }
        return "Good case";
    }
}