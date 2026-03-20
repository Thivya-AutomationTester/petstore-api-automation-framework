package auth;

/**
 * Enumeration of supported authentication types for REST-assured requests.
 * <p>
 * Used by {@link AuthManager#applyAuth(ThreadLocal, AuthType)} to determine
 * which authentication method to apply.
 * </p>
 */
public enum AuthType {
    NONE,
    API_KEY,
    BEARER,
    BASIC
}