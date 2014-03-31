package ru.javaschool.database.criteria;


import java.io.Serializable;

public class SampleObject<T,K> implements Serializable{
    private static final long serialVersionUID = 7322705805494879411L;

    public final T t;
    public final K k;

    public SampleObject(T t, K k){

        this.t = t;
        this.k = k;
    }

    public SampleObject(SampleObject<T,K> sampleObject){
        this.t = sampleObject.t;
        this.k = sampleObject.k;
    }
}
