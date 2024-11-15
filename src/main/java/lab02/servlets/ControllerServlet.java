package lab02.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lab02.commands.Delete;
import lab02.commands.Get;
import lab02.commands.Post;
import lab02.data.RequestData;
import lab02.exceptions.InvalidRequestException;
import lab02.util.RequestDataAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class ControllerServlet extends HttpServlet {
//    private Post post = new Post();
    private final Delete delete = new Delete();
    private final Get get = new Get();
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(RequestData.class, new RequestDataAdapter()) //используем кастомный
            .setPrettyPrinting()
            .create();

    //todo: сделать так чтобы история хранилась в хттп сессии а не как сейчас
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        get.execute();
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        delete.execute();
    }


}
