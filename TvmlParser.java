import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Created by Xurxo on 17/06/2016.
 */
public class TvmlParser {

    private static ArrayList<Document> docArray;
    private static ArrayList<String> daysList;
    private static ArrayList<String> urlsList;
    private static ArrayList<String> langsList;
    private static ArrayList<ChannelDay> channelsFilms;
    private static ArrayList<ChannelLangs> channelsProgs;

    private static String initialUrl = "http://clave.det.uvigo.es:8080/~lroprof/13-14/tvml14-20-06.xml";
//    private static String initialUrl = "src/tvml14-20-06.xml";
//    private static String initialUrl = "src/tvml-ok.xml";

    private static String errors = null;

    public TvmlParser() {

    }

//    public String Read() {
//
//        return null;
//    }

    public void read () {

        docArray = new ArrayList<Document>();
        daysList = new ArrayList<String>();
        urlsList = new ArrayList<String>();
        langsList = new ArrayList<String>();
        channelsFilms = new ArrayList<ChannelDay>();
        channelsProgs = new ArrayList<ChannelLangs>();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(true);
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            XML_DTD_ErrorHandler errorHandler = new XML_DTD_ErrorHandler();
            db.setErrorHandler(errorHandler);

            Document doc = db.parse(initialUrl);
            urlsList.add(initialUrl);
            docArray.add(doc);

            int xx = 0;
            do {
                ListIterator<Document> it = docArray.listIterator(xx);
                Document d = it.next();
                String fecha = d.getElementsByTagName("Fecha").item(0).getTextContent();
//                System.out.println(fecha);


                NodeList channelsList = d.getElementsByTagName("Canal");
                for(int jj = 0; jj < channelsList.getLength(); jj++) {
                    Element el = (Element) channelsList.item(jj);
                    NodeList urlList = el.getElementsByTagName("UrlTVML");
                    String defaultLang = el.getAttribute("lang");
                    String nameChannel = el.getElementsByTagName("NombreCanal").item(0).getTextContent();

                    ArrayList<String> currentLangs = new ArrayList<String>();

                    // Getting all the possible urls
                    for(int ii = 0; ii < urlList.getLength(); ii++) {
                        String nextUrl = el.getElementsByTagName("UrlTVML").item(ii).getTextContent();
//                        System.out.println(nextUrl);
                        if(!urlsList.contains(nextUrl)) {
                            urlsList.add(nextUrl);
                            try {
                                doc = db.parse(nextUrl);
                                docArray.add(doc);
                                urlsList.add(nextUrl);
                                if(!daysList.contains(fecha)) {
                                    daysList.add(fecha);
                                }
                            } catch (Exception e) {
                                System.out.println("TVML ERROR");
                            }
                        }
                    }

                    // Getting the different languages of the programs
                    NodeList progList = el.getElementsByTagName("Programa");
                    for(int zz = 0; zz < progList.getLength(); zz++) {
                        Element prog = (Element) progList.item(zz);
                        String langsProg = prog.getAttribute("langs");

                        if(langsProg.equals("")) {
                            if(!langsList.contains(defaultLang)) {
                                langsList.add(defaultLang);
                            }
                            if(!currentLangs.contains(defaultLang)) {
                                currentLangs.add(defaultLang);
                            }

                        } else {
                            String[] langs = langsProg.split("\\ ");
                            for(int ff = 0; ff < langs.length; ff++) {
                                if(!langsList.contains(langs[ff])) {
                                    langsList.add(langs[ff]);
                                }
                                if(!currentLangs.contains(langs[ff])) {
                                    currentLangs.add(langs[ff]);
                                }
                            }

                        }

                        // Checking if there are films
                        if(prog.getElementsByTagName("Categoria").item(0).getTextContent().equals("Cine")) {
                            System.out.println("CINE -> " + prog.getElementsByTagName("NombrePrograma").item(0).getTextContent());

                        }


                    }


//                    System.out.println(currentLangs);
                    ChannelLangs aux1 = new ChannelLangs(fecha, nameChannel, currentLangs);
                    channelsProgs.add(aux1);


                    ChannelDay aux = new ChannelDay(fecha, nameChannel);
                    channelsFilms.add(aux);
//                    System.out.println(nameChannel);
                }

            xx++;

            } while(xx < docArray.size());


            String tipo = doc.getDoctype().getName();
//            System.out.println("El tipo de documento es: "+tipo);
            Element raiz = doc.getDocumentElement();
//            System.out.println("El elemento raiz es: "+raiz.getTagName());


            // TESTS
/*
            for(ChannelDay a: channelsFilms) {
                System.out.println(a.toString());
            }

            for(String day: daysList) {
                ArrayList<String> readChannels = getChannels(day);
                for(String channel: readChannels) {
                    System.out.println(day + " -> " + channel);
                }
            }

            System.out.println("---------");

            for(String lang: langsList) {
                System.out.println(lang);
            }

            System.out.println("---------");

            for(ChannelLangs c: channelsProgs) {
                System.out.println(c.toString());
            }

            System.out.println("---------");

            String lang = "en";
            String dd = daysList.get(1);
            ArrayList<String> cChannels = getChannelsForProg(dd, lang);
            for(String cc: cChannels) {
                System.out.println(dd + " : " + lang + " ==> " + cc);
            }
*/



        } catch (Exception e) {
            System.out.println("ERROR");
            e.printStackTrace();
        }
    }

    public String getErrors() {
        return errors;
    }

    public static class XML_DTD_ErrorHandler extends DefaultHandler {
        public XML_DTD_ErrorHandler () {}
        public void warning(SAXParseException spe) {
            System.out.println("Warning: "+spe.toString());
            errors = errors + "<br>" + "Warning: " + spe.toString();
        }
        public void error(SAXParseException spe) {
            System.out.println("Error: "+spe.toString());
            errors = errors + "<br>" + "Error: " + spe.toString();
        }
        public void fatalerror(SAXParseException spe) {
            System.out.println("Fatal Error: "+spe.toString());
            errors = errors + "<br>" + "Fatal Error: " + spe.toString();
        }
    }

    public static ArrayList<String> getSchedule() { return daysList; }

    public static ArrayList<String> getChannels(String day) {
        ArrayList<String> channels = new ArrayList<String>();
        for(ChannelDay a: channelsFilms) {
            if(a.day.equals(day)) {
                channels.add(a.channel);
            }
        }
        return channels;
    }

    public static ArrayList<String> getChannelsForProg(String day, String lang) {
        ArrayList<String> channels = new ArrayList<String>();
        for(ChannelLangs a: channelsProgs) {
            if(a.day.equals(day) && a.langsArray.contains(lang)) {
                channels.add(a.channel);
            }
        }
        return channels;
    }

    public static ArrayList<String> getLangs() { return langsList; }

    public static ArrayList<Movies> getMovies(String day, String channel) {
        ArrayList<Movies> filmList = new ArrayList<Movies>();
        ListIterator<String> it = daysList.listIterator();
        for(int ii=0; ii<daysList.size(); ii++){
            if(it.next().equals(day)) {
                ListIterator<Document> docIt = docArray.listIterator(ii);
                NodeList lChannels = docIt.next().getElementsByTagName("Canal");
                for(int jj=0; jj<lChannels.getLength(); jj++){
                    Element eChannel = (Element)lChannels.item(jj);
                    String sChannel = eChannel.getElementsByTagName("NombreCanal").item(0).getTextContent();
                    if(sChannel.equals(channel) || channel.equals("all")){
                        NodeList lPrograms = eChannel.getElementsByTagName("Programa");
                        for(int ij=0; ij<lPrograms.getLength(); ij++){
                            Element eFilm = (Element)lPrograms.item(ij);
                            String category = eFilm.getElementsByTagName("Categoria").item(0).getTextContent();
                            if(category.equals("Cine")){
                                Movies film = new Movies();
                                film.setTitle(eFilm.getElementsByTagName("NombrePrograma").item(0).getTextContent());
                                Element eIntervalo = (Element)eFilm.getElementsByTagName("Intervalo").item(0);
                                film.setTime(eIntervalo.getElementsByTagName("HoraInicio").item(0).getTextContent());

                                Element eFilmCp = (Element)eFilm.cloneNode(true);
                                eFilmCp.getElementsByTagName("Categoria").item(0).setTextContent("");
                                eFilmCp.getElementsByTagName("NombrePrograma").item(0).setTextContent("");
                                ((Element)eFilmCp.getElementsByTagName("Intervalo").item(0)).setTextContent("");

                                film.setSynopsis(eFilmCp.getTextContent());
                                filmList.add(film);
                            }
                        }
                    }
                }
                return filmList;
            }
        }
        return filmList;
    }

    public static ArrayList<Programas> getProgramas(String lang, String day, String channel) {
        ArrayList<Programas> progList = new ArrayList<Programas>();
        ListIterator<String> it = daysList.listIterator();
        for(int ii=0; ii<daysList.size(); ii++){
            if(it.next().equals(day)) {
                ListIterator<Document> docIt = docArray.listIterator(ii);
                NodeList lChannels = docIt.next().getElementsByTagName("Canal");
                for(int jj=0; jj<lChannels.getLength(); jj++){
                    Element eChannel = (Element)lChannels.item(jj);
                    String sChannel = eChannel.getElementsByTagName("NombreCanal").item(0).getTextContent();
                    NodeList lPrograms = eChannel.getElementsByTagName("Programa");
                    for(int ij=0; ij<lPrograms.getLength(); ij++){
                        Element eShow = (Element)lPrograms.item(ij);
                        if(((eShow.getAttribute("langs").equals("") && eChannel.getAttribute("lang").equals(lang))
                                || (!eShow.getAttribute("langs").equals("") && eShow.getAttribute("langs").contains(lang)))
                                || lang.equals("all")){

                            Programas prog = new Programas();
                            prog.setTitle(eShow.getElementsByTagName("NombrePrograma").item(0).getTextContent());
                            Element eIntervalo = (Element)eShow.getElementsByTagName("Intervalo").item(0);
                            prog.setSchedule(eIntervalo.getElementsByTagName("HoraInicio").item(0).getTextContent());
                            prog.setMinAge(eShow.getAttribute("edadminima"));

                            progList.add(prog);
                        }
                    }
                }
                return progList;
            }
        }
        return progList;
    }


}
