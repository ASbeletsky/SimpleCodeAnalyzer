package bl.model.impl;

import bl.model.IAnalyticFile;

/**
 * Created by Peter on 14.09.2016.
 */
public class Parser {
    public static IAnalyticFile parse(String src) {
        //todo
        return new AnalyticFileImpl(src);
    }
}
