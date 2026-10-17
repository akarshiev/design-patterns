package factory.document;

import java.util.Map;

public class DocumentFactory {

    public static Document createDocument(
            DocumentType docType,
            String title,
            String author,
            Map<String, Object> options
    ) {

        return switch (docType) {
            case TEXT -> {
                Object encoding = options.get("encoding");

                if (encoding == null) {
                    encoding = "utf-8";
                }

                yield new TextDocument(
                        title,
                        author,
                        (String) encoding
                );
            }

            case PDF -> {
                Object pageSize = options.get("page_size");
                Object securityLevel = options.get("security_level");

                if (pageSize == null) {
                    pageSize = "A4";
                }

                if (securityLevel == null) {
                    securityLevel = 0;
                }

                yield new PDFDocument(
                        title,
                        author,
                        (String) pageSize,
                        (Integer) securityLevel
                );
            }

            case SPREADSHEET -> {
                Object rows = options.get("rows");
                Object columns = options.get("columns");

                if (rows == null) {
                    rows = 100;
                }

                if (columns == null) {
                    columns = 26;
                }

                yield new SpreadsheetDocument(
                        title,
                        author,
                        (Integer) rows,
                        (Integer) columns
                );
            }
        };
    }
}