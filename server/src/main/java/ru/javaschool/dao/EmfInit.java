package ru.javaschool.dao;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EmfInit {

    public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("railway");

//    public static EntityManagerFactory initEmf(){
//        if(emf == null){
//            synchronized(EmfInit.class){
//                if(emf == null){
//                    emf = Persistence.createEntityManagerFactory("railway");
//                }
//            }
//        }
//        emf = Persistence.createEntityManagerFactory("railway");
//        return emf;
//    }

    private static EntityManager em = emf.createEntityManager();

    public static EntityManager getEm() {
        return em;
    }
}
