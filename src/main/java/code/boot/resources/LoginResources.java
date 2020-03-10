package code.boot.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import code.boot.entity.User;
import code.boot.entity.dto.ResponseMessage;
import code.boot.entity.dto.UserRequest;
import code.boot.manager.UserManager;

@RestController
@RequestMapping("/api/user")
public class LoginResources {

	private static final Logger logger = LoggerFactory.getLogger(UserResources.class);

	@Autowired
	@Qualifier("userManagerService")
	private UserManager userManager;
	
	@PostMapping("/login-user")
	public ResponseEntity<?> loginUser(@RequestBody UserRequest userRequest) {
		logger.info("Inside login user");
		ResponseEntity<?> response = null;

		try {
			User user = userManager.loginUser(userRequest);
			if (user == null) {
				response = new ResponseEntity<>(new ResponseMessage("No user found"), HttpStatus.NOT_FOUND);
				logger.debug("No user found");
			} else {
				response = new ResponseEntity<User>(user, HttpStatus.OK);
				logger.info("User has been found");
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
