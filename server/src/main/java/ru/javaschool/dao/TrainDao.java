package ru.javaschool.dao;


import ru.javaschool.database.entities.Train;


/**
 * Implements working with database.
 */
public class TrainDao extends GenericDaoHiberImpl<Train, Long>{

    public TrainDao() {
        super(Train.class);
    }
}
