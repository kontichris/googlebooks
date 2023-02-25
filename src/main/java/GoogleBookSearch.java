
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GoogleBookSearch {

    private static final String URL_SEARCHIN = "https://www.googleapis.com/books/v1/volumes?q=";
    private static final String GOOGLE_VOLUME_URL = "https://www.googleapis.com/books/v1/volumes/";

    private static ArrayList<String> historyList = new ArrayList<>();
    private static final int MAX_SEARCHES = 5;
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        int choice = 1;
        boolean run = false;
        while (true) {
            System.out.println("1) Search volumes based on search term and then View information about a specific volume");
            System.out.println("2) Search history");
            System.out.println("3) Search books based on filter for previewability");
            System.out.println("4) Search books based on pagination");
            System.out.println("5) Search books based on print type");
            System.out.println("6) Search books based on projection");
            System.out.println("7) Search books with sorting");
            System.out.println("8) Get a list of another user's public bookshelves and the volumes");
            System.out.println("0) Exit");
            System.out.print("Enter your choice: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice != 1 && choice != 2 && choice != 3 && choice != 4 && choice != 5 && choice != 6 && choice != 7 && choice != 8) {
                    System.out.println("Please choose a valid option (1-3)");
                    continue;
                }
                switch (choice) {
                    case 1:
                        // Get user input
                        System.out.print("Enter search term: ");
                        if (scanner.hasNext()) {
                            String searchTerm = scanner.next();
                            searchVolumes(searchTerm);
                        }
                        break;
                    case 2:
                        for (int i = historyList.size()-1; i > historyList.size()-6; i--) {
                            printLastSearches(historyList.get(i));
                        }
                        break;

                    case 3:
                        boolean nonValid = false;
                        int filterOption = 0;
                        while (!nonValid) {
                            System.out.print("Enter filter option (1 for partial, 2 for full, 3 for free-ebooks, 4 for paid-books, 5 for ebooks.): ");
                            filterOption = scanner.nextInt();
                            if (filterOption >= 1 && filterOption <= 5) {
                                nonValid = true;
                            } else {
                                System.out.println("Please enter a valid number 1-5.");
                            }
                        }
                        searchWithFilter(filterOption);
                        break;

                    case 4:
                        System.out.print("Enter search term: ");
                        if (scanner.hasNext()) {
                            String searchTerm = scanner.next();
                            System.out.print("Enter start index: ");
                            int startIndex = scanner.nextInt();
                            System.out.print("Enter max results: ");
                            int maxResults = scanner.nextInt();
                            searchWithPagination(searchTerm, startIndex, maxResults);
                        }
                        break;
                    case 5:

                        System.out.print("Enter search term: ");
                        if (scanner.hasNext()) {
                            String searchTerm = scanner.next();
                            nonValid = false;
                            int printType = 0;
                            while (!nonValid) {
                                System.out.print("Enter \"all\" or \"book\" or \"magazines\"");
                                printType = scanner.nextInt();
                                if (printType >= 1 && printType <= 3) {
                                    nonValid = true;
                                } else {
                                    System.out.println("Please enter a valid number 1-3.");
                                }
                            }
                            searchWithPrintType(searchTerm, printType);
                        }
                        break;
                    case 6:

                        System.out.print("Enter search term: ");
                        if (scanner.hasNext()) {
                            String searchTerm = scanner.next();
                            nonValid = false;
                            int projectionOption = 0;
                            while (!nonValid) {
                                System.out.print("Enter 1 for full, 2 for lite.");
                                projectionOption = scanner.nextInt();
                                if (projectionOption >= 1 && projectionOption <= 2) {
                                    nonValid = true;
                                } else {
                                    System.out.println("Please enter a valid number 1-2.");
                                }
                            }
                            searchWithProjection(projectionOption, searchTerm);
                        }
                        break;
                    case 7:

                        System.out.print("Enter search term: ");
                        if (scanner.hasNext()) {
                            String searchTerm = scanner.next();
                            nonValid = false;
                            int orderingOption = 0;
                            while (!nonValid) {
                                System.out.print("Enter 1 for relevance, 2 for newest.");
                                orderingOption = scanner.nextInt();
                                if (orderingOption >= 1 && orderingOption <= 2) {
                                    nonValid = true;
                                } else {
                                    System.out.println("Please enter a valid number 1-2.");
                                }
                            }
                            searchWithOrdering(searchTerm, orderingOption);
                        }
                        break;

                    case 8:
                        //retrieve user's public bookshelves

                        boolean validInput = false;
                        String userId = scanner.nextLine();
                        while (!validInput) {
                            System.out.print("Enter user id: ");
                            userId = scanner.nextLine();
                            if (userId.isEmpty()) {
                                System.out.println("Please enter a valid user id.");
                            } else {
                                validInput = true;
                            }
                        }
                        getBookShelveInfo(userId);
                        break;

                    case 0:
                        System.out.println("Exiting...");
                        run = true;
                        System.exit(0);

                        break;
                    default:
                        System.out.println("Please choose a valid option (1-3)");
                        break;
                }
            } else {
                System.out.println("Please enter a valid number");
                scanner.next(); // discard invalid input
            }
        }
    }

    /*
    //CHECK
     */
    public static void searchWithOrdering(String searchTerm, int orderBy) throws IOException {
        //removes last
        if (historyList.size() == MAX_SEARCHES) {
            historyList.remove(historyList.size() - 1);
        }
        historyList.add(searchTerm);

        String orderByString = "";
        if (orderBy == 1) {
            orderByString = "relevance";
        } else if (orderBy == 2) {
            orderByString = "newest";
        }
        // Build the API call using the searchTerm and orderByString
        String url = "https://www.googleapis.com/books/v1/volumes?q=" + searchTerm + "&orderBy=" + orderByString;
        // Create a request
        Request request = new Request.Builder()
                .url(url)
                .build();

        OkHttpClient client = new OkHttpClient();

        // Execute the request
        Response response = client.newCall(request).execute();

        // Parse the response
        String jsonData = response.body().string();
        JSONObject json = new JSONObject(jsonData);
        JSONArray items = json.getJSONArray("items");
        for (int i = 0; i < items.length(); i++) {
            JSONObject itemjson = items.getJSONObject(i);
            Item item = objectMapper.readValue(itemjson.toString(), Item.class);
            System.out.println(item);
        }
    }

    /*
    // CHECK
     */
    public static void searchWithProjection(int projectionOption, String searchTerm) throws IOException {
        //removes last
        if (historyList.size() == MAX_SEARCHES) {
            historyList.remove(historyList.size() - 1);
        }
        historyList.add(searchTerm);
        // Set the base url
        String url = "https://www.googleapis.com/books/v1/volumes?q=";

        url += searchTerm;

        // Add the projection parameter to the url
        switch (projectionOption) {
            case 1:
                url += "&projection=full";
                break;
            case 2:
                url += "&projection=lite";
                break;
            default:
                System.out.println("Invalid input. Please enter a valid projection option (1 or 2).");
                return;
        }

        // Create a request
        Request request = new Request.Builder()
                .url(url)
                .build();

        OkHttpClient client = new OkHttpClient();

        // Execute the request
        Response response = client.newCall(request).execute();

        // Parse the response
        String jsonData = response.body().string();
        JSONObject json = new JSONObject(jsonData);
        JSONArray itemsjson = json.getJSONArray("items");

        System.out.println("SEARCHING");
        for (int i = 0; i < itemsjson.length(); i++) {
            JSONObject itemjson = itemsjson.getJSONObject(i);
            Item item = objectMapper.readValue(itemjson.toString(), Item.class);
            System.out.println(item);
        }
    }

    /*
    // CHECK
     */
    public static void searchWithPrintType(String searchTerm, int printType) throws IOException {
        //removes last
        if (historyList.size() == MAX_SEARCHES) {
            historyList.remove(historyList.size() - 1);
        }
        historyList.add(searchTerm);
        // Build the query string
        String url = "https://www.googleapis.com/books/v1/volumes?q=" + searchTerm + "&printType=" + printType;

        // Create a request
        Request request = new Request.Builder()
                .url(url)
                .build();

        OkHttpClient client = new OkHttpClient();

        // Execute the request
        Response response = client.newCall(request).execute();

        // Parse the response
        String jsonData = response.body().string();
        JSONObject json = new JSONObject(jsonData);
        JSONArray items = json.getJSONArray("items");
        for (int i = 0; i < items.length(); i++) {
            JSONObject itemjson = items.getJSONObject(i);
            Item item = objectMapper.readValue(itemjson.toString(), Item.class);
            System.out.println(item);
        }
    }

    /*
    // CHECK
     */
    private static void searchWithPagination(String searchTerm, int startIndex, int maxResults) throws IOException {
        //removes last
        if (historyList.size() == MAX_SEARCHES) {
            historyList.remove(historyList.size() - 1);
        }
        historyList.add(searchTerm);

        String query = URLEncoder.encode(searchTerm, StandardCharsets.UTF_8);

        // Form the URL
        String url = "https://www.googleapis.com/books/v1/volumes?q=" + searchTerm + "&startIndex=" + startIndex + "&maxResults=" + maxResults;

        // Create a request
        Request request = new Request.Builder()
                .url(url)
                .build();

        OkHttpClient client = new OkHttpClient();

        // Execute the request
        Response response = client.newCall(request).execute();

        // Parse the response
        String jsonData = response.body().string();
        JSONObject json = new JSONObject(jsonData);
        JSONArray items = json.getJSONArray("items");

        for (int i = 0; i < items.length(); i++) {
            JSONObject itemjson = items.getJSONObject(i);
            Item item = objectMapper.readValue(itemjson.toString(), Item.class);
            System.out.println(item);
        }
    }

    /*
    // CHECK
     */
    private static void searchWithFilter(int filterOption) throws IOException {
        String filter = "";
        switch (filterOption) {
            case 1:
                filter = "partial";
                break;
            case 2:
                filter = "full";
                break;
            case 3:
                filter = "free-ebooks";
                break;
            // add more cases as needed
        }
        String query = URLEncoder.encode("filter:" + filter, StandardCharsets.UTF_8);
        // Form the URL
        String url = URL_SEARCHIN + query;

        // Create a request
        Request request = new Request.Builder()
                .url(url)
                .build();

        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(request).execute();
        String jsonData = response.body().string();
        JSONObject json = new JSONObject(jsonData);
        JSONArray items = json.getJSONArray("items");

        for (int i = 0; i < items.length(); i++) {
            JSONObject itemjson = items.getJSONObject(i);
            System.out.println(itemjson);
            Item item = objectMapper.readValue(itemjson.toString(), Item.class);
            System.out.println(item);

        }
    }

    public static void getBookShelveInfo(String userId) throws IOException {
        OkHttpClient client = new OkHttpClient();

        // Set the userId for which you want to retrieve the bookshelves
        // Form the URL
        String url = "https://www.googleapis.com/books/v1/users/" + userId + "/bookshelves";

        // Create a request
        Request request = new Request.Builder()
                .url(url)
                .build();

        // Execute the request
        Response response = client.newCall(request).execute();

        // Parse the response
        String jsonData = response.body().string();
        System.out.println(jsonData);
        JSONObject json = new JSONObject(jsonData);
        if (json.has("items")) {
            JSONArray items = json.getJSONArray("items");
            for (int i = 0; i < items.length(); i++) {
                JSONObject bookshelf = items.getJSONObject(i);
                int bookshelfId = bookshelf.getInt("id");

                // to get volumes of this userId and bookself id
                retrieveVolumes(userId, bookshelfId);
            }
        } else {
            System.out.println("not found...");
            return;
        }

    }

    private static void retrieveVolumes(String userId, int bookshelfId) throws IOException {
        String url = "https://www.googleapis.com/books/v1/users/" + userId + "/bookshelves/" + bookshelfId + "/volumes";
        OkHttpClient client = new OkHttpClient();

        // Create a request
        Request request = new Request.Builder().url(url).build();

        Response response = client.newCall(request).execute();
        if (response.code() == 200) {
            // Parse the response
            String jsonData = response.body().string();
            try {
                JSONObject itemjson = new JSONObject(jsonData);

                // Retrieve and print the items
                if (itemjson.has("items")) {
                    Item item = objectMapper.readValue(itemjson.toString(), Item.class);
                    System.out.println(item);
                }
            } catch (JSONException e) {
                System.out.println("Error retrieving volumes: " + e.getMessage());
            }
        }
    }


    /*
    // CHECK
     */
    public static void searchVolumes(String searchTerm) throws IOException {

        //removes last
        if (historyList.size() == MAX_SEARCHES) {
            historyList.remove(historyList.size() - 1);
        }
        historyList.add(searchTerm);

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

        for (int i = 0; i < items.length(); i++) {
            JSONObject itemjson = items.getJSONObject(i);
            Item item = objectMapper.readValue(itemjson.toString(), Item.class);
            itemsList.add(item);
            System.out.println(item);
        }
    }

    /*
    // CHECK
     */
    private static void printLastSearches(String searchTerm) throws IOException {
        String query = URLEncoder.encode(searchTerm, StandardCharsets.UTF_8);
        String url = URL_SEARCHIN + query;
        Request request = new Request.Builder().url(url).build();
        OkHttpClient client = new OkHttpClient();

        // Execute the request
        Response response = client.newCall(request).execute();

        // Parse the response
        String jsonData = response.body().string();
        System.out.println(jsonData);
        JSONObject json = new JSONObject(jsonData);
        JSONArray items = json.getJSONArray("items");

        ArrayList<Item> itemsList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            JSONObject itemjson = items.getJSONObject(i);
            Item item = objectMapper.readValue(itemjson.toString(), Item.class);
            itemsList.add(item);
            System.out.println(item);
        }

    }
}
