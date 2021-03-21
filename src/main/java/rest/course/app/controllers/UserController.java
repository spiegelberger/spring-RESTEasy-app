package rest.course.app.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rest.course.app.model.User;
import rest.course.app.service.UserService;

/*
 * If we use RestEasy we do not use this controller
 */
//@RestController
//@RequestMapping(path = "/api/v1/users")
public class UserController {

	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<User> fetchUsers(@QueryParam("gender")String gender) {
		
		return userService.getAllUsers(Optional.ofNullable(gender));
	}

	@GetMapping(path = "{userUid}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> fetchUser(@PathVariable("userUid") UUID userUid) {

		Optional<User> userOptional = userService.getUser(userUid);

		if (userOptional.isPresent()) {
			return ResponseEntity.ok(userOptional.get());
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ErrorMessage("user " + userUid + " was not found."));
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
				produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> insertNewUser(@RequestBody User user) {

		int result = userService.insertUser(user);
		return getIntegerResponseEntity(result);
	}

	
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
				produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> updateUser(@RequestBody User user) {

		int result = userService.updateUser(user);
		return getIntegerResponseEntity(result);

	}
	
	@DeleteMapping(path = "{userUid}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer>deleteUser(@PathVariable ("userUid") UUID userUid){
		
		int result = userService.removeUser(userUid);
		return getIntegerResponseEntity(result);
		
	}

	
	private ResponseEntity<Integer> getIntegerResponseEntity(int result) {
		if (result == 1) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.badRequest().build();
	}

	
	
	
}
