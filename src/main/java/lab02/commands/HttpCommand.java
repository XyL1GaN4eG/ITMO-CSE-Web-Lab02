package lab02.commands;


import lab02.data.ResponseData;
import lab02.exceptions.InvalidRequestException;

import java.util.List;

public abstract class HttpCommand {
    protected final History history = History.getInstance();
    abstract List<ResponseData> execute() throws InvalidRequestException;
}