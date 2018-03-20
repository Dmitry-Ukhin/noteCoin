import com.noteCoin.models.Transaction;

import java.util.Date;

public class ClassForTests {
    public static void main(String[] args) {
        Date date = new Date();
        Transaction transaction = new Transaction("incomes", 100, date, "test");
        Object object = transaction;
        System.out.println(object.getClass() == Transaction.class);
    }
}
