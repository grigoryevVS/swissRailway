package ru.javaschool.dao;


import ru.javaschool.database.entities.EmployeeData;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;
import java.sql.SQLException;
import java.text.ParseException;

public class AddData {
//    static TrainDao trainDao = new TrainDao();
//    static StationDao stDao = new StationDao();
//    static ScheduleDao schDao = new ScheduleDao();
//    static RouteDao rDao = new RouteDao();
//    static StationDistanceDao sdDao = new StationDistanceDao();


    public static void main(String[] args) throws SQLException, ParseException {
        EntityManager em = EmfInit.getEm();
        EntityTransaction transact = em.getTransaction();
        EmployeeDataDao e = new EmployeeDataDao();
        TrainDao td = new TrainDao();
        PassengerDao pd = new PassengerDao();
        TicketDao tdd = new TicketDao();
        ScheduleDao schDao = new ScheduleDao();
        RouteDao rdd = new RouteDao();
        StationDao std = new StationDao();
        StationDistanceDao stdd = new StationDistanceDao();


        try {
            transact.begin();

//            Route route = new Route();
//            route.setRouteId(13);
//            route.setTitle("LucernBern");
//            Station st1 = new Station();
//            Station st2 = new Station();
//            st1.setStationId(14);
//            st2.setStationId(15);
//            st1.setName("Brann");
//            st2.setName("Dersey");
//            StationDistance stdi = new StationDistance();
//            stdi.setSequenceNumber(4);
//            SimpleDateFormat df = new SimpleDateFormat("HH:mm");
//            stdi.setAppearTime(df.parse("18:00"));
//            stdi.setRoute(route);
//            stdi.setStation(st1);
//            Train t = new Train();
//            t.setTrainId(1111);
//            t.setNumberOfSeats(10);
//            t.setName("RCCA");
//            route.setStationDistances(new ArrayList<StationDistance>());
//            Schedule sch = new Schedule();
//            sch.setScheduleId(13);
//            SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yyyy");
//            sch.setDateTrip(formatDate.parse("02.04.2014"));
//            sch.setRoute(route);
//            sch.setTrain(t);



            EmployeeData ed = new EmployeeData();
            EmployeeData ed1 = new EmployeeData("qqq", "1234");

//            Passenger pass = new Passenger();
//            pass.setPassengerId(2);
//            pass.setFirstName("Sergei");
//            pass.setLastName("Grigoriev");
//            pass.setBirthDate(new Date(10,12,1980));
//            Ticket ti = new Ticket();
//            ti.setTicketId(2);
//            ti.setSchedule(sch);
//            ti.setPassenger(pass);

//            StationDistance stl = new StationDistance();
//            stl.setKey(new StationDistanceEPK(r, 1));
//            stl.setStation(stDao.findByName("Station A"));
//            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
//            stl.setAppearTime(sdf.parse("08:10"));
//            StationDistance stl1 = new StationDistance();
//            stl.setKey(new StationDistanceEPK(r, 2));
//            stl.setStation(stDao.findByName("Station B"));
//            stl.setAppearTime(sdf.parse("09:08"));

//            Schedule sch = new Schedule();
//            sch.setScheduleId(11);
//            SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yyyy");
//            sch.setDateTrip(formatDate.parse("02.04.2014"));
//            sch.setRoute(r);
//
//
//
//
           try {
//              e.create(ed);
               e.create(ed1);
//               std.create(st1);
//               std.create(st2);
//               td.create(t);
//               pd.create(pass);
//               rdd.create(route);
//               stdd.create(stdi);
//               schDao.create(sch);



               //tdd.create(ti);

//                trainDao.create(train1);
//                trainDao.create(train2);
//                stDao.create(sr);
//                stDao.create(st);
//                schDao.create(sch);
//                rDao.create(r);
//                sdDao.create(stl);
//                sdDao.create(stl1);
           } catch (SQLException ex) {
             //logger.error("Error while creating a train", e);
              ex.printStackTrace();
         }

            transact.commit();

        } catch (RollbackException exc) {
            System.out.println("There is some error" + exc.toString());
            if (transact.isActive()) {
                transact.rollback();
            }
            System.exit(0);
        } finally {
            em.close();
        }
    }
}
