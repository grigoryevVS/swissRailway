package ru.javaschool.dao;

import ru.javaschool.database.entities.Passenger;
import ru.javaschool.database.entities.Schedule;

import javax.persistence.Query;
import java.util.List;


/**
 * Implements working with database.
 */
public class ScheduleDao extends GenericDaoHiberImpl<Schedule, Long>{

    public ScheduleDao() {
        super(Schedule.class);
    }

    /**
     * Getting all registered passengers on some train.
     * @param schedule - which concrete schedule registered passengers, we want to see.
     * @return  -   return List of passengers.
     */
    public List<Passenger> getAllRegisteredOnTrain(Schedule schedule){                      // что принимать?
        Query query = getEm().createQuery("select t.passenger from Ticket t where t.schedule.scheduleId =: scheduleId");
        query.setParameter("scheduleId", schedule.getScheduleId());

        return query.getResultList();
    }
}

