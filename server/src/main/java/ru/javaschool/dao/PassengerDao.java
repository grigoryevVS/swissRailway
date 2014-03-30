package ru.javaschool.dao;


import ru.javaschool.database.entities.Passenger;

import javax.persistence.Query;
import java.util.List;

public class PassengerDao extends GenericDaoHiberImpl{
    public PassengerDao() {
        super(Passenger.class);
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

    public Passenger registrationPass(Passenger p){

        Query query = getEm().createQuery("select p from Passenger p " +
                "where p.firstName =:firstName and p.lastName =: lastName and p.birthDate =: birthDate",Passenger.class);
        query.setParameter("firstName",p.getFirstName());
        query.setParameter("lastName", p.getLastName());
        query.setParameter("birthDate", p.getBirthDate());

        List<Passenger> passengerList = query.getResultList();
        if(passengerList.size() == 0) {
            return null;
        } else{
            return passengerList.get(0);
        }
    }

    /*
    public Passenger findByNameDate(String firstName, String lastName, Date birthdate) {
		TypedQuery<Passenger> q = em.createQuery("select p from Passenger p"
				+ " where p.firstname = :firstname"
				+ " and p.lastname = :lastname"
				+ " and p.birthdate = :birthdate", Passenger.class);
		q.setParameter("firstname", firstName);
		q.setParameter("lastname", lastName);
		q.setParameter("birthdate", birthdate);
		List<Passenger> list = q.getResultList();
		Passenger ret = null;
		if (list.size() == 1)
			ret = list.get(0);
		else if (list.size() >= 2) {
			// TODO Error
		}
		return ret;
	}

	public Passenger registerPassenger(Passenger p) {
		Passenger passenger = this.findByNameDate(p.getFirstname(), p.getLastname(), p.getBirthdate());
		if (passenger == null) {
			passenger = p;
			insert(passenger);
		}
		return passenger;
	}
     */

}
