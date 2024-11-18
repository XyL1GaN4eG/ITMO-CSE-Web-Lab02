package lab02.messaging;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lab02.data.RequestData;
import lab02.exceptions.InvalidRequestException;
import lab02.util.SingletonGson;
import lombok.NonNull;

import java.io.BufferedReader;
import java.io.IOException;

public class ParseRequest {
    private final Gson gson = SingletonGson.getInstance();

    @NonNull
    public RequestData getRequestData(HttpServletRequest request) throws InvalidRequestException {
        try {
            var json = readRequestBody(request);
            RequestData requestData = gson.fromJson(json, RequestData.class);
            //todo: проверить не излишня ли такая конструкция
            if (requestData != null) {
                return requestData;
            }
            throw new NullPointerException();
        } catch (NullPointerException | IOException e) {
            throw new InvalidRequestException("Invalid JSON");
        } catch (NumberFormatException ex) {
            throw new InvalidRequestException("Invalid input format: Floating-point precision too high.");
        }
    }

    private String readRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder jsonBuilder = new StringBuilder();
        String line;

        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
        }
        return jsonBuilder.toString();
    }
}
