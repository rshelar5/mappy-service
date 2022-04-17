package com.aaa.mappy.etl.tool.exception;

import lombok.Data;

import java.io.FileNotFoundException;


public class ETLException extends Exception{

    private ErrorCode errorCode;

    public ETLException(ErrorCode errorCode, FileNotFoundException e) {

        super(e);
        this.errorCode = errorCode;
    }
}
