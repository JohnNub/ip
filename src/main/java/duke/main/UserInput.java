package duke.main;

import java.util.Map;

public class UserInput {
    private String command;
    private Map<String, String> args;

    public UserInput(String c, Map<String, String> a) {
        command = c;
        args = a;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Map<String, String> getArgs() {
        return args;
    }

    public void setArgs(Map<String, String> args) {
        this.args = args;
    }

    public String getArg(String s){
        return args.get(s);
    }

    public int getNumArgs(){
        return args.size();
    }
}
