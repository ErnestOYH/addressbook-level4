package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RELATIONSHIP;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddRelationshipCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Relationship;

/**
 * Parses input arguments and creates a new AddRelationshipCommand object
 */
public class AddRelationshipCommandParser implements Parser<AddRelationshipCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddRelationshipCommand
     * and returns an AddRelationshipCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddRelationshipCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_RELATIONSHIP);

        Index indexInput;

        if (!isPrefixPresent(argMultimap, PREFIX_RELATIONSHIP)) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, AddRelationshipCommand.MESSAGE_USAGE));
        }
        try {
            indexInput = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        String relation = argMultimap.getValue(PREFIX_RELATIONSHIP).orElse("");

        try {
            return new AddRelationshipCommand(indexInput, new Relationship(relation));
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean isPrefixPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
