package ru.javaschool.dao;


import ru.javaschool.database.entities.EmployeeData;
import ru.javaschool.database.entities.Route;
import ru.javaschool.database.entities.Schedule;
import ru.javaschool.database.entities.Train;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;
import java.sql.SQLException;

public class AddData {
    static TrainDao trainDao = new TrainDao();
    static EmployeeDataDao e = new EmployeeDataDao();
    static ScheduleDao sd = new ScheduleDao();
    static Schedule s = new Schedule();
    static Route r = new Route();
    static RouteDao rd = new RouteDao();
    static Train t = new Train();


    public static void main(String[] args) {
        EntityManager em = EmfInit.em;
        EntityTransaction transact = em.getTransaction();

        try {
            transact.begin();
            t = new Train();

            EmployeeData ed = new EmployeeData();
            EmployeeData ed1 = new EmployeeData(1, "qqq", "1234");
            ed.setLogin("ww");
            ed.setPassword("1234");
            Train train = new Train();
            train.setTrainId(121);
            train.setName("TGV");
            train.setNumberOfSeats(100);
            Train train1 = new Train();
            train1.setTrainId(161);
            train1.setName("broiler");
            train1.setNumberOfSeats(200);
            Train train2 = new Train();
            train2.setTrainId(141);
            train2.setName("Sun");
            train2.setNumberOfSeats(100);
            Train train3 = new Train();
            train3.setTrainId(151);
            train3.setName("BCS");
            train3.setNumberOfSeats(100);

            try {
                e.create(ed);
                e.create(ed1);
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
