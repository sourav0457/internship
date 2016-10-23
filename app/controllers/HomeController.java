package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Profile;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.stream.Collectors;

/**
 * Created by lubuntu on 10/23/16.
 */
public class HomeController extends Controller {

    @Inject
    ObjectMapper objectMapper;

    public Result getProfile(Long id){

        User user = User.find.byId(id);
        Profile profile= user.profile.find.byId(id);

        ObjectNode userJson = objectMapper.createObjectNode();
        userJson.put("emailid",user.email);
        userJson.put("firstname",profile.firstname);
        userJson.put("lastname",profile.lastname);
        userJson.put("company",profile.company);
        userJson.set("connections", objectMapper.valueToTree(user.connections.stream().map(connection ->{
            ObjectNode connectionJson = objectMapper.createObjectNode();
            Profile profile1= profile.find.byId(id);

            connectionJson.put("firstname",profile1.firstname);
            connectionJson.put("lastname",profile1.lastname);
            connectionJson.put("company",profile1.company);
            connectionJson.put("id",profile1.id);
            return connectionJson;
        }).collect(Collectors.toList())));

    }
    }

