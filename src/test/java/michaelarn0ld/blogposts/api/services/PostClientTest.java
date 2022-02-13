package michaelarn0ld.blogposts.api.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;

import static org.junit.jupiter.api.Assertions.*;

@RestClientTest(PostClient.class)
class PostClientTest {

    @Autowired
    PostClient client;

    @Autowired
    MockRestServiceServer server;

    String health = """
            {
              "posts": [
                {
                  "author": "Rylee Paul",
                  "authorId": 9,
                  "id": 1,
                  "likes": 960,
                  "popularity": 0.13,
                  "reads": 50361,
                  "tags": [
                    "tech",
                    "health"
                  ]
                },
                {
                  "author": "Trevon Rodriguez",
                  "authorId": 5,
                  "id": 3,
                  "likes": 425,
                  "popularity": 0.68,
                  "reads": 11381,
                  "tags": [
                    "startups",
                    "health"
                  ]
                }
              ]
            }
            """;



    @Test
    void shouldReturnPosts() throws Exception {
        server.expect(MockRestRequestMatchers.requestTo("/assessment/blog/posts?tag=health"))
                .andRespond(MockRestResponseCreators.withSuccess(health, MediaType.APPLICATION_JSON));
        var response = client.consumePostService("health");
        assertEquals(2, response.size());
    }

    @Test
    void shouldFailInCaseOfSystemDown() {
        server.expect(MockRestRequestMatchers.requestTo("/assessment/blog/posts?tag=test"))
                .andRespond(MockRestResponseCreators.withServerError());
        assertThrows(RuntimeException.class, () -> client.consumePostService("test"));
    }
}