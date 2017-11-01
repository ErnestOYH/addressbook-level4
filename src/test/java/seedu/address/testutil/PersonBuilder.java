package seedu.address.testutil;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.Bloodtype;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Relationship;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_BLOODTYPE = "O";
    public static final String DEFAULT_TAGS = "friends";
    public static final String DEFAULT_REMARK = "";
    public static final String DEFAULT_DATE = "2018/01/01 00:00";
    public static final String DEFAULT_RELATIONSHIP = "John Doe";
    private Person person;

    public PersonBuilder() {
        try {
            Name defaultName = new Name(DEFAULT_NAME);
            Phone defaultPhone = new Phone(DEFAULT_PHONE);
            Email defaultEmail = new Email(DEFAULT_EMAIL);
            Address defaultAddress = new Address(DEFAULT_ADDRESS);
            Bloodtype defaultBloodType = new Bloodtype(DEFAULT_BLOODTYPE);
            Set<Tag> defaultTags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
            Remark defaultRemark = new Remark(DEFAULT_REMARK);
            Relationship defaultRelationship = new Relationship(DEFAULT_RELATIONSHIP);
            Calendar calendar = Calendar.getInstance();
            try {
                calendar.setTime(Appointment.DATE_FORMATTER.parse(DEFAULT_DATE));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Appointment  appointment = new Appointment(defaultName.toString(), calendar);
            this.person = new Person(defaultName, defaultPhone, defaultEmail,
                defaultAddress, defaultBloodType, defaultTags, defaultRemark, appointment, defaultRelationship);
        } catch (IllegalValueException ive) {
            throw new AssertionError("Default person's values are invalid.");
        }
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(ReadOnlyPerson personToCopy) {
        this.person = new Person(personToCopy);
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        try {
            this.person.setName(new Name(name));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("name is expected to be unique.");
        }
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String... tags) {
        try {
            this.person.setTags(SampleDataUtil.getTagSet(tags));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tags are expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        try {
            this.person.setAddress(new Address(address));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("address is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        try {
            this.person.setPhone(new Phone(phone));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("phone is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        try {
            this.person.setEmail(new Email(email));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("email is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Person} that we are building.
     */
    public PersonBuilder withRemark(String remark) {
        this.person.setRemark(new Remark(remark));
        return this;
    }

    //@@author Ernest
    /**
     * Sets the {@code Bloodtype} of the {@code Person} that we are building.
     */
    public PersonBuilder withBloodType(String bloodType) {
        try {
            this.person.setBloodType(new Bloodtype(bloodType));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("bloodType is expected to be unique.");
        }
        return this;
    }
    //@@author

    //@@author Eric
    /**
     * Sets appointment with Date of the person that we are building
     */
    public PersonBuilder withAppointment(String name, String date) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(Appointment.DATE_FORMATTER.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.person.setAppointment(new Appointment(person.getName().toString(), calendar));
        return this;
    }

    /**
     * With appointment that specified a endDate
     */
    public PersonBuilder withAppointment(String name, String date, String endDate) {
        Calendar calendar = Calendar.getInstance();
        Calendar calendar1 = Calendar.getInstance();
        try {
            calendar.setTime(Appointment.DATE_FORMATTER.parse(date));
            calendar1.setTime(Appointment.DATE_FORMATTER.parse(endDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.person.setAppointment(new Appointment(person.getName().toString(), calendar, calendar1));
        return this;
    }

    /**
     * Sets an empty appointment with the person that we are building
     */
    public PersonBuilder withAppointment(String name) {
        this.person.setAppointment(new Appointment(person.getName().toString()));
        return this;
    }
    //@@author
    public Person build() {
        return this.person;
    }

    //@@author Ernest
    /**
     * Sets the {@code Relationship} of the {@code Person} that we are building.
     */
    public PersonBuilder withRelationship(String relation) {
        try {
            this.person.setRelationship(new Relationship(relation));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("relation is expected to be unique.");
        }
        return this;
    }
    //@@author
}
