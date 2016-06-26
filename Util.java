/**
 * Created by Xurxo on 19/06/2016.
 */
public class Util {
    public static String backButton = "<button onclick='goBack()'>Atras</button><script>function goBack() { window.history.back(); } </script>";
    public static String homeButton = "<form method=\"GET\" action=\"localhost:8025/lro25/TV2\">\n" +
            "    <input type=\"submit\" value=\"Inicio\">\n" +
            "</form>";
}
