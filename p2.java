/**
 * Created by Xurxo on 17/06/2016.
 */

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class p2 extends HttpServlet {

    TvmlParser TvGuide;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        TvGuide = new TvmlParser();
        String errors = TvGuide.Read();

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>Servicio de TV - Practica 2</title></head>");
        out.println("<body>");
        out.println("<h1>Servicio de TV - Practica 2</h1>");
        out.println("<h2>Inicio</h2>");
        out.println("Errores de lectura de los TVML: " + errors + "<br/>");
        out.println("<h3>Selecciona la consulta deseada:</h3>");
        out.println("<form method='POST' action='?page=1'>");
        out.println("<input type='radio' name='type' value='programs' checked> Programas<br>");
        out.println("<input type='radio' name='type' value='movies'> Pel&iacute;culas<br>");
        out.println("<p><input type='submit' value='Enviar'>");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String page = request.getParameter("page");
        String type = request.getParameter("type");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        switch (type) {

            case "movies":

                switch(page) {
                    case "1":
                        out.println("<html><head><title>Servicio de TV - Practica 2</title></head>");
                        out.println("<body>");
                        out.println("<h1>Servicio de TV - Practica 2</h1>");
                        out.println("<h2>Peliculas</h2>");
                        out.println("<h3>Selecciona fecha:</h3>");
                        out.println("<form method='POST' action='?page=2'>");



                        out.println("<p><input type='submit' value='Enviar'>");
                        out.println("<input type='submit' value='Inicio' onClick='document.forms[0].method=\"GET\"'>");
                        out.println("</form>");
                        out.println("</body>");
                        out.println("</html>");
                        break;
                    case "2":
                        out.println("<html><head><title>Servicio de TV - Practica 2</title></head>");
                        out.println("<body>");
                        out.println("<h1>Servicio de TV - Practica 2</h1>");
                        out.println("<h2>Peliculas</h2>");
                        out.println("<h3>Selecciona canal:</h3>");
                        out.println("<form method='POST' action='?page=3'>");



                        out.println("<p><input type='submit' value='Enviar'>");
                        out.println(Util.backButton);
                        out.println("<input type='submit' value='Inicio' onClick='document.forms[0].method=\"GET\"'>");
                        out.println("</form>");
                        out.println("</body>");
                        out.println("</html>");
                        break;
                    case "3":
                        out.println("<html><head><title>Servicio de TV - Practica 2</title></head>");
                        out.println("<body>");
                        out.println("<h1>Servicio de TV - Practica 2</h1>");
                        out.println("<h2>Peliculas</h2>");
                        out.println("<h3>Resultado de su consulta:</h3>");



                        out.println(Util.backButton);
                        out.println("<input type='submit' value='Inicio' onClick='document.forms[0].method=\"GET\"'>");
                        out.println("</form>");
                        out.println("</body>");
                        out.println("</html>");
                        break;
                    default:
                        break;
                }

                break;


            case "programs":

                switch(page) {
                    case "1":
                        out.println("<html><head><title>Servicio de TV - Practica 2</title></head>");
                        out.println("<body>");
                        out.println("<h1>Servicio de TV - Practica 2</h1>");
                        out.println("<h2>Programas</h2>");
                        out.println("<h3>Selecciona idioma:</h3>");
                        out.println("<form method='POST' action='?page=2'>");



                        out.println("<p><input type='submit' value='Enviar'>");
                        out.println("<input type='submit' value='Inicio' onClick='document.forms[0].method=\"GET\"'>");
                        out.println("</form>");
                        out.println("</body>");
                        out.println("</html>");
                        break;
                    case "2":
                        out.println("<html><head><title>Servicio de TV - Practica 2</title></head>");
                        out.println("<body>");
                        out.println("<h1>Servicio de TV - Practica 2</h1>");
                        out.println("<h2>Programas</h2>");
                        out.println("<h3>Selecciona fecha:</h3>");
                        out.println("<form method='POST' action='?page=3'>");



                        out.println("<p><input type='submit' value='Enviar'>");
                        out.println(Util.backButton);
                        out.println("<input type='submit' value='Inicio' onClick='document.forms[0].method=\"GET\"'>");
                        out.println("</form>");
                        out.println("</body>");
                        out.println("</html>");
                        break;
                    case "3":
                        out.println("<html><head><title>Servicio de TV - Practica 2</title></head>");
                        out.println("<body>");
                        out.println("<h1>Servicio de TV - Practica 2</h1>");
                        out.println("<h2>Programas</h2>");
                        out.println("<h3>Selecciona canal:</h3>");
                        out.println("<form method='POST' action='?page=4'>");



                        out.println("<p><input type='submit' value='Enviar'>");
                        out.println(Util.backButton);
                        out.println("<input type='submit' value='Inicio' onClick='document.forms[0].method=\"GET\"'>");
                        out.println("</form>");
                        out.println("</body>");
                        out.println("</html>");
                        break;
                    case "4":
                        out.println("<html><head><title>Servicio de TV - Practica 2</title></head>");
                        out.println("<body>");
                        out.println("<h1>Servicio de TV - Practica 2</h1>");
                        out.println("<h2>Programas</h2>");
                        out.println("<h3>Resultado de su consulta:</h3>");

                        out.println(Util.backButton);
                        out.println("<input type='submit' value='Inicio' onClick='document.forms[0].method=\"GET\"'>");
                        out.println("</form>");
                        out.println("</body>");
                        out.println("</html>");
                        break;
                    default:
                        break;
                }

                break;
        }

    }
}
