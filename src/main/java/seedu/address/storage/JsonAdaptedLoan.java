package seedu.address.storage;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.DateUtil;
import seedu.address.model.person.Loan;

/**
 * Jackson-friendly version of {@link Loan}.
 */
public class JsonAdaptedLoan {

    private final BigDecimal value;
    private final String startDate;
    private final String returnDate;
    private final int id;
    private final boolean isReturned;
    private final JsonAdaptedPerson assignee;

    /**
     * Constructs a {@code JsonAdaptedLoan} with the given loan details.
     */
    @JsonCreator
    public JsonAdaptedLoan(@JsonProperty("value") BigDecimal value, @JsonProperty("startDate") String startDate,
                           @JsonProperty("returnDate") String returnDate, @JsonProperty("id") int id,
                           @JsonProperty("isReturned") boolean isReturned,
                           @JsonProperty("assignee") JsonAdaptedPerson assignee) {
        this.value = value;
        this.startDate = startDate;
        this.returnDate = returnDate;
        this.id = id;
        this.isReturned = isReturned;
        this.assignee = assignee;
    }

    /**
     * Converts a given {@code Loan} into this class for Jackson use.
     */
    public JsonAdaptedLoan(Loan source) {
        value = source.getValue();
        startDate = DateUtil.format(source.getStartDate());
        returnDate = DateUtil.format(source.getReturnDate());
        id = source.getId();
        isReturned = source.isReturned();
        assignee = new JsonAdaptedPerson(source.getAssignee());
    }

    /**
     * Converts this Jackson-friendly adapted loan object into the model's {@code Loan} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted loan.
     */
    public Loan toModelType() throws IllegalValueException {
        if (!Loan.isValidValue(value)) {
            throw new IllegalValueException(Loan.VALUE_CONSTRAINTS);
        }
        if (!Loan.isValidDates(DateUtil.parse(startDate), DateUtil.parse(returnDate))) {
            throw new IllegalValueException(Loan.DATE_CONSTRAINTS);
        }
        return new Loan(id, value, DateUtil.parse(startDate), DateUtil.parse(returnDate), isReturned,
                assignee.toModelType());
    }

}
