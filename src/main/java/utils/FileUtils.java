package utils;

import java.io.InputStream;

/**
 * Utility class for reading files from the resources folder.
 * <p>
 * This class provides a method to read files from the resources directory,
 * typically used to read configuration files, JSON payloads or any other static
 * resources needed for the application.
 * </p>
 */

public class FileUtils {
	/**
	 * Reads the content of a file located in the resources directory.
	 * <p>
	 * This method reads the file specified by the given path and returns its
	 * content as a string. It throws an exception if the file is not found or if
	 * there is any error reading the file.
	 * </p>
	 *
	 * @param filePath the path to the file within the resources directory
	 * @return the content of the file as a string
	 * @throws RuntimeException if the file is not found or there is an error
	 *                          reading the file
	 */
	public static String readFromResources(String filePath) {

		try (InputStream is = FileUtils.class.getClassLoader().getResourceAsStream(filePath)) {

			// If the file is not found in the resources directory
			if (is == null) {
				throw new RuntimeException("File not found in resources: " + filePath);
			}
			// Read and return the file content as a String
			return new String(is.readAllBytes());

		} catch (Exception e) {
			// If an error occurs during file reading, throw a runtime exception
			throw new RuntimeException("Error reading file: " + filePath, e);
		}
	}

}