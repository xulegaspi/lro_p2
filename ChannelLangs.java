import java.util.ArrayList;

/**
 * Created by Xurxo on 20/06/2016.
 */
public class ChannelLangs {
    public String day;
    public String channel;
    public ArrayList<String> langsArray;

    public ChannelLangs(String fecha, String nameChannel, ArrayList<String> langs) {
        day = fecha;
        channel = nameChannel;
        langsArray = langs;
    }

    public String toString() {
        String aux = "";
        for(String l: langsArray) {
            if(aux.equals("")) {
                aux = aux + l;
            } else {
                aux = aux + ", " + l;
            }
        }
        return day + " -> " + channel + " -> " + aux;
    }
}
