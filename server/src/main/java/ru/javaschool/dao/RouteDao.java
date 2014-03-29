package ru.javaschool.dao;


import ru.javaschool.database.entities.Route;
import ru.javaschool.database.entities.Train;

import javax.persistence.Query;
import java.util.List;

public class RouteDao extends GenericDaoHiberImpl<Route, Long>{


    public RouteDao(Class<Route> type) {
        super(type);
    }

    /**
     * Getting schedule of all trains at current station, which we are giving as a parameter
     * @param stationDepart - station, from which we need to give a schedule.
     * @return - returns List of trains.
     */
    public List<Train> getScheduleInStation(Long stationDepart){
        Query query = getEm().createQuery("select r.train from Route r where( r.stationDistance.station.id = " +
                "(select s.stationId from Station s where s.stationId = : depart))");

        query.setParameter("depart" , stationDepart);

        List<Train> schedule = query.getResultList();
        return schedule;
    }

    public Long getStationDeparture(Long stationDepart){

        Query query = getEm().createQuery("select s.stationId from Station s where s.stationId =: departure");
        query.setParameter("departure", stationDepart);
        Long departure = (Long) query.getSingleResult();
        return departure;
    }
}
