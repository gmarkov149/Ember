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
		    	system.getPotentialMatches(user);
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
		 
		    system.editUser(currentUser);
		    currentUser.parseHobbies();
		    system.updatePotential(currentUser);
      	    return new Gson().toJson(
      	      new StandardResponse(StatusResponse.SUCCESS,new Gson()
      	        .toJsonTree(currentUser)));
        });
        //get potential
        get("/users/potentialMatches/:username", (request, response) -> {
      	   response.type("application/json");
      	   User currentUser = system.findUser(request.params(":username"));
      	   ArrayList<Key_Value_Pair> key_valueList = currentUser.getPotential();
      	   Collections.sort(key_valueList, new Comparator<Key_Value_Pair>() 
			  {
				  @Override
				  public int compare(Key_Value_Pair n1, Key_Value_Pair n2)
				  {
					  if(n1.getValue() < n2.getValue())
					  {
					    	return 1;
					  }
					  if(n1.getValue() > n2.getValue())
					   {
						    return -1;
					   }
					  return 0;
				  }
			  });
      	   ArrayList<User> returnList = new ArrayList<User>();
      	   for(int i=0;i<key_valueList.size();i++)
      	   {
      		   returnList.add(system.findUser(key_valueList.get(i).getKey()));
      	   }
      	    return new Gson().toJson(
      	      new StandardResponse(StatusResponse.SUCCESS,new Gson()
      	        .toJsonTree(returnList)));
          });
        //add a match for the specified user
        post("/users/match/:username", (request, response) -> {
			response.type("application/json");
		    
		    User userToMatch = new Gson().fromJson(request.body(), User.class);
		    User currentUser = system.findUser(request.params(":username"));
		    currentUser.getMatched().add(system.findUser(userToMatch.getUsername()));
		    currentUser.removePotential(userToMatch.getUsername());
      	    return new Gson().toJson(
      	      new StandardResponse(StatusResponse.SUCCESS,new Gson()
      	        .toJsonTree(currentUser)));
        });
    }
}
