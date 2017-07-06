package com.oscar.proyectoapptodo.Utils;

import android.util.Patterns;

import java.util.regex.Pattern;

/**
 * Created by daniel on 13/06/2017.
 */

public final class Validator {
    public Validator() {
    }

    public static boolean isValidEmail(String email)
    {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isMoreSix(String cadena){
        return cadena.length() > 5;
    }
}
