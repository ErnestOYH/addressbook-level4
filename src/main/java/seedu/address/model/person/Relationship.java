package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author Ernest

/**
 * Represents a Person's Relationship in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRelationship(String)}
 */
public class Relationship {

    public static final String MESSAGE_RELATIONSHIP_CONSTRAINTS =
            "Person names should only contain alphanumeric characters and spaces, and it should not be blank";

    public static final String RELATIONSHIP_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String relation;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public Relationship(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!isValidRelationship(trimmedName)) {
            throw new IllegalValueException(MESSAGE_RELATIONSHIP_CONSTRAINTS);
        }
        this.relation = trimmedName;
    }

    /**
     * Returns true if a given string is a valid person name.
     */
    public static boolean isValidRelationship(String test) {
        return test.matches(RELATIONSHIP_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return relation;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Relationship // instanceof handles nulls
                && this.relation.equals(((Relationship) other).relation)); // state check
    }

    @Override
    public int hashCode() {
        return relation.hashCode();
    }

}
