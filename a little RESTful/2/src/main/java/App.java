import java.util.LinkedList;

import com.google.gson.Gson;

import static spark.Spark.*;

public class App 
{
    public static void main( String[] args )
    {
        UserController system = new UserController();
        post("/users", (request, response) -> {
			response.type("application/json");
		    User user = new Gson().fromJson(request.body(), User.class);
		    system.addUser(user);
		 
		    return new Gson()
		      .toJson(new StandardResponse(StatusResponse.SUCCESS));
        });
        get("/users", (request, response) -> {
     	   response.type("application/json");
     	   for(User user: system.getSystem())
     	   {
     		   System.out.println(user);
     	   }
     	    return new Gson().toJson(
     	      new StandardResponse(StatusResponse.SUCCESS,new Gson()
     	        .toJsonTree(system.getSystem())));
         });
        //get  matched for username
        get("/users/matched/:username/:password", (request, response) -> {
      	   response.type("application/json");
      	   User currentUser = system.findUser(request.params(":username"), request.params(":password"));
      	   system.initCurrentMatched(currentUser);
      	    return new Gson().toJson(
      	      new StandardResponse(StatusResponse.SUCCESS,new Gson()
      	        .toJsonTree(currentUser.getMatched())));
          });
        post("/users/matched", (request, response) -> {
			response.type("application/json");
		    User toReturnMatches = new Gson().fromJson(request.body(), User.class);
		    User currentUser = system.findUser(toReturnMatches.getUsername(), toReturnMatches.getPassword());
		 
		    system.initCurrentMatched(currentUser);
      	    return new Gson().toJson(
      	      new StandardResponse(StatusResponse.SUCCESS,new Gson()
      	        .toJsonTree(currentUser.getMatched())));
        });
        options("/users/:username/:password", (request, response) -> {
        	response.type("application/json");
            return new Gson().toJson(
              new StandardResponse(StatusResponse.SUCCESS, 
                (system.userExists(
                  request.params(":username"), request.params(":password"))) ? "User exists" : "User does not exists" ));
          });
        post("/users/exists", (request, response) -> {
			response.type("application/json");
		    User temp = new Gson().fromJson(request.body(), User.class);
		    
		 
		    if(system.userExists(
	                  temp.getUsername(), temp.getPassword()))
		    {
		    	return new Gson().toJson(
		        	      new StandardResponse(StatusResponse.SUCCESS,new Gson()
		        	        .toJsonTree(temp)));
		    }
		    else
		    {
		    	return new Gson().toJson(
		                new StandardResponse(StatusResponse.SUCCESS, 
		                  "User does not exist"));
		            
		    }
		    
        });
        
    }
}
