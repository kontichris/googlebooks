
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class ShowHistory {

    public static void showHistory() {
        //dimiourgia thread gia tin emfanisi apotelesmatwn se 3exwristo thread/parathuro apto menu
        Thread inlineThread = new Thread() {
            public void run() {
                JFrame frame = new JFrame();
                frame.setSize(500, 700);
                JTextArea tarea = new JTextArea();
                frame.add(tarea);
                tarea.setLineWrap(true);

                for (Item it : Main.historyList) {
                    tarea.append(it.toString() + "\n");
                    tarea.append("------------");
                }

                frame.setVisible(true);

            }
        };
        inlineThread.start();

    }
}
