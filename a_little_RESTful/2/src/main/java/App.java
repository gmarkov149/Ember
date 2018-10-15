import com.google.gson.Gson;

import static spark.Spark.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class App 
{
    public static void main( String[] args )
    {
    	//open a potentialMatches endpoint
    	//add potential matches for previous users
    	//add the filtering functionality
        UserController system = new UserController();
        post("/users", (request, response) -> {
			response.type("application/json");
		    User user = new Gson().fromJson(request.body(), User.class);
		    if(system.checkIfAvailable(user.getUsername()))
		    {
		    	user.parseHobbies();
		    	system.updatePotentialMatches(user);
		    	system.addUser(user);
		 
		    	return new Gson()
		    			.toJson(new StandardResponse(StatusResponse.SUCCESS));
		    }
		    else
		    {
		    	return new Gson()
		    			.toJson(new StandardResponse(StatusResponse.ERROR));
		    }
        });
        get("/users", (request, response) -> {
     	   response.type("application/json");
     	   /*for(User user: system.getSystem())
     	   {
     		   System.out.println(user);
     	   }*/
     	    return new Gson().toJson(
     	      new StandardResponse(StatusResponse.SUCCESS,new Gson()
     	        .toJsonTree(system.getSystem())));
         });
        //get  matched for username
        get("/users/matched/:username", (request, response) -> {
      	   response.type("application/json");
      	   User currentUser = system.findUser(request.params(":username"));
      	   /*if(currentUser.getMatched().size() == 0)
      	   {
      		 system.initCurrentMatched(currentUser);
      	   }*/
      	   
      	    return new Gson().toJson(
      	      new StandardResponse(StatusResponse.SUCCESS,new Gson()
      	        .toJsonTree(currentUser.getMatched())));
          });
        // View a users matches
        post("/users/matched", (request, response) -> {
			response.type("application/json");
		    User toReturnMatches = new Gson().fromJson(request.body(), User.class);
		    User currentUser = system.findUser(toReturnMatches.getUsername());
		    /*if(currentUser.getMatched().size() == 0)
		    {
		    	system.initCurrentMatched(currentUser);
		    }*/
		    
      	    return new Gson().toJson(
      	      new StandardResponse(StatusResponse.SUCCESS,new Gson()
      	        .toJsonTree(currentUser.getMatched())));
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
        get("/users/potentialMatches/:username", (request, response) -> {
      	   response.type("application/json");
      	   User currentUser = system.findUser(request.params(":username"));

      	   // Update potential matches for user
      	   system.updatePotentialMatches(currentUser); 

      	   ArrayList<User> potentialMatches = currentUser.getPotential();

      	    return new Gson().toJson(
      	      new StandardResponse(StatusResponse.SUCCESS,new Gson()
      	        .toJsonTree(potentialMatches)));
          });
        //add a match for the specified user
        post("/users/match/:username", (request, response) -> {
			response.type("application/json");
		    
		    User userToMatch = new Gson().fromJson(request.body(), User.class);
		    User currentUser = system.findUser(request.params(":username"));

		    // Add new match to BOTH users
		    system.updateMatches(currentUser, userToMatch);

      	    return new Gson().toJson(
      	      new StandardResponse(StatusResponse.SUCCESS,new Gson()
      	        .toJsonTree(currentUser)));
        });
    }
}
