package ru.javaschool.dao;


import ru.javaschool.database.entities.StationDistance;

public class StationDistanceDao  extends GenericDaoHiberImpl<StationDistance, Long> {
    public StationDistanceDao() {
        super(StationDistance.class);
    }


}
