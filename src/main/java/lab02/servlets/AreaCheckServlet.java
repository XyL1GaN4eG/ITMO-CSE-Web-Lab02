package lab02.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lab02.data.RequestData;
import lab02.data.ResponseData;
import lab02.messaging.ResponseBuilder;
import lab02.util.AreaChecker;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AreaCheckServlet extends HttpServlet {
    private final AreaChecker areaChecker = new AreaChecker();
    private final DateTimeFormatter yyyymmddhhmmss = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final ResponseBuilder responseBuilder = new ResponseBuilder();
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        var requestDataObj = request.getAttribute("requestData");
        var requestData = (RequestData) requestDataObj;

        var handledRequestData = handleRequestData(requestData);
        var json = gson.toJson(handledRequestData);

        //todo: вынести подобную отправку сообщений в отдельный метод, возможно в responseBuilder
         try (var out = response.getWriter()) {
             out.println(responseBuilder.buildHttpResponseMessage(json, 200));
             out.flush();
         } catch (IOException e) {
             //todo: нормальная обработка ошибки
             e.printStackTrace();
         }
    }
    //todo: добавить поддержку истории
    private List<ResponseData> handleRequestData(RequestData requestsArray) {
        var elementOfResponse = new ResponseData();
        var responseArr = new ArrayList<ResponseData>();
        for (int x : requestsArray.x()) {
            elementOfResponse = createElementOfResponse(requestsArray, x);
            responseArr.add(elementOfResponse);
        }
        return responseArr;
    }

    private ResponseData createElementOfResponse(RequestData requestsArray, int x) {
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

}
