package Engine;

import java.io.InputStream;
import java.io.IOException;

import static Engine.FileExtension.TXT;
import static Engine.FileExtension.UNKNOWN;

public class TxtFileHandler {
    public static FileExtension checkIfFileIsTextFile(InputStream inputStream) throws IOException {
        for(int symbol = inputStream.read(); symbol != -1; symbol = inputStream.read()) {
            if(symbol > 127) {
                return UNKNOWN;
            }
        }
        return TXT;
    }
}
