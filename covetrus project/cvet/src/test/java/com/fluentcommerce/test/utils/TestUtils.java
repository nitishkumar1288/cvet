package com.fluentcommerce.test.utils;

import com.fluentcommerce.graphql.type.AttributeInput;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.IntStream;

public class TestUtils {
    // why not just inline?
    // because notEquals is much more readable than !


    public static boolean notEquals(String string1, String string2) {
        return !StringUtils.equals(string1, string2);
    }

    public static boolean notEquals(boolean bool1, boolean bool2) {
        return bool1 != bool2;
    }

    public static <T extends Comparable<? super T>> boolean notEquals(List<T> one, List<T> two) {
        return !equals(one, two);
    }

    public static <T extends Comparable<? super T>> boolean equals(List<T> one, List<T> two) {
        if (CollectionUtils.isEmpty(one) && CollectionUtils.isEmpty(two)) return true;

        if ((one == null && two != null)
                || one != null && two == null
                || one.size() != two.size()) {
            return false;
        }

        //to avoid messing the order of the lists we will use a copy
        //as noted in comments by A. R. S.
        one = new ArrayList<>(one);
        two = new ArrayList<>(two);

        Collections.sort(one);
        Collections.sort(two);
        return one.equals(two);
    }

    public static boolean notEquals(Integer int1, Integer int2) {
        if (int1 == null) return int2 == null;
        return !int1.equals(int2);
    }

    public static boolean notEquals(Boolean bool1, Boolean bool2) {
        if (bool1 == null) return bool2 == null;
        return !bool1.equals(bool2);
    }

    //in the system date objects created with time data and usually created via recent time
    //we can't compare two time object those crete via recent time because those never match so
    //we ignore time part of object
    public static boolean notEquals(Date date1, Date date2) {
        if (date1 == null && date2 == null) {
            return false;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        return !dateFormat.format(date1).equals(dateFormat.format(date2));
    }

    public static <T> boolean notEquals(List<T> one, List<T> two, Comparator<T> comparator) {
        return !equals(one, two, comparator);
    }

    public static <T> boolean equals(List<T> one, List<T> two, Comparator<T> comparator) {
        if (one == null && two == null) {
            return true;
        }

        if ((one == null && two != null)
                || one != null && two == null
                || one.size() != two.size()) {
            return false;
        }

        //to avoid messing the order of the lists we will use a copy
        //as noted in comments by A. R. S.
        List<T> finalOne = new ArrayList<>(one);
        List<T> finalTwo = new ArrayList<>(two);

        finalOne.sort(comparator);
        finalTwo.sort(comparator);
        return IntStream.range(0, one.size())
                .allMatch(i -> comparator.compare(finalOne.get(i), finalTwo.get(i)) == 0);
    }

    public static boolean equals(AttributeInput input1, AttributeInput input2) {
        if (notEquals(input1.name(), input2.name())) return false;
        if (notEquals(input1.type(), input2.type())) return false;
        if (notEquals(input1.value().toString(), input2.value().toString())) return false;
        return true;
    }
}
