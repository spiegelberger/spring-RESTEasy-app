package rest.course.app.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jersey.repackaged.com.google.common.collect.ImmutableList;
import rest.course.app.dao.UserDao;
import rest.course.app.model.Gender;
import rest.course.app.model.User; 

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	
	@InjectMocks
	UserService userService;
	
	@Mock
	UserDao userDao;

	
	@BeforeEach
	void setUp() throws Exception {
	}

	
	@Test
	void testGetAllUsers() {
		UUID annaUserUid = UUID.randomUUID();
		User anna = new User(annaUserUid, "Anna", "Montana", Gender.FEMALE, 30, "annamontana@gmail.com");
		ImmutableList<User>users = new ImmutableList.Builder<User>()
				.add(anna)
				.build();
		
		when(userDao.selectAllUsers()).thenReturn(users);
		
		List<User>allUsers = userService.getAllUsers(Optional.empty());
		
		assertThat(allUsers).hasSize(1);
		
		User user = allUsers.get(0);
		
		assertAnnaUserFields(user);
		
	}
	
	
	@Test
	void testGetAllUsersByGender() {
		UUID annaUserUid = UUID.randomUUID();
		UUID joeUserUid = UUID.randomUUID();
		User anna = new User(annaUserUid, "Anna", "Montana", Gender.FEMALE, 30, "annamontana@gmail.com");
		User joe = new User(joeUserUid, "Joe", "Jones", Gender.MALE, 30, "joejones@outlook.com");
		ImmutableList<User>users = new ImmutableList.Builder<User>()
				.add(anna)
				.add(joe)
				.build();
		
		when(userDao.selectAllUsers()).thenReturn(users);
		
		List<User>females = userService.getAllUsers(Optional.of("FEMALE"));
		
		assertThat(females).hasSize(1);
		assertAnnaUserFields(females.get(0));
			
	}
	
	
	@Test
	void testGetAllUsersByGenderShouldThrowExceptionWhenGenderIsInvalid() {
		
		assertThatThrownBy(() -> userService.getAllUsers(Optional.of("anyNonsense")))
				.isInstanceOf(IllegalStateException.class)
				.hasMessageContaining("Invalid gender");
	}

	
	@Test
	void testGetUser() {
		UUID annaUserUid = UUID.randomUUID();
		User anna = new User(annaUserUid, "Anna", "Montana", Gender.FEMALE, 30, "annamontana@gmail.com");
		
		when(userDao.selectUserByUserUid(annaUserUid)).thenReturn(Optional.of(anna));
		Optional<User> optionalUser = userService.getUser(annaUserUid);
		
		assertThat(optionalUser.isPresent()).isTrue();
		
		User user = optionalUser.get();
		
		assertAnnaUserFields(user);
	}


	@Test
	void testUpdateUser() {
		
		UUID annaUserUid = UUID.randomUUID();
		User anna = new User(annaUserUid, "Anna", "Montana", Gender.FEMALE, 30, "annamontana@gmail.com");
		
		when(userDao.selectUserByUserUid(any(UUID.class))).thenReturn(Optional.of(anna));
		when(userDao.updateUser(anna)).thenReturn(1);
		
		ArgumentCaptor<User>captor = ArgumentCaptor.forClass(User.class);
		
		int updateResult = userService.updateUser(anna);
		
		verify(userDao).selectUserByUserUid(any(UUID.class));
		verify(userDao).updateUser(anna);
		assertThat(updateResult).isEqualTo(1);
		verify(userDao).updateUser(captor.capture());
		
		User user = captor.getValue();
		assertAnnaUserFields(user);
		
	}
	

	@Test
	void testRemoveUser() {
		
		UUID annaUserUid = UUID.randomUUID();
		User anna = new User(annaUserUid, "Anna", "Montana", Gender.FEMALE, 30, "annamontana@gmail.com");
		
		when(userDao.selectUserByUserUid(any(UUID.class))).thenReturn(Optional.of(anna));
		when(userDao.removeUser(annaUserUid)).thenReturn(1);
		
		int deleteResult = userService.removeUser(annaUserUid);
		
		verify(userDao).selectUserByUserUid(any(UUID.class));
		verify(userDao).removeUser(annaUserUid);
		assertThat(deleteResult).isEqualTo(1);
		
	}

	
	@Test
	void testInsertUser() {
		User anna = new User(null, "Anna", "Montana", Gender.FEMALE, 30, "annamontana@gmail.com");
		
		when(userDao.insertUser(any(UUID.class), eq(anna))).thenReturn(1);
		
		ArgumentCaptor<User>captor = ArgumentCaptor.forClass(User.class);		
		
		int insertResult = userService.insertUser(anna);
		
		verify(userDao).insertUser(any(UUID.class), captor.capture());
		
		User user = captor.getValue();
		assertAnnaUserFields(user);
				
		assertThat(insertResult).isEqualTo(1);
		
	}

	
	private void assertAnnaUserFields(User user) {
		assertThat(user.getAge()).isEqualTo(30);
		assertThat(user.getFirstName()).isEqualTo("Anna");
		assertThat(user.getLastName()).isEqualTo("Montana");
		assertThat(user.getGender()).isEqualTo(Gender.FEMALE);
		assertThat(user.getEmail()).isEqualTo("annamontana@gmail.com");
		assertThat(user.getUserUid()).isInstanceOf(UUID.class);
	}
}
