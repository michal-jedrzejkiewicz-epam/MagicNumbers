package Engine;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

public class Main {
    //static final String filePath = "C:\\myCode.jpg";
    public static void main(String[] args) {

        try {
            final String filePath = args[0];
            InputStream inputStream = new BufferedInputStream(new FileInputStream(filePath));
            String fileExtension = FilePathHandler.getExtension(filePath);
            BasicFileExtensionValidator fileValidator = new BasicFileExtensionValidator(inputStream, fileExtension);
            System.out.println("File signature matches with its extension: " + fileValidator.checkIfFileIsSafe());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}


