package Engine;

import java.io.FileInputStream;

public class BasicFileExtensionValidator implements IFileValidator {
    private final FileInputStream fileToCheck;
    private final String extensionFromPath;

    public BasicFileExtensionValidator(String filePath) throws Exception {
        fileToCheck = new FileInputStream(filePath);
        extensionFromPath = getFileExtension(filePath);
    }

    private String getFileExtension(String filePath) throws Exception {
        int lastDotInFilePath = filePath.lastIndexOf(".");
        if(-1 == lastDotInFilePath || filePath.length() - 1 == lastDotInFilePath) {
            throw new Exception("Improper path format");
        }
        return filePath.substring(lastDotInFilePath + 1).toLowerCase();
    }


    @Override
    public boolean checkIfFileIsSafe() {
        return false;
    }
}
