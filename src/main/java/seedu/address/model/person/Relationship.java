package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
  * Represents a Person's relationship in the address book.
  * Guarantees: immutable; is always valid
  */
public class Relationship {

    public static final String MESSAGE_RELATIONSHIP_CONSTRAINTS =
            "Person relationship can take any values, can even be blank";

    public final String value;

    public Relationship(String value) {
        requireNonNull(value);
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Relationship // instanceof handles nulls
                && this.value.equals(((Relationship) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
