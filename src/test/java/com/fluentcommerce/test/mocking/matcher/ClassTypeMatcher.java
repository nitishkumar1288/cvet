package com.fluentcommerce.test.mocking.matcher;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class ClassTypeMatcher<T> extends BaseMatcher<T> {

    private final Class<T> targetClass;

    public ClassTypeMatcher(Class<T> targetClass) {
        this.targetClass = targetClass;
    }

    @SuppressWarnings("unchecked")
    public boolean matches(Object obj) {
        if (obj != null) {
            return targetClass.isAssignableFrom(obj.getClass());
        }
        return false;
    }

    public void describeTo(Description desc) {
        desc.appendText("Matches parameters of type: " + targetClass.getName());
    }

    public static <T> ClassTypeMatcher<T> of(Class<T> clazz) {
        return new ClassTypeMatcher<>(clazz);
    }
}
