package code.boot.resources;

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

	@Autowired
	@Qualifier("userManagerService")
	private UserManager userManager;

	@PostMapping("/create-user")
	public ResponseEntity<?> addUser(@RequestBody UserRequest userRequest) {
		ResponseEntity<?> response = null;

		try {
			String message = userManager.createUser(userRequest);
			if (message == null || message == "No data found") {
				response = new ResponseEntity<>(new ResponseMessage("No data found"), HttpStatus.NOT_FOUND);
			} else {
				response = new ResponseEntity<>(new ResponseMessage(message), HttpStatus.OK);
			}

		} catch (Exception e) {
			e.printStackTrace();
			response = new ResponseEntity<>(new ResponseMessage("Internal server error"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return response;
	}

	@GetMapping("/read-user")
	public ResponseEntity<?> getUser(@RequestParam long id) {
		ResponseEntity<?> response = null;

		try {
			User user = userManager.readUser(id);
			if (user == null) {
				response = new ResponseEntity<>(new ResponseMessage("No user found"), HttpStatus.NOT_FOUND);
			} else {
				response = new ResponseEntity<User>(user, HttpStatus.OK);
			}

		} catch (Exception e) {
			e.printStackTrace();
			response = new ResponseEntity<>(new ResponseMessage("Internal server error"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@PutMapping("/update-user")
	public ResponseEntity<?> editUser(@RequestParam long id, @RequestBody UserRequest userRequest) {
		ResponseEntity<?> response = null;

		try {
			User user = userManager.updateUser(id, userRequest);
			if (user == null) {
				response = new ResponseEntity<>(new ResponseMessage("No user found"), HttpStatus.NOT_FOUND);
			}
			response = new ResponseEntity<User>(user, HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			e.printStackTrace();
			response = new ResponseEntity<>(new ResponseMessage("Internal server error"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@DeleteMapping("/delete-user")
	public ResponseEntity<?> removeUser(@RequestParam long id) {
		ResponseEntity<?> response = null;
		try {
			String message = userManager.deleteUser(id);
			if (message == null || message == "No user found") {
				response = new ResponseEntity<>(new ResponseMessage("No user found"), HttpStatus.NOT_FOUND);
			} else {
				response = new ResponseEntity<>(new ResponseMessage(message), HttpStatus.OK);
			}

		} catch (Exception e) {
			e.printStackTrace();
			response = new ResponseEntity<>(new ResponseMessage("Internal server error"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

}
