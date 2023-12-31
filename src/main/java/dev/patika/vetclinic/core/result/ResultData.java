package dev.patika.vetclinic.core.result;

import lombok.Getter;

@Getter
public class ResultData<T> extends Result { // This class is a generic class for Result objects.

    private T data;

    public ResultData(boolean status, String message, String httpCode, T data) {
        super(status, message, httpCode);
        this.data = data;
    }

    public boolean isSuccess() {
        return super.isStatus();
    }
}

