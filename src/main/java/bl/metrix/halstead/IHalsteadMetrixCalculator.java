package bl.metrix.halstead;

import bl.model.IAnalytic;

/**
 * Created by Anton on 09.10.2016.
 */
public interface IHalsteadMetrixCalculator<TAnalytic extends IAnalytic>   {
    int getNumberOfUniqueOperands();
    int getNumberOfOperands();
    int getNumberOfUniqueOperators();
    int getNumberOfOperators();

    int getProgramVocabulary();
    int getProgramLength();
    double getProgramVolume();

    int getErrorsCount();

    void calculateMetrix();
}
