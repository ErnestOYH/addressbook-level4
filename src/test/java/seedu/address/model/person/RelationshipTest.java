package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RelationshipTest {

    @Test
    public void isValidRelationship() {
        // invalid name
        assertFalse(Relationship.isValidRelationship("")); // empty string
        assertFalse(Relationship.isValidRelationship(" ")); // spaces only
        assertFalse(Relationship.isValidRelationship("^")); // only non-alphanumeric characters
        assertFalse(Relationship.isValidRelationship("huang%")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Relationship.isValidRelationship("ah long")); // alphabets only
        assertTrue(Relationship.isValidRelationship("12345")); // numbers only
        assertTrue(Relationship.isValidRelationship("yandao the 2nd")); // alphanumeric characters
        assertTrue(Relationship.isValidRelationship("Ah Lian")); // with capital letters
        assertTrue(Relationship.isValidRelationship("George Richard Benjamin Michael Sr 3rd")); // long names
    }
}
