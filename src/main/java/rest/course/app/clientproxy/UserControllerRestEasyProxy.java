package rest.course.app.clientproxy;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import rest.course.app.model.User;

public interface UserControllerRestEasyProxy {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	List<User> fetchUsers(@QueryParam("gender") String gender);
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{userUid}")
	User fetchUser(@PathParam("userUid") UUID userUid);
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	void insertNewUser(User user); 
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	void updateUser(User user); 
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{userUid}")
	void deleteUser(@PathParam("userUid") UUID userUid);
	
	
}
