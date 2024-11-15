package lab02.servlets;

import lab02.util.AreaChecker;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AreaCheckServlet extends HttpServlet {
    private final AreaChecker areaChecker = new AreaChecker();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    }

}
