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
import java.util.ArrayList;
import java.util.List;

public class EmployeeService {

    private static final Logger logger = Logger.getLogger(EmployeeService.class);
    private TrainDao trainDao = new TrainDao();
    private StationDao stationDao = new StationDao();
    private EmployeeDataDao employeeDataDao = new EmployeeDataDao();
    private TicketDao ticketDao = new TicketDao();
    private ScheduleDao scheduleDao = new ScheduleDao();
    private PassengerDao passengerDao = new PassengerDao();
    private RouteDao routeDao = new RouteDao();
    private StationDistanceDao distanceDao = new StationDistanceDao();

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
    public String createTrain(Train train) {

        EntityManager em = EmfInit.getEm();
        EntityTransaction transact = em.getTransaction();
        String result;
        if (!trainDao.findByName(train.getName())) {
            try {
                transact.begin();
                try {
                    trainDao.create(train);
                } catch (SQLException e) {
                    logger.error(e.getMessage());
                    e.printStackTrace();
                }

                transact.commit();
                result = "Success!";
            } catch (RollbackException e) {
                System.out.println(e.getMessage());
                if (transact.isActive()) {
                    transact.rollback();
                }
                result = "Failed cause: " + e.getMessage();
            }
        } else {
            result = "Such train is already exist!";
        }
        return result;
    }

    /**
     * This method creating a new station
     */
    public String createStation(Station station) {
        EntityManager em = EmfInit.getEm();
        EntityTransaction transact = em.getTransaction();
        String result;
        if(!stationDao.findExistanceByName(station.getName())){
        try {
            transact.begin();
            try {
                stationDao.create(station);
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }

            transact.commit();
            result = "Success!";

        } catch (RollbackException e) {
            System.out.println(e.getMessage());
            if (transact.isActive()) {
                transact.rollback();
            }
            result = "Failed cause: " + e.getMessage();
        }
        }else {
            result = "Such station is already exist!";
        }
        return result;
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

    public Schedule getScheduleById(Long id) {

        try {
            return scheduleDao.findByPK(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Passenger> getAllRegisteredPassOnTrain(Schedule schedule) {
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
        EntityManager em = EmfInit.getEm();
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
        EntityManager em = EmfInit.getEm();
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

    /**
     * This method looks, which constraints were initialized and gets schedule list with that constraints.
     *
     * @param constraints - station from and/or station to going train, and date when it is going.
     * @return - list of schedule, which succeed this constraints.
     */
    public List<Schedule> getRevisedScheduleList(ScheduleConstraints constraints) {
        logger.debug("get revisedScheduleList");
        try {

            List<Schedule> scheduleListDate;

            // first of all, checks date, do we need to set this constraint, and getting first intermediate list.
            if (constraints.getDate() != null) {
                scheduleListDate = scheduleDao.getDateRevisedList(constraints.getDate());
            } else {
                scheduleListDate = scheduleDao.findAll();
            }
            // fo second check if we set station from.
            if (!constraints.getStationFromName().equals("not selected")) {
                List<Schedule> res = new ArrayList<Schedule>();
                for (Schedule schedule : scheduleListDate) {
                    boolean flag = false;
                    for (StationDistance s : schedule.getRoute().getStationDistances())
                        // check conditions, that its not the last station in the route, and its equal to the constraint
                        if (s.getSequenceNumber() != schedule.getRoute().getStationDistances().size() &&
                                s.getStation().getName().equals(constraints.getStationFromName()))
                            flag = true;
                    if (flag)
                        res.add(schedule);
                }
                scheduleListDate = res;
            }
            // the last check constraint of the station to.
            if (!constraints.getStationToName().equals("not selected")) {
                List<Schedule> res = new ArrayList<Schedule>();
                for (Schedule schedule : scheduleListDate) {
                    boolean flag = false;
                    for (StationDistance s : schedule.getRoute().getStationDistances())
                        // check conditions that its not the first station in the route and its equal to the constraint
                        if (s.getSequenceNumber() > 1 && s.getStation().getName().equals(constraints.getStationToName()))
                            flag = true;
                    if (flag)
                        res.add(schedule);
                }
                scheduleListDate = res;
            }
            return scheduleListDate;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return new ArrayList<Schedule>();
        }
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
        List<Station> stationList = new ArrayList<Station>();
        try {
           stationList = stationDao.findAll();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return stationList;
    }


    public String createRoute(Route route) {
        EntityManager em = EmfInit.getEm();
        EntityTransaction transact = em.getTransaction();
        try {
            transact.begin();
            try {
                routeDao.create(route);
                if (route.getStationDistances() != null) {
                    for (StationDistance sd : route.getStationDistances()) {
                        distanceDao.create(sd);
                    }
                }
            } catch (SQLException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
                return "Failed, cause: " + e.getMessage();
            }

            transact.commit();
            return "Success!";

        } catch (RollbackException e) {
            logger.error(e.getMessage());
            if (transact.isActive()) {
                transact.rollback();
            }
            return "Failed,cause: " + e.getMessage();
        }
    }

    public String createSchedule(Schedule schedule) {
        EntityManager em = EmfInit.getEm();
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

    public List<Route> getRouteList() {
        logger.debug("get Routes method");
        try {
            return routeDao.findAll();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
