package com.dtalavera.ejerciciosoa.crudaws.methods;

import java.util.HashMap;
import java.util.Map;

public class ReplaceChars {

	//Método para sustituir caracteres especiales de un json y mandarlo a la uri
	public static String transformarLetrasRaras(String palabro) {
		palabro = palabro.replace("ñ", "n").replace("Ñ", "N").replace("Á", "A").replace("á", "a").replace("É", "E").replace("é", "e").replace("Í", "I").replace("í", "i").replace("Ó", "O").replace("ó", "o").replace("Ú", "U").replace("ú", "u").replace("Ü", "U").replace("ü", "u");
        return palabro;
    }

    private static Map<Character, String> MAP_NORM;

    //Método para sustituir caracteres especiales de un json recibido o un json para enviar (unicode)
    public static String stripDiacritics(String value) {
        if (MAP_NORM == null || MAP_NORM.isEmpty()) {
            MAP_NORM = new HashMap<>();
            MAP_NORM.put('!', "\\u0021");
            MAP_NORM.put('"', "\\u0022");
            MAP_NORM.put('#', "\\u0023");
            MAP_NORM.put('$', "\\u0024");
            MAP_NORM.put('%', "\\u0025");
            MAP_NORM.put('&', "\\u0026");
            MAP_NORM.put('(', "\\u0028");
            MAP_NORM.put(')', "\\u0029");
            MAP_NORM.put('*', "\\u002A");
            MAP_NORM.put('+', "\\u002B");
            MAP_NORM.put(',', "\\u002C");
            MAP_NORM.put('-', "\\u002D");
            MAP_NORM.put('.', "\\u002E");
            MAP_NORM.put('/', "\\u002F");
            MAP_NORM.put(':', "\\u003A");
            MAP_NORM.put(';', "\\u003B");
            MAP_NORM.put('<', "\\u003C");
            MAP_NORM.put('=', "\\u003D");
            MAP_NORM.put('>', "\\u003E");
            MAP_NORM.put('?', "\\u003F");
            MAP_NORM.put('@', "\\u0040");
            MAP_NORM.put('[', "\\u005B");
            MAP_NORM.put('\'', "\\u005C");
            MAP_NORM.put(']', "\\u005D");
            MAP_NORM.put('^', "\\u005E");
            MAP_NORM.put('_', "\\u005F");
            MAP_NORM.put('`', "\\u0060");
            MAP_NORM.put('{', "\\u007B");
            MAP_NORM.put('|', "\\u007C");
            MAP_NORM.put('}', "\\u007D");
            MAP_NORM.put('~', "\\u007E");
            MAP_NORM.put('¡', "\\u00A1");
            MAP_NORM.put('¢', "\\u00A2");
            MAP_NORM.put('£', "\\u00A3");
            MAP_NORM.put('¤', "\\u00A4");
            MAP_NORM.put('¥', "\\u00A5");
            MAP_NORM.put('¦', "\\u00A6");
            MAP_NORM.put('§', "\\u00A7");
            MAP_NORM.put('¨', "\\u00A8");
            MAP_NORM.put('©', "\\u00A9");
            MAP_NORM.put('ª', "\\u00AA");
            MAP_NORM.put('«', "\\u00AB");
            MAP_NORM.put('¬', "\\u00AC");
            MAP_NORM.put('®', "\\u00AE");
            MAP_NORM.put('¯', "\\u00AF");
            MAP_NORM.put('°', "\\u00B0");
            MAP_NORM.put('±', "\\u00B1");
            MAP_NORM.put('²', "\\u00B2");
            MAP_NORM.put('³', "\\u00B3");
            MAP_NORM.put('´', "\\u00B4");
            MAP_NORM.put('µ', "\\u00B5");
            MAP_NORM.put('¶', "\\u00B6");
            MAP_NORM.put('·', "\\u00B7");
            MAP_NORM.put('¸', "\\u00B8");
            MAP_NORM.put('¹', "\\u00B9");
            MAP_NORM.put('º', "\\u00BA");
            MAP_NORM.put('»', "\\u00BB");
            MAP_NORM.put('¼', "\\u00BC");
            MAP_NORM.put('½', "\\u00BD");
            MAP_NORM.put('¾', "\\u00BE");
            MAP_NORM.put('¿', "\\u00BF");
            MAP_NORM.put('À', "\\u00C0");
            MAP_NORM.put('Á', "\\u00C1");
            MAP_NORM.put('Â', "\\u00C2");
            MAP_NORM.put('Ã', "\\u00C3");
            MAP_NORM.put('Ä', "\\u00C4");
            MAP_NORM.put('Å', "\\u00C5");
            MAP_NORM.put('Æ', "\\u00C6");
            MAP_NORM.put('Ç', "\\u00C7");
            MAP_NORM.put('È', "\\u00C8");
            MAP_NORM.put('É', "\\u00C9");
            MAP_NORM.put('Ê', "\\u00CA");
            MAP_NORM.put('Ë', "\\u00CB");
            MAP_NORM.put('Ì', "\\u00CC");
            MAP_NORM.put('Í', "\\u00CD");
            MAP_NORM.put('Î', "\\u00CE");
            MAP_NORM.put('Ï', "\\u00CF");
            MAP_NORM.put('Ð', "\\u00D0");
            MAP_NORM.put('Ñ', "\\u00D1");
            MAP_NORM.put('Ò', "\\u00D2");
            MAP_NORM.put('Ó', "\\u00D3");
            MAP_NORM.put('Ô', "\\u00D4");
            MAP_NORM.put('Õ', "\\u00D5");
            MAP_NORM.put('Ö', "\\u00D6");
            MAP_NORM.put('×', "\\u00D7");
            MAP_NORM.put('Ø', "\\u00D8");
            MAP_NORM.put('Ù', "\\u00D9");
            MAP_NORM.put('Ú', "\\u00DA");
            MAP_NORM.put('Û', "\\u00DB");
            MAP_NORM.put('Ü', "\\u00DC");
            MAP_NORM.put('Ý', "\\u00DD");
            MAP_NORM.put('Þ', "\\u00DE");
            MAP_NORM.put('ß', "\\u00DF");
            MAP_NORM.put('à', "\\u00E0");
            MAP_NORM.put('á', "\\u00E1");
            MAP_NORM.put('â', "\\u00E2");
            MAP_NORM.put('ã', "\\u00E3");
            MAP_NORM.put('ä', "\\u00E4");
            MAP_NORM.put('å', "\\u00E5");
            MAP_NORM.put('æ', "\\u00E6");
            MAP_NORM.put('ç', "\\u00E7");
            MAP_NORM.put('è', "\\u00E8");
            MAP_NORM.put('é', "\\u00E9");
            MAP_NORM.put('ê', "\\u00EA");
            MAP_NORM.put('ë', "\\u00EB");
            MAP_NORM.put('ì', "\\u00EC");
            MAP_NORM.put('í', "\\u00ED");
            MAP_NORM.put('î', "\\u00EE");
            MAP_NORM.put('ï', "\\u00EF");
            MAP_NORM.put('ð', "\\u00F0");
            MAP_NORM.put('ñ', "\\u00F1");
            MAP_NORM.put('ò', "\\u00F2");
            MAP_NORM.put('ó', "\\u00F3");
            MAP_NORM.put('ô', "\\u00F4");
            MAP_NORM.put('õ', "\\u00F5");
            MAP_NORM.put('ö', "\\u00F6");
            MAP_NORM.put('÷', "\\u00F7");
            MAP_NORM.put('ø', "\\u00F8");
            MAP_NORM.put('ù', "\\u00F9");
            MAP_NORM.put('ú', "\\u00FA");
            MAP_NORM.put('û', "\\u00FB");
            MAP_NORM.put('ü', "\\u00FC");
            MAP_NORM.put('ý', "\\u00FD");
            MAP_NORM.put('þ', "\\u00FE");
            MAP_NORM.put('ÿ', "\\u00FF");
        }
        if (value == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder(value);
        for (int i = 0; i < value.length(); i++) {
            String c = MAP_NORM.get(sb.charAt(i));
            if (c != null) {
                sb.setCharAt(i, sb.charAt(i));
            }
        }
        return sb.toString();
    }  
}
