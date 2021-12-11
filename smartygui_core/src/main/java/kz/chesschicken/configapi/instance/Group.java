package kz.chesschicken.configapi.instance;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Group - a couple of {@link kz.chesschicken.configapi.instance.Property} in one phrase.
 * @author ChessChicken-KZ
 */
public class Group extends ConfigPart {

    private final List<Property> properties;

    Group(@NotNull String s) {
        super(s);
        properties = new ArrayList<>();
    }

    /**
     * The method adds a specific {@link kz.chesschicken.configapi.instance.Property} instance to the group's list.
     * @param property Property instance.
     */
    public void add(@NotNull Property property) {
        properties.add(property);
    }

    /**
     * The method adds a specific {@link kz.chesschicken.configapi.instance.Property} instance, also attaching commentary string with it.
     * @param property Property instance.
     * @param commentary Commentary string.
     */
    public void add(@NotNull Property property, @Nullable String commentary) {
        property.setCommentary(commentary);
        properties.add(property);
    }

    /**
     * The method returns the specific {@link kz.chesschicken.configapi.instance.Property} instance from the group's list.
     * @param propertyName Property name.
     * @return Property instance, if it exists in the list.
     */
    public @Nullable Property getProperty(@NotNull String propertyName) {
        for(Property p : properties)
            if(p.getName().equalsIgnoreCase(propertyName))
                return p;
        return null;
    }

    /**
     * The method returns the list of {@link kz.chesschicken.configapi.instance.Property} of the object.
     * @return The list of properties.
     */
    public @NotNull List<Property> getProperties() {
        return this.properties;
    }

    /**
     * The method generates a {@link kz.chesschicken.configapi.instance.Group} instance.
     * @param name Group name.
     * @return Generated instance.
     */
    public static @NotNull Group createGroup(@NotNull String name) {
        return new Group(name);
    }
}