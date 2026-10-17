
package factory.document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PDFDocument extends Document {

    private final String pageSize;
    private final int securityLevel;
    private List<String> pages = new ArrayList<>();

    public PDFDocument(
            String title,
            String author,
            String pageSize,
            int securityLevel
    ) {
        super(title, author);
        this.pageSize = pageSize;
        this.securityLevel = securityLevel;
    }

    @Override
    public String open() {
        return "Opening PDF document: " + title;
    }

    @Override
    public void save(String content) {
        this.content = content;
        this.pages = new ArrayList<>(
                List.of(content.split("\n---\n", -1))
        );
        this.modifiedAt = LocalDateTime.now();
    }

    @Override
    public Map<String, Object> getMetadata() {
        Map<String, Object> metadata = new LinkedHashMap<>();

        metadata.put("type", "PDF Document");
        metadata.put("title", title);
        metadata.put("author", author);
        metadata.put("page_size", pageSize);
        metadata.put("security_level", securityLevel);
        metadata.put("page_count", pages.size());
        metadata.put("created_at", createdAt);
        metadata.put("modified_at", modifiedAt);

        return metadata;
    }
}
