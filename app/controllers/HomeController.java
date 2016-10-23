package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.ConnectionRequest;
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
        Profile profile = Profile.find.byId(user.profile.id);

        ObjectNode userJson = objectMapper.createObjectNode();
        userJson.put("emailid",user.email);
        userJson.put("firstname",profile.firstname);
        userJson.put("lastname",profile.lastname);
        userJson.put("company",profile.company);
        userJson.set("connections", objectMapper.valueToTree(user.connections.stream().map(connection ->{
            ObjectNode connectionJson = objectMapper.createObjectNode();
            Profile profile1= Profile.find.byId(id);

            connectionJson.put("firstname", profile1.firstname);
            connectionJson.put("lastname", profile1.lastname);
            connectionJson.put("company", profile1.company);
            connectionJson.put("id", connection.id);
            return connectionJson;
        }).collect(Collectors.toList())));

        userJson.set("connectionRequests", objectMapper.valueToTree(user.connectionRequestReceived.stream().filter(
                x -> x.status.equals(ConnectionRequest.Status.WAITING)).map(connectionRequest ->{
            ObjectNode connectionRequestJson = objectMapper.createObjectNode();
            User sender= User.find.byId(connectionRequest.sender.id);
            Profile senderProfile= Profile.find.byId(sender.profile.id);
            connectionRequestJson.put("id",connectionRequest.id);
            connectionRequestJson.put("firstname",senderProfile.firstname);
            return connectionRequestJson ;
        }).collect(Collectors.toList())));

        userJson.set("suggestions", objectMapper.valueToTree(User.find.all().stream()
                .filter(x -> !user.equals(x))
                .filter(x -> !user.connections.contains(x))
                .filter(x -> !user.connectionRequestReceived.stream().map(y -> y.sender).collect(Collectors.toList()).contains(x))
                .filter(x -> !user.connectionRequestSent.stream().map(y -> y.receiver).collect(Collectors.toList()).contains(x))
                .map(x -> {
                    ObjectNode suggestionJson = objectMapper.createObjectNode();
                    Profile suggestionProfile = Profile.find.byId(x.profile.id);
                    suggestionJson.put("id", x.id);
                    suggestionJson.put("firstName", suggestionProfile.firstname);
                    suggestionJson.put("lastName", suggestionProfile.lastname);
                    return suggestionJson;
                }).collect(Collectors.toList())));

        return ok(userJson);
    }
}

