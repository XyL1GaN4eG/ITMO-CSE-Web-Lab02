package lab02.messaging;

import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class ResponseBuilder {
    public String buildHttpResponseMessage(String jsonBody, int httpCode) {
        var headers = new Properties();
        headers.put("Status", httpCode);
        headers.put("Content-Type", httpCode > 399 ? "text/html" : "application/json");
        headers.put("Content-Length", String.valueOf(jsonBody.getBytes(StandardCharsets.UTF_8).length));
        return buildHeaders(headers, jsonBody);
    }

    private String buildHeaders(Properties headers, String message) {
        StringBuilder headerBuilder = new StringBuilder();
        for (String key : headers.stringPropertyNames()) {
            headerBuilder.append(key).append(": ").append(headers.getProperty(key)).append("\r\n");
        }
        headerBuilder.append("\r\n");
        return String.valueOf(headerBuilder);
    }
}
