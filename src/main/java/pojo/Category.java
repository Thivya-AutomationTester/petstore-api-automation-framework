package pojo;

/**
 * A class representing a category for a pet.
 * <p>
 * This class contains details about a pet category
 * It is used to model the category information of a pet in the Petstore API.
 * </p>
 */
public class Category {

    private long id;
    private String name;

    // Getters and Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
