package kz.chesschicken.smartygui.common.configapi.instance;

import java.util.ArrayList;
import java.util.List;

public class Group extends ConfigPart
{
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

    public Property getProperty(String propertyName)
    {
        for(Property p : properties)
        {
            if(p.getName().equalsIgnoreCase(propertyName))
                return p;
        }
        return null;
    }

    public List<Property> getProperties() {
        return this.properties;
    }

    public static Group createGroup(String s) {
        return new Group(s);
    }
}