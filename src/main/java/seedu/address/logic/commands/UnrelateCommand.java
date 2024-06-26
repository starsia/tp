package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Id;
import seedu.address.model.person.filter.IdContainsDigitsPredicate;
import seedu.address.model.util.IdTuple;

/**
 * Represents a command to unrelate two persons in NetConnect using either their unique id or name.
 * The unique IDs or names provided must exist.
 * Parameters: [i/ID_1] [i/ID_2]
 * Example: unrelate i/4 i/12
 */
public class UnrelateCommand extends Command {
    public static final String COMMAND_WORD = "unrelate";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unrelates the two specified persons in NetConnect using either their unique id, OR, name.\n"
            + "The unique IDs or names provided must exist.\n"
            + "Parameters: i/ID_1 i/ID_2\n"
            + "Example: " + COMMAND_WORD + " i/4 i/12";

    private final IdContainsDigitsPredicate predicate;

    private final Id firstPersonId;
    private final Id secondPersonId;

    /**
     * Creates a UnrelateCommand to unrelate the two specified {@code IdContainsDigitsPredicate}
     */
    public UnrelateCommand(IdContainsDigitsPredicate predicate) {
        this.predicate = predicate;
        this.firstPersonId = Id.generateTempId(predicate.getFirstId());
        this.secondPersonId = Id.generateTempId(predicate.getSecondId());
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (firstPersonId.equals(secondPersonId)) {
            model.clearFilter();
            throw new CommandException(Messages.MESSAGE_CANNOT_UNRELATE_ITSELF);
        }
        // actual execution occurs here
        if (!model.hasId(firstPersonId)) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_PERSON_ID, firstPersonId.value));
        } else if (!model.hasId(secondPersonId)) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_PERSON_ID, secondPersonId.value));
        }

        IdTuple tuple = new IdTuple(firstPersonId, secondPersonId);

        if (!model.hasRelatedIdTuple(tuple)) {
            throw new CommandException(Messages.MESSAGE_RELATION_NOT_EXISTS);
        } else {
            model.removeRelatedIdTuple(tuple);
        }

        // reset user view from any previous commands
        model.clearFilter();

        // if ids are valid AND exists, model will display them, otherwise, it will be an empty list
        model.stackFilters(predicate);

        return new CommandResult(String.format(Messages.MESSAGE_UNRELATION_SUCCESS, tuple));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof UnrelateCommand
                && predicate.equals(((UnrelateCommand) other).predicate));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
