package ru.javaschool.database.entities;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import java.io.Serializable;

@Embeddable
public class StationDistanceEPK implements Serializable {
    private static final long serialVersionUID = 1312321L;
    @ManyToOne
    @JoinColumn(name = "route_routeId")
    private Route route;
    @Column
	@Min(1)
    private int sequenceNumber;

    public StationDistanceEPK() {

    }
    public StationDistanceEPK(Route route, int sequenceNumber) {
        this.route = route;
        this.sequenceNumber = sequenceNumber;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StationDistanceEPK that = (StationDistanceEPK) o;

        if (sequenceNumber != that.sequenceNumber) return false;
        if (route != null ? !route.equals(that.route) : that.route != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = route != null ? route.hashCode() : 0;
        result = 31 * result + sequenceNumber;
        return result;
    }

    @Override
    public String toString() {
        return "StationDistanceEPK{" +
                "route=" + route +
                ", sequenceNumber=" + sequenceNumber +
                '}';
    }
}
