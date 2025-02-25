package seedu.linkedout.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.linkedout.commons.core.Messages;
import seedu.linkedout.commons.core.index.Index;
import seedu.linkedout.logic.commands.exceptions.CommandException;
import seedu.linkedout.model.Model;
import seedu.linkedout.model.applicant.Applicant;

/**
 * Deletes an applicant identified using it's displayed index from the linkedout app.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the applicant identified by the index number used in the displayed applicant list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_APPLICANT_SUCCESS = "Deleted Applicant: %1$s";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Applicant> lastShownList = model.getDefaultApplicantList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_APPLICANT_DISPLAYED_INDEX);
        }

        Applicant applicantToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteApplicant(applicantToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_APPLICANT_SUCCESS, applicantToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
