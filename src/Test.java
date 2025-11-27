import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Test {
    public static void main(String[] args) {
        LocalDateTime dateObj = LocalDateTime.now();
        DateTimeFormatter formatDateObj = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formatDate = dateObj.format(formatDateObj);
        System.out.println(formatDate);
    }
}
