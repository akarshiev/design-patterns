
package factory.document;

import java.time.LocalDateTime;
import java.util.Map;

public abstract class Document {

    protected final String title;
    protected final String author;
    protected final LocalDateTime createdAt;

    protected LocalDateTime modifiedAt;
    protected String content = "";

    public Document(String title, String author) {
        this.title = title;
        this.author = author;
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = this.createdAt;
    }

    public abstract String open();

    public abstract void save(String content);

    public abstract Map<String, Object> getMetadata();
}
