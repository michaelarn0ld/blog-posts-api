package michaelarn0ld.blogposts.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import michaelarn0ld.blogposts.api.models.Post;
import michaelarn0ld.blogposts.api.services.PostClient;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @MockBean
    PostClient client;

    @Autowired
    MockMvc mvc;


    List<Post> techPosts = List.of(
            new Post(1, "oneAuth", 1, 100, 0.78, 100, null),
            new Post(2, "twoAuth", 2, 1000, 0.84, 600, null),
            new Post(3, "threeAuth", 3, 500, 0.29, 1000, null)
    );

    List<Post> healthPosts = List.of(
            new Post(2, "twoAuth", 2, 1000, 0.84, 600, null),
            new Post(4, "fourAuth", 4, 800, 0.45, 5000, null),
            new Post(5, "fiveAuth", 5, 300, 0.56, 1000, null)
    );

    ObjectMapper jsonMapper = new ObjectMapper();

    @BeforeEach
    void setup() throws Exception {
        when(client.consumePostService("tech")).thenReturn(techPosts);
        when(client.consumePostService("health")).thenReturn(healthPosts);

    }

    @Test
    void shouldGetByTag() throws Exception {
        String expectedJson = jsonMapper.writeValueAsString(techPosts);
        mvc.perform(get("/api/post?tag=tech"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldGetByMultipleTags() throws Exception {
        List<Post> expected = List.of(
            new Post(1, "oneAuth", 1, 100, 0.78, 100, null),
            new Post(2, "twoAuth", 2, 1000, 0.84, 600, null),
            new Post(3, "threeAuth", 3, 500, 0.29, 1000, null),
            new Post(4, "fourAuth", 4, 800, 0.45, 5000, null),
            new Post(5, "fiveAuth", 5, 300, 0.56, 1000, null)
        );
        String expectedJson = jsonMapper.writeValueAsString(expected);
        mvc.perform(get("/api/post?tag=tech,health"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldBeEmptyJSONIfInvalidTag() throws Exception {
        List<Post> expected = List.of();
        String expectedJson = jsonMapper.writeValueAsString(expected);
        mvc.perform(get("/api/post?tag=invalid"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldBe400IfNoTag() throws Exception {
        mvc.perform(get("/api/post?tag="))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldBe400IfBadSort() throws Exception {
        mvc.perform(get("/api/post?tag=tech&sortBy=bad"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldBe400IfBadDirection() throws Exception {
        mvc.perform(get("/api/post?tag=tech&direction=bad"))
                .andExpect(status().isBadRequest());
    }
}