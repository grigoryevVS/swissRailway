package ru.javaschool.database.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Train implements Serializable{

    private static final long serialVersionUID = -6808983024241846152L;

    @Id
    private long trainId;
    @Column( nullable = false)
    private int numberOfSeats;
    @Column(nullable = false, length = 25)
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "train")
    private List<Route> trainRoutes;

    public long getTrainId() {
        return trainId;
    }

    public void setTrainId(long trainId) {
        this.trainId = trainId;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Route> getTrainRoutes() {
        return trainRoutes;
    }

    public void setTrainRoutes(List<Route> trainRoutes) {
        this.trainRoutes = trainRoutes;
    }

    @Override
    public String toString() {
        return "Train{" +
                "trainId=" + trainId +
                ", numberOfSeats=" + numberOfSeats +
                ", name='" + name +
                '}';
    }
}
