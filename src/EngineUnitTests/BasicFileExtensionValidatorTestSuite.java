package EngineUnitTests;

import Engine.BasicFileExtensionValidator;
import Engine.IFileValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


public class BasicFileExtensionValidatorTestSuite {
    private final static String testFilesPath = Paths.get("exampleFilesToTest").toAbsolutePath().toString();

    private static Stream<String> successCases() {
        return Stream.of(testFilesPath + "\\myCode.jpg",
                         testFilesPath + "\\programmingLanguages.gif",
                         testFilesPath + "\\jpegFile.jpeg",
                         testFilesPath + "\\pdfFile.pdf",
                         testFilesPath + "\\pcapFile.pcap",
                         testFilesPath + "\\pcapFileSecondSignature.pcap",
                         testFilesPath + "\\rarFile.rar"
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
        IFileValidator fileValidator =
                new BasicFileExtensionValidator(testFilesPath + "\\bosman.txt");
        assertTrue(fileValidator.checkIfFileIsSafe());
    }

    @Test
    void shouldReturnFalseIfTxtFileContainsBytesValuesAbove127() throws Exception {
        IFileValidator fileValidator = new BasicFileExtensionValidator(testFilesPath + "\\myCodeText.txt");
        assertFalse(fileValidator.checkIfFileIsSafe());
    }

    @Test
    void shouldThrowIfFileIsEmpty() throws Exception {
        IFileValidator fileValidator =
                new BasicFileExtensionValidator(testFilesPath + "\\emptyTxtFile.txt");
        assertThrows(IllegalStateException.class, () -> fileValidator.checkIfFileIsSafe());
    }

    @ParameterizedTest
    @MethodSource("successCases")
    void shouldReturnTrueForAllTestFiles(String filePath) throws Exception {
        IFileValidator fileValidator = new BasicFileExtensionValidator(filePath);
        assertTrue(fileValidator.checkIfFileIsSafe());
    }

    @ParameterizedTest
    @MethodSource("failureCases")
    void shouldReturnFalseForAllTestFilesWithBadSignature(String filePath) throws Exception {
        IFileValidator fileValidator = new BasicFileExtensionValidator(filePath);
        assertFalse(fileValidator.checkIfFileIsSafe());
    }



}
