package Servlets;

import org.apache.log4j.Logger;
import telephoneStation.entity.Station;
import telephoneStation.entity.Subscriber;
import telephoneStation.exceptions.DaoGetException;
import telephoneStation.manage.Controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class MainServlet extends javax.servlet.http.HttpServlet {
    private Station station;
    private static final Logger logger = Logger.getLogger(MainServlet.class);

    @Override
    public void init() throws ServletException {
        super.init();
        station = new Station();
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        List<Subscriber> subList = new ArrayList<>();
        String parseType = request.getParameter("parseType");
        if(parseType == null)
            response.sendRedirect("/form.jsp");
        else
        {
            try
            {
                switch(parseType) {
                    case "SAX":
                        subList = Controller.getSubList(2);
                        break;
                    case "StaX":
                        subList = Controller.getSubList(3);
                        break;
                    case "DOM":
                    default:
                        subList = Controller.getSubList(1);
                }
            }catch(DaoGetException ex){
                logger.info(ex.getMessage());
            }
            if(subList != null) {
                station.setSubs(subList);
                request.setAttribute("station", station);
                request.getRequestDispatcher("/station.jsp").forward(request, response);
            }
            else
                response.sendRedirect("/form.jsp");
        }
    }
}
