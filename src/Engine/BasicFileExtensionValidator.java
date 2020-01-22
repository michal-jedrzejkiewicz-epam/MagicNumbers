package Engine;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class BasicFileExtensionValidator implements IFileValidator {
    private final FileInputStream fileToCheck;
    private final String extensionFromPath;
    private int currentReadingSymbol;
    private final ArrayList<Integer> possibleExtensionSignature = new ArrayList<>(4);

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

    private void fillPossibleExtensionSignature() {
        for(int i = 0; i < SPECIFIC_EXTENSION_SIGNATURES.length; ++i) {
            if(SPECIFIC_EXTENSION_SIGNATURES[i][0] == currentReadingSymbol) {
                possibleExtensionSignature.add(i);
            }
        }
    }

    private boolean isTextFile() throws IOException {
        while (currentReadingSymbol != -1) {
            if(currentReadingSymbol > 127) {
                return false;
            }
            currentReadingSymbol = fileToCheck.read();
        }
        return true;
    }

    private boolean isFileSignatureExists() throws IOException {
        int columnNumber = -1;
        while (currentReadingSymbol != -1 && possibleExtensionSignature.size() != 0) {
            ++columnNumber;
            for(int i = 0; i < possibleExtensionSignature.size(); ++i) {
                if (columnNumber + 1 == SPECIFIC_EXTENSION_SIGNATURES[possibleExtensionSignature.get(i)].length)  {
                    return true;
                }
                if (SPECIFIC_EXTENSION_SIGNATURES[possibleExtensionSignature.get(i)][columnNumber] != currentReadingSymbol) {
                    possibleExtensionSignature.remove(i);
                }
            }
            currentReadingSymbol = fileToCheck.read();
        }
        return false;
    }

    @Override
    public boolean checkIfFileIsSafe() throws IOException {
        currentReadingSymbol = fileToCheck.read();

        if(currentReadingSymbol == -1) {
            throw new IllegalStateException("The file is empty!");
        }

        fillPossibleExtensionSignature();

        if(possibleExtensionSignature.size() == 0) {
            if(isTextFile()) {
                System.out.println("File extension: txt");
                System.out.println("Signature: unknown");
                return true;
            }
        }

        boolean result = isFileSignatureExists();
        System.out.println("File extension: " + extensionFromPath);
        if(possibleExtensionSignature.size() == 1) {
            System.out.println("Signature: " + COMMON_FILE_EXTENSIONS[possibleExtensionSignature.get(0)]);
        } else {
            System.out.println("Signature: unknown");
        }

        return result;
    }
}
