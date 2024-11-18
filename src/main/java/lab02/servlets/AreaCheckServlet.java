package lab02.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lab02.data.RequestData;
import lab02.data.ResponseData;
import lab02.messaging.ResponseHandler;
import lab02.util.AreaChecker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AreaCheckServlet extends HttpServlet {
    private final AreaChecker areaChecker = new AreaChecker();
    private final DateTimeFormatter yyyymmddhhmmss = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        var requestDataObj = request.getAttribute("requestData");
        var requestData = (RequestData) requestDataObj;

        var handledRequestData = handleRequestData(requestData, request);


        var json = gson.toJson(handledRequestData);

        //todo: вынести отправку в отдельный класс/метод
        ResponseHandler.handleResponse(response, json);
    }

    private List<ResponseData> handleRequestData(RequestData requestsArray, HttpServletRequest request) {
        var handledPoint = new ResponseData();
        var responseArr = new ArrayList<ResponseData>();
        for (double x : requestsArray.x()) {
            handledPoint = createElementOfResponse(requestsArray, x);
            saveToHistory(request, handledPoint);
            responseArr.add(handledPoint);
        }
        return responseArr;
    }

    private ResponseData createElementOfResponse(RequestData requestsArray, double x) {
        ResponseData response;
        long startTime = System.nanoTime();
        response = new ResponseData(x,
                requestsArray.y(),
                requestsArray.r());
        response.setIn(areaChecker.validate(response));
        response.setServerTime(LocalDateTime.now().format(yyyymmddhhmmss));
        response.setExecutionTime(System.nanoTime() - startTime);
        return response;
    }

    private void saveToHistory(HttpServletRequest request, ResponseData handledPoint) {
        // Получаем сессию
        var session = request.getSession();
        List<ResponseData> historyList = (List<ResponseData>) session.getAttribute("history");

        if (historyList == null) {
            historyList = new ArrayList<>();
            session.setAttribute("history", historyList);
        }

        // Добавляем новый объект в историю
        historyList.add(handledPoint);
    }
}
