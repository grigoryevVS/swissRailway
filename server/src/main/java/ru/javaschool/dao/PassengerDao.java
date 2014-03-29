package ru.javaschool.dao;


import javax.persistence.Query;

public class PassengerDao extends GenericDaoHiberImpl{
    public PassengerDao(Class type) {
        super(type);
    }

    /**
     * Cheks availability of the login.
     * @param passengerLogin - login, which passenger input while authorization.
     * @return - true, if such login is not using yet.
     */
    boolean checkAvailableLogin(String passengerLogin){

        Query query = getEm().createQuery("select count(*) from" + getType().getSimpleName()
                + "Passenger p where p.login = : passengerLogin").setParameter("passengerLogin", passengerLogin);

        Long count = (Long) query.getSingleResult();
        return count < 1;
    }
}
