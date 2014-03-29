package ru.javaschool.controller;


import org.apache.log4j.Logger;
import ru.javaschool.database.criteria.Request;
import ru.javaschool.database.criteria.Response;
import ru.javaschool.database.entities.EmployeeData;
import ru.javaschool.database.entities.Schedule;
import ru.javaschool.database.entities.Station;
import ru.javaschool.database.entities.Train;
import ru.javaschool.services.EmployeeService;

import java.io.*;
import java.net.Socket;


public class SocketHandler implements Runnable {

    private static final Logger logger = Logger.getLogger(SocketHandler.class);
    private Socket socket;
    private EmployeeService emplService = new EmployeeService();

    public SocketHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream inStream = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));

            Request request = (Request) inStream.readObject();

            Response actualResponse = this.handleRequest(request);
            ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
            outStream.writeObject(actualResponse);
            outStream.flush();
            socket.shutdownOutput();
            socket.close();

        } catch (IOException e) {
            logger.error("IO error while running handling socket!" + e);
        } catch (ClassNotFoundException e) {
            logger.error("Class not found! " + e);
        } finally {
            if (!(socket != null && socket.isClosed())) {
                try {
                    if (socket != null) {
                        socket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error("Error while closing socket!" + e);
                }
            }
        }
    }

    /**
     * Working with services.
     *
     * @param request
     * @return
     */
    private Response handleRequest(Request request) {
        Response response;
        Boolean b = false;

        if (request.getTitle().equals("Get all trains")) {
            response = new Response((Serializable) (emplService.getTrainList()));
        } else if (request.getTitle().equals("Create train")) {
            response = new Response(emplService.createTrain((Train) request.getReqBody()));
        } else if (request.getTitle().equals("Create station")){
            response = new Response(emplService.createStation((Station) request.getReqBody()));
        } else if (request.getTitle().equals("Check authorization")){
            response = new Response(emplService.checkExist((EmployeeData) request.getReqBody()));
        } else if (request.getTitle().equals("Get registered passengers")){
            response = new Response((Serializable) emplService.getAllRegisteredPassOnTrain((Schedule) request.getReqBody()));
        } else if(request.getTitle().equals("Get all schedule")){
            response = new Response((Serializable) (emplService.getScheduleList()));
        } else {
            response = new Response(request.getTitle(), request.getTitle() + " - it is wrong request, try fix this.", true );
        }
        return response;
    }
}