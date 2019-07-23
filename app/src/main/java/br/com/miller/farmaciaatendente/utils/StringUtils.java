package br.com.miller.farmaciaatendente.utils;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static String value =
            "À Á Â Ã Ä Å Æ Ç È É Ê Ë Ì " +
            "Í Î Ï Ð Ñ Ò Ó Ô Õ Ö Ø Ù Ú Û Ü Ý Þ ß " +
            "à á â ã ä å æ ç è é ê ë ì í î ï ð ñ " +
            "ò ó ô õ ö ø ù ú û ü ý þ ÿ ";

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


    public static String formatDate(Date solicitationDate) {

        return new SimpleDateFormat("dd/MM HH:mm", Locale.getDefault()).format(solicitationDate);
    }

    public static BigDecimal parseMonetaryStringToBigDecimal(final String amount, final Locale locale) throws ParseException {
        final NumberFormat format = NumberFormat.getNumberInstance(locale);
        if (format instanceof DecimalFormat) {
            ((DecimalFormat) format).setParseBigDecimal(true);
        }
        return (BigDecimal) format.parse(amount.replaceAll("[^\\d.,]",""));
    }

    public static String normalizer(String title){

        String temp = title.toLowerCase().replace(" ", "_");

        String nfdNormalizedString = Normalizer.normalize(temp, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");

        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }
}
