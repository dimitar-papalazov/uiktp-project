package mk.ukim.finki.wp.project.epetshop.demo.model.exceptions;

public class InvalidVerificationCodeException extends RuntimeException {
    public InvalidVerificationCodeException() {
        super("Verification codes do not match exception.");
    }
}
