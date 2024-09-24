package ru.yandex.practicum.filmorate.utility.classes;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class NextId {
    private long nextId = 0;

    public long getNextId() {
        return ++nextId;
    }
}
