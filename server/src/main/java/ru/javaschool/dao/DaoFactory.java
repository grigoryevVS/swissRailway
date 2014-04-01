//package ru.javaschool.dao;
//
//
//import java.sql.SQLException;
//
///**
// * Factory, creates objects which works with database
// * @param <Context> object
// */
//public interface DaoFactory<Context> {
//
//    public interface DaoCreator<Context> {
//        public GenericDao create(Context context);
//    }
//
//    /**
//     * returning connection to the database
//     */
//    public Context getContext() throws SQLException;
//
//    /**
//     *  return object, which manage persistence state of some object
//     */
//    public GenericDao getDao(Context context, Class dtoClass) throws SQLException;
//}
