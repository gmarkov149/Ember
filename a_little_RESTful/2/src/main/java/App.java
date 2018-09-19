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
        get("/users/matched/:username", (request, response) -> {
      	   response.type("application/json");
      	   User currentUser = system.findUser(request.params(":username"));
      	   if(currentUser.getMatched().size() == 0)
      	   {
      		 system.initCurrentMatched(currentUser);
      	   }
      	   
      	    return new Gson().toJson(
      	      new StandardResponse(StatusResponse.SUCCESS,new Gson()
      	        .toJsonTree(currentUser.getMatched())));
          });
        post("/users/matched", (request, response) -> {
			response.type("application/json");
		    User toReturnMatches = new Gson().fromJson(request.body(), User.class);
		    User currentUser = system.findUser(toReturnMatches.getUsername());
		    if(currentUser.getMatched().size() == 0)
		    {
		    	system.initCurrentMatched(currentUser);
		    }
		    
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
		 
		    system.editUser(currentUser);
      	    return new Gson().toJson(
      	      new StandardResponse(StatusResponse.SUCCESS,new Gson()
      	        .toJsonTree(currentUser)));
        });
    }
}
