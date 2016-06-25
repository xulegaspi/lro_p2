/**
 * Created by Xurxo on 17/06/2016.
 */

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

public class p2 extends HttpServlet {

    TvmlParser tvmlParser = new TvmlParser();
    private String ssday;
    private String sschannel;
    private String sslang;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        tvmlParser.read();
        String errors = tvmlParser.getErrors();

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>Servicio de TV - Practica 2</title></head>");
        out.println("<body>");
        out.println("<h1>Servicio de TV - Practica 2</h1>");
        out.println("<h2>Inicio</h2>");
        if(errors != null) {
            out.println("Errores de lectura de los TVML: " + errors + "<br/>");
        }
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

        int typeInt = 0;
        if(type.equals("movies")) {
            typeInt = 1;
        } else {
            if(type.equals("programs")) {
                typeInt = 2;
            }
        }

        int pageInt = Integer.parseInt(page);

        switch (typeInt) {

            case 1:

                switch(pageInt) {
                    case 1:
                        out.println("<html><head><title>Servicio de TV - Practica 2</title></head>");
                        out.println("<body>");
                        out.println("<h1>Servicio de TV - Practica 2</h1>");
                        out.println("<h2>Peliculas</h2>");
                        out.println("<h3>Selecciona fecha:</h3>");
                        out.println("<form method='POST' action='?page=2'>");

                        ArrayList<String> aDays = TvmlParser.getSchedule();
                        Iterator<String> it =  aDays.listIterator();
                        boolean first = true;
                        while(it.hasNext()) {
                            String day = it.next();
                            if(first) {
                                out.println("<input type='radio' name='day' value='" + day + "' checked>" + day);
                                out.println("<br>");
                                first = false;
                            } else {
                                out.println("<input type='radio' name='day' value='" + day + "'>" + day);
                                out.println("<br>");
                            }
                        }

                        out.println("<p><input type='submit' value='Enviar'>");
                        out.println("<input type='submit' value='Inicio' onClick='document.forms[0].method=\"GET\"'>");
                        out.println("</form>");
                        out.println("</body>");
                        out.println("</html>");
                        break;
                    case 2:
                        String day = request.getParameter("day");
                        ssday = day;
                        out.println("<html><head><title>Servicio de TV - Practica 2</title></head>");
                        out.println("<body>");
                        out.println("<h1>Servicio de TV - Practica 2</h1>");
                        out.println("<h2>Peliculas</h2>");
                        out.println("<h3>Selecciona canal:</h3>");
                        out.println("<form method='POST' action='?page=3'>");

                        ArrayList<String> aChannel = TvmlParser.getChannels(day);
                        it =  aChannel.listIterator();
                        first = true;
                        while(it.hasNext()) {
                            String sChannel = it.next();
                            if(first) {
                                out.println("<input type='radio' name='channel' value='" + sChannel + "' checked>" + sChannel);
                                out.println("<br>");
                                first = false;
                            } else {
                                out.println("<input type='radio' name='channel' value='" + sChannel + "'>" + sChannel);
                                out.println("<br>");
                            }
                        }

                        out.println("<p><input type='submit' value='Enviar'>");
                        out.println(Util.backButton);
                        out.println("<input type='submit' value='Inicio' onClick='document.forms[0].method=\"GET\"'>");
                        out.println("</form>");
                        out.println("</body>");
                        out.println("</html>");
                        break;
                    case 3:
                        String channel = request.getParameter("channel");
                        sschannel = channel;
                        out.println("<html><head><title>Servicio de TV - Practica 2</title></head>");
                        out.println("<body>");
                        out.println("<h1>Servicio de TV - Practica 2</h1>");
                        out.println("<h2>Peliculas</h2>");
                        out.println("<h3>Resultado de su consulta:</h3>");

                        ArrayList<Movies> aFilms = TvmlParser.getMovies(ssday, sschannel);
                        Iterator<Movies> it2 =  aFilms.listIterator();
                        while(it2.hasNext()) {
                            Movies mFilm = it2.next();
                            out.println("<ul>");
                            out.println(mFilm.getTitle());
                            out.println("<ul>");
                            out.println("Horario: " + mFilm.getTime());
                            out.println("</ul>");
                            out.println("<ul>");
                            out.println("Synopsis: " + mFilm.getSynopsis());
                            out.println("</ul>");
                            out.println("</ul>");
                        }

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


            case 2:

                switch(pageInt) {
                    case 1:
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
                    case 2:
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
                    case 3:
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
                    case 4:
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
