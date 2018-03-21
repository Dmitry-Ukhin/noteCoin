import com.noteCoin.models.Transaction;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClassForTests {
    public static void main(String[] args) {
        String word = "123.2134";
        Pattern pattern = Pattern.compile(".?[0-9]+\\.[0-9]+.?");
        Matcher matcher = pattern.matcher(word);
        if (matcher.matches()){
            System.out.println("find");
        }else{
            Pattern pattern1 = Pattern.compile(".?[0-9]+");
            matcher = pattern1.matcher(word);
            if (matcher.matches()) {
                System.out.println("find");
            }else{
                System.out.println("not find");
            }
        }
    }
}
