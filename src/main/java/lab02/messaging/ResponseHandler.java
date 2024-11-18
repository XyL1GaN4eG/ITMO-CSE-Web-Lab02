package lab02.messaging;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ResponseHandler {
    public static void handleResponse(HttpServletResponse response, String json) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);

        try (var out = response.getWriter()) {
            out.print(json);
            out.flush();
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }
}
