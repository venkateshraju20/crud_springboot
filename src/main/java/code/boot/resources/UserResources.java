package code.boot.resources;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import code.boot.entity.User;
import code.boot.entity.dto.ResponseMessage;
import code.boot.entity.dto.UserRequest;
import code.boot.manager.UserManager;

@RestController
@RequestMapping("/api/user")
public class UserResources {

	private static final Logger logger = LoggerFactory.getLogger(UserResources.class);

	@Autowired
	@Qualifier("userManagerService")
	private UserManager userManager;

	@PostMapping("/create-user")
	public ResponseEntity<?> addUser(@RequestBody UserRequest userRequest) {
		logger.info("Inside create user");
		ResponseEntity<?> response = null;

		try {
			String message = userManager.createUser(userRequest);
			if (message == null || message == "No data found") {
				response = new ResponseEntity<>(new ResponseMessage("No data found"), HttpStatus.NOT_FOUND);
				logger.debug("No data found");
			} else if (message == "Email address already exists") {
				response = new ResponseEntity<>(new ResponseMessage("Email address already exists"),
						HttpStatus.CONFLICT);
				logger.debug("Email address already exists");
			} else if (message == "Mobile number already exists") {
				response = new ResponseEntity<>(new ResponseMessage("Mobile number already exists"),
						HttpStatus.CONFLICT);
				logger.debug("Mobile number already exists");
			} else {
				response = new ResponseEntity<>(new ResponseMessage(message), HttpStatus.OK);
				logger.info("User has been created");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Internal server error");
			response = new ResponseEntity<>(new ResponseMessage("Internal server error"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@GetMapping("/read-user")
	public ResponseEntity<?> getUser(@RequestParam long id) {
		logger.info("Inside read user");
		ResponseEntity<?> response = null;

		try {
			User user = userManager.readUser(id);
			if (user == null) {
				response = new ResponseEntity<>(new ResponseMessage("No user found"), HttpStatus.NOT_FOUND);
				logger.debug("No user found");
			} else {
				response = new ResponseEntity<User>(user, HttpStatus.OK);
				logger.info("User has been read");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Internal server error");
			response = new ResponseEntity<>(new ResponseMessage("Internal server error"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@PutMapping("/update-user")
	public ResponseEntity<?> editUser(@RequestParam long id, @RequestBody UserRequest userRequest) {
		logger.info("Inside update user");
		ResponseEntity<?> response = null;

		try {
			User user = userManager.updateUser(id, userRequest);
			if (user == null) {
				logger.debug("No user found");
				response = new ResponseEntity<>(new ResponseMessage("No user found"), HttpStatus.NOT_FOUND);
			} else {
				response = new ResponseEntity<User>(user, HttpStatus.OK);
				logger.info("User has been updated");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Internal server error");
			response = new ResponseEntity<>(new ResponseMessage("Internal server error"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@DeleteMapping("/delete-user")
	public ResponseEntity<?> removeUser(@RequestParam long id) {
		logger.info("Inside delete user");
		ResponseEntity<?> response = null;
		try {
			String message = userManager.deleteUser(id);
			if (message == null || message == "No user found") {
				response = new ResponseEntity<>(new ResponseMessage("No user found"), HttpStatus.NOT_FOUND);
				logger.debug("No user found");
			} else {
				response = new ResponseEntity<>(new ResponseMessage(message), HttpStatus.OK);
				logger.info("User deleted");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Internal server error");
			response = new ResponseEntity<>(new ResponseMessage("Internal server error"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@GetMapping("/read-all-users")
	public ResponseEntity<?> getUsers() {
		logger.info("Inside get all users");
		ResponseEntity<?> response = null;

		try {
			List<User> user = userManager.readUsers();
			if (user == null) {
				response = new ResponseEntity<>(new ResponseMessage("No users found"), HttpStatus.NOT_FOUND);
				logger.debug("No users found");
			} else {
				response = new ResponseEntity<List<User>>(user, HttpStatus.OK);
				logger.info("Fetched all users");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Internal server error");
			response = new ResponseEntity<>(new ResponseMessage("Internal server error"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
}
