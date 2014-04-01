package ru.javaschool.dao;

import ru.javaschool.database.entities.Passenger;
import ru.javaschool.database.entities.Schedule;
import ru.javaschool.database.entities.StationDistance;

import javax.persistence.Query;
import java.util.*;


/**
 * Implements working with database.
 */
public class ScheduleDao extends GenericDaoHiberImpl<Schedule, Long> {

    public ScheduleDao() {
        super(Schedule.class);
    }

    /**
     * Getting all registered passengers on some train.
     *
     * @param schedule - which concrete schedule registered passengers, we want to see.
     * @return -   return List of passengers.
     */
    public List<Passenger> getAllRegisteredOnTrain(Schedule schedule) {
        Query query = getEm().createQuery("select t.passenger from Ticket t where t.schedule.scheduleId =:scheduleId");
        query.setParameter("scheduleId", schedule.getScheduleId());
        return query.getResultList();
    }

    public List<Schedule> getDateRevisedList(Date date) {

        Query dateQuery = getEm().createQuery("select s from Schedule s where s.dateTrip =:dateDeparture", Schedule.class);
        dateQuery.setParameter("dateDeparture", date);
        return dateQuery.getResultList();
    }

    public Set<Schedule> getStationRevisedList(String from, String to) {

        List<Schedule> scheduleList = new ArrayList<Schedule>();
        Set<Schedule> resultSet = new HashSet<Schedule>();
        String stationFrom = "";
        String stationTo = "";
        if (!(to.equals("not selected"))) {
            for (Schedule s : scheduleList) {
                List<StationDistance> distanceList = s.getRoute().getStationDistances();
                for (StationDistance sd : distanceList) {
                    if (stationFrom.equals("") && sd.getStation().getName().equals(from)) {
                        stationFrom = sd.getStation().getName();
                    }
                    if (stationTo.equals("") && sd.getStation().getName().equals(to)) {
                        stationTo = sd.getStation().getName();
                    }
                }
                if (!stationFrom.equals("") && (!stationTo.equals(""))) {
                    resultSet.add(s);
                }
            }
        } else {
            for (Schedule s : scheduleList) {
                List<StationDistance> distanceList = s.getRoute().getStationDistances();
                for (StationDistance sd : distanceList) {
                    if (stationFrom.equals("") && sd.getStation().getName().equals(from)) {
                        stationFrom = sd.getStation().getName();
                    }
                }
                if (!stationFrom.equals("")) {
                    resultSet.add(s);
                }
            }
        }
        return resultSet;
    }
}