package controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.varigui.ResourceNotFoundException;
import model.User;
import repository.UserRepository;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class UserController {

	@Autowired
	private UserRepository userRepository;

// get all users
	@GetMapping("/cadastros")
	public List<User> getUsers() {
		return userRepository.findAll();
	}

// create user rest api
	@PostMapping("/cadastros")
	public User createUser(@RequestBody User user) {
		return userRepository.save(user);
	}

// get user by id rest api
	@GetMapping("/cadastros/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuário com Id não cadastrado :" + id));
		return ResponseEntity.ok(user);
	}

// update user rest api
	@PutMapping("/cadastros/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
		user.setNome(userDetails.getNome());
		user.setUsername(userDetails.getUsername());
		user.setEmail(userDetails.getEmail());
		user.setDatNasc(userDetails.getDatNasc());
		User updatedUser = userRepository.save(user);
		return ResponseEntity.ok(updatedUser);
	}

// delete user rest api
	@DeleteMapping("/cadastros/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuário com Id não cadastrado :" + id));
		userRepository.delete(user);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}