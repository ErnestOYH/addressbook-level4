package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Calendar;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

//@@author Eric
/**
 * Command to add appointment to a person in addressBook
 */
public class AddAppointmentCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "appointment";
    public static final String COMMAND_ALIAS = "appt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an appoint to a person in address book. \n"
            + COMMAND_ALIAS + ": Shorthand equivalent for add. \n"
            + "Parameters: " + PREFIX_NAME + "PERSON "
            + PREFIX_DATE + "TIME" + "\n"
            + "Example 1:" + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_DATE + "Next Monday 3pm";

    public static final String MESSAGE_SUCCESS = "New appointment added. ";
    public static final String INVALID_PERSON = "This person is not in your address book";
    public static final String INVALID_DATE = "Invalid Date. Please enter a valid date.";


    private final Index index;
    private final Appointment appointment;


    //For sorting
    public AddAppointmentCommand() {
        this.index = null;
        this.appointment = null;
    }

    //For setting appointments
    public AddAppointmentCommand(Index index, Appointment appointment) {
        this.index = index;
        this.appointment = appointment;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        if (appointment == null && index == null) {
            model.listAppointment();
            return new CommandResult("Rearranged contacts to show upcoming appointments.");
        }

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        requireNonNull(index);

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToAddAppointment = lastShownList.get(index.getZeroBased());

        if (appointment.getDate() != null && !isDateValid()) {
            return new CommandResult(INVALID_DATE);
        }

        try {
            model.addAppointment(personToAddAppointment, appointment);
        } catch (PersonNotFoundException e) {
            return new CommandResult(INVALID_PERSON);
        }

        return new CommandResult(MESSAGE_SUCCESS);

    }

    /**
     * @return is appointment date set to after current time
     */
    private boolean isDateValid() {
        requireNonNull(appointment);
        Calendar calendar = Calendar.getInstance();
        return !appointment.getDate().before(calendar.getTime());
    }

    public Index getIndex() {
        return this.index;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddAppointmentCommand // instanceof handles nulls
                && (this.index.getZeroBased() == ((AddAppointmentCommand) other).index.getZeroBased())
                && (this.appointment.equals(((AddAppointmentCommand) other).appointment)));
    }

    /**
     * For testing purposes
     *
     */
    public void setData(Model model) {
        this.model = model;
    }

}
