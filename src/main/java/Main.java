
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Main {

    private static final String URL_SEARCHIN = "https://www.googleapis.com/books/v1/volumes?q=";
    public static ArrayList<Item> historyList = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        do {
            String options[] = {"Αναζήτηση τόμων", "Διαχείριση ραφιών", "Έξοδος"};

            String selectedOption = (String) JOptionPane.showInputDialog(null, "Choose an option", "Dropdown Options", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (selectedOption != null) {
                System.out.println("Selected option: " + selectedOption);

                if (selectedOption == options[0]) {
                    String options1[] = {"Αναζήτηση τόμων με βάση κριτήρια", "Προβολή πληροφοριών για συγκεκριμένο τόμο", "Ιστορικό αναζητήσεων"};
                    String selectedOption1 = (String) JOptionPane.showInputDialog(null, "Choose an option", "Dropdown Options", JOptionPane.QUESTION_MESSAGE, null, options1, options1[0]);
                    if (selectedOption1 == options1[0]) {
                        String searchTerm = JOptionPane.showInputDialog("Give term");
                        try {
                            PerformingSearch.searchVolumes(searchTerm);
                        } catch (IOException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (selectedOption1 == options1[1]) {
                        String volumeID = JOptionPane.showInputDialog("Give volume ID");
                        try {
                            GetVolumeInfo.getVolumeInfo(volumeID);
                        } catch (IOException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (selectedOption1 == options1[2]) {
                        ShowHistory.showHistory();
                    }

                } else if (selectedOption == options[1]) {
                    String options2[] = {"Ανάκτηση λίστας δημοσίων ραφιών χρήστη","Προβολή πληροφοριών για δημόσιο ράφι χρήστη", "Aνάκτηση περιεχομένων δημόσιουραφιού χρήστη"};
                    String selectedOption2 = (String) JOptionPane.showInputDialog(null, "Choose an option", "Dropdown Options", JOptionPane.QUESTION_MESSAGE, null, options2, options2[0]);
                    if (selectedOption2 == options2[0]) {
                        String userid = JOptionPane.showInputDialog("Give User ID");
                        try {
                            UserShelf.getBookShelveInfo(userid);
                        } catch (IOException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (selectedOption2 == options2[1]) {
                           
                        
                    } else if (selectedOption2 == options2[2]) {
                        String userid = JOptionPane.showInputDialog("Give User ID");
                        String shelfID = JOptionPane.showInputDialog("Give User shelf ID");
                        try {
                            UserShelfVolumes.retrieveVolumes(userid,Integer.parseInt(shelfID));
                        } catch (IOException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                } else if (selectedOption == options[2]) {
                    System.exit(0);
                }
            }
        } while (true);
    }

}
