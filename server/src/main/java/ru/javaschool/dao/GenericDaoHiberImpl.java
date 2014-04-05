package ru.javaschool.dao;


import javax.persistence.EntityManager;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;


/**
 * this class is abstract because he hasn't need to be instantiated
 * @param <T>   type of some entity class
 * @param <PK> type of the element, which is a primary key, in this object T
 */
public abstract class GenericDaoHiberImpl<T, PK extends Serializable> implements GenericDao<T, PK> {

    protected EntityManager em = EmfInit.getEm();
    private final Class<T> type;

    public GenericDaoHiberImpl(Class<T> type){
        this.type = type;
    }

    public EntityManager getEm() {
        return em;
    }

    public Class<T> getType() {
        return type;
    }

    @Override
    public T create(final T t) throws SQLException{
        getEm().persist(t);
        return t;
    }

    @Override
    public T findByPK(final PK key) throws SQLException {
        return getEm().find(getType(), key);
    }

    @Override
    public List<T> findAll() throws SQLException {
        return getEm().createQuery("select x from " + getType().getSimpleName() +  " x").getResultList();
    }

    @Override
    public T update(final T t) throws SQLException {
        T mergedEntity = getEm().merge(t);
        return mergedEntity;
    }

    @Override
    public void delete(final T t) throws SQLException {
        getEm().remove(t);
    }

    @Override
    public void deleteObject(final PK key) {
        getEm().remove(getEm().getReference(getType(), key));
    }
}

