package bl.model.impl;

import javafx.beans.property.SimpleStringProperty;
import java.io.File;

/**
 * Created by radislavcrechet on 12/4/16.
 */

public class SourceFile {
    public SimpleStringProperty name;
    public SimpleStringProperty lastModified;
    public SimpleStringProperty content;
    public File file;

    public SourceFile(String name, String lastModified, String content, File file) {
        this.name = new SimpleStringProperty(name);
        this.lastModified = new SimpleStringProperty(lastModified);
        this.content = new SimpleStringProperty(content);
        this.file = file;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getLastModified() {
        return lastModified.get();
    }

    public void setLastModified(String lastModified) {
        this.lastModified.set(lastModified);
    }

    public String getContent() {
        return content.get();
    }

    public void setContent(String content) {
        this.content.set(content);
    }
}
