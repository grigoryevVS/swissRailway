package ru.javaschool.database.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Passenger implements Serializable{

    private static final long serialVersionUID = 7322705805494879474L;

    @Id
    private long passengerId;
    @Column( nullable = false, length = 20)
    private String firstName;
    @Column( nullable = false, length = 20)
    private String lastName;
    @Column( nullable = false)
    private Date birthDate;

    public Passenger() {
    }

    public Passenger(long passengerId, String firstName, String lastName, Date birthDate) {
        this.passengerId = passengerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    public long getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(long passengerId) {
        this.passengerId = passengerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Passenger passenger = (Passenger) o;

        if (!birthDate.equals(passenger.birthDate)) return false;
        if (!firstName.equals(passenger.firstName)) return false;
        if (!lastName.equals(passenger.lastName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + birthDate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Passenger{");
        sb.append("passengerId=").append(passengerId);
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", birthDate=").append(birthDate).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
