package ru.javaschool.dao;


import ru.javaschool.database.entities.Passenger;

import javax.persistence.Query;
import java.util.List;

public class PassengerDao extends GenericDaoHiberImpl<Passenger, Long> {
    public PassengerDao() {
        super(Passenger.class);
    }

    /**
     * Cheks availability of the login.
     *
     * @param passengerLogin - login, which passenger input while authorization.
     * @return - true, if such login is not using yet.
     */
    boolean checkAvailableLogin(String passengerLogin) {

        Query query = getEm().createQuery("select count(*) from" + getType().getSimpleName()
                + "Passenger p where p.login = :passengerLogin").setParameter("passengerLogin", passengerLogin);

        Long count = (Long) query.getSingleResult();
        return count < 1;
    }

    public Passenger registrationPass(Passenger p) {

        Query query = getEm().createQuery("select p from Passenger p " +
                "where p.firstName =:firstName and p.lastName =:lastName and p.birthDate =:birthDate", Passenger.class);
        query.setParameter("firstName", p.getFirstName());
        query.setParameter("lastName", p.getLastName());
        query.setParameter("birthDate", p.getBirthDate());

        List<Passenger> passengerList =  query.getResultList();
        if (passengerList.size() == 0) {
            return null;
        } else {
            return passengerList.get(0);
        }
    }
}
