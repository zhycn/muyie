package org.muyie.framework.sensitive;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.alibaba.fastjson.serializer.ValueFilter;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
public @interface Sensitive {

  SensitiveType value() default SensitiveType.CUSTOMIZE_HIDE;

  Class<? extends ValueFilter> filter() default DefaultValueFilter.class;

}
