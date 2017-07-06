package com.oscar.proyectoapptodo.Utils;

import com.oscar.proyectoapptodo.R;

/**
 * Created by daniel on 13/06/2017.
 */

public class Constants {

    public interface errorType{
        int ERROR_LOGIN_CREATE_USER = 101;
        int ERROR_LOGIN_CONNECTION_USER = 102;
        int ERROR_LOGIN_EMAIL = 103;
        int ERROR_LOGIN_PASS = 104;
        int ERROR_LOGIN_CLOSE_SESSION = 105;
    }

    public interface errorMessage{
        String errorLoginCreateUser = "No se pudo crear el usuario";
        String errorLoginConnectionUser = "No se pudo iniciar sesion";
    }

    public interface successType{
        int SUCCES_LOGIN_CREATE_USER = 1;
        int SUCCES_LOGIN_CONNECTION_USER = 2;
    }

    public interface succesMessage{
        String successLoginCreateUser = "Se pudo crear el usuario";
        String successLoginConnectionUser = "Se pudo iniciar sesion";
    }

    public interface RegExps {

    }

    public interface typeResponseWebService{
        int TYPE_JS0N = 401;
        int TYPE_XML = 402;
        int RESPONSE_OK = 200;
        int RESPONSE_NOT_OK = 201;

    }
}
