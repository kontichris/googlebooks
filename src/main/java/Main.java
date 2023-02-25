
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Main {

    private static final String URL_SEARCHIN = "https://www.googleapis.com/books/v1/volumes?q=";
    public static ArrayList<String> historyList = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        do {
            String options[] = {"Αναζήτηση τόμων", "Διαχείριση ραφιών", "Έξοδος"};

            String selectedOption = (String) JOptionPane.showInputDialog(null, "Choose an option", "Dropdown Options", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (selectedOption != null) {
                System.out.println("Selected option: " + selectedOption);

                if (selectedOption == options[0]) {
                    String options1[] = {"Αναζήτηση τόμων με βάση κριτήρια","Προβολή πληροφοριών για συγκεκριμένο τόμο","Ιστορικό αναζητήσεων"};
                    String selectedOption1 = (String) JOptionPane.showInputDialog(null, "Choose an option", "Dropdown Options", JOptionPane.QUESTION_MESSAGE, null, options1, options1[0]);
                    if (selectedOption1 == options1[0]){
                    
                    }
                    else if (selectedOption1 == options1[1]){}
                    else if (selectedOption1 == options1[2]){}
                        
                } else if (selectedOption == options[1]) {
                    String options2[] = {"Αναζήτηση τόμων με βάση κριτήρια","Προβολή πληροφοριών για συγκεκριμένο τόμο","Ιστορικόαναζητήσεων"};
                    String selectedOption2 = (String) JOptionPane.showInputDialog(null, "Choose an option", "Dropdown Options", JOptionPane.QUESTION_MESSAGE, null, options2, options2[0]);
                    if (selectedOption2 == options2[0]){}
                    else if (selectedOption2 == options2[1]){}
                    else if (selectedOption2 == options2[2]){}

                } else if (selectedOption == options[2]) {
                    System.exit(0);
                }
            }
        } while (true);
    }

}
