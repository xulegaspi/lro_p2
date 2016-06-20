/**
 * Created by Xurxo on 20/06/2016.
 */
public class ChannelDay {
    public String day;
    public String channel;

    public ChannelDay(String fecha, String nameChannel) {
        day = fecha;
        channel = nameChannel;
    }

    public String toString() {
        return day + " -> " + channel;
    }
}
