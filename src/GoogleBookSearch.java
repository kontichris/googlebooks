import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    private static final String SEARCH_API_URL  = "https://www.googleapis.com/books/v1/volumes?q=";
    private static final String VOLUME_API_URL = "https://www.googleapis.com/books/v1/volumes/";
    private static LinkedList<String> lastSearches = new LinkedList<>();
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
    	    System.out.println("9) Get a list of another user's public bookshelves and the volumes");
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
	    	        	
	    	            // Call the search history method
	    	        	if (lastSearches.isEmpty()) {
	    	                System.out.print("No history");
	    	        	} else {
		    	        	for (int i=0; i<lastSearches.size(); i++) {
		    	        		printLastSearches(lastSearches.get(i));
		    	        	}
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
			    	            System.out.print("Enter 1 for all, 2 for book, 3 for magazines.");
			    	             printType = scanner.nextInt();
		    	                if (printType >= 1 && printType <= 3 ) {
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
		    	                if (projectionOption >= 1 && projectionOption <= 2 ) {
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
		    	                if (orderingOption >= 1 && orderingOption <= 2 ) {
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
		    	            if(userId.isEmpty()){
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
    
    public static void searchWithOrdering(String searchTerm, int orderBy) throws IOException {
        addToLastSearches(searchTerm);

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
        	 JSONObject item = items.getJSONObject(i);
             System.out.println("Result " + (i+1));
        	 if (item.has("kind")) {
                 String kind = item.getString("kind");
                 if(kind!=null && !kind.isEmpty())
                     System.out.println("Kind: " + kind);
             }
  
             if (item.has("id")) {
                 String id = item.getString("id");
                 if(id!=null && !id.isEmpty())
                     System.out.println("id: " + id);
             }
             if (item.has("etag")) {
                 String etag = item.getString("etag");
                 if(etag!=null && !etag.isEmpty())
                     System.out.println("Etag: " + etag);
             }

             
             if (item.has("selfLink")) {
                 String selfLink = item.getString("selfLink");
                 if(selfLink!=null && !selfLink.isEmpty())
                     System.out.println("SelfLink: " + selfLink);
             }
             
             
            JSONObject volumeInfo = items.getJSONObject(i).getJSONObject("volumeInfo");
            String title = volumeInfo.optString("title", "N/A");
            JSONArray authors = volumeInfo.optJSONArray("authors");
            String author = "N/A";
            if (authors != null) {
                author = authors.getString(0);
            }
            System.out.println("Title: " + title);
            System.out.println("Author: " + author);
        }
    }

    
    	public static void searchWithProjection(int projectionOption, String searchTerm) throws IOException {
            addToLastSearches(searchTerm);
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
               // JSONObject item = itemsjson.getJSONObject(i);
                
               // Item itemobject = objectMapper.readValue(itemsjson, Item.class);
                //System.out.println("done");
                
            	
//                 System.out.println("Result " + (i+1));
//            	 if (item.has("kind")) {
//                     String kind = item.getString("kind");
//                     if(kind!=null && !kind.isEmpty())
//                         System.out.println("Kind: " + kind);
//                 }
//      
//                 if (item.has("id")) {
//                     String id = item.getString("id");
//                     if(id!=null && !id.isEmpty())
//                         System.out.println("id: " + id);
//                 }
//                 if (item.has("etag")) {
//                     String etag = item.getString("etag");
//                     if(etag!=null && !etag.isEmpty())
//                         System.out.println("Etag: " + etag);
//                 }
//
//                 
//                 if (item.has("selfLink")) {
//                     String selfLink = item.getString("selfLink");
//                     if(selfLink!=null && !selfLink.isEmpty())
//                         System.out.println("SelfLink: " + selfLink);
//                 }
                 

//                JSONObject volumeInfo = items.getJSONObject(i).getJSONObject("volumeInfo");
//                String title = volumeInfo.optString("title", "N/A");
//                JSONArray authors = volumeInfo.optJSONArray("authors");
//                String author = "N/A";
//                if (authors != null) {
//                    author = authors.getString(0);
//                }
//                System.out.println("Title: " + title);
//                System.out.println("Author: " + author);
            }
        }

    	
    	
    public static void searchWithPrintType(String searchTerm, int printType) throws IOException {
        addToLastSearches(searchTerm);

    	String printTypeString = "";
        switch (printType) {
            case 1:
                printTypeString = "all";
                break;
            case 2:
                printTypeString = "books";
                break;
            case 3:
                printTypeString = "magazines";
                break;
            default:
                System.out.println("Invalid input. Please enter a valid print type option (1-3).");
                return;
        }

        // Build the query string
        String url = "https://www.googleapis.com/books/v1/volumes?q=" + searchTerm + "&printType=" + printTypeString;

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
        	 JSONObject item = items.getJSONObject(i);
             System.out.println("Result " + (i+1));
        	 if (item.has("kind")) {
                 String kind = item.getString("kind");
                 if(kind!=null && !kind.isEmpty())
                     System.out.println("Kind: " + kind);
             }
  
             if (item.has("id")) {
                 String id = item.getString("id");
                 if(id!=null && !id.isEmpty())
                     System.out.println("id: " + id);
             }
             if (item.has("etag")) {
                 String etag = item.getString("etag");
                 if(etag!=null && !etag.isEmpty())
                     System.out.println("Etag: " + etag);
             }

             
             if (item.has("selfLink")) {
                 String selfLink = item.getString("selfLink");
                 if(selfLink!=null && !selfLink.isEmpty())
                     System.out.println("SelfLink: " + selfLink);
             }
             
             
            JSONObject volumeInfo = items.getJSONObject(i).getJSONObject("volumeInfo");
            String title = volumeInfo.optString("title", "N/A");
            JSONArray authors = volumeInfo.optJSONArray("authors");
            String author = "N/A";
            if (authors != null) {
                author = authors.getString(0);
            }
            System.out.println("Title: " + title);
            System.out.println("Author: " + author);
        }
    }

    
    private static void searchWithPagination(String searchTerm, int startIndex, int maxResults) throws IOException {
        addToLastSearches(searchTerm);

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
        	 JSONObject item = items.getJSONObject(i);
             System.out.println("Result " + (i+1));
        	 if (item.has("kind")) {
                 String kind = item.getString("kind");
                 if(kind!=null && !kind.isEmpty())
                     System.out.println("Kind: " + kind);
             }
  
             if (item.has("id")) {
                 String id = item.getString("id");
                 if(id!=null && !id.isEmpty())
                     System.out.println("id: " + id);
             }
             if (item.has("etag")) {
                 String etag = item.getString("etag");
                 if(etag!=null && !etag.isEmpty())
                     System.out.println("Etag: " + etag);
             }

             
             if (item.has("selfLink")) {
                 String selfLink = item.getString("selfLink");
                 if(selfLink!=null && !selfLink.isEmpty())
                     System.out.println("SelfLink: " + selfLink);
             }
             
             
            JSONObject volumeInfo = items.getJSONObject(i).getJSONObject("volumeInfo");
            String title = volumeInfo.optString("title", "N/A");
            JSONArray authors = volumeInfo.optJSONArray("authors");
            String author = "N/A";
            if (authors != null) {
                author = authors.getString(0);
            }
            System.out.println("Title: " + title);
            System.out.println("Author: " + author);
        }
    }
           

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
    	    String url = SEARCH_API_URL + query;

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
            JSONObject item = items.getJSONObject(i);

        	 if (item.has("kind")) {
                 String kind = item.getString("kind");
                 if(kind!=null && !kind.isEmpty())
                     System.out.println("Kind: " + kind);
             }
  
             if (item.has("id")) {
                 String id = item.getString("id");
                 if(id!=null && !id.isEmpty())
                     System.out.println("id: " + id);
             }
             if (item.has("etag")) {
                 String etag = item.getString("etag");
                 if(etag!=null && !etag.isEmpty())
                     System.out.println("Etag: " + etag);
             }

             
             if (item.has("selfLink")) {
                 String selfLink = item.getString("selfLink");
                 if(selfLink!=null && !selfLink.isEmpty())
                     System.out.println("SelfLink: " + selfLink);
             }
             
             
            JSONObject volumeInfo = items.getJSONObject(i).getJSONObject("volumeInfo");
            String title = volumeInfo.optString("title", "N/A");
            JSONArray authors = volumeInfo.optJSONArray("authors");
            String author = "N/A";
            if (authors != null) {
                author = authors.getString(0);
            }
            System.out.println("Title: " + title);
            System.out.println("Author: " + author);
        }
    }

    
    
    public static  void getBookShelveInfo(String userId) throws IOException {
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
    	JSONObject json = new JSONObject(jsonData);
    	JSONArray items = json.getJSONArray("items");
    	for (int i = 0; i < items.length(); i++) {
    	    JSONObject bookshelf = items.getJSONObject(i);
            int bookshelfId = bookshelf.getInt("id");
    	    String title = bookshelf.getString("title");
    	    System.out.println("Bookshelf ID: " + bookshelfId);
    	    System.out.println("Title: " + title);
    	    if (bookshelf.has("kind")) {
    	        String kind = bookshelf.getString("kind");
    	        System.out.println("Kind: " + kind);
    	    }
    	    if (bookshelf.has("selfLink")) {
    	        String selfLink = bookshelf.getString("selfLink");
    	        System.out.println("Self Link: " + selfLink);
    	    }
    	    if (bookshelf.has("description")) {
    	        String description = bookshelf.getString("description");
    	        System.out.println("Description: " + description);
    	    }
    	    if (bookshelf.has("access")) {
    	        String access = bookshelf.getString("access");
    	        System.out.println("Access: " + access);
    	    }
    	    if (bookshelf.has("updated")) {
    	        String updated = bookshelf.getString("updated");
    	        System.out.println("Updated: " + updated);
    	    }
    	    if (bookshelf.has("created")) {
    	        String created = bookshelf.getString("created");
    	        System.out.println("Created: " + created);
    	    }
    	    if (bookshelf.has("volumeCount")) {
    	        int volumeCount = bookshelf.getInt("volumeCount");
    	        System.out.println("Volume Count: " + volumeCount);
    	    }
    	    if (bookshelf.has("volumeLastUpdated")) {
    	        String volumeLastUpdated = bookshelf.getString("volumeLastUpdated");
    	        System.out.println("Volume Last Updated: " + volumeLastUpdated);
    	    }
    	    
    	    // to get volumes of this userId and bookself id
            retrieveVolumes(userId, bookshelfId);
    	}

    }
    
    private static  void retrieveVolumes(String userId, int bookshelfId) throws IOException {
            String url = "https://www.googleapis.com/books/v1/users/"+userId+"/bookshelves/"+bookshelfId+"/volumes";
            OkHttpClient client = new OkHttpClient();

            // Create a request
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            if (response.code() == 200) {
                // Parse the response
                String jsonData = response.body().string();
                try{
	                JSONObject json = new JSONObject(jsonData);
	    	
	    	        // Retrieve the kind
	    	        String kind = json.getString("kind");
	    	        if(kind!=null && !kind.isEmpty())
	    	            System.out.println("Kind: " + kind);
	    	
	    	        // Retrieve and print the items
	    	        if (json.has("items")) {
		    	        JSONArray items = json.getJSONArray("items");

		    	        // Iterate through the items array\
	    	        	 for (int i = 0; i < items.length(); i++) {
	 	    	            JSONObject item = items.getJSONObject(i);
	 	
	 	    	            // Retrieve and print the item's kind, id, etag, and selfLink
	 	    	            String itemKind = item.getString("kind");
	 	    	            String itemId = item.getString("id");
	 	    	            String itemEtag = item.getString("etag");
	 	    	            String itemSelfLink = item.getString("selfLink");
	 	    	            System.out.println("Item " + i + ":");
	 	    	            System.out.println("Kind: " + itemKind);
	 	    	            System.out.println("Id: " + itemId);
	 	    	            System.out.println("Etag: " + itemEtag);
	 	    	            System.out.println("SelfLink: " + itemSelfLink);
	 	
	 	    	            // Check if volumeInfo exists and retrieve its values
	 	    	            if (item.has("volumeInfo")) {
	 	    	                JSONObject volumeInfo = item.getJSONObject("volumeInfo");
	 	    	                if (volumeInfo.has("title")) {
	 	    	                    String title = volumeInfo.getString("title");
	 	    	                    System.out.println("Title: " + title);
	 	    	                }
	 	    	                if (volumeInfo.has("authors")) {
	 	    	                    JSONArray authors = volumeInfo.getJSONArray("authors");
	 	    	                    for (int j = 0; j < authors.length(); j++) {
	 	    	                        System.out.println("Author " + j + ": " + authors.getString(j));
	 	    	                    }
	 	    	                }
	 	    	                if (volumeInfo.has("publisher")) {
	 	    	                    String publisher = volumeInfo.getString("publisher");
	 	    	                    System.out.println("Publisher: " + publisher);
	 	    	                }
	 	    	                if (volumeInfo.has("publishedDate")) {
	 	    	                    String publishedDate = volumeInfo.getString("publishedDate");
	 	    	                    System.out.println("Published Date: " + publishedDate);
	 	    	                }
	 	    	            }
	 	    	        }
	    	        } else {
	    	            System.out.println("No items");
	    	        }
                }catch(JSONException e){
                    System.out.println("Error retrieving volumes: " + e.getMessage());
                }
            } else {
                System.out.println("Invalid volumeId. Please enter a valid volumeId.");
            }
    }

    public static  void getVolumeInfo(String volumeId) throws IOException {
        // Create a client
        OkHttpClient client = new OkHttpClient();

        // Form the URL
        String url = "https://www.googleapis.com/books/v1/volumes/" + volumeId;

        // Create a request
        Request request = new Request.Builder()
                .url(url)
                .build();
        
        VolumeInfo vinfo = new VolumeInfo();

        // Execute the request
        Response response = client.newCall(request).execute();
        if (response.code() == 200) {
            // Parse the response
            String jsonData = response.body().string();
            JSONObject json = new JSONObject(jsonData);

            // Retrieve and print the kind,id, etag, and selfLink
            if (json.has("kind")) {
                String kind = json.getString("kind");
                vinfo.setKind(kind);
            }
 
            if (json.has("id")) {
                String id = json.getString("id");
                vinfo.setId(id);
            }
            if (json.has("etag")) {
                String etag = json.getString("etag");
                vinfo.setEtag(etag);
            }

            
            if (json.has("selfLink")) {
                String selfLink = json.getString("selfLink");
                vinfo.setSelfLink(selfLink);
            }
            
            
            VolumeAccessInfo vaccessInfo = new VolumeAccessInfo();

          
            if (json.has("accessInfo")) {
	            JSONObject accessInfo = json.optJSONObject("accessInfo");
	            if (accessInfo != null) {
	                String country = accessInfo.optString("country", "N/A");
	                vaccessInfo.setCountry(country);
	                
	                String viewability = accessInfo.optString("viewability", "N/A");
	                vaccessInfo.setViewability(viewability);
	                
	                Boolean embeddable = accessInfo.optBoolean("embeddable", false);
	                vaccessInfo.setEmbeddable(embeddable);
	                
	                Boolean publicDomain = accessInfo.optBoolean("publicDomain", false);
	                vaccessInfo.setPublicDomain(publicDomain);
	                
	                String textToSpeechPermission = accessInfo.optString("textToSpeechPermission", "N/A");
	                vaccessInfo.setTextToSpeechPermission(textToSpeechPermission);
		               
	                
	                JSONObject epub = accessInfo.optJSONObject("epub");
	                if (epub != null) {
	                    Boolean isAvailable = epub.optBoolean("isAvailable", false);
	                    vaccessInfo.setIsAvailable(isAvailable);
	                    
	                    String acsTokenLink = epub.optString("acsTokenLink", "N/A");
	                    vaccessInfo.setAcsTokenLink(acsTokenLink);
	                }
	                
	                JSONObject pdf = accessInfo.optJSONObject("pdf");
	                if (pdf != null) {
	                    Boolean pdfisAvailable = pdf.optBoolean("isAvailable", false);
	                    vaccessInfo.setPdfisAvailable(pdfisAvailable);
	                }
	                
	                String accessViewStatus = accessInfo.optString("accessViewStatus", "N/A");
	                vaccessInfo.setAccessViewStatus(accessViewStatus);
	            }
            }
            
            VolumeSaleInfo vSaleInfo = new VolumeSaleInfo();
            
            if (json.has("saleInfo")) {
	            JSONObject saleInfo = json.optJSONObject("saleInfo");
	            if (saleInfo != null) {
		                String country = saleInfo.optString("country", "N/A");
	                    System.out.println("Sale info: " );

		                System.out.println("Country: " + country);
		                vSaleInfo.setCountry(country);
	
		                String saleability = saleInfo.optString("saleability", "N/A");
		                vSaleInfo.setSaleability(saleability);
	
		                Boolean isEbook = saleInfo.optBoolean("isEbook", false);
		                vSaleInfo.setIsEbook(isEbook);
		                
		                JSONObject listPrice = saleInfo.optJSONObject("listPrice");
		                if (listPrice != null) {
		                    int amount = listPrice.optInt("amount", 0);
		                    System.out.println("List Price Amount: " + amount);
		                    vSaleInfo.setAmount(amount);
	
		                    String currencyCode = listPrice.optString("currencyCode", "N/A");
		                    vSaleInfo.setCurrencyCode(currencyCode);
		                }
		                JSONObject retailPrice = saleInfo.optJSONObject("retailPrice");
		                if (retailPrice != null) {
		                    int amount = retailPrice.optInt("amount", 0);
		                    vSaleInfo.setAmount(amount);
	
		                    String currencyCode = retailPrice.optString("currencyCode", "N/A");
		                    vSaleInfo.setCurrencyCode(currencyCode);
		                }
		                String buyLink = saleInfo.optString("buyLink", "N/A");
		                vSaleInfo.setBuyLink(buyLink);
		            }         
            }
            
            vinfo.setVSaleInfo(vSaleInfo);
            
            // Retrieve and print the volumeInfo
            if (json.has("volumeInfo")) {
            	JSONObject volumeInfo = json.getJSONObject("volumeInfo");
            	

                JSONArray categories = volumeInfo.optJSONArray("categories");
                ArrayList<String> categoriesList = new ArrayList<>();
                if (categories != null) {
                    for (int j = 0; j < categories.length(); j++) {
                    	categoriesList.add(categories.getString(j));
                    	vinfo.setCategoriesList(categoriesList);
                }
                if (volumeInfo.has("authors")) {
                    System.out.println("Volume info: " );
                	String title = volumeInfo.optString("title", "N/A");
                    vinfo.setTitle(title);
                    
                }
                
                
                ArrayList<String> authorsList = new ArrayList<>();
                if (volumeInfo.has("authors")) {
                    JSONArray authors = volumeInfo.optJSONArray("authors");
                    String author = "N/A";
                    if (authors != null) {
                        for (int i = 0; i < authors.length(); i++) {
                        	authorsList.add(authors.getString(i));
                        	
                        }
                    }
                    vinfo.setAuthorsList(authorsList);
                }


                String publisher = "N/A";
                if (volumeInfo.has("publisher")) {
                    publisher = volumeInfo.getString("publisher");
                    vinfo.setPublisher(publisher);
                }   
                String publishedDate = "N/A";
                if (volumeInfo.has("publishedDate")) {
                    publishedDate = volumeInfo.getString("publishedDate");
                    vinfo.setPublishedDate(publishedDate);
                }
    	            String description = "N/A";
    	            if (volumeInfo.has("description")) {
    	            	description = volumeInfo.getString("description");
    	            	vinfo.setDescription(description);

    	            }
    	            
    	            if (volumeInfo.has("industryIdentifiers")) {
    	            	JSONArray  industryIdentifiers = volumeInfo.optJSONArray("industryIdentifiers");
    		            String isbn = "N/A";
    		            if (industryIdentifiers != null) {
    		                for (int j = 0; j < industryIdentifiers.length(); j++) {
    		                	 JSONObject identifier = industryIdentifiers.getJSONObject(j);
    		                     
    		                     if (identifier.has("type")) {
    			                     String type = identifier.getString("type");
    			                     if (type.equals("ISBN_13")) {
    				                     if (identifier.has("identifier")) {
    				                         isbn = identifier.getString("identifier");
    				                     }
    			                         break;
    			                     }
    		                     }
    		                }
    		            }
    	            }
    	            if (volumeInfo.has("pageCount")) {
    		            int pageCount = volumeInfo.getInt("pageCount");
    		            System.out.println("page count: " + pageCount);
    		            vinfo.setPageCount(pageCount);
    	            }

    	            VolumeDimension dim = new VolumeDimension();
    	            if (volumeInfo.has("dimensions")) {
    	            JSONObject dimensions = volumeInfo.optJSONObject("dimensions");
    		            if (dimensions != null) {
    		                    System.out.println("Dimensions:");
    		    	            if (dimensions.has("height")) {
    			                    String height = dimensions.getString("height");
    			                    dim.setHeight(height);
    		    	            }
    		    	            if (dimensions.has("width")) {
    			                    String width = dimensions.getString("width");
    			                    dim.setWidth(width);
    		    	            }
    		    	            if (dimensions.has("thickness")) {
    			                    String thickness = dimensions.getString("thickness");
    			                    dim.setThickness(thickness);
    		    	            }
    		            }
    	           }  
    	            vinfo.setVolumeDimension(dim);
    	            
    	            if (volumeInfo.has("printType")) {
    		            String printType = volumeInfo.getString("printType");
    		            vinfo.setPrintType(printType);
    	            }
    	            
    	            if (volumeInfo.has("averageRating")) {
    		            int averageRating = volumeInfo.getInt("averageRating");
    		            vinfo.setAverageRating(averageRating);
    	            }

    	            if (volumeInfo.has("ratingsCount")) {
    		            int ratingsCount = volumeInfo.getInt("ratingsCount");
    		            vinfo.setRatingsCount(ratingsCount);

    	            }
    	            if (volumeInfo.has("contentVersion")) {
    		            String contentVersion = volumeInfo.getString("contentVersion");
    		            vinfo.setContentVersion(contentVersion);
    	            }
    	            if (volumeInfo.has("language")) {
    		            String language = volumeInfo.getString("language");
    		            vinfo.setLanguage(language);
    	            }

    	            if (volumeInfo.has("infoLink")) {
    		            String infoLink = volumeInfo.getString("infoLink");
    		            vinfo.setInfoLink(infoLink);
    	            }
    	            if (volumeInfo.has("canonicalVolumeLink")) {
    		            String canonicalVolumeLink = volumeInfo.getString("canonicalVolumeLink");
    		            vinfo.setCanonicalVolumeLink(canonicalVolumeLink);
    	            }
            }	   
    	}
            vinfo.setVaccessInfo(vaccessInfo);
        } else {
            System.out.println("Invalid volumeId. Please enter a valid volumeId.");
        }
        
       
        
    }
  
    
    private static void addToLastSearches(String searchTerm) {
        if (lastSearches.size() == MAX_SEARCHES) {
            lastSearches.removeLast();
        }
        lastSearches.addFirst(searchTerm);
    }
    
    public static void searchVolumes(String searchTerm) throws IOException {
        addToLastSearches(searchTerm);
    	// Create a client
        OkHttpClient client = new OkHttpClient();
        
        // Create a query
        String query = URLEncoder.encode(searchTerm, StandardCharsets.UTF_8);

        // Form the URL
        String url = SEARCH_API_URL  + query;

        // Create a request
        Request request = new Request.Builder()
                .url(url)
                .build();

        // Execute the request
        Response response = client.newCall(request).execute();
        
        ArrayList<Volume>  volumeList = new ArrayList<>();

        // Parse the response
        String jsonData = response.body().string();
        JSONObject json = new JSONObject(jsonData);
        String kind = json.getString("kind");
        if(kind!=null && !kind.isEmpty())
            System.out.println("Kind: " + kind);
        JSONArray items = json.getJSONArray("items");
        for (int i = 0; i < items.length(); i++) {
    	    JSONObject item = items.getJSONObject(i);

            JSONObject volumeInfo = items.getJSONObject(i).getJSONObject("volumeInfo");
            String title = volumeInfo.optString("title", "N/A");
            JSONArray authors = volumeInfo.optJSONArray("authors");
            String author = "N/A";
            if (authors != null) {
                author = authors.getString(0);
            }
            String publisher = volumeInfo.optString("publisher", "N/A");
            JSONArray industryIdentifiers = volumeInfo.optJSONArray("industryIdentifiers");
            String isbn = "N/A";
            if (industryIdentifiers != null) {
                for (int j = 0; j < industryIdentifiers.length(); j++) {
                    JSONObject identifier = industryIdentifiers.getJSONObject(j);
                    String type = identifier.getString("type");
                    if (type.equals("ISBN_13")) {
                        isbn = identifier.getString("identifier");
                        break;
                    }
                }
            }
            String fileFormat = "N/A";
            if (volumeInfo.has("fileFormat")) {
                fileFormat = volumeInfo.getString("fileFormat");
            }
            String subject = "N/A";
            if (volumeInfo.has("subject")) {
                fileFormat = volumeInfo.getString("subject");
            }
            String preview = "N/A";
            if (volumeInfo.has("previewLink")) {
                preview = volumeInfo.getString("previewLink");
            }
            String lccn = "N/A";
            if (volumeInfo.has("lccn")) {
            	lccn = volumeInfo.getString("lccn");
            }
            String oclc = "N/A";
            if (volumeInfo.has("oclc")) {
            	oclc = volumeInfo.getString("oclc");
            }
            
            
           
            
            System.out.println("Volume: " + i);
    	    if (item.has("kind")) {
    	        String kind1 = item.getString("kind");
    	        System.out.println("Kind: " + kind1);
    	    }
    	    if (item.has("id")) {
    	        String id = item.getString("id");
    	        System.out.println("id:" + id);
    	    }
    	    if (item.has("etag")) {
    	        String etag = item.getString("etag");
    	        System.out.println("etag: " + etag);
    	    }
    	    if (item.has("selfLink")) {
    	        String selfLink = item.getString("selfLink");
    	        System.out.println("selfLink: " + selfLink);
    	    }

    	    Volume v = new Volume(title, author, publisher, isbn, fileFormat, preview, subject, lccn,oclc);

    	    System.out.println(v);
            volumeList.add(v);
        }
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number corresponding to the volume you want to select:");
        int selection = scanner.nextInt();
        JSONObject selectedVolume = items.getJSONObject(selection);
        String volumeId = selectedVolume.get("id").toString();
        getVolumeInfo(volumeId);
    }
    	
    private static void printLastSearches(String searchTerm) throws IOException { 
	    String query = URLEncoder.encode(searchTerm, StandardCharsets.UTF_8);
	
	    // Form the URL
	    String url = SEARCH_API_URL  + query;
	
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
	    
	    ArrayList<Volume> volumeList = new ArrayList<>();
	    for (int i = 0; i < items.length(); i++) {
	        JSONObject volumeInfo = items.getJSONObject(i).getJSONObject("volumeInfo");
	        JSONObject item = items.getJSONObject(i);
	        String volumeId = item.getString("id");
	        getVolumeInfo(volumeId);
	        String title = volumeInfo.optString("title", "N/A");
	        JSONArray authors = volumeInfo.optJSONArray("authors");
	        String author = "N/A";
	        if (volumeInfo.has("authors")) {
		        if (authors != null) {
		            author = authors.getString(0);
		        }
	        }

	        String publisher = volumeInfo.optString("publisher", "N/A");
	        JSONArray industryIdentifiers = volumeInfo.optJSONArray("industryIdentifiers");
	        String isbn = "N/A";
	        if (industryIdentifiers != null) {
	            for (int j = 0; j < industryIdentifiers.length(); j++) {
	                JSONObject identifier = industryIdentifiers.getJSONObject(j);
	                String type = identifier.getString("type");
	                if (type.equals("ISBN_13")) {
	                    isbn = identifier.getString("identifier");
	                    break;
	                }
	            }
	        }
	        String fileFormat = "N/A";
	        if (volumeInfo.has("fileFormat")) {
	            fileFormat = volumeInfo.getString("fileFormat");
	        }
	        String subject = "N/A";
	        if (volumeInfo.has("subject")) {
	            fileFormat = volumeInfo.getString("subject");
	        }
	        String preview = "N/A";
	        if (volumeInfo.has("previewLink")) {
	            preview = volumeInfo.getString("previewLink");
	        }
	        String lccn = "N/A";
	        if (volumeInfo.has("lccn")) {
	        	lccn = volumeInfo.getString("lccn");
	        }
	        String oclc = "N/A";
	        if (volumeInfo.has("oclc")) {
	        	oclc = volumeInfo.getString("oclc");
	        }
	        
	        Volume v = new Volume(title, author, publisher, isbn, fileFormat, preview, subject, lccn,oclc);

    	    System.out.println(v);
            volumeList.add(v);
	    }
    }
}
