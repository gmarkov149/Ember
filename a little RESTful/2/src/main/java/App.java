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
		    user.getGender();
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
    }
}
