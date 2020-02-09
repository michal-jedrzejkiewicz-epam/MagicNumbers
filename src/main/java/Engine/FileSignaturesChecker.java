package Engine;

import java.io.InputStream;
import java.io.IOException;
import java.util.*;

import static Engine.FileExtension.*;

public class FileSignaturesChecker {
    public static final Map<ArrayList<Integer>, FileExtension> EXTENSION_SIGNATURES = Collections.unmodifiableMap(new HashMap<>() {{
            put(new ArrayList<>(Arrays.asList(0xFF, 0xD8, 0xFF, 0xDB))                                                , JPG);
            put(new ArrayList<>(Arrays.asList(0xFF, 0xD8, 0xFF, 0xE0, 0x00, 0x10, 0x4A, 0x46, 0x49, 0x46, 0x00, 0x01)), JPG);
            put(new ArrayList<>(Arrays.asList(0xFF, 0xD8, 0xFF, 0xEE))                                                , JPEG);
            put(new ArrayList<>(Arrays.asList(0xFF, 0xD8, 0xFF, 0xE1))                                                , JPEG_LONG); //jpeg first part
            put(new ArrayList<>(Arrays.asList(0x45, 0x78, 0x69, 0x66, 0x00, 0x00))                                    , JPEG); // jpeg second part
            put(new ArrayList<>(Arrays.asList(0x47, 0x49, 0x46, 0x38, 0x37, 0x61))                                    , GIF);
            put(new ArrayList<>(Arrays.asList(0x47, 0x49, 0x46, 0x38, 0x39, 0x61))                                    , GIF);
            put(new ArrayList<>(Arrays.asList(0x25, 0x50, 0x44, 0x46, 0x2D))                                          , PDF);
            put(new ArrayList<>(Arrays.asList(0x52, 0x61, 0x72, 0x21, 0x1A, 0x07, 0x00))                              , RAR);
            put(new ArrayList<>(Arrays.asList(0xA1, 0xB2, 0xC3, 0xD4))                                                , PCAP);
            put(new ArrayList<>(Arrays.asList(0xD4, 0xC3, 0xB2, 0xA1))                                                , PCAP);
    }});

    private static final int LONGEST_SIGNATURE = 12;
    private ArrayList<Integer> tempSignature;
    private int currentReadingSymbol;
    private InputStream inputStream;

    public FileSignaturesChecker(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    private FileExtension getFileExtensionBasedOnSignature() throws IOException {
        tempSignature = new ArrayList<>(LONGEST_SIGNATURE);
        while(currentReadingSymbol != -1 && tempSignature.size() != LONGEST_SIGNATURE) {
            tempSignature.add(currentReadingSymbol);
            FileExtension extension = EXTENSION_SIGNATURES.get(tempSignature);
            if(extension != null && extension != JPEG) {
                return extension;
            }
            currentReadingSymbol = inputStream.read();
        }
        return UNKNOWN;
    }

    public FileExtension findExtensionBaseOnSignature() throws IOException {
        currentReadingSymbol = inputStream.read();

        if(currentReadingSymbol == -1) {
            throw new IllegalStateException("The file is empty!");
        }

        FileExtension extensionFromSignature = getFileExtensionBasedOnSignature();
        if(extensionFromSignature == JPEG_LONG) {
            extensionFromSignature = JpegSignatureHandler.checkIfFileContainsSecondPartOfJpegSignature(inputStream);
        }
        return extensionFromSignature;
    }
}
