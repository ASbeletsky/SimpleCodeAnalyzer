package bl.model.impl;

import bl.model.IAnalyticFile;
import bl.model.ParseException;

import java.io.File;

/**
 * Created by Peter on 14.09.2016.
 */
public class Parser {
    public static IAnalyticFile parse(File file) throws ParseException {
        return new AnalyticFile(file);
    }
}
