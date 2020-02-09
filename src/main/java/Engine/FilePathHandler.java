package Engine;

public class FilePathHandler {
    public static String getExtension(String filePath) throws FileExtensionNotFoundInPathException {
        int lastDotInFilePath = filePath.lastIndexOf(".");
        if(-1 == lastDotInFilePath || filePath.length() - 1 == lastDotInFilePath) {
            throw new FileExtensionNotFoundInPathException("Improper path format");
        }
        return filePath.substring(filePath.lastIndexOf(".") + 1).toLowerCase();
    }
}
