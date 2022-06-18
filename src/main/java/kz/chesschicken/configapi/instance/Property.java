package kz.chesschicken.configapi.instance;

import kz.chesschicken.configapi.EnumPropertyType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Property - specific object, that handles objects inside config.
 * @author ChessChicken-KZ
 */
public class Property extends ConfigPart {

    private EnumPropertyType type;
    private Object value;

    Property(@NotNull String s, @Nullable Object o) {
        super(s);
        value = o;
        type = EnumPropertyType.parseObject(o);
    }

    /**
     * The method allows exchanging current value with the new one.
     * @param o New value instance.
     * @param f Should be rechecked? Recommended being always true.
     */
    public void setOtherValue(Object o, boolean f) {
        value = o;
        if(f) type = EnumPropertyType.parseObject(o);
    }

    /**
     * The method returns the current instance of property value.
     * @return Instance of the current property value.
     */
    public Object getValue() {
        return value;
    }

    /**
     * The method returns the current type of property value.
     * @return Type of the current property value.
     */
    public EnumPropertyType getType() {
        return this.type;
    }

    /**
     * The method generates a {@link kz.chesschicken.configapi.instance.Property} instance.
     * @param name Property name.
     * @param instance Object instance.
     * @return Generated property instance.
     */
    public static Property createProperty(String name, Object instance) {
        return new Property(name, instance);
    }
}