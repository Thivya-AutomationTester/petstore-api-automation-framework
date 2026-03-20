package enums;

/**
 * Enumeration of API endpoints for the Pet service.
 * <p>
 * Each enum constant represents a REST API path and can be retrieved
 * via {@link #getPath()}.
 * </p>
 */

public enum EndPoints {
	
	/** Endpoint for creating or updating a pet. */
	PET("/pet"),
	
	 /** Endpoint to retrieve a pet by its ID. */
    PET_BY_ID("/pet/{petid}"),
    
    /** Endpoint to find pets by their status. */
    FIND_BY_STATUS("/pet/findByStatus");

	/** The URL path associated with the endpoint. */
    private final String path;

    /**
     * Constructor to initialize the endpoint path.
     *
     * @param path the API path for the endpoint
     */
    EndPoints(String path) {
        this.path = path;
    }
    /**
     * Retrieves the API path of the endpoint.
     *
     * @return the endpoint path as a String
     */
    public String getPath() {
        return path;
    }
}