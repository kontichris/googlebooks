
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

public class PerformingSearch {

    private static final String URL_SEARCHIN = "https://www.googleapis.com/books/v1/volumes?q=";
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void searchVolumes(String searchTerm) throws IOException {
        

        // Create a client
        OkHttpClient client = new OkHttpClient();

        // Create a query
        String query = URLEncoder.encode(searchTerm, StandardCharsets.UTF_8);

        // Form the URL
        String url = URL_SEARCHIN + query;

        // Create a request and execute it
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();

        ArrayList<Item> itemsList = new ArrayList<>();

        // Parse the response
        String jsonData = response.body().string();
        JSONObject json = new JSONObject(jsonData);
        System.out.println(json);

        String kind = json.getString("kind");
        if (kind != null && !kind.isEmpty()) {
            System.out.println("Kind: " + kind);
        }
        JSONArray items = json.getJSONArray("items");
        
        
        //dimiourgia thread gia tin emfanisi apotelesmatwn se 3exwristo thread/parathuro apto menu
        Thread inlineThread = new Thread() {
            public void run() {
                JFrame frame = new JFrame();
                frame.setSize(500,700);
                JTextArea tarea = new JTextArea();
                frame.add(tarea);
                tarea.setLineWrap(true);

                for (int i = 0; i < items.length(); i++) {
                    try {
                        JSONObject itemjson = items.getJSONObject(i);
                        Item item = objectMapper.readValue(itemjson.toString(), Item.class);
                        System.out.println(item);
                        tarea.append(item.toString() + "\n");
                        tarea.append("------------");
                    } catch (JsonProcessingException ex) {
                        Logger.getLogger(PerformingSearch.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                

                frame.setVisible(true);
            }
        };
        inlineThread.start();
        
        
    }

}
