package thomas;

/**
 * Thrown when a Thomas command cannot be completed.
 */
public class ThomasException extends Exception {
    /**
     * Creates an exception with a user-facing message.
     *
     * @param message explanation shown to the user
     */
    public ThomasException(String message) {
        super(message);
    }
}
