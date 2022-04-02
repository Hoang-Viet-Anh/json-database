package client;

import com.beust.jcommander.Parameter;

public class Command {
    @Parameter (names = "-t")
    public String type;
    @Parameter (names = "-k")
    public String key;
    @Parameter (names = "-v")
    public String value = null;
    @Parameter (names = "-in")
    public String fileName = null;
}
