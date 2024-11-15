package lab02.commands;


import lab02.data.ResponseData;

import java.util.List;

public class Get extends HttpCommand {
    @Override
    public List<ResponseData> execute() {
        return history.get();
    }
}
