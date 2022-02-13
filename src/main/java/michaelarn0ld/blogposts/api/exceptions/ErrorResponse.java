package michaelarn0ld.blogposts.api.exceptions;


import lombok.Data;

import java.util.List;

@Data
public class ErrorResponse {
    private final List<String> errors;

    public ErrorResponse(List<String> errors) {
        this.errors = errors;
    }
}
