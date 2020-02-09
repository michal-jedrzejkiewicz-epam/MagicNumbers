package Engine;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Paths;

import static Engine.FileExtension.TXT;
import static Engine.FileExtension.UNKNOWN;
import static org.junit.jupiter.api.Assertions.*;

class TxtFileHandlerTest {

    private final static String testFilesPath = Paths.get("exampleFilesToTest").toAbsolutePath().toString();

    @Test
    void shouldReturnTxtExtension() throws IOException {
        InputStream inputStream = new BufferedInputStream(new FileInputStream(testFilesPath + "\\bosman.txt"));
        assertEquals(TXT, TxtFileHandler.checkIfFileIsTextFile(inputStream));
    }

    @Test
    void shouldReturnUnknownIfTxtFileContainsBytesValuesAbove127() throws IOException {
        InputStream inputStream = new BufferedInputStream(new FileInputStream(testFilesPath + "\\myCodeText.txt"));
        assertEquals(UNKNOWN, TxtFileHandler.checkIfFileIsTextFile(inputStream));
    }
}