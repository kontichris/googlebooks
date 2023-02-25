
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

public class UserShelf {

    private static final String URL_SEARCHIN = "https://www.googleapis.com/books/v1/volumes?q=";
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void getBookShelveInfo(String userId) throws IOException {
        OkHttpClient client = new OkHttpClient();

        // Form the URL
        String url = "https://www.googleapis.com/books/v1/users/" + userId + "/bookshelves";

        // Create a request
        Request request = new Request.Builder().url(url).build();

        // Execute the request
        Response response = client.newCall(request).execute();

        // Parse the response
        String jsonData = response.body().string();
        JSONObject json = new JSONObject(jsonData);
        JSONArray items = json.getJSONArray("items");
        for (int i = 0; i < items.length(); i++) {
        //dimiourgia thread gia tin emfanisi apotelesmatwn se 3exwristo thread/parathuro apto menu
            Thread inlineThread = new Thread() {
                public void run() {
                    JFrame frame = new JFrame();
                    frame.setSize(500, 700);
                    JTextArea tarea = new JTextArea();
                    frame.add(tarea);
                    tarea.setLineWrap(true);

                    for (int i = 0; i < items.length(); i++) {
                        try {
                            JSONObject bookshelf = items.getJSONObject(i);
                            Item item = objectMapper.readValue(bookshelf.toString(), Item.class);
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
}
