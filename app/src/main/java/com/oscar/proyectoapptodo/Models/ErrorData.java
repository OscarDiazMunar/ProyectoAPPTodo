package com.oscar.proyectoapptodo.Models;

/**
 * Created by daniel on 13/06/2017.
 */

public class ErrorData extends BaseData{
    public ErrorData() {
        super();
    }

    public ErrorData(String message, int type){
        super(message, type);
    }
}
