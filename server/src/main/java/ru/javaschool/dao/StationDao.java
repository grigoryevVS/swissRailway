package ru.javaschool.dao;


import ru.javaschool.database.entities.Station;

import java.sql.SQLException;
import java.util.List;

public class StationDao extends GenericDaoHiberImpl<Station, Long> {

    public StationDao() {
        super(Station.class);
    }

    public boolean findExistanceByName(String name) {
        List<Station> stationList = getEm().createQuery("select s from Station s " +
                "where s.name =:name", Station.class).setParameter("name", name).getResultList();
        return stationList.size() > 0;
    }

    public Station findByName(String name) throws SQLException {
        return getEm().createQuery("select s from Station s " +
                "where s.name =:name", Station.class).setParameter("name", name).getSingleResult();
    }

    public boolean findByKey(Long key) throws SQLException{
        Station resultStation = getEm().createQuery("select s from Station s " +
                "where s.stationId =:key", Station.class).setParameter("key", key).getSingleResult();
        return resultStation.getStationId() == key;
    }

//    public boolean contain(Station station) throws SQLException{
//        Query existQuery = em.createQuery("select s from Station s where s.id =:stationId and s.name =:stationName");
//        existQuery.setParameter("stationId", station.getStationId());
//        existQuery.setParameter("stationName",station.getName());
//        return  (existQuery.equals(station));
//    }
}
