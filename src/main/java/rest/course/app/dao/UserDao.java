package rest.course.app.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;



import rest.course.app.model.User;

public interface UserDao {

	List<User>selectAllUsers();
	
	Optional<User> selectUserByUserUid(UUID userUid);
	
	int updateUser(User user);
	
	int removeUser(UUID userUid);
	
	int insertUser(UUID userUid, User user);
	
}
