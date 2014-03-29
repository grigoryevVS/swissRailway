package ru.javaschool.dao;


import ru.javaschool.database.entities.EmployeeData;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;
import java.sql.SQLException;

public class AddData {
    static TrainDao trainDao = new TrainDao();
    static EmployeeDataDao e = new EmployeeDataDao();


    public static void main(String[] args) {
        EntityManager em = EmfInit.em;
        EntityTransaction transact = em.getTransaction();

        try {
            transact.begin();
            EmployeeData ed = new EmployeeData();
            EmployeeData ed1 = new EmployeeData(1,"qqq","1234");
            ed.setLogin("ww");
            ed.setPassword("1234");
//            Train train = new Train();
//            train.setTrainId(121);
//            train.setName("TGV");
//            train.setNumberOfSeats(100);
//            Train train1 = new Train();
//            train1.setTrainId(161);
//            train1.setName("broiler");
//            train1.setNumberOfSeats(200);
//            Train train2 = new Train();
//            train2.setTrainId(141);
//            train2.setName("Sun");
//            train2.setNumberOfSeats(100);
//            Train train3 = new Train();
//            train3.setTrainId(151);
//            train3.setName("BCS");
//            train3.setNumberOfSeats(100);
            try {
                e.create(ed);
                e.create(ed1);
//                trainDao.create(train);
//                trainDao.create(train1);
//                trainDao.create(train2);
//                trainDao.create(train3);
            } catch (SQLException e) {
                //logger.error("Error while creating a train", e);
                e.printStackTrace();
            }

            transact.commit();

        } catch (RollbackException e) {
            System.out.println("There is some error" + e.toString());
            if (transact.isActive()) {
                transact.rollback();
            }
            System.exit(0);
        } finally {
            em.close();
        }
    }
}
