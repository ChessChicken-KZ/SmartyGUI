package kz.chesschicken.configapi.instance;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An abstract class of config object. Should be extended.
 * @author ChessChicken-KZ
 */
public abstract class ConfigPart {

    private String commentary = "";
    private final String name;

    ConfigPart(@NotNull String name) {
        this.name = name;
    }

    /**
     * The method sets the commentary string of the object by other value.
     * @param commentary Commentary string.
     */
    public void setCommentary(@Nullable String commentary) {
        this.commentary = commentary;
    }

    /**
     * The method returns true, if the commentary string of the object is not null.
     * Otherwise, returns false.
     * @return Existence of commentary as boolean.
     */
    public boolean isCommentaryPresent() {
        return this.commentary != null && this.commentary.length() > 0;
    }

    /**
     * The method for getting commentary string of the object.
     * @return Commentary string.
     */
    public @Nullable String getCommentary() {
        return this.commentary;
    }

    /**
     * The method for getting identifier of the object.
     * @return The identifier of the value.
     */
    public @NotNull String getName() {
        return this.name;
    }
}
