package bg.softuni.pochivka.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ObjectNotFoundException extends RuntimeException {
    private final long objectId;
    private final String objectName;
    private final String objectUsername;

    public ObjectNotFoundException(long objectId, String objectUsername, String objectName) {
        this.objectId = objectId;
        this.objectUsername = objectUsername;
        this.objectName = objectName;
    }

    public long getObjectId() {
        return objectId;
    }

    public String getObjectName() {
        return objectName;
    }

    public String getObjectUsername() {
        return objectUsername;
    }
}
