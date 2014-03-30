package ru.javaschool.dao;


import ru.javaschool.database.entities.Station;

import java.sql.SQLException;

public class StationDao extends GenericDaoHiberImpl<Station, Long> {

    public StationDao() {
        super(Station.class);
    }

    public Station findByName(String name) throws SQLException {
        Station resultStation = em.createQuery("select s from Station s " +
                "where s.name =: name", Station.class).setParameter("name", name).getSingleResult();
        return resultStation;
    }
}
