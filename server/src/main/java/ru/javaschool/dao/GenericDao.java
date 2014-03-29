package ru.javaschool.dao;


import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

/**
Universal interface, this layer implements crud operations
 with database entities
@param <T>  persistence object
@param <PK> type of the primary key
 */
public interface GenericDao <T, PK extends Serializable> {

    /**
     * Method for object saving (controls object inserting and updating).(create)
     * @param t object to save
     */
    public T create(T t) throws SQLException;

    /**
     * Method for extracting object that bases on object class and id. If
     * no object found returns null.(read)
     * @param key the identifier (primary key) of the class
     * @return filled object
     */
    public T findByPK(PK key) throws SQLException;

    public List<T> findAll() throws SQLException;

    /**
     * Method for updating existing objectin the database.(update)
     * @param t to be updated in the database
     */
    public T update(T t) throws SQLException;

    /**
     * Base method for deleting a specified object.(delete)
     * @param t an object to delete
     */
    public void delete(T t) throws SQLException;

    /**
     * Base method for object deleting that bases on object class and id.(delete)
     * @param key - an id (primary key) of the object
     */
    public void deleteObject(PK key);




//    /**
//     * Get count of all objects of given type.
//     *
//     * @return count of objects
//     */
//    public int getAllObjectsCount();
//
//    /**
//     * The HQL, SQL or other request in the form of the prepared statement.
//     *
//     * @param request the request to database
//     * @param values  values to put instead of "?"
//     * @return list of objects that setisfy the criteria
//     */
//    public List<T> getObjects(String request, Object[] values);

}
