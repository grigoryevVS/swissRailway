package ru.javaschool.dao;


import ru.javaschool.database.entities.Ticket;

public class TicketDao extends GenericDaoHiberImpl<Ticket, Long>{
    public TicketDao() {
        super(Ticket.class);
    }
}
//"where p.passengerId = (select t.passenger.passengerId from Ticket t " +
//        "where (t.schedule.scheduleId = t.schedule.route.train.trainId) = :trainId)");
//        query.setParameter("trainId", train.getTrainId());