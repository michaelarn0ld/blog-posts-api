package michaelarn0ld.blogposts.api.controllers;

import michaelarn0ld.blogposts.api.exceptions.ErrorResponse;
import michaelarn0ld.blogposts.api.models.Post;
import michaelarn0ld.blogposts.api.services.PostConsumerService;
import michaelarn0ld.blogposts.api.services.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    private PostConsumerService service;

    @GetMapping
    public ResponseEntity<?> getPosts(
            @RequestParam(name = "tag", required = false) List<String> tags,
            @RequestParam(name = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc", required = false) String direction) {

        Result<List<Post>> result = service.getPosts(tags, sortBy, direction);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), result.getType());
        }
        ErrorResponse errorResponse = new ErrorResponse(result.getMessages());
        return new ResponseEntity<>(errorResponse, result.getType());
    };
}
