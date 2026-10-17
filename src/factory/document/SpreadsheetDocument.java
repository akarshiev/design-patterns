package factory.document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SpreadsheetDocument extends Document {

    private final int rows;
    private final int columns;
    private List<String> sheets = new ArrayList<>();

    public SpreadsheetDocument(String title, String author, int rows, int columns) {
        super(title, author);
        this.rows = rows;
        this.columns = columns;
    }

    @Override
    public String open() {
        return "Opening spreadsheet: " + title;
    }

    @Override
    public void save(String content) {
        this.content = content;
        this.sheets = new ArrayList<>(List.of(content.split("\n###\n", -1)));
        this.modifiedAt = LocalDateTime.now();
    }

    @Override
    public Map<String, Object> getMetadata() {
        Map<String, Object> metadata = new LinkedHashMap<>();

        metadata.put("type", "Spreadsheet");
        metadata.put("title", title);
        metadata.put("author", author);
        metadata.put("dimensions", rows + "x" + columns);
        metadata.put("sheet_count", sheets.size());
        metadata.put("created_at", createdAt);
        metadata.put("modified_at", modifiedAt);

        return metadata;
    }
}
