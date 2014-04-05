package ru.javaschool.dao;


import ru.javaschool.database.entities.Train;

import java.util.List;


/**
 * Implements working with database.
 */
public class TrainDao extends GenericDaoHiberImpl<Train, Long>{

    public TrainDao() {
        super(Train.class);
    }

    public boolean findByName(String name){
        List<Train> trainList = em.createQuery("select t from Train t " +
                "where t.name =:name", Train.class).setParameter("name", name).getResultList();
        return trainList.size() > 0;
    }
}
