package lab02.servlets;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lab02.data.ResponseData;
import lab02.exceptions.InvalidRequestException;
import lab02.messaging.ParseRequest;
import lab02.messaging.ResponseHandler;
import lab02.util.SingletonGson;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/ControllerServlet")
public class ControllerServlet extends HttpServlet {
    //todo: а может каждый хттп метод отдельным сервлетом сделать....
    private final ParseRequest parseRequest = new ParseRequest();
    private final Gson gson = SingletonGson.getInstance();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var session = request.getSession();
        //todo: добавить обработку ошибок
        var history = (List<ResponseData>) session.getAttribute("history");

        if (history == null) {
            history = new ArrayList<>();
            session.setAttribute("history", history);
        }

        var json = gson.toJson(history);
        ResponseHandler.handleResponse(response, json);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            var requestData = parseRequest.getRequestData(request);
            request.setAttribute("requestData", requestData);
            //если реквестдата пришла без ошибок то закидываем реквест на сервлет areacheck
            request.getRequestDispatcher("/area-check").forward(request, response);
        } catch (InvalidRequestException e) {

            //todo: все пути на жспшки и сервлеты вынести в отдельный класс с константами
            var urlToIndexJSP = "/index.jsp";
            var indexDispatcher = request.getRequestDispatcher(urlToIndexJSP);
            indexDispatcher.forward(request, response);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var session = request.getSession();
        session.removeAttribute("history");
    }
}
