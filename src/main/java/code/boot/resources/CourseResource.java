package code.boot.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import code.boot.entity.Course;
import code.boot.manager.impl.CourseManagerImpl;

@RestController
@RequestMapping("/api/user")
public class CourseResource {

	@Autowired
	private CourseManagerImpl courseManagerImpl;

	@GetMapping("/courses")
	public List<Course> getAllCourses() {
		return courseManagerImpl.findAll();
	}

	@PostMapping("/create-course")
	public ResponseEntity<Void> createCourse(@RequestBody Course course) {
		Course createdCourse = courseManagerImpl.save(course);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdCourse.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping("/courses/{id}")
	public ResponseEntity<Void> deleteCourse(@PathVariable long id) {
		Course course = courseManagerImpl.deleteById(id);
		if (course != null) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/courses/{id}")
	public Course getCourse(@PathVariable long id) {
		return courseManagerImpl.findById(id);
	}

	@PutMapping("/courses/{id}")
	public ResponseEntity<Course> updateCourse(@PathVariable long id, @RequestBody Course course) {
		Course courseUpdated = courseManagerImpl.save(course);
		return new ResponseEntity<Course>(courseUpdated, HttpStatus.OK);
	}
}
