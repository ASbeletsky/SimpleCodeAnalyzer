package bl.model.impl;

import bl.Utils;
import bl.model.IAnalytic;
import bl.model.IAnalyticClass;
import bl.model.IAnalyticFile;
import bl.model.ParseException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Peter on 14.09.2016.
 */
class AnalyticFile implements IAnalyticFile {
    private final String fileName;
    private final String filePackage;
    private String src;
    private final List<IAnalyticClass> classes = new ArrayList<>();

    AnalyticFile(File file) throws ParseException {
        try {
            this.fileName = file.getName();
            this.src = parseSrc(Utils.fileToString(file));
            this.filePackage = parsePackage(src);
            this.classes.addAll(parseClasses(src));
        } catch (Exception e) {
            throw new ParseException(e);
        }
    }

    @Override
    public String getName() {
        return fileName;
    }

    @Override
    public String getSrcCode() {
        return src;
    }

    @Override
    public List<IAnalyticClass> getClasses() {
        return classes;
    }

    @Override
    public String getPackage() {
        return filePackage;
    }

    @Override
    public int compareTo(IAnalytic o) {
        if(o instanceof AnalyticFile) {
            return this.fileName.compareTo(((AnalyticFile) o).fileName);
        } else return -1;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof AnalyticFile && ((AnalyticFile) obj).fileName.equals(fileName);
    }

    static String parseSrc(String src) {

        //TODO: find and delete all imports with * (like import java.io.*;)

        //find all imports
        Pattern importPattern = Pattern.compile("import[\\s]+([^;\\s\\n]+);");
        Matcher importMatcher = importPattern.matcher(src);
        List<String> importReplacerList = new ArrayList<>();
        while (importMatcher.find()) {
            importReplacerList.add(importMatcher.group(1));
            src = src.substring(0, importMatcher.start()) + src.substring(importMatcher.end());
            importMatcher = importPattern.matcher(src);
        }

        //delete all imports
        for(String importToReplace : importReplacerList) {
            String className = importToReplace.substring(importToReplace.lastIndexOf('.')+1);
            src = src.replaceAll(className, importToReplace);
        }

        //TODO: delete all annotations
        Pattern annotationsPattern = Pattern.compile("(@[^\\s]+)");



        return src
                .trim()
                .replaceAll("(/\\*([^*]|[\\r\\n]|(\\*+([^*/]|[\\r\\n])))*\\*+/)|(//.*)", "") //delete all comments
                .replaceAll("[\\n\\r]", "");
    }

    static String parsePackage(String src) {
        //package <any count of spaces> packageName <any count of spaces>;
        Matcher m = Pattern.compile("package[\\s]*([^\\s]+)[\\s]*;").matcher(src);
        //noinspection ResultOfMethodCallIgnored
        m.find();
        return m.group(1);
    }

    static List<IAnalyticClass> parseClasses(String src) {
        List<IAnalyticClass> result = new ArrayList<>();
        Pattern pattern =
                Pattern.compile("([^;{}]*)[\\s]+class[\\s]+([^<\\s]+)[\\s]*([^{]*)[\\s]*\\{");
        Matcher matcher = pattern.matcher(src);

        while (matcher.find()) {
            //group(1) - public abstract...
            //group(2) - class name
            //group(3) - <T...> extends ... implements...

            int openedBracketsCount = 1;
            int closedBracketsCount = 0;
            int startIndex = matcher.start()/*+1*/;
            int endIndex = matcher.end();
            for(; endIndex < src.length(); endIndex++) {
                if(openedBracketsCount == closedBracketsCount) {
                    break;
                }

                if(src.charAt(endIndex) == '{') {
                    openedBracketsCount++;
                } else if(src.charAt(endIndex) == '}') {
                    closedBracketsCount++;
                }
            }
            result.add(new AnalyticClass(src.substring(startIndex, endIndex)));
            src = src.substring(0, startIndex) + src.substring(endIndex, src.length());
            matcher = pattern.matcher(src);
        }

        return result;
    }
}
