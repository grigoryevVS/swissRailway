package ru.javaschool.database.criteria;

import java.io.Serializable;

/**
 * Clients request, send to the server.
 * Request can hold information about changing data or
 * finding some result by this criteria.
 */
public class Request implements Serializable{

    private static final long serialVersionUID = -4304486052013797450L;

    private String title;
    private Object reqBody;

    public String getTitle() {
        return title;
    }

    public Object getReqBody() {
        return reqBody;
    }

    public Request(String title){
        this.title = title;
        reqBody = null;
    }

    public Request(String title, Object obj){
        this.title = title;
        this.reqBody = obj;
    }

}
