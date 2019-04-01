package org.kevin;

public class Test {
    static String escape(String t){
        return '"' + t.replaceAll("\"", "\\\"").replaceAll("\r\n", " ") + '"';
    }

    public static void main(String[] args) {
        System.out.println(escape("dasd dasd \r\ndasdasd\"dasdadgdgdg\""));
    }
}
