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


/**
 * Command to add appointment to a person in addressBook
 */
public class AddAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "appointment";
    public static final String COMMAND_ALIAS = "apt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an appoint to a person in address book. \n"
            + COMMAND_ALIAS + ": Shorthand equivalent for add. \n"
            + "Parameters: " + PREFIX_NAME + "PERSON "
            + PREFIX_DATE + Appointment.DATE_FORMAT + "\n"
            + "Example 1:" + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe"
            + PREFIX_DATE + "2017/01/01 17:20";

    public static final String MESSAGE_SUCCESS = "New appointment added. ";
    public static final String INVALID_PERSON = "This person is not in your address book";
    public static final String INVALID_DATE = "Invalid Date. Please enter a valid date.";


    private final Index index;
    private final Calendar date;

    public AddAppointmentCommand() {
        date = null;
        index = null;
    }

    public AddAppointmentCommand(Index index, Calendar date) {
        this.index = index;
        this.date = date;
    }

    @Override
    public CommandResult execute() throws CommandException {

        if (date == null || index == null) {
            model.listAppointment();
            return new CommandResult("Rearranged contacts to show upcoming appointments.");
        }
        requireNonNull(date);
        requireNonNull(index);

        if (!isDateValid()) {
            return new CommandResult(INVALID_DATE);
        }
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToAddAppointment = lastShownList.get(index.getZeroBased());
        Appointment appointment = new Appointment(personToAddAppointment.getName().toString(), date);
        try {
            model.addAppointment(appointment);
        } catch (PersonNotFoundException e) {
            return new CommandResult(INVALID_PERSON);
        }
        return new CommandResult(MESSAGE_SUCCESS + "Meet " +  appointment.getPersonName().toString()
                + " on "
                +  appointment.getDate().toString());
    }

    /**
     * @return is appointment date set to after current time
     */
    private boolean isDateValid() {
        requireNonNull(date);
        Calendar calendar = Calendar.getInstance();
        return !date.getTime().before(calendar.getTime());
    }

    public Index getIndex() {
        return this.index;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddAppointmentCommand // instanceof handles nulls
                && this.index.getZeroBased() ==  ((AddAppointmentCommand) other).index.getZeroBased());
    }

    /**
     * For testing purposes
     *
     */
    public void setData(Model model) {
        this.model = model;
    }

}
