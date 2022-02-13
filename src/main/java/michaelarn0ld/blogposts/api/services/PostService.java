package michaelarn0ld.blogposts.api.services;

import michaelarn0ld.blogposts.api.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

@Service
public class PostService {

    @Autowired
    private PostClient postClient;

    public Result<List<Post>> getPosts(List<String> tags, String sortBy, String direction) {
        Result<List<Post>> result = validate(tags, sortBy, direction);
        if (!result.isSuccess()) {
            return result;
        }
        result.setPayload(sortAndOrderPosts(tags, sortBy, direction).toList());
        return result;
    }


    /*
    Hard coding the method to respond to ascending and descending this way makes the code less DRY but saves a lot on
    sorting performance.

    If we sort in one method and then try to direct the sort in another method, then we end up just sorting twice. It is
    actually even worse because the second time we have to sort (in the direction method), we end up getting worst case
    runtime because the collection we are trying to sort is inverse sorted.
     */
    private Stream<Post> sortAndOrderPosts(List<String> tags, String sortBy, String direction) {
        if (direction.equals("asc")) {
            switch (sortBy) {
                case "id":
                    return accumulatePosts(tags).sorted(Comparator.comparingInt(Post::getId));
                case "reads":
                    return accumulatePosts(tags).sorted(Comparator.comparingInt(Post::getReads));
                case "likes":
                    return accumulatePosts(tags).sorted(Comparator.comparingInt(Post::getLikes));
                case "popularity":
                    return accumulatePosts(tags).sorted(Comparator.comparingDouble(Post::getId));
                default:
                    return null;
            }
        } else {
            switch (sortBy) {
                case "id":
                    return accumulatePosts(tags).sorted(Comparator.comparingInt(Post::getId).reversed());
                case "reads":
                    return accumulatePosts(tags).sorted(Comparator.comparingInt(Post::getReads).reversed());
                case "likes":
                    return accumulatePosts(tags).sorted(Comparator.comparingInt(Post::getLikes).reversed());
                case "popularity":
                    return accumulatePosts(tags).sorted(Comparator.comparingDouble(Post::getId).reversed());
                default:
                    return null;
            }
        }
    }

    private Stream<Post> accumulatePosts(List<String> tags) {
        return tags.parallelStream()
                .map(tag -> {
                    try {
                        return postClient.consumePostService(tag);
                    } catch (Exception ignored) {
                        return null;
                    }
                })
                .flatMap(Collection::stream)
                .distinct();
    }

    private Result<List<Post>> validate(List<String> tags, String sortBy, String direction) {
        Result<List<Post>> result = new Result<>();
        if (tags == null || tags.size() == 0) {
            result.addMessage("Tags parameter is required", HttpStatus.BAD_REQUEST);
        }
        if (!sortBy.equals("id") && !sortBy.equals("reads") && !sortBy.equals("likes") && !sortBy.equals("popularity")) {
            result.addMessage("sortBy parameter is invalid", HttpStatus.BAD_REQUEST);
        }
        if(!direction.equals("asc") && !direction.equals("desc")) {
            result.addMessage("direction parameter is invalid", HttpStatus.BAD_REQUEST);
        }
        return result;
    }

}
