package hello;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dao.PostDAOImpl;
import model.Post;
import model.Status;

@RestController
@CrossOrigin(methods = { RequestMethod.GET, RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PUT })
@RequestMapping("/posts")
public class PostsController {

	PostDAOImpl impl = new PostDAOImpl();

	@RequestMapping("/all")
	@GetMapping
	public List<Post> getPosts() {

		List<Post> list = impl.view();

		return list;

	}

	@RequestMapping("/get/{title}")
	@GetMapping
	public List<Post> getPostsByTitle(@PathVariable String title) {

		List<Post> list = impl.view(title);

		return list;
	}

	@RequestMapping("/add")
	@PostMapping
	public int addPost(@RequestBody Post post) {

		int res = impl.insert(post);

		return res;
	}

	@RequestMapping("/update")
	@PutMapping
	public Status updatePost(@RequestBody Post post) {

		int res = impl.update(post);

		Status s = new Status((res == 1) ? true : false);

		return s;

	}

	@RequestMapping("/delete/{id}")
	@DeleteMapping
	public Status deletePost(@PathVariable int id) {

		int res = impl.delete(id);

		Status s = new Status((res == 1) ? true : false);

		return s;

	}
}