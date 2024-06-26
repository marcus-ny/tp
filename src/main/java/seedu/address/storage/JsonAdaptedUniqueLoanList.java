package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Loan;
import seedu.address.model.person.UniqueLoanList;

/**
 * Jackson-friendly version of {@link UniqueLoanList}.
 */
public class JsonAdaptedUniqueLoanList {

    public static final String MISSING_MESSAGE = "UniqueLoanList's loans field is missing!";

    private final List<JsonAdaptedLoan> loans;

    /**
     * Constructs a {@code JsonAdaptedUniqueLoanList} with the given loan details.
     */
    @JsonCreator
    public JsonAdaptedUniqueLoanList(@JsonProperty("loans") List<JsonAdaptedLoan> loans) {
        this.loans = loans;
    }

    /**
     * Converts a given {@code UniqueLoanList} into this class for Jackson use.
     */
    public JsonAdaptedUniqueLoanList(UniqueLoanList source) {
        if (source != null) {
            loans = source.getLoanList().stream()
                .map(JsonAdaptedLoan::new)
                .collect(Collectors.toList());
        } else {
            loans = null;
        }
    }

    /**
     * Factory method to create a new instance of JsonAdaptedUniqueLoanList
     * to disambiguate the constructor for null values.
     */
    public static JsonAdaptedUniqueLoanList factory(UniqueLoanList source) {
        return new JsonAdaptedUniqueLoanList(source);
    }

    /**
     * Converts this Jackson-friendly adapted loan object into the model's {@code UniqueLoanList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted loan.
     */
    public UniqueLoanList toModelType() throws IllegalValueException {
        if (loans == null) {
            throw new IllegalValueException(MISSING_MESSAGE);
        }

        ArrayList<Loan> loanList = new ArrayList<>();
        for (JsonAdaptedLoan loan : loans) {
            loanList.add(loan.toModelType());
        }
        UniqueLoanList uniqueLoanList = new UniqueLoanList();

        uniqueLoanList.setLoans(loanList);

        return uniqueLoanList;
    }

}
