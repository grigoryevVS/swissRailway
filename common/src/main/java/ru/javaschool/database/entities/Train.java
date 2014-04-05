package ru.javaschool.database.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import java.io.Serializable;

@Entity
public class  Train implements Serializable{

    private static final long serialVersionUID = -6808983024241846152L;

    @Id
    @GeneratedValue
    private long trainId;
    @Column( nullable = false)
    @Min(1)
    private int numberOfSeats;
    @Column(nullable = false, length = 25)
    private String name;

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

    @Override
    public String toString() {
        return "Train{" +
                "trainId=" + trainId +
                ", numberOfSeats=" + numberOfSeats +
                ", name='" + name +
                '}';
    }
}
