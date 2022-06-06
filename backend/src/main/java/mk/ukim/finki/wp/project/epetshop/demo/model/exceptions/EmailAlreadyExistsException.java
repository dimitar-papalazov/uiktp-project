package mk.ukim.finki.wp.project.epetshop.demo.model.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String email) {
        super(String.format("Email %s does not exists", email));
    }
}
