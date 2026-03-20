package pojo;

/**
 * A class representing a tag associated with a pet.
 * <p>
 * This class contains details about a pet tag, including its unique ID and name.
 * </p>
 */

public class Tag {

    private long id;
    private String name;

    //Getters and Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
