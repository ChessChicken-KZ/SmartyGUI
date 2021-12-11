package kz.chesschicken.configapi.instance;

/**
 * An abstract class of config object. Should be extended.
 * @author ChessChicken-KZ
 */
public abstract class ConfigPart {

    private String commentary = "";
    private final String name;

    ConfigPart(String name) {
        this.name = name;
    }

    /**
     * The method sets the commentary string of the object by other value.
     * @param commentary commentary string.
     */
    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    /**
     * The method returns true, if the commentary string of the object is not null.
     * Otherwise, returns false.
     * @return existence of commentary as boolean.
     */
    public boolean isCommentaryPresent() {
        return this.commentary.length() > 0;
    }

    /**
     * The method for getting commentary string of the object.
     * @return commentary string.
     */
    public String getCommentary() {
        return this.commentary;
    }

    /**
     * The method for getting identifier of the object.
     * @return the identifier of the value.
     */
    public String getName() {
        return this.name;
    }
}
