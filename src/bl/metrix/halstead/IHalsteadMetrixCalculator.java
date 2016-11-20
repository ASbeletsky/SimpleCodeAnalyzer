package bl.metrix.halstead;

import bl.model.IAnalytic;

/**
 * Created by Anton on 09.10.2016.
 */
public interface IHalsteadMetrixCalculator<TAnalytic extends IAnalytic>   {
    public int getProgramVocabulary();
    public int getProgramLength();
    public double getProgramVolume();

    int calculateNumberOfUniqueOperands();
    int calculateNumberOfOperands();
    int calculateNumberOfUniqueOperators();
    int calculateNumberOfOperators();
}
