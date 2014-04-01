package ru.javaschool.dao;


import ru.javaschool.database.entities.StationDistance;
import ru.javaschool.database.entities.StationDistanceEPK;

public class StationDistanceDao  extends GenericDaoHiberImpl<StationDistance, StationDistanceEPK> {
    public StationDistanceDao() {
        super(StationDistance.class);
    }


}
