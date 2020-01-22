package EngineUnitTests;

import Engine.BasicFileExtensionValidator;
import Engine.IFileValidator;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class BasicFileExtensionValidatorTestSuite {
    private final static String testFilesPath = Paths.get("exampleFilesToTest").toAbsolutePath().toString();

    private static Stream<String> testFilePaths() {
        return Stream.of(testFilesPath + "\\myCode.jpg",
                         testFilesPath + "\\programmingLanguages.gif",
                         testFilesPath + "\\jpegFile.jpeg"
        );
    }
    private static Stream<String> failureCases() {
        return Stream.of(testFilesPath + "\\jpgWithMistake.jpg",
                         testFilesPath + "\\jpgTooShort.jpg",
                         testFilesPath + "\\myCodeBadSig.gif",
                         testFilesPath + "\\programmingLanguagesBadSig.jpg"
        );
    }

    @Test
    void shouldNotThrowThrowIfFileDoesExists() throws Exception {
        IFileValidator fileValidator =  new BasicFileExtensionValidator(testFilesPath + "\\myCode.jpg");
    }

    @Test
    void shouldThrowIfFileDoesNotExists() {
        assertThrows(FileNotFoundException.class,
                     () -> new BasicFileExtensionValidator(testFilesPath + "\\ugabuga.txt"));
    }

    @Test
    void shouldReturnTrueForTextFile() throws Exception {
        BasicFileExtensionValidator fileValidator =
                new BasicFileExtensionValidator(testFilesPath + "\\bosman.txt");
        assertTrue(fileValidator.checkIfFileIsSafe());
    }

    @Test
    void shouldThrowIfFileIsEmpty() throws Exception {
        BasicFileExtensionValidator fileValidator =
                new BasicFileExtensionValidator(testFilesPath + "\\emptyTxtFile.txt");
        assertThrows(IllegalStateException.class, () -> fileValidator.checkIfFileIsSafe());
    }




}
