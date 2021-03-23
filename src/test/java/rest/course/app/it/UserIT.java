package rest.course.app.it;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.NotFoundException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import rest.course.app.clientproxy.UserControllerRestEasyProxy;
import rest.course.app.model.Gender;
import rest.course.app.model.User;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
class UserIT {
	
	@Autowired
	private UserControllerRestEasyProxy userControllerRestEasyProxy;

	
	@Test
	public void shouldInsertUser() {
		UUID userUid = UUID.randomUUID();
		User joe = new User(userUid, "Joe", "Jones", Gender.MALE, 22, "joejones@gmail.com");
		
		userControllerRestEasyProxy.insertNewUser(joe);
		User responseUser =  userControllerRestEasyProxy.fetchUser(userUid);
		
		assertThat(responseUser).usingRecursiveComparison().isEqualTo(joe);
		
	}
	
	
	@Test
	//Instead of assertThatThrownBy this is also possible:
	//@Test(expected = NotFoundException.class)
	public void shouldDeleteUser() {
		UUID userUid = UUID.randomUUID();
		User joe = new User(userUid, "Joe", "Jones", Gender.MALE, 22, "joejones@gmail.com");
		
		userControllerRestEasyProxy.insertNewUser(joe);
		User responseUser =  userControllerRestEasyProxy.fetchUser(userUid);
		
		assertThat(responseUser).usingRecursiveComparison().isEqualTo(joe);
		userControllerRestEasyProxy.deleteUser(userUid);
		 
		assertThatThrownBy(()-> userControllerRestEasyProxy.fetchUser(userUid))
		.isInstanceOf(NotFoundException.class);

	}
	
	
	@Test
	public void shouldUpdateUser() {
		
		UUID userUid = UUID.randomUUID();
		User joe = new User(userUid, "Joe", "Jones", Gender.MALE, 22, "joejones@gmail.com");		
		userControllerRestEasyProxy.insertNewUser(joe);
		
		User updatedUser = new User(userUid, "Joe1", "Jones1", Gender.MALE, 23, "joe1jones1@gmail.com");
		userControllerRestEasyProxy.updateUser(updatedUser);
		
		User regainedUser = userControllerRestEasyProxy.fetchUser(userUid);
		assertThat(regainedUser).usingRecursiveComparison().isEqualTo(updatedUser);
	}
	
	
	@Test
	public void shouldFetchUsersByGender() {
		UUID userUid = UUID.randomUUID();
		User joe = new User(userUid, "Joe", "Jones", Gender.MALE, 22, "joejones@gmail.com");		
		userControllerRestEasyProxy.insertNewUser(joe);
		
		List<User>females = userControllerRestEasyProxy.fetchUsers(Gender.FEMALE.name());
		assertThat(females).isEmpty();
		//This would be also an option:
		assertThat(females).extracting("userUid").doesNotContain(joe.getUserUid());
		assertThat(females).extracting("firstName").doesNotContain(joe.getFirstName());
		assertThat(females).extracting("lastName").doesNotContain(joe.getLastName());
		assertThat(females).extracting("gender").doesNotContain(joe.getGender());
		assertThat(females).extracting("email").doesNotContain(joe.getEmail());
		assertThat(females).extracting("age").doesNotContain(joe.getAge());
		
		List<User>males = userControllerRestEasyProxy.fetchUsers(Gender.MALE.name());		
		assertThat(males).extracting("userUid").contains(joe.getUserUid());
		assertThat(males).extracting("firstName").contains(joe.getFirstName());
		assertThat(males).extracting("lastName").contains(joe.getLastName());
		assertThat(males).extracting("gender").contains(joe.getGender());
		assertThat(males).extracting("email").contains(joe.getEmail());
		assertThat(males).extracting("age").contains(joe.getAge());
	}
		
	
	
	
}
