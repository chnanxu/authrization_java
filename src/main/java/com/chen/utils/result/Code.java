package com.chen.utils.result;

public enum Code implements ResultCode{

    //密码错误
    PASSWORDFILE(true,11,"密码错误");

    //



    boolean success;
    int code;
    String message;

    Code(boolean success,int code,String message){
        this.success=success;
        this.code=code;
        this.message=message;
    }

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
