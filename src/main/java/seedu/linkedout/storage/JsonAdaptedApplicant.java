package seedu.linkedout.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.linkedout.commons.exceptions.IllegalValueException;
import seedu.linkedout.model.applicant.Applicant;
import seedu.linkedout.model.applicant.Email;
import seedu.linkedout.model.applicant.Flag;
import seedu.linkedout.model.applicant.Job;
import seedu.linkedout.model.applicant.Name;
import seedu.linkedout.model.applicant.Phone;
import seedu.linkedout.model.applicant.Round;
import seedu.linkedout.model.skill.Skill;

/**
 * Jackson-friendly version of {@link Applicant}.
 */
class JsonAdaptedApplicant {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Applicant's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String job;
    private final String round;
    private final Boolean flag;
    private final List<JsonAdaptedSkill> skilled = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedApplicant} with the given applicant details.
     */
    @JsonCreator
    public JsonAdaptedApplicant(@JsonProperty("name") String name,
                                @JsonProperty("phone") String phone, @JsonProperty("email") String email,
                                @JsonProperty("job") String job, @JsonProperty("round") String round,
                                @JsonProperty("flag") Boolean flag,
                                @JsonProperty("skilled") List<JsonAdaptedSkill> skills) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.job = job;
        this.round = round;
        this.flag = flag;
        if (skills != null) {
            this.skilled.addAll(skills);
        }
    }

    /**
     * Converts a given {@code Applicant} into this class for Jackson use.
     */
    public JsonAdaptedApplicant(Applicant source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        job = source.getJob().value;
        round = source.getRound().value;
        flag = source.getFlag().value;
        skilled.addAll(source.getSkills().stream()
                .map(JsonAdaptedSkill::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted applicant object into the model's {@code Applicant} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted applicant.
     */
    public Applicant toModelType() throws IllegalValueException {
        final List<Skill> applicantSkills = new ArrayList<>();
        for (JsonAdaptedSkill skill : skilled) {
            applicantSkills.add(skill.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (job == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Job.class.getSimpleName()));
        }
        if (!Job.isValidJob(job)) {
            throw new IllegalValueException(Job.MESSAGE_CONSTRAINTS);
        }

        final Job modelJob = new Job(job);

        if (round == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Round.class.getSimpleName()));
        }
        if (!Round.isValidRound(round)) {
            throw new IllegalValueException(Round.MESSAGE_CONSTRAINTS);
        }
        final Round modelRound = new Round(round);

        if (flag == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Flag.class.getSimpleName()));
        }
        final Flag modelFlag = new Flag(flag);

        final Set<Skill> modelSkills = new HashSet<>(applicantSkills);
        return new Applicant(modelName, modelPhone, modelEmail, modelJob, modelRound, modelSkills, modelFlag);
    }

}
