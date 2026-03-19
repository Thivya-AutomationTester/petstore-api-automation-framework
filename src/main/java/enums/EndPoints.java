package enums;

public enum EndPoints {
	
	//API endpoints for pets
	
    PET("/pet"),
    PET_BY_ID("/pet/{petid}"),
    FIND_BY_STATUS("/pet/findByStatus");

    private final String path;

    EndPoints(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}