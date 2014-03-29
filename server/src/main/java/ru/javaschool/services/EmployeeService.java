package ru.javaschool.services;


import org.apache.log4j.Logger;
import ru.javaschool.dao.*;
import ru.javaschool.database.entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;
import java.sql.SQLException;
import java.util.List;

public class EmployeeService {

    private static final Logger logger = Logger.getLogger(EmployeeService.class);
    private TrainDao trainDao = new TrainDao();
    private StationDao stationDao = new StationDao();
    private EmployeeDataDao employeeDataDao = new EmployeeDataDao();
    private TicketDao ticketDao = new TicketDao();
    private ScheduleDao scheduleDao = new ScheduleDao();

    /**
     * This method implements getting list of trains, which are
     * ply in the swissRailway.
     * Available only for the employee, which was authorized.
     * @return returns null, when catching exception in sql, else return List of all trains
     */
    public List<Train> getTrainList() {         // done!
        logger.debug("get trainMethod");
        try {
            return trainDao.findAll();
        } catch (SQLException e) {
            logger.error("Error while sql runs", e);
            e.printStackTrace();
            return null;
        }

    }

    /**
     * This method creating a new train
     */
    public Train createTrain(Train train) {

        EntityManager em = EmfInit.em;
        EntityTransaction transact = em.getTransaction();

        try {
            transact.begin();
            try {
                trainDao.create(train);
            } catch (SQLException e) {
                logger.error("Error while creating a train", e);
                e.printStackTrace();
            }

            transact.commit();

        } catch (RollbackException e) {
            System.out.println("There is some error" + e.toString());
            if (transact.isActive()) {
                transact.rollback();
            }
        }
        return train;
    }

    /**
     * This method creating a new station
     */
    public Station createStation(Station station) {
        EntityManager em = EmfInit.em;
        EntityTransaction transact = em.getTransaction();
        try {
            transact.begin();
            try {
                stationDao.create(station);
            } catch (SQLException e) {
                logger.error("Error while creating a station", e);
                e.printStackTrace();
            }

            transact.commit();

        } catch (RollbackException e) {
            System.out.println("There is some error" + e.toString());
            if (transact.isActive()) {
                transact.rollback();
            }
        }
        return station;
    }

    /**
     * This method allows employee to be authorized.
     * Getting from client login password, actuality database
     * and check, if this data exist in this db.
     * @param ed - login and password of the employee.
     * @return  -   true - if such data exist, and authorization passed, else return false.
     */
    public boolean checkExist(EmployeeData ed) {        // done!
        employeeDataDao.getActualLogins();
        return employeeDataDao.getAuthorizeData().containsKey(ed.getLogin()) && employeeDataDao.getAuthorizeData().containsValue(ed.getPassword());
    }

    public List<Schedule> getScheduleList(){            // done!
        logger.debug("get trainMethod");
        try {
            return scheduleDao.findAll();
        } catch (SQLException e) {
            logger.error("Error while sql runs", e);
            e.printStackTrace();
            return null;
        }
    }

    public List<Passenger> getAllRegisteredPassOnTrain(Schedule schedule){      // done!
            return scheduleDao.getAllRegisteredOnTrain(schedule);
    }

}
