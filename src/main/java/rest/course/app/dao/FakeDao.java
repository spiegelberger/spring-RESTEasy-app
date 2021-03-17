package rest.course.app.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import rest.course.app.model.Gender;
import rest.course.app.model.User;

@Repository
public class FakeDao implements UserDao{

	
	private  Map<UUID, User>database;
			
		
		public FakeDao() {
			database = new HashMap<>();
			//add an element to the database:
			UUID joeUserUid = UUID.randomUUID();
			database.put(joeUserUid, new User(joeUserUid, "Joe", "Jones", Gender.MALE, 22, "joejones@gmail.com") );
	}

			
	
	@Override
	public List<User> selectAllUsers() {
		
		return new ArrayList<>(database.values());
	}

	@Override
	public Optional<User> selectUserByUserUid(UUID userUid) {
		
		return Optional.ofNullable(database.get(userUid));
	}

	@Override
	public int updateUser(User user) {
		
		database.put(user.getUserUid(), user);
		return 1;
	}

	@Override
	public int removeUser(UUID userUid) {
		database.remove(userUid);
		return 1;
	}

	@Override
	public int insertUser(UUID userUid, User user) {
		database.put(userUid, user);
		return 1;
	}



}
