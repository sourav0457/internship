package controllers;

import models.ConnectionRequest;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by lubuntu on 10/23/16.
 */
public class RequestController extends Controller {

    public Result sendRequest(Long sid,Long rid){

    if(sid == null || rid == null || User.find.byId(sid)==null || User.find.byId(rid)==null){
        return ok();
    }
        else{
        ConnectionRequest c = new ConnectionRequest();
        c.sender.id=sid;
        c.receiver.id =rid;
        c.status=ConnectionRequest.Status.WAITING;
        ConnectionRequest.db().save(c)
        return ok();
    }
    }
    public Result acceptRequest(Long req){

        if(req!= null)
        {
            return ok();
        }
        else{
            ConnectionRequest c =ConnectionRequest.find.byId(req);
            c.status= ConnectionRequest.Status.ACCEPTED;
            ConnectionRequest.db().save(c);
            c.sender.connections.add(c.receiver);
            c.receiver.connections.add(c.sender);
            User.db().save(c.receiver);
            User.db().save(c.sender);
        }
    }

}
