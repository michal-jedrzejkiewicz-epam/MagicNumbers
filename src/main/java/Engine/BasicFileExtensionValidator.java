package Engine;

import java.io.InputStream;
import java.io.IOException;

import static Engine.FileExtension.*;

public class BasicFileExtensionValidator {
    private final InputStream inputStream;
    private final String extensionFromPath;
    private static final int READ_LIMIT_OF_SYMBOLS_20 = 20;
    private FileSignaturesChecker fileSignaturesChecker;

    public BasicFileExtensionValidator(InputStream inputStream, String extension) {
        this.inputStream = inputStream;
        inputStream.mark(READ_LIMIT_OF_SYMBOLS_20);
        extensionFromPath = extension;
        fileSignaturesChecker = new FileSignaturesChecker(inputStream);
    }

    public boolean checkIfFileIsSafe() throws IOException {
        FileExtension fileExtensionFromSignature = fileSignaturesChecker.findExtensionBaseOnSignature();
        if(fileExtensionFromSignature == UNKNOWN) {
            fileExtensionFromSignature = TxtFileHandler.checkIfFileIsTextFile(inputStream);
        }
        System.out.println("File extension: " + extensionFromPath);
        System.out.println("Signature: " + fileExtensionFromSignature.getLowerString());
        return extensionFromPath.equals(fileExtensionFromSignature.getLowerString());
    }
}

