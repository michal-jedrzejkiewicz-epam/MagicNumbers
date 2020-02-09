package Engine;

import javafx.util.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static Engine.FileExtension.*;
import static org.junit.jupiter.api.Assertions.*;

class FileExtensionTest {

    private static final Stream<Arguments> enumStatesWithItsLowerStringProvider() {
        return Stream.of(
                Arguments.of("unknown", UNKNOWN),
                Arguments.of("txt"    , TXT),
                Arguments.of("pdf"    , PDF),
                Arguments.of("pcap"   , PCAP),
                Arguments.of("jpg"    , JPG),
                Arguments.of("jpeg"   , JPEG));
    }

    @ParameterizedTest(name = "{index} => lowerCaseExtension={0}, enumExtension={1}")
    @MethodSource("enumStatesWithItsLowerStringProvider")
    void shouldInAllCasesConvertEnumToLowerString(String lowerCaseExtension, FileExtension enumExtension) {
        assertTrue(lowerCaseExtension.equals(enumExtension.getLowerString()));
    }
}