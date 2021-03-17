package rest.course.app.dao;

import static org.assertj.core.api.Assertions.assertThat;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import rest.course.app.model.Gender;
import rest.course.app.model.User;

class FakeDaoTest {
	
	private FakeDao fakeDao;

	@BeforeEach
	void setUp() throws Exception {
		fakeDao = new FakeDao();
	}

	@Test
	void testSelectAllUsers() {
		List<User>users = fakeDao.selectAllUsers();
		assertThat(users.size()).isEqualTo(1);
		
		User user = users.get(0);
		
		assertThat(user.getAge()).isEqualTo(22);
		assertThat(user.getFirstName()).isEqualTo("Joe");
		assertThat(user.getLastName()).isEqualTo("Jones");
		assertThat(user.getGender()).isEqualTo(Gender.MALE);
		assertThat(user.getEmail()).isEqualTo("joejones@gmail.com");
		assertThat(user.getUserUid()).isNotNull();
	}

	@Test
	void testSelectUserByUserUid() {
		UUID annaUserUid = UUID.randomUUID();
		
		User anna = new User(annaUserUid, "Anna", "Montana", Gender.FEMALE, 30, "annamontana@gmail.com");
		fakeDao.insertUser(annaUserUid, anna);
		
		assertThat(fakeDao.selectAllUsers().size()).isEqualTo(2);
		
		Optional<User> optionalUser = fakeDao.selectUserByUserUid(annaUserUid);
		
		assertThat(optionalUser.isPresent()).isTrue();
		assertThat(optionalUser.get()).usingRecursiveComparison().isEqualTo(anna);
		
	}
	
	@Test
	void testShouldNotSelectUserByUserUid() {
		Optional<User> nullUser = fakeDao.selectUserByUserUid(UUID.randomUUID());
		
		assertThat(nullUser.isEmpty()).isTrue();
			}

	@Test
	void testUpdateUser() {
		UUID joeUserUid = fakeDao.selectAllUsers().get(0).getUserUid();		
		User newJoe = new User(joeUserUid, "Joe1", "Jones1", Gender.MALE, 23, "joejones1@gmail.com");
		
		fakeDao.updateUser(newJoe);
		
		Optional<User> optionalUser = fakeDao.selectUserByUserUid(joeUserUid);
		
		assertThat(optionalUser.isPresent()).isTrue();
		assertThat(fakeDao.selectAllUsers()).hasSize(1);
		assertThat(optionalUser.get()).usingRecursiveComparison().isEqualTo(newJoe);
	}

	@Test
	void testRemoveUser() {
		UUID joeUserUid = fakeDao.selectAllUsers().get(0).getUserUid();
		
		fakeDao.removeUser(joeUserUid);
		
		assertThat(fakeDao.selectAllUsers().size()).isEqualTo(0);
		assertThat(fakeDao.selectUserByUserUid(joeUserUid).isPresent()).isFalse();
	}

	@Test
	void testInsertUser() {
	
		UUID userUUID = UUID.randomUUID();
		User newJoe = new User(userUUID, "Joe1", "Jones1", Gender.MALE, 23, "joejones1@gmail.com");
		
		fakeDao.insertUser(userUUID, newJoe);
		
		assertThat(fakeDao.selectAllUsers()).hasSize(2);
		assertThat(fakeDao.selectUserByUserUid(userUUID)
								.get()).usingRecursiveComparison().isEqualTo(newJoe);
	}

}
