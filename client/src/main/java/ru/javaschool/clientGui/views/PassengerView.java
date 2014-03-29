package ru.javaschool.clientGui.views;


import ru.javaschool.database.entities.Passenger;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class PassengerView  extends AbstractTableModel {

    enum PassColumns {ID, FIRST_NAME, LAST_NAME, BIRTH_DATE};

    private final String[] columnTitles = {"Id", "FirstName", "LastName", "BirthDate"};

    private List<Passenger> passengerList;

    public PassengerView(List<Passenger> passengerList) {
        super();
        this.passengerList = passengerList;
    }

    @Override
    public int getRowCount() {
        return passengerList.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Passenger passenger = passengerList.get(rowIndex);
        PassColumns passColumns = (PassColumns.values())[columnIndex];
        switch (passColumns) {
            case ID:
                return passenger.getPassengerId();
            case FIRST_NAME:
                return passenger.getFirstName();
            case LAST_NAME:
                return passenger.getLastName();
            case BIRTH_DATE:
                return passenger.getBirthDate();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int c) {
        return columnTitles[c];
    }
}
