package Engine;

import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;

import static Engine.FileExtension.JPEG;
import static Engine.FileExtension.UNKNOWN;
import static Engine.FileSignaturesChecker.EXTENSION_SIGNATURES;

public class JpegSignatureHandler {
    private static final int JPEG_SECOND_PART_SIZE = 6;
    private static final long NUMBER_OF_BYTES_TO_SKIP_2= 2L;

    private static void skipTwoUndefinedSymbols(InputStream inputStream) throws IOException {
        inputStream.skip(NUMBER_OF_BYTES_TO_SKIP_2);
    }

    public static FileExtension checkIfFileContainsSecondPartOfJpegSignature(InputStream inputStream) throws IOException {
        skipTwoUndefinedSymbols(inputStream);
        ArrayList<Integer> tempSignature = new ArrayList<>(JPEG_SECOND_PART_SIZE);
        for(int i = 0; i < JPEG_SECOND_PART_SIZE; ++i) {
            tempSignature.add(inputStream.read());
        }
        return EXTENSION_SIGNATURES.get(tempSignature) == JPEG ? JPEG : UNKNOWN;
    }
}
