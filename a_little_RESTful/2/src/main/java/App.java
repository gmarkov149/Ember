import com.google.gson.Gson;

import static spark.Spark.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class App 
{
    public static void main( String[] args )
    {
    	
    	
    	
        UserController system = new UserController();
        post("/users", (request, response) -> {
			response.type("application/json");
		    User user = new Gson().fromJson(request.body(), User.class);
		    if(system.checkIfAvailable(user.getUsername()))
		    {
		    	user.parseHobbies();

		    	
		    	
		    	system.addUser(user);
		    	system.updatePotentialMatches(user);

		    	return new Gson()
		    			.toJson(new StandardResponse(StatusResponse.SUCCESS));
		    }
		    else
		    {
		    	return new Gson()
		    			.toJson(new StandardResponse(StatusResponse.ERROR));
		    }
        });
        //method is useless if we're having trouble storing all users in the same place
        get("/users", (request, response) -> {
     	   response.type("application/json");
     	    return new Gson().toJson(
     	      new StandardResponse(StatusResponse.SUCCESS,new Gson()
     	        .toJsonTree(system.getSystem())));
         });
        //get a user with the specified username
        get("/users/a/:username", (request, response) -> {
       	   response.type("application/json");
       	   User toReturn = system.toUserObject(request.params(":username"));
       	    return new Gson().toJson(
       	      new StandardResponse(StatusResponse.SUCCESS,new Gson()
       	        .toJsonTree(toReturn)));
           });
        //get  matched for username
        get("/users/matched/:username/:start/:end", (request, response) -> {
      	   response.type("application/json");
      	   User currentUser = system.toUserObject(request.params(":username"));
      	    return new Gson().toJson(
      	      new StandardResponse(StatusResponse.SUCCESS,new Gson()
      	        .toJsonTree(system.getMatches(currentUser, Integer.parseInt(request.params(":start")),
      	        		Integer.parseInt(request.params(":end"))))));
          });
        
        get("/users/chat/:user/:match/:startIndex", (request, response) -> {
			response.type("application/json");
			ArrayList<String> messages = system.getChat(request.params(":user"), request.params(":match"), Integer.parseInt(request.params(":startIndex")));
		    
      	    return new Gson().toJson(
      	      new StandardResponse(StatusResponse.SUCCESS,new Gson()
      	        .toJsonTree(messages)));
        });
        get("/users/chat/message/:sender/:receiver/:date/:time/:message", (request, response) -> {
			response.type("application/json");
			system.sendMessage(
        		request.params(":sender"),
        		request.params(":receiver"),
        		request.params(":message"),
        		request.params(":date"),
        		request.params(":time")
        	);
      	    return new Gson().toJson(
      	      new StandardResponse(StatusResponse.SUCCESS));
        });
        post("/users/exists", (request, response) -> {
			response.type("application/json");
		    User temp = new Gson().fromJson(request.body(), User.class);
		    
		    User toReturn = system.userExists(
	                  temp.getUsername(), temp.getPassword());
		    if(toReturn != null)
		    {
		    	return new Gson().toJson(
		        	      new StandardResponse(StatusResponse.SUCCESS,new Gson()
		        	        .toJsonTree(toReturn)));
		    }
		    else
		    {
		    	return new Gson().toJson(
		                new StandardResponse(StatusResponse.SUCCESS, 
		                  "User does not exist"));
		            
		    }
		    
        });
        post("/users/edit", (request, response) -> {
			response.type("application/json");
		    
		    User currentUser = new Gson().fromJson(request.body(), User.class);
		 
		    currentUser.parseHobbies();
		    system.editUser(currentUser);
		    system.updatePotentialMatches(currentUser);
      	    return new Gson().toJson(
      	      new StandardResponse(StatusResponse.SUCCESS,new Gson()
      	        .toJsonTree(currentUser)));
        });
        //get potential
        get("/users/potentialMatches/:username/:start/:end", (request, response) -> {
      	   response.type("application/json");
      	   User currentUser = system.toUserObject(request.params(":username"));

      	   // Update potential matches for user
      	   system.updatePotentialMatches(currentUser); 
      	   
      	   ArrayList<User> potentialMatches = system.get10Potential(system.toUserObject(request.params(":username")), Integer.parseInt(request.params(":start")), Integer.parseInt(request.params(":end")));
      	   

      	    return new Gson().toJson(
      	      new StandardResponse(StatusResponse.SUCCESS,new Gson()
      	        .toJsonTree(potentialMatches)));
          });
        //add a match for the specified user
        post("/users/match/:username", (request, response) -> {
			response.type("application/json");
		    
		    User userToMatch = new Gson().fromJson(request.body(), User.class);
		    User currentUser = system.toUserObject(request.params(":username"));

		    // Add new match to one user
		    system.updateMatches(currentUser, userToMatch);

      	    return new Gson().toJson(
      	      new StandardResponse(StatusResponse.SUCCESS,new Gson()
      	        .toJsonTree(currentUser)));
        });
        get("/users/reset", (request, response) -> {
			response.type("application/json");
		    
		    system.reset();
      	    return new Gson().toJson(
      	      new StandardResponse(StatusResponse.SUCCESS));
        });
        
    }
}
