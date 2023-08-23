package com.fluentcommerce.test.mocking.matcher;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import java.util.function.Predicate;

public class PredicateMatcher<T> extends BaseMatcher<T> {
    private Predicate<T> predicate;
    private Class<T> targetClass;
    public PredicateMatcher(Class<T> clazz, Predicate<T> predicate) {
        targetClass = clazz;
        this.predicate = predicate;
    }

    @Override
    public boolean matches(Object o) {
        if (o == null) return false;
        if (!targetClass.isAssignableFrom(o.getClass())) return false;
//        if (!targetClass.getName().equals(o.getClass().getName())) return false;

        try {
            return predicate.test((T) o);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("Predicate based matcher");
    }

    public static <T> PredicateMatcher<T> of(Class<T> clazz, Predicate<T> predicate) {
        return new PredicateMatcher<>(clazz, predicate);
    }

    public static PredicateMatcher<String> stringMatcher(String string) {
        Predicate<String> comparator = (targetString) -> {
            if (string == null) return targetString == null;
            return string.equals(targetString);
        };

        return PredicateMatcher.of(String.class, comparator);
    }
}
