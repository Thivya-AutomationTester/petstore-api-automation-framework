package auth;

import io.restassured.specification.RequestSpecification;
import utils.ConfigReader;

/**
 * Utility class for applying authentication to REST-assured requests.
 * <p>
 * Supports API_KEY, BEARER, BASIC, and NONE authentication types.
 * Uses {@link ConfigReader} to fetch credentials or tokens from configuration.
 * </p>
 */
public class AuthManager {
	
	 /**
     * Applies the specified authentication type to the provided {@link RequestSpecification}.
     *
     * @param requestSpec the ThreadLocal holding the request specification
     * @param authType    the type of authentication to apply
     * @return a RequestSpecification with the requested authentication applied
     */

	public static RequestSpecification applyAuth(ThreadLocal<RequestSpecification> requestSpec, AuthType authType) {

		// Get the actual RequestSpecification from ThreadLocal
		RequestSpecification spec = requestSpec.get();

		switch (authType) {

		case API_KEY:
			// Add API key header from configuration
			spec = spec.header("api_key", ConfigReader.get("auth.api.key"));
			break;

		case BEARER:
			// Add Bearer token header
			spec = spec.header("Authorization", "Bearer " + getToken());
			break;

		case BASIC:
			// Apply Basic authentication using username and password from config
			spec = spec.auth().preemptive().basic(ConfigReader.get("auth.username"), ConfigReader.get("auth.password"));
			break;

		case NONE:
		default:
			// No authentication applied
			break;
		}

		return spec;
	}

	// Retrieves the Bearer token from configuration.
	private static String getToken() {
		// Placeholder for real token API call
		return ConfigReader.get("auth.token");
	}
}