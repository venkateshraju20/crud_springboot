package code.boot.manager;

import code.boot.entity.User;
import code.boot.entity.dto.UserRequest;

public interface UserManager {

	public String createUser(UserRequest userRequest);

	public User readUser(long id);

	public User updateUser(long id, UserRequest userRequest);

	public String deleteUser(long id);

}
