package factory.document;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class TextDocument extends Document {
    private final String encoding;
    private int lineCount;


    public TextDocument(String title, String author, String encoding) {
        super(title, author);
        this.encoding = encoding;
        this.lineCount = 0;
    }

    @Override
    public String open() {
        return "Text Document: " + title;
    }

    @Override
    public void save(String content) {
        this.content = content;
        this.lineCount = content.split("\n", -1).length;
        this.modifiedAt = LocalDateTime.now();
    }

    @Override
    public Map<String, Object> getMetadata() {
        Map<String, Object> metadata = new HashMap<>();

        metadata.put("type", "text Document");
        metadata.put("title", title);
        metadata.put("author", author);
        metadata.put("encoding", encoding);
        metadata.put("line_count", lineCount);
        metadata.put("created_at", createdAt);
        metadata.put("modified_at", modifiedAt);

        return metadata;
    }
}
