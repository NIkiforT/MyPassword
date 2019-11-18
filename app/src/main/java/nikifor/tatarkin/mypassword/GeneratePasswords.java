package nikifor.tatarkin.mypassword;

import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneratePasswords {
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS = "0123456789";
    private static final String SYMBOLS = "!@#$%&*()_+-=[]|?><";

    public String generate(boolean useUpper, boolean useNumbers, boolean useSymbols, int length) {
        StringBuilder password = new StringBuilder(length);
        Random random = new Random();

        List<String> charCategories = new ArrayList<>(4);
        charCategories.add(LOWER);
        if (useUpper) {
            charCategories.add(UPPER);
        }
        if (useNumbers) {
            charCategories.add(NUMBERS);
        }
        if (useSymbols) {
            charCategories.add(SYMBOLS);
        }
        for (int i = 0; i < length; i++) {
            String charCategory = charCategories.get(random.nextInt(charCategories.size()));
            int position = random.nextInt(charCategory.length());
            password.append(charCategory.charAt(position));
        }

        return new String(password);
    }


}
