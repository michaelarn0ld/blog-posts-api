package michaelarn0ld.blogposts.api.services;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class Result<T> {

    @Getter
    private final List<String> messages = new ArrayList<>();

    @Getter
    private HttpStatus type = HttpStatus.OK;

    @Getter
    @Setter
    private T payload;

    public boolean isSuccess() {
        return type == HttpStatus.OK;
    }

    public void addMessage(String message, HttpStatus type) {
        messages.add(message);
        this.type = type;
    }
}