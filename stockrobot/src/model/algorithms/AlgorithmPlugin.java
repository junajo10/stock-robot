package model.algorithms;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.RUNTIME)
public @interface AlgorithmPlugin {
	String name() default "AlgortihmPlugin";
}
