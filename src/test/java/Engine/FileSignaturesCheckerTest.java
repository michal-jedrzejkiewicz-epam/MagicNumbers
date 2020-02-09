package Engine;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.*;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static Engine.FileExtension.*;
import static org.junit.jupiter.api.Assertions.*;

class FileSignaturesCheckerTest {

    private final static String testFilesPath = Paths.get("exampleFilesToTest").toAbsolutePath().toString();

    private static Stream<Arguments> successCases() {
        return Stream.of(
                Arguments.of(testFilesPath + "\\myCode.jpg"                  , JPG),
                Arguments.of(testFilesPath + "\\programmingLanguages.gif"    , GIF),
                Arguments.of(testFilesPath + "\\jpegFile.jpeg"               , JPEG),
                Arguments.of(testFilesPath + "\\pdfFile.pdf"                 , PDF),
                Arguments.of(testFilesPath + "\\pcapFile.pcap"               , PCAP),
                Arguments.of(testFilesPath + "\\pcapFileSecondSignature.pcap", PCAP),
                Arguments.of(testFilesPath + "\\rarFile.rar"                 , RAR)
        );
    }
    private static Stream<Arguments> failureCases() {
        return Stream.of(
                Arguments.of(testFilesPath + "\\jpgWithMistake.jpg"            , UNKNOWN),
                Arguments.of(testFilesPath + "\\jpgTooShort.jpg"               , UNKNOWN),
                Arguments.of(testFilesPath + "\\myCodeBadSig.gif"              , JPG),
                Arguments.of(testFilesPath + "\\programmingLanguagesBadSig.jpg", GIF)
        );
    }
    @ParameterizedTest(name = "{index} => path{0}, fileExtension{1}")
    @MethodSource("successCases")
    void shouldReturnProperFileExtension(String filePath, FileExtension fileExtension) throws FileNotFoundException, IOException {
        InputStream inputStream = new BufferedInputStream(new FileInputStream(filePath));
        FileSignaturesChecker fileSignaturesChecker = new FileSignaturesChecker(inputStream);
        assertEquals(fileSignaturesChecker.findExtensionBaseOnSignature(), fileExtension);
    }

    @ParameterizedTest(name = "{index} => path{0}, fileExtension{1}")
    @MethodSource("failureCases")
    void shouldReturnIncorrectExtensionForAllTestFilesWithBadSignature(String filePath, FileExtension fileExtension) throws Exception {
        InputStream inputStream = new BufferedInputStream(new FileInputStream(filePath));
        FileSignaturesChecker fileSignaturesChecker = new FileSignaturesChecker(inputStream);
        assertEquals(fileSignaturesChecker.findExtensionBaseOnSignature(), fileExtension);
    }

    @Test
    void shouldThrowIfFileIsEmpty() throws IllegalStateException, FileNotFoundException {
        InputStream inputStream = new BufferedInputStream(new FileInputStream(testFilesPath + "\\emptyTxtFile.txt"));
        FileSignaturesChecker fileSignaturesChecker = new FileSignaturesChecker(inputStream);
        assertThrows(IllegalStateException.class, fileSignaturesChecker::findExtensionBaseOnSignature);
    }
    @Test
    void shouldNotThrowIfFileExists() throws FileNotFoundException {
        InputStream inputStream = new BufferedInputStream(new FileInputStream(testFilesPath + "\\myCode.jpg"));
        assertDoesNotThrow(() -> new FileSignaturesChecker(inputStream));
    }

    @Test
    void shouldReturnUnknownExtensionForTextFiles() throws Exception {
        InputStream inputStream = new BufferedInputStream(new FileInputStream(testFilesPath + "\\bosman.txt"));
        FileSignaturesChecker fileSignaturesChecker = new FileSignaturesChecker(inputStream);
        assertEquals(UNKNOWN, fileSignaturesChecker.findExtensionBaseOnSignature());
    }
}