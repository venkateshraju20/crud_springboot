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

import code.boot.entity.dto.ResponseMessage;
import code.boot.entity.dto.UserRequest;
import code.boot.manager.UserManager;

@RestController
@RequestMapping("/api/user")
public class ResetPasswordResources {
	private static final Logger logger = LoggerFactory.getLogger(UserResources.class);

	@Autowired
	@Qualifier("userManagerService")
	private UserManager userManager;

	@PostMapping("/reset-find-user")
	public ResponseEntity<?> resetFindUser(@RequestBody UserRequest userRequest) {
		logger.info("Inside reset find user");
		ResponseEntity<?> response = null;

		try {
			String message = userManager.resetFindUser(userRequest);
			if (message == null || message == "No data found") {
				response = new ResponseEntity<>(new ResponseMessage("No data found"), HttpStatus.NOT_FOUND);
				logger.debug("No data found");
			} else if (message == "Email not found") {
				response = new ResponseEntity<>(new ResponseMessage(message), HttpStatus.NOT_FOUND);
				logger.debug(message);
			} else {
				response = new ResponseEntity<>(new ResponseMessage(message), HttpStatus.OK);
				logger.debug(message);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Internal server error");
			response = new ResponseEntity<>(new ResponseMessage("Internal server error"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@PostMapping("/reset-user-password")
	public ResponseEntity<?> resetPasswordUser(@RequestBody UserRequest userRequest) {
		logger.info("Inside reset password user");
		ResponseEntity<?> response = null;

		try {
			String message = userManager.resetPasswordUser(userRequest);
			if (message == null || message == "No data found") {
				response = new ResponseEntity<>(new ResponseMessage("No data found"), HttpStatus.NOT_FOUND);
				logger.debug("No data found");
			} else if (message == "Email not found") {
				response = new ResponseEntity<>(new ResponseMessage(message), HttpStatus.NOT_FOUND);
				logger.debug(message);
			} else {
				response = new ResponseEntity<>(new ResponseMessage(message), HttpStatus.OK);
				logger.debug(message);
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
