package michaelarn0ld.blogposts.api.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode
public class Post {

    private int id;
    private String author;
    private int authorId;
    private int likes;
    private double popularity;
    private int reads;
    private List<String> tags;

}
