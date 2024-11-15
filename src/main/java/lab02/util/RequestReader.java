package lab02.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lab02.data.RequestData;
import lab02.exceptions.InvalidRequestException;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

@NoArgsConstructor
public class RequestReader {
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(RequestData.class, new RequestDataAdapter()) //используем кастомный
            .setPrettyPrinting()
            .create();

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
