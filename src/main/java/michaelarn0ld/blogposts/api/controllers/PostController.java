package michaelarn0ld.blogposts.api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import michaelarn0ld.blogposts.api.models.Post;
import michaelarn0ld.blogposts.api.services.PostConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    private PostConsumerService service;

    @GetMapping
    public List<Post> getPosts(@RequestParam(name = "tag") String tag) throws IOException {
        return service.getPosts(tag);
    };
}
