
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
import org.json.JSONException;
import org.json.JSONObject;

public class UserShelfVolumes {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void retrieveVolumes(String userId, int bookshelfId) throws IOException {
        final String url = "https://www.googleapis.com/books/v1/users/" + userId + "/bookshelves/" + bookshelfId + "/volumes";
        OkHttpClient client = new OkHttpClient();

        // Create a request
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        if (response.code() == 200) {
            // Parse the response
            String jsonData = response.body().string();
            try {
                JSONObject json = new JSONObject(jsonData);

                // Retrieve the kind
                String kind = json.getString("kind");
                if (kind != null && !kind.isEmpty()) {
                    System.out.println("Kind: " + kind);
                }

                // Retrieve and print the items
                if (json.has("items")) {
                    JSONArray items = json.getJSONArray("items");

                    // Iterate through the items array\
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
            } catch (JSONException e) {
                System.out.println("No available volumes");
            }
        } else {
            System.out.println("No such id");
        }
    }

}
