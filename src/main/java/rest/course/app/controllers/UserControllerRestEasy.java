package rest.course.app.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import rest.course.app.model.User;
import rest.course.app.service.UserService;

@Component
@Validated
@Path("api/v1/users")
public class UserControllerRestEasy {

	private UserService userService;

	@Autowired
	public UserControllerRestEasy(UserService userService) {
		this.userService = userService;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> fetchUsers(@QueryParam("gender") String gender) {
		return userService.getAllUsers(Optional.ofNullable(gender));
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{userUid}")
	public User fetchUser(@PathParam("userUid") UUID userUid) {

		return userService.getUser(userUid)
				.orElseThrow(() -> new NotFoundException("user " + userUid + " not found."));
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void insertNewUser(@Valid User user) {

		userService.insertUser(user);
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void updateUser(User user) {

		userService.updateUser(user);
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{userUid}")
	public void deleteUser(@PathParam("userUid") UUID userUid) {

		userService.removeUser(userUid);
	}

}
