package fr.archives.nat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * @author Patrick Allain - 12/6/18.
 */
@Disabled("Ignored ...")
class JUnit5Test {

    @Test
    void testJunit5() {
        Assertions.fail("JUnit 5 Failure");
    }

}
