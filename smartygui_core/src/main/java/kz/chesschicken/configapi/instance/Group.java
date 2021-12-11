package kz.chesschicken.configapi.instance;

import java.util.ArrayList;
import java.util.List;

public class Group extends ConfigPart {

    private final List<Property> properties;

    Group(String s) {
        super(s);
        properties = new ArrayList<>();
    }

    public void add(Property property) {
        properties.add(property);
    }

    public void add(Property property, String commentary) {
        property.setCommentary(commentary);
        properties.add(property);
    }

    public Property getProperty(String propertyName) {
        for(Property p : properties)
            if(p.getName().equalsIgnoreCase(propertyName))
                return p;
        return null;
    }

    /**
     * The method returns the list of {@link kz.chesschicken.configapi.instance.Property} of the object.
     * @return list of properties.
     */
    public List<Property> getProperties() {
        return this.properties;
    }

    /**
     * The method generates a {@link kz.chesschicken.configapi.instance.Group} instance.
     * @param name name of a group.
     * @return generated instance.
     */
    public static Group createGroup(String name) {
        return new Group(name);
    }
}