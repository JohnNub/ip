package duke.main;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Parser {
    Scanner in;

    public Parser() {
        in = new Scanner(System.in);
    }

    public UserInput parse(String input) {
        String userInput;

        userInput = sanitize(input);
        // Regex looks silly, you  have to escape the / so you type double \ to escape the \ used to escape the /
        String[] output = userInput.split("\\/");
        UserInput ui;
        Map<String, String> map = new HashMap<>();
        if (output.length == 1) {
            // There are no arguments supplied
            // Check if the input has any other arguments
            String tmp = output[0].trim();
            String cmd;
            if (tmp.indexOf(' ') != -1) {
                String arg = tmp.substring(tmp.indexOf(' ') + 1);
                cmd = tmp.substring(0, tmp.indexOf(' '));
                map.put("", arg);
            } else {
                cmd = tmp;
            }
            ui = new UserInput(cmd, map);
        } else {
            String base = output[0].trim();
            if (base.indexOf(' ') != -1) {
                String arg = base.substring(base.indexOf(' ') + 1);
                base = base.substring(0, base.indexOf(' '));
                map.put("", arg);
            }
            for (int i = 1; i < output.length; i++) {
                String tmp = output[i].trim();
                String key;
                String val;
                if(tmp.indexOf(' ')!=-1) {
                    val = tmp.substring(tmp.indexOf(' ') + 1);
                    key = tmp.substring(0, tmp.indexOf(' '));
                } else {
                    val = "";
                    key = tmp;
                }

                map.put(key.trim().toLowerCase(), val.trim());
            }
            ui = new UserInput(base, map);
        }
        return ui;

    }

    /**
     * TODO implement string sanitization
     * Cleans up the string to ensure that anything else is safe for the rest of the program to handle
     * @param s String to be sanitized
     * @return Sanitized string S
     */
    public String sanitize(String s) {
        return s;
    }
}
