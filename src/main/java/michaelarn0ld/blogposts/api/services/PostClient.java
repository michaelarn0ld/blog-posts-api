package michaelarn0ld.blogposts.api.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import michaelarn0ld.blogposts.api.models.Post;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.List;

@Component
public class PostClient {

    private final RestTemplate restTemplate;

    public PostClient(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder
                .rootUri("https://api.hatchways.io")
                .setConnectTimeout(Duration.ofSeconds(2))
                .setReadTimeout(Duration.ofSeconds(2))
                .build();
    }

    public List<Post> consumePostService(String tag) throws Exception {
        JsonNode node = restTemplate
                .getForObject("/assessment/blog/posts?tag=" + tag, JsonNode.class)
                .get("posts");
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(node.traverse(), new TypeReference<List<Post>>(){});
    }


}
