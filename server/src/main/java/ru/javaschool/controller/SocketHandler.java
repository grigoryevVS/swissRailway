package ru.javaschool.controller;


import org.apache.log4j.Logger;
import ru.javaschool.database.criteria.Request;
import ru.javaschool.database.criteria.Response;
import ru.javaschool.database.criteria.SampleObject;
import ru.javaschool.database.criteria.ScheduleConstraints;
import ru.javaschool.database.entities.*;
import ru.javaschool.services.EmployeeService;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
            logger.error(e.getMessage());
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                if (!(socket != null && socket.isClosed())) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
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

        if (request.getTitle().equals("Get all trains")) {
            response = new Response((emplService.getTrainList()));
        } else if (request.getTitle().equals("Get all stations")) {
            response = new Response((emplService.getStationList()));
        } else if (request.getTitle().equals("Create train")) {
            response = new Response(emplService.createTrain((Train) request.getReqBody()));
        } else if (request.getTitle().equals("Create station")) {
            response = new Response(emplService.createStation((Station) request.getReqBody()));
        } else if (request.getTitle().equals("Check authorization")) {
            response = new Response(emplService.checkExist((EmployeeData) request.getReqBody()));
        } else if (request.getTitle().equals("Get registered passengers")) {
            response = new Response(emplService.getAllRegisteredPassOnTrain((Schedule) request.getReqBody()));
        } else if (request.getTitle().equals("Get all schedule")) {
            response = new Response((emplService.getScheduleList()));
        } else if (request.getTitle().equals("Buy ticket")) {
            response = new Response(emplService.buyTicket((SampleObject<Long, Passenger>) request.getReqBody()));
        } else if (request.getTitle().equals("Get revised schedule")) {
            response = new Response(emplService.getRevisedScheduleList((ScheduleConstraints) request.getReqBody()));
        } else if (request.getTitle().equals("Get station by name")) {
            response = new Response(emplService.getStationByName((String) request.getReqBody()));
        } else if (request.getTitle().equals("Create route")) {
            emplService.createRoute((Route) request.getReqBody());
            return new Response(null);
        } else if (request.getTitle().equals("Create schedule")) {
            response = new Response(emplService.createSchedule((Schedule) request.getReqBody()));
        } else {
            response = new Response(request.getTitle(), request.getTitle() + " - it is wrong request, try fix this.", true);
        }
        return response;
    }
}