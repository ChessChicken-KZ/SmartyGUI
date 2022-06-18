package kz.chesschicken.configapi;

public enum EnumPropertyType {
    BOOLEAN(Boolean.class),
    STRING(String.class),
    INTEGER(Integer.class),
    DOUBLE(Double.class),
    FLOAT(Float.class),
    STRING_ARRAY(String.class);

    private final Class<?> extending;

    EnumPropertyType(Class<?> c) {
        this.extending = c;
    }

    public Class<?> getTypeClass() {
        return this.extending;
    }

    public static EnumPropertyType parseObject(Object o) {
        if(o instanceof Boolean)
            return EnumPropertyType.BOOLEAN;
        if(o instanceof String)
            return EnumPropertyType.STRING;
        if(o instanceof Integer || o instanceof Byte)
            return EnumPropertyType.INTEGER;
        if(o instanceof Double)
            return EnumPropertyType.DOUBLE;
        if(o instanceof Float)
            return EnumPropertyType.FLOAT;
        return EnumPropertyType.STRING;
    }
}
