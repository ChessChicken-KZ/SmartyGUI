package kz.chesschicken.smartygui.commonloader;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Additional code to make these kind of mods properties more clear and cool.
 * TODO: More description.
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface ModDescription {
	
	String name() default "DEF_MOD_NAME";
	
	String description() default "DEF_MOD_DESC";
	
	String version() default "DEF_MOD_VERSION";
	
	String icon() default "/pack.png";
}
