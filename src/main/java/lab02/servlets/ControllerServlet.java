package lab02.servlets;

import lab02.commands.Delete;
import lab02.commands.Get;
import lab02.data.RequestData;
import lab02.exceptions.InvalidRequestException;
import lab02.util.RequestReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ControllerServlet extends HttpServlet {
    //todo: а может каждый хттп метод отдельным сервлетом сделать....
    private final Delete delete = new Delete();
    private final Get get = new Get();

    private final RequestReader requestReader = new RequestReader();

    //todo: сделать так чтобы история хранилась в хттп сессии а не как сейчас
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //todo: добавить отправку клиенту
        get.execute();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            var requestData = requestReader.getRequestData(request);
            request.setAttribute("requestData", requestData);
            //если реквестдата пришла без ошибок то закидываем реквест на сервлет areacheck
            request.getRequestDispatcher("/AreaCheckServlet").forward(request, response);
        } catch (InvalidRequestException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, String.valueOf(e));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //todo: добавить отправку клиенту 200 ответа
        delete.execute();
    }


}
