package Engine;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static Engine.FilePathHandler.getExtension;
import static org.junit.jupiter.api.Assertions.*;

class FilePathHandlerTest {

    private static Stream<Arguments> successCases() {
        return Stream.of(
                Arguments.of("C:\\abcd.pdf", "pdf"),
                Arguments.of("file.txt", "txt"),
                Arguments.of("1283737218928.pcap", "pcap"),
                Arguments.of("!@#^%$^!!#..rar", "rar"),
                Arguments.of(".agh.edu.pl.jpg", "jpg")
        );
    }

    @ParameterizedTest(name = "{index} => path={0}, fileExtension={1}")
    @MethodSource("successCases")
    void shouldGetExtensionProperly(String path, String fileExtension) throws FileExtensionNotFoundInPathException {
        String extensionFromPath = getExtension(path);
        assertTrue(extensionFromPath.equals(fileExtension));
    }

    @ParameterizedTest
    @ValueSource(strings = {"path.", "", "path"})
    void shouldGetExtensionProperly(String improperPath) throws FileExtensionNotFoundInPathException {
        assertThrows(FileExtensionNotFoundInPathException.class, () -> getExtension(improperPath));
    }

}