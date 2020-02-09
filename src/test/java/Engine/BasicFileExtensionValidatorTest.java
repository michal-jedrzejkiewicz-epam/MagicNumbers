package Engine;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


public class BasicFileExtensionValidatorTest {
    private final static String testFilesPath = Paths.get("exampleFilesToTest").toAbsolutePath().toString();

    private static Stream<String> successCases() {
        return Stream.of(
                testFilesPath + "\\myCode.jpg",
                testFilesPath + "\\programmingLanguages.gif",
                testFilesPath + "\\jpegFile.jpeg",
                testFilesPath + "\\pdfFile.pdf",
                testFilesPath + "\\pcapFile.pcap",
                testFilesPath + "\\pcapFileSecondSignature.pcap",
                testFilesPath + "\\rarFile.rar"
        );
    }
    private static Stream<String> failureCases() {
        return Stream.of(
                testFilesPath + "\\jpgWithMistake.jpg",
                testFilesPath + "\\jpgTooShort.jpg",
                testFilesPath + "\\myCodeBadSig.gif",
                testFilesPath + "\\programmingLanguagesBadSig.jpg"
        );
    }

    @Test
    void shouldNotThrowIfFileExists() throws Exception {
        InputStream inputStream = new BufferedInputStream(new FileInputStream(testFilesPath + "\\myCode.jpg"));
        BasicFileExtensionValidator fileValidator = new BasicFileExtensionValidator(inputStream, "jpg");
    }

    @Test
    void shouldReturnTrueForTextFile() throws Exception {
        InputStream inputStream = new BufferedInputStream(new FileInputStream(testFilesPath + "\\bosman.txt"));
        BasicFileExtensionValidator fileValidator = new BasicFileExtensionValidator(inputStream, "txt");
        assertTrue(fileValidator.checkIfFileIsSafe());
    }

    @Test
    void shouldThrowIfFileIsEmpty() throws Exception {
        InputStream inputStream = new BufferedInputStream(new FileInputStream(testFilesPath + "\\emptyTxtFile.txt"));
        BasicFileExtensionValidator fileValidator = new BasicFileExtensionValidator(inputStream, "txt");
        assertThrows(IllegalStateException.class, fileValidator::checkIfFileIsSafe);
    }

    @ParameterizedTest
    @MethodSource("successCases")
    void shouldReturnTrueForAllTestFiles(String filePath) throws Exception {
        InputStream inputStream = new BufferedInputStream(new FileInputStream(filePath));
        String fileExtension = FilePathHandler.getExtension(filePath);
        BasicFileExtensionValidator fileValidator = new BasicFileExtensionValidator(inputStream, fileExtension);
        assertTrue(fileValidator.checkIfFileIsSafe());
    }

    @ParameterizedTest
    @MethodSource("failureCases")
    void shouldReturnFalseForAllTestFilesWithBadSignature(String filePath) throws Exception {
        InputStream inputStream = new BufferedInputStream(new FileInputStream(filePath));
        String fileExtension = FilePathHandler.getExtension(filePath);
        BasicFileExtensionValidator fileValidator = new BasicFileExtensionValidator(inputStream, fileExtension);
        assertFalse(fileValidator.checkIfFileIsSafe());
    }



}


