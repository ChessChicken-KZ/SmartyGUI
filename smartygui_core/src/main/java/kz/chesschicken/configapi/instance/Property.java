package kz.chesschicken.configapi.instance;

import kz.chesschicken.configapi.EnumPropertyType;

public class Property extends ConfigPart {

    private EnumPropertyType type;
    private Object value;

    Property(String s, Object o) {
        super(s);
        value = o;
        type = EnumPropertyType.parseObject(o);
    }

    public void setOtherValue(Object o, boolean f) {
        value = o;
        if(f) type = EnumPropertyType.parseObject(o);
    }

    public Object getValue() {
        return value;
    }

    public EnumPropertyType getType() {
        return this.type;
    }

    public static Property createProperty(String s, Object instance) {
        return new Property(s, instance);
    }
}