package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_RELATIONSHIP;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Relationship;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

//@@author Ernest

/**
 * Command to add relationship to a person in addressBook
 */
public class AddRelationshipCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "relationship";
    public static final String COMMAND_ALIAS = "rel";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a relationship to a person in address book. \n"
            + COMMAND_ALIAS + ": Shorthand equivalent for relationship. \n"
            + "Parameters: INDEX "
            + PREFIX_RELATIONSHIP
            + "John Doe \n"
            + "Example 1: " + COMMAND_WORD + " 1 "
            + PREFIX_RELATIONSHIP + "John Doe \n"
            + "Example 2: " + COMMAND_ALIAS + " 1 "
            + PREFIX_RELATIONSHIP + "Mary Jane \n"
            + "Removing Relationship: " + COMMAND_WORD + " 1 "
            + PREFIX_RELATIONSHIP;

    public static final String MESSAGE_SUCCESS_RELATIONSHIP_ADDED = "Relationship added for Person: %1$s";
    public static final String MESSAGE_SUCCESS_RELATIONSHIP_REMOVED = "Relationship removed for Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";


    private final Index targetIndex;
    private final Relationship relation;

    public AddRelationshipCommand(Index targetIndex, Relationship relation) {
        requireNonNull(targetIndex);
        requireNonNull(relation);

        this.targetIndex = targetIndex;
        this.relation = relation;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();


        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToAddRelationship = lastShownList.get(targetIndex.getZeroBased());

        Person editedPerson = new Person(personToAddRelationship.getName(), personToAddRelationship.getPhone(),
                personToAddRelationship.getEmail(), personToAddRelationship.getAddress(),
                personToAddRelationship.getBloodType(), personToAddRelationship.getTags(),
                personToAddRelationship.getRemark(), personToAddRelationship.getAppointment(),
                this.relation);

        try {
            model.updatePerson(personToAddRelationship, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.getFilteredPersonList();

        if (editedPerson.getRemark().toString().isEmpty()) {
            return new CommandResult(String.format(MESSAGE_SUCCESS_RELATIONSHIP_REMOVED, editedPerson));
        } else {
            return new CommandResult(String.format(MESSAGE_SUCCESS_RELATIONSHIP_ADDED, editedPerson));
        }
    }

    public Index getIndex() {
        return this.targetIndex;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddRelationshipCommand // instanceof handles nulls
                && this.targetIndex.equals(((AddRelationshipCommand) other).targetIndex))
                && this.relation.equals(((AddRelationshipCommand) other).relation); // state check
    }

}
