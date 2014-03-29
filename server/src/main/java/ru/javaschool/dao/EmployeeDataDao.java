package ru.javaschool.dao;


import ru.javaschool.database.entities.EmployeeData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Hashmap - key its a login, value - password.
 */
public class EmployeeDataDao extends GenericDaoHiberImpl<EmployeeData, Long> {

    private Map<String, String> authorizeData = new HashMap<String, String>();

    public EmployeeDataDao() {
        super(EmployeeData.class);
    }

    public void getActualLogins() {
        List<EmployeeData> empData = getEm().createQuery("select x from EmployeeData x").getResultList();
        for (EmployeeData data : empData) {
            authorizeData.put(data.getLogin(), data.getPassword());
        }
    }

    public Map<String, String> getAuthorizeData() {
        return authorizeData;
    }


}
