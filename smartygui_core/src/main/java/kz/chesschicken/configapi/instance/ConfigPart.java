package kz.chesschicken.configapi.instance;

public class ConfigPart {

    private String commentary = "";
    private final String name;

    ConfigPart(String s) {
        this.name = s;
    }

    public void setCommentary(String s) {
        this.commentary = s;
    }

    public boolean isCommentaryPresent() {
        return this.commentary.length() > 0;
    }

    public String getCommentary() {
        return this.commentary;
    }

    public String getName() {
        return this.name;
    }
}
