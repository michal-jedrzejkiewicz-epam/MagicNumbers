package EngineUnitTests;

import Engine.BasicFileExtensionValidator;
import Engine.IFileValidator;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.stream.Stream;


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




}
