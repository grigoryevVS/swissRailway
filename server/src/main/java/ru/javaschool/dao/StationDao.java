package ru.javaschool.dao;


import ru.javaschool.database.entities.Station;

public class StationDao extends GenericDaoHiberImpl<Station, Long>{

    public StationDao() {
        super(Station.class);
    }

    public Station findByName(String name){
        Station resultStation = em.createQuery("select s from Station s " +
                "where s.name =: name",Station.class).setParameter("name",name).getSingleResult();
        return resultStation;
    }
}
