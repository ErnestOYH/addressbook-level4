package seedu.address.logic.parser;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Calendar;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddAppointmentCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Appointment;
import seedu.address.testutil.TypicalPersons;

//@@author Eric
public class AddAppointmentParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private AddAppointmentParser parser = new AddAppointmentParser();

    @Test
    public void prefixesNotPresent() throws ParseException {
        thrown.expect(ParseException.class);
        parser.parse("1 lunch tomorrow 5pm");
    }

    @Test
    public void illegalExpression() throws ParseException {
        thrown.expect(ParseException.class);
        parser.parse("n/@@@@ d/2018/02/10 10:10");
    }

    @Test
    public void nonParsableString() throws ParseException {
        thrown.expect(ParseException.class);
        parser.parse("appt 1 d/lunch ,cant parse this string");
    }
    @Test
    public void parseDateExpression() throws ParseException, java.text.ParseException {

        AddAppointmentCommand command = parser.parse("appt 1 d/Lunch, tomorrow 5pm");
        Appointment appointment = AddAppointmentParser.getAppointmentFromString("Lunch, tomorrow 5pm");
        assertEquals(new AddAppointmentCommand(Index.fromOneBased(1), appointment), command);

    }

    @Test
    public void parseEmptyExpression() {

        //No name and no date will just call the parser to return a command with no attributes initialized
        try {
            AddAppointmentCommand command = parser.parse("appointment");
            assertTrue(command.getIndex() == null);
        } catch (ParseException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void parseAppointmentsWithDuration() {

        try {
            AddAppointmentCommand command = parser.parse("appt 1 d/Lunch, tomorrow 5pm to 7pm");
            Appointment appointment =AddAppointmentParser.getAppointmentFromString("Lunch, tomorrow 5pm to 7pm");
            assertEquals(new AddAppointmentCommand(Index.fromOneBased(1), appointment), command);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
