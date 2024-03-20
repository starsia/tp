package seedu.address.logic.commands;

import org.junit.jupiter.api.Test;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.PhoneContainsDigitsPredicate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.*;

/**
 * Contains integration tests (interaction with the Model) for
 * {@code FindNumCommand}.
 */
public class FindNumCommandTest {
    private final Model model = new ModelManager(getTypicalNetConnect(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalNetConnect(), new UserPrefs());

    @Test
    public void equals() {
        PhoneContainsDigitsPredicate firstPredicate = new PhoneContainsDigitsPredicate(
                Collections.singletonList("first"));
        PhoneContainsDigitsPredicate secondPredicate = new PhoneContainsDigitsPredicate(
                Collections.singletonList("second"));

        FindNumCommand findNumFirstCommand = new FindNumCommand(firstPredicate);
        FindNumCommand findNumSecondCommand = new FindNumCommand(secondPredicate);

        // same object -> returns true
        assertEquals(findNumFirstCommand, findNumFirstCommand);

        // same values -> returns true
        FindNumCommand findNumFirstCommandCopy = new FindNumCommand(firstPredicate);
        assertEquals(findNumFirstCommand, findNumFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, findNumFirstCommand);

        // null -> returns false
        assertNotEquals(null, findNumFirstCommand);

        // different person -> returns false
        assertNotEquals(findNumFirstCommand, findNumSecondCommand);
    }

    @Test
    public void execute_zeroPhones_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        PhoneContainsDigitsPredicate predicate = preparePredicate(" ");
        FindNumCommand command = new FindNumCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multiplePhones_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        PhoneContainsDigitsPredicate predicate = preparePredicate("94351253 9482224 9482442");
        FindNumCommand command = new FindNumCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, ELLE, GEORGE), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        PhoneContainsDigitsPredicate predicate = new PhoneContainsDigitsPredicate(List.of("keyword"));
        FindNumCommand findNumCommand = new FindNumCommand(predicate);
        String expected = FindNumCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findNumCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private PhoneContainsDigitsPredicate preparePredicate(String userInput) {
        return new PhoneContainsDigitsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}