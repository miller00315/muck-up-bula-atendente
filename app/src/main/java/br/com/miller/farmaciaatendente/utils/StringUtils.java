package br.com.miller.farmaciaatendente.utils;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static boolean isValidEmail(String email){

        final String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        final Pattern pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);

        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }

    public static Date parseDate(String date){

        try {
            return new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(date);
        } catch (ParseException e) {
            return new Date();
        }
    }

    public static String doubleToMonetaryString(double value){

        return String.format(Locale.getDefault(), "R$ %.2f", value);
    }


    public static String formatDate(Date date){

        return new SimpleDateFormat("dd/MM HH:mm",Locale.getDefault()).format(date);

    }



}
