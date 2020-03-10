package code.boot.manager.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import code.boot.entity.User;
import code.boot.entity.dto.UserRequest;
import code.boot.manager.UserManager;
import code.boot.repository.UserRepository;

@Service("userManagerService")
public class UserManagerImpl implements UserManager {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder encoder;

	@Override
	public String createUser(UserRequest userRequest) {
		String message = null;

		try {
			if (userRequest.getEmail() == null && userRequest.getFirstName() == null
					&& userRequest.getLastName() == null && userRequest.getProfession() == null
					&& userRequest.getMobile() == 0) {
				message = "No data found";
			} else {

				if (userRepository.existsByEmail(userRequest.getEmail())) {
					message = "Email address already exists";
				} else if (userRepository.existsByMobile(userRequest.getMobile())) {
					message = "Mobile number already exists";
				} else {
					User user = new User(userRequest.getEmail(), encoder.encode(userRequest.getPassword()),
							userRequest.getFirstName(), userRequest.getLastName(), userRequest.getProfession(),
							userRequest.getMobile());
					userRepository.save(user);
					message = "User has been created";
				}
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

	@Override
	public List<User> readUsers() {
		List<User> users = userRepository.findAll();
		try {
			if (users.isEmpty()) {
				return null;
			} else {
				return users;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}

	@Override
	public User loginUser(UserRequest userRequest) {

		Optional<User> user = null;

		try {
			user = userRepository.findByEmail(userRequest.getEmail());
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
	public String resetFindUser(UserRequest userRequest) {
		String message = null;

		Optional<User> user = null;

		try {
			if (userRequest.getEmail() == null) {
				message = "No data found";
			} else {

				user = userRepository.findByEmail(userRequest.getEmail());
				if (user.isPresent()) {
					message = user.get().getEmail();
				} else {
					message = "Email not found";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}

	@Override
	public String resetPasswordUser(UserRequest userRequest) {
		String message = null;

		Optional<User> user = null;
		try {
			if (userRequest.getPassword() == null) {
				message = "No data found";
			} else {

				user = userRepository.findByEmail(userRequest.getEmail());
				if (user.isPresent()) {
					user.get().setPassword(encoder.encode(userRequest.getPassword()));
					userRepository.saveAndFlush(user.get());
					message = "User password has been reset";
				} else {
					message = "Email not found";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}
}
