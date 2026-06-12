package com.lay.betterbadges.common.util;

import java.util.List;

public class Utils {

    public static <T> T getNextOfList(List<T> list, T object){
        return getNextOfList(list, list.indexOf(object), false);
    }

    public static <T> T getNextOfList(List<T> list, T object, boolean reverse){
        return getNextOfList(list, list.indexOf(object), reverse);
    }

    public static <T> T getNextOfList(List<T> list, int index, boolean reverse){
        if (list.isEmpty()) {
            throw new RuntimeException("Empty List");
        }
        int size = list.size();
        if (size == 1) return list.getFirst();

        if (index == -1) index = 0;

        int nextIndex = (index + (reverse ? -1 : 1)) % size;

        if (nextIndex <= -1) nextIndex = size - 1;

        return list.get(nextIndex);
    }

}
