package ru.javaschool.dao;


import ru.javaschool.database.entities.Route;
import ru.javaschool.database.entities.StationDistance;

import javax.persistence.Query;
import java.sql.SQLException;
import java.util.List;

public class RouteDao extends GenericDaoHiberImpl<Route, Long> {


    public RouteDao() {
        super(Route.class);
    }

    /**
     * Getting schedule of all trains at current station, which we are giving as a parameter
     *
     * @param stationDepart - station, from which we need to give a schedule.
     * @return - returns List of trains.
     */
    public Long getStationDeparture(Long stationDepart) {

        Query query = getEm().createQuery("select s.stationId from Station s where s.stationId =:departure");
        query.setParameter("departure", stationDepart);
        return (Long) query.getSingleResult();
    }

    public List<Route> getAllRoutes() {
        return em.createQuery("select st from Route st", Route.class).getResultList();
    }

    public void insert(Route route) throws SQLException {

        create(route);
        if (route.getStationDistances() != null) {
            StationDistanceDao stDao = new StationDistanceDao();
            for (StationDistance sd : route.getStationDistances()) {
                stDao.create(sd);
            }
        }
    }
}
