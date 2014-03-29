package ru.javaschool.controller;


import org.apache.log4j.Logger;
import ru.javaschool.dao.EmfInit;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class implements server socket behaviour.
 * Describes how working server side connection.
 */
public class ServerSocketImpl {

    private static final Logger logger = Logger.getLogger(ServerSocketImpl.class);

   public static EntityManagerFactory emf = EmfInit.emf;
    private ServerSocket serverSocket;
    private boolean isRunable;
    private final ExecutorService poolConnections = Executors.newFixedThreadPool(5);

    /**
     * Starting socket on port, works while shutDownServer wouldn't be pressed.
     */

    public void startServer() {
        if (!isRunable) {
            Socket socket;

            try {
                isRunable = true;
                serverSocket = new ServerSocket(4444);
                while (true) {
                    socket = serverSocket.accept();
                    poolConnections.execute(new SocketHandler(socket));

                }
            } catch (IOException e) {
                logger.error("IO error, starting server was failed caused by: " + e);
            }
        }
    }

    /**
     * This method implement shutting down server by closing socket connection.
     */
    public void shutDownServer() {
        try {
            emf.close();
            emf = null;
            serverSocket.close();
        } catch (IOException e) {
            logger.error("IO error happend while closing a serverSocket: " + e);
        }
        isRunable = false;
    }
}

