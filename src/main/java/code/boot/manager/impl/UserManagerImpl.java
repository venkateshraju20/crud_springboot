package code.boot.manager.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import code.boot.entity.User;
import code.boot.entity.dto.UserRequest;
import code.boot.manager.UserManager;
import code.boot.repository.UserRepository;

@Service("userManagerService")
public class UserManagerImpl implements UserManager {

	@Autowired
	private UserRepository userRepository;

	@Override
	public String createUser(UserRequest userRequest) {
		String message = null;

		try {
			if (userRequest.getEmail() == null && userRequest.getFirstName() == null
					&& userRequest.getLastName() == null && userRequest.getProfession() == null
					&& userRequest.getMobile() == 0) {
				message = "No data found";
			} else {
				User user = new User(userRequest.getEmail(), userRequest.getFirstName(), userRequest.getLastName(),
						userRequest.getProfession(), userRequest.getMobile());
				userRepository.save(user);
				message = "User saved";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}

	@Override
	public User readUser(long id) {
		Optional<User> user = null;

		try {
			user = userRepository.findById(id);
			if (user.isPresent()) {
				return user.get();
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return user.get();
	}

	@Override
	public User updateUser(long id, UserRequest userRequest) {
		Optional<User> user = null;

		try {
			user = userRepository.findById(id);
			if (user.isPresent()) {
				if (userRequest.getMobile() != 0) {
					user.get().setMobile(userRequest.getMobile());
				} else if (userRequest.getFirstName() != null) {
					user.get().setFirstName(userRequest.getFirstName());
				} else if (userRequest.getLastName() != null) {
					user.get().setLastName(userRequest.getLastName());
				} else if (userRequest.getProfession() != null) {
					user.get().setProfession(userRequest.getProfession());
				}
				userRepository.saveAndFlush(user.get());
				return user.get();
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return user.get();
	}

	@Override
	public String deleteUser(long id) {
		String message = null;
		Optional<User> user = null;

		try {
			user = userRepository.findById(id);
			if (user.isPresent()) {
				userRepository.delete(user.get());
				message = "User deleted";
			} else {
				message = "No user found";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}
}
