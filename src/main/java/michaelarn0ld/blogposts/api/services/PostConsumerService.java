package michaelarn0ld.blogposts.api.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import michaelarn0ld.blogposts.api.models.Post;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Service
public class PostConsumerService {

    private final RestTemplate restTemplate;

    public PostConsumerService() {
        this.restTemplate = new RestTemplate();
    }

    public List<Post> getPosts(String tag) throws IOException {
        String json = restTemplate.getForObject("https://api.hatchways.io/assessment/blog/posts?tag=" + tag, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(json).get("posts");
        return mapper.readValue(node.traverse(), new TypeReference<List<Post>>(){});
    }
}
