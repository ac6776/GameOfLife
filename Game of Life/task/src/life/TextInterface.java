package life;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextInterface {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ResourceBundle rules = ResourceBundle.getBundle("patterns");
    private static final Map<String, Pattern> patterns;

    static {
        patterns = new HashMap<>();
        for (String s : rules.keySet()) {
            if (!s.endsWith(".replace")) {
                patterns.put(s, Pattern.compile(rules.getString(s), Pattern.CASE_INSENSITIVE));
            }
        }
    }

    public String readLine() {
        return scanner.nextLine().toLowerCase().trim();
    }

    public boolean isCorrect(String rule, String input) {
        return patterns.get(rule + ".isCorrect").matcher(input).matches();
    }

    public String applyRule(String rule, String input) {
        Pattern pattern = patterns.get(rule + ".pattern");
        if (pattern == null) {
            return input;
        }
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            return matcher.replaceFirst(rules.getString(rule + ".replace"));
        }
        return input;
    }
}
