package enums;

/**
 * Enumeration of common HTTP status codes used in API responses.
 * <p>
 * Each enum constant represents a standard HTTP response code and can be retrieved
 * using {@link #getCode()}.
 * </p>
 */

public enum HttpStatusCode {

	/** The request was successful. */
    OK(200),
    
    /** The request was malformed or invalid. */
    BAD_REQUEST(400),
    
    /** The requested resource was not found. */
    NOT_FOUND(404),
    
    /** The server encountered an internal error. */
    INTERNAL_SERVER_ERROR(500);

	/** The numeric HTTP status code. */
    private final int code;

    /**
     * Constructor to initialize the HTTP status code.
     *
     * @param code the numeric HTTP status code
     */
    HttpStatusCode(int code) {
        this.code = code;
    }
    /**
     * Retrieves the numeric HTTP status code.
     *
     * @return the HTTP status code as an integer
     */
    public int getCode() {
        return code;
    }
}