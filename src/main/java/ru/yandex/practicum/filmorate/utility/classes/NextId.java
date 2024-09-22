package ru.yandex.practicum.filmorate.utility.classes;

import java.util.Map;

public class NextId {
    public static Long getNextId(Map<Long,?> collection) {
        Long maxId = collection.keySet().stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++maxId;
    }
}
