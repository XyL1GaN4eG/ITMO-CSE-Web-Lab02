package lab02.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lab02.commands.Delete;
import lab02.commands.Get;
import lab02.exceptions.InvalidRequestException;
import lab02.messaging.ParseRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebServlet("/ControllerServlet")
public class ControllerServlet extends HttpServlet {
    //todo: а может каждый хттп метод отдельным сервлетом сделать....
    private final Delete delete = new Delete();
    private final Get get = new Get();

    private final ParseRequest parseRequest = new ParseRequest();

    //todo: сделать так чтобы история хранилась в хттп сессии а не как сейчас
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("loool");
        //todo: добавить отправку клиенту
        get.execute();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            log.info("loool");
            var requestData = parseRequest.getRequestData(request);
            request.setAttribute("requestData", requestData);
            //если реквестдата пришла без ошибок то закидываем реквест на сервлет areacheck
            request.getRequestDispatcher("/AreaCheckServlet").forward(request, response);
        } catch (InvalidRequestException e) {

            //todo: все пути вынести в отдельный класс с константами
            var urlToIndexJSP = "/index.jsp";
            var indexDispatcher = request.getRequestDispatcher(urlToIndexJSP);
            indexDispatcher.forward(request, response);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("loool");
        //todo: добавить отправку клиенту 205 ответа
        // upd:(а точно надо это добавить?)
        delete.execute();
    }


}
