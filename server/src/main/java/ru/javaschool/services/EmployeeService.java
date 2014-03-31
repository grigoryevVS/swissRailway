package ru.javaschool.services;


import org.apache.log4j.Logger;
import ru.javaschool.dao.*;
import ru.javaschool.database.criteria.SampleObject;
import ru.javaschool.database.criteria.ScheduleConstraints;
import ru.javaschool.database.entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class EmployeeService {

    private static final Logger logger = Logger.getLogger(EmployeeService.class);
    private TrainDao trainDao = new TrainDao();
    private StationDao stationDao = new StationDao();
    private EmployeeDataDao employeeDataDao = new EmployeeDataDao();
    private TicketDao ticketDao = new TicketDao();
    private ScheduleDao scheduleDao = new ScheduleDao();
    private PassengerDao passengerDao = new PassengerDao();
    private RouteDao routeDao = new RouteDao();

    /**
     * This method implements getting list of trains, which are
     * ply in the swissRailway.
     * Available only for the employee, which was authorized.
     *
     * @return returns null, when catching exception in sql, else return List of all trains
     */
    public List<Train> getTrainList() {         // done!
        logger.debug("get trainMethod");
        try {
            return trainDao.findAll();
        } catch (SQLException e) {
            logger.error(e.getMessage());
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
                logger.error(e.getMessage());
                e.printStackTrace();
            }

            transact.commit();

        } catch (RollbackException e) {
            System.out.println(e.getMessage());
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
                logger.error(e.getMessage());
                e.printStackTrace();
            }

            transact.commit();

        } catch (RollbackException e) {
            System.out.println(e.getMessage());
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
     *
     * @param ed - login and password of the employee.
     * @return -   true - if such data exist, and authorization passed, else return false.
     */
    public boolean checkExist(EmployeeData ed) {
        employeeDataDao.getActualLogins();
        return employeeDataDao.getAuthorizeData().containsKey(ed.getLogin()) && employeeDataDao.getAuthorizeData().containsValue(ed.getPassword());
    }

    public List<Schedule> getScheduleList() {
        logger.debug("get trainMethod");
        try {
            return scheduleDao.findAll();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<Passenger> getAllRegisteredPassOnTrain(Schedule schedule) {      // done!
        return scheduleDao.getAllRegisteredOnTrain(schedule);
    }

    public String buyTicket(SampleObject<Long, Passenger> sampleObject) {
        Long scheduleId = sampleObject.t;
        Passenger passenger = passengerDao.registrationPass(sampleObject.k);
        if (passenger == null) {
            passenger = createPassenger(sampleObject.k);
        }
        try {
            Schedule schedule = scheduleDao.findByPK(scheduleId);
            String intermediateResult = ticketDao.buyTicket(schedule, passenger);
            if (intermediateResult.equals("Good case")) {
                Ticket ticket = new Ticket();
                ticket.setSchedule(schedule);
                ticket.setPassenger(passenger);
                return createTicket(ticket);
            } else {
                return "Failed cause" + intermediateResult;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public Passenger createPassenger(Passenger passenger) {
        EntityManager em = EmfInit.em;
        EntityTransaction transact = em.getTransaction();
        try {
            transact.begin();
            try {
                passengerDao.create(passenger);
            } catch (SQLException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }

            transact.commit();

        } catch (RollbackException e) {
            logger.error(e.getMessage());
            if (transact.isActive()) {
                transact.rollback();
            }
        }
        return passenger;
    }

    public String createTicket(Ticket ticket) {
        EntityManager em = EmfInit.em;
        EntityTransaction transact = em.getTransaction();
        try {
            transact.begin();
            try {
                ticketDao.create(ticket);
            } catch (SQLException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }

            transact.commit();
            return "Success";

        } catch (RollbackException e) {
            if (transact.isActive()) {
                transact.rollback();
            }
            return (e.getMessage());
        }

    }

    public List<Schedule> getRevisedScheduleList(ScheduleConstraints constraints) {
        logger.debug("get revisedScheduleList");
        List<Schedule> scheduleListDate = null;
        Set<Schedule> scheduleSetStation;

        if (constraints.getDate() != null) {
            scheduleListDate = scheduleDao.getDateRevisedList(constraints.getDate());
        } else {
            try {
                scheduleListDate = scheduleDao.findAll();
            } catch (SQLException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
        if (!constraints.getStationFromName().equals("")) {
            scheduleSetStation = scheduleDao.getStationRevisedList(constraints.getStationFromName(), constraints.getStationToName());
            if (scheduleListDate != null) {
                scheduleSetStation.addAll(scheduleListDate);
                scheduleListDate.clear();
                scheduleListDate.addAll(scheduleSetStation);
            }
        }
        return scheduleListDate;
    }

    public Station getStationByName(String stationName) {
        try {
            return stationDao.findByName(stationName);
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<Station> getStationList() {
        logger.debug("get trainMethod");
        try {
            return stationDao.findAll();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }


    public void createRoute(Route route) {
        EntityManager em = EmfInit.em;
        EntityTransaction transact = em.getTransaction();
        try {
            transact.begin();
            try {
                routeDao.insert(route);
            } catch (SQLException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }

            transact.commit();

        } catch (RollbackException e) {
            logger.error(e.getMessage());
            if (transact.isActive()) {
                transact.rollback();
            }
        }
    }

    public String createSchedule(Schedule schedule) {
        EntityManager em = EmfInit.em;
        EntityTransaction transact = em.getTransaction();
        String result = "Success";
        try {
            transact.begin();
            try {
                scheduleDao.create(schedule);
            } catch (SQLException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
                return e.getMessage();
            }

            transact.commit();
            return result;

        } catch (RollbackException e) {
            logger.error(e.getMessage());
            if (transact.isActive()) {
                transact.rollback();
            }
            return e.getMessage();
        }
    }
}
