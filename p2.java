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
    private String sType;

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
        if(type != null) {
            sType = type;
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        int typeInt = 0;
        if(sType.equals("movies")) {
            typeInt = 1;
        } else {
            if(sType.equals("programs")) {
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

                        ArrayList<String> aDays = tvmlParser.getSchedule();
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
//                        out.println("<input type='submit' value='Inicio' onClick='document.forms[0].method=\"GET\"'>");
                        out.println(Util.homeButton);
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

                        ArrayList<String> aChannel = tvmlParser.getChannels(day);
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
//                        out.println("<input type='submit' value='Inicio' onClick='document.forms[0].method=\"GET\"'>");
                        out.println(Util.homeButton);
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

                        ArrayList<Movies> aFilms = tvmlParser.getMovies(ssday, sschannel);
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
//                        out.println("<input type='submit' value='Inicio' onClick='document.forms[0].method=\"GET\"'>");
                        out.println(Util.homeButton);
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

                        ArrayList<String> aLangs = tvmlParser.getLangs();
                        Iterator<String> it =  aLangs.listIterator();
                        boolean first = true;
                        while(it.hasNext()) {
                            String lang = it.next();
                            if(first) {
                                out.println("<input type='radio' name='lang' value='" + lang + "' checked>" + lang);
                                out.println("<br>");
                                first = false;
                            } else {
                                out.println("<input type='radio' name='lang' value='" + lang + "'>" + lang);
                                out.println("<br>");
                            }
                        }

                        out.println("<p><input type='submit' value='Enviar'>");
//                        out.println("<input type='submit' value='Inicio' onClick='document.forms[0].method=\"GET\"'>");
                        out.println(Util.homeButton);
                        out.println("</form>");
                        out.println("</body>");
                        out.println("</html>");
                        break;
                    case 2:
                        String lang = request.getParameter("lang");
                        out.println("<html><head><title>Servicio de TV - Practica 2</title></head>");
                        out.println("<body>");
                        out.println("<h1>Servicio de TV - Practica 2</h1>");
                        out.println("<h2>Programas</h2>");
                        out.println("<h3>Selecciona fecha:</h3>");
                        out.println("<form method='POST' action='?page=3'>");
                        out.println("<input type='hidden' name='lang' value='" + lang + "'>");

                        ArrayList<String> aDays = tvmlParser.getSchedule();
                        it =  aDays.listIterator();
                        first = true;
                        while(it.hasNext()) {
                            String day2 = it.next();
                            if(first) {
                                out.println("<input type='radio' name='day' value='" + day2 + "' checked>" + day2);
                                out.println("<br>");
                                first = false;
                            } else {
                                out.println("<input type='radio' name='day' value='" + day2 + "'>" + day2);
                                out.println("<br>");
                            }
                        }


                        out.println("<p><input type='submit' value='Enviar'>");
                        out.println(Util.backButton);
//                        out.println("<input type='submit' value='Inicio' onClick='document.forms[0].method=\"GET\"'>");
                        out.println(Util.homeButton);
                        out.println("</form>");
                        out.println("</body>");
                        out.println("</html>");
                        break;
                    case 3:
                        String day = request.getParameter("day");
                        String lang2 = request.getParameter("lang");

                        out.println("<html><head><title>Servicio de TV - Practica 2</title></head>");
                        out.println("<body>");
                        out.println("<h1>Servicio de TV - Practica 2</h1>");
                        out.println("<h2>Programas</h2>");
                        out.println("<h3>Selecciona canal:</h3>");
                        out.println("<form method='POST' action='?page=4'>");

                        ArrayList<String> aChannel = tvmlParser.getChannelsForProg(day, lang2);
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
//                        out.println("<input type='submit' value='Inicio' onClick='document.forms[0].method=\"GET\"'>");
                        out.println(Util.homeButton);
                        out.println("</form>");
                        out.println("</body>");
                        out.println("</html>");
                        break;
                    case 4:
                        String day3 = request.getParameter("day");
                        String lang3 = request.getParameter("lang");
                        String chan3 = request.getParameter("channel");

                        out.println("<html><head><title>Servicio de TV - Practica 2</title></head>");
                        out.println("<body>");
                        out.println("<h1>Servicio de TV - Practica 2</h1>");
                        out.println("<h2>Programas</h2>");
                        out.println("<h3>Resultado de su consulta:</h3>");

                        ArrayList<Programas> aProgramas = tvmlParser.getProgramas(lang3, day3, chan3);
                        Iterator<Programas> it2 =  aProgramas.listIterator();
                        while(it2.hasNext()) {
                            Programas mPrograma = it2.next();
                            out.println("<ul>");
                            out.println(mPrograma.getTitle());
                            out.println("<ul>");
                            out.println("Horario: " + mPrograma.getSchedule());
                            out.println("</ul>");
                            out.println("<ul>");
                            out.println("Synopsis: " + mPrograma.getMinAge());
                            out.println("</ul>");
                            out.println("</ul>");
                        }

                        out.println(Util.backButton);
//                        out.println("<input type='submit' value='Inicio' onClick='document.forms[0].method=\"GET\"'>");
                        out.println(Util.homeButton);
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
