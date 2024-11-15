package lab02.commands;


import lab02.data.ResponseData;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class History {
    @Getter
    private static final History instance = new History();
    private final List<ResponseData> history = Collections.synchronizedList(new ArrayList<>());

    public void add(ResponseData response) {
        history.add(response);
    }

    public List<ResponseData> get() {
        return history;
    }

    public void clear() {
        history.clear();
    }
}
