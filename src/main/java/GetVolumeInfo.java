
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
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

public class GetVolumeInfo {

    private static final String URL_SEARCHIN = "https://www.googleapis.com/books/v1/volumes?q=";
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void getVolumeInfo(String volumeId) throws IOException {
        // Create a client
        OkHttpClient client = new OkHttpClient();

        // Form the URL
        String url = "https://www.googleapis.com/books/v1/volumes/" + volumeId;

        // Create a request
        Request request = new Request.Builder().url(url).build();

        VolumeInfo vinfo = new VolumeInfo();

        // Execute the request
        Response response = client.newCall(request).execute();
        if (response.code() == 200) {
            // Parse the response
            String jsonData = response.body().string();
            JSONObject json = new JSONObject(jsonData);

            System.out.println(json);

            //dimiourgia thread gia tin emfanisi apotelesmatwn se 3exwristo thread/parathuro apto menu
            Thread inlineThread = new Thread() {
                public void run() {
                    JFrame frame = new JFrame();
                    frame.setSize(500, 700);
                    JTextArea tarea = new JTextArea();
                    frame.add(tarea);
                    tarea.setLineWrap(true);

                    try {
                        Item item = objectMapper.readValue(json.toString(), Item.class);

                        Main.historyList.add(item);
                        if (Main.historyList.size() > 5) {
                            Main.historyList.remove(0);
                        }

                        System.out.println(item);
                        tarea.append(item.toString() + "\n");
                        tarea.append("------------");
                    } catch (JsonProcessingException ex) {
                        Logger.getLogger(PerformingSearch.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    frame.setVisible(true);

                }
            };
            inlineThread.start();

        } else {
            System.out.println("Invalid volumeId. Please enter a valid volumeId.");
        }

    }
}
