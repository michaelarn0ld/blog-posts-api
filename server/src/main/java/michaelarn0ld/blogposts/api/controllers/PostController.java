package michaelarn0ld.blogposts.api.controllers;

import michaelarn0ld.blogposts.api.exceptions.ErrorResponse;
import michaelarn0ld.blogposts.api.models.Post;
import michaelarn0ld.blogposts.api.services.PostService;
import michaelarn0ld.blogposts.api.services.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    private PostService client;

    @GetMapping
    public ResponseEntity<?> getPosts(
            @RequestParam(name = "tag", required = false) List<String> tags,
            @RequestParam(name = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc", required = false) String direction) {

        Result<List<Post>> result = client.getPosts(tags, sortBy, direction);
        if (result.isSuccess()) {
            Map<String, List<Post>> success = new HashMap<>();
            success.put("posts", result.getPayload());
            return new ResponseEntity<>(success, result.getType());
        }
        ErrorResponse errorResponse = new ErrorResponse(result.getMessages());
        return new ResponseEntity<>(errorResponse, result.getType());
    };
}
