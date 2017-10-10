package seedu.address.model;

import static org.junit.Assert.*;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.Arrays;

import javafx.collections.ObservableList;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Config;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.TypicalPersons;
import seedu.address.ui.Ui;
import seedu.address.ui.UiManager;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        ModelManager modelManager = new ModelManager();
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredPersonList().remove(0);
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        ModelManager modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookName("differentName");
        assertTrue(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }

    @Test
    public void deleteTag() throws IllegalValueException, PersonNotFoundException {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        UserPrefs userPrefs = new UserPrefs();
        Tag tag = new Tag("friend");

        // duplicate persons
        ModelManager modelManager = new ModelManager(addressBook, userPrefs);
        thrown.expect(DuplicatePersonException.class);
        modelManager.addPerson(TypicalPersons.ALICE);

        modelManager.deleteTag(tag);

        //person not found, empty AddressBook
        AddressBook emptyAddressBook = new AddressBookBuilder().build();
        ModelManager emptyModelManager = new ModelManager(emptyAddressBook, userPrefs);
        AddressBook expectedAddressBook = new AddressBookBuilder().build();
        emptyModelManager.deleteTag(tag);
        assertEquals(emptyAddressBook,expectedAddressBook);

        //person not found, no such tag
        Tag noSuchTag = new Tag("nosuchtag");
        modelManager.deleteTag(noSuchTag);
        expectedAddressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        assertEquals(addressBook,expectedAddressBook);
    }

    @Test
    public void tagColor(){
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        UserPrefs userPrefs = new UserPrefs();
        ModelManager modelManager = new ModelManager(addressBook, userPrefs);
        Logic logic = new LogicManager(modelManager);
        Config config = new Config();
        Ui ui = new UiManager(logic,config,userPrefs);
        modelManager.getUi(ui);

        //default tagcolor should be off
        ObservableList<Tag> tags = addressBook.getTagList();
        for (Tag tag : tags){
            assertEquals(tag.getTagColor(),"#dcdcdc");
        }
    }
}
