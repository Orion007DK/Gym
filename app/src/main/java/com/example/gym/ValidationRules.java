package com.example.gym;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationRules {

    public static boolean isNameRight(String textName){
        //stworzenie wzoru(regex) mającego sprawdzić poprawność imienia
        Pattern pattern = Pattern.compile("^[A-ZŻŹĆĄŚĘŁÓŃ][a-zżźćńółęąś][a-zżźćńółęąś]+$");
        //stworzenie matchera i przypisanie wzorowi tekstu wpisanego do pola tekstowego
        Matcher matchName = pattern.matcher(textName);
        //sprawdzenie czy podany tekst pasuje do wzoru
        return matchName.matches();
    }

    public static boolean isPhoneNumberRight(String textNumber){
        Pattern pattern = Pattern.compile("^[0-9]{9}$");
        //stworzenie matchera i przypisanie wzorowi tekstu wpisanego do pola tekstowego
        Matcher matchNumber = pattern.matcher(textNumber);
        //sprawdzenie czy podany tekst pasuje do wzoru
        return matchNumber.matches();
    }

    public static boolean isSurnameRight(String textSurname){
        //stworzenie wzoru(regex) mającego sprawdzić poprawność nazwiska
        Pattern pattern = Pattern.compile("^[A-ZŻŹĆĄŚĘŁÓŃ][a-zżźćńółęąś][a-zżźćńółęąś]+$|[A-ZŻŹĆĄŚĘŁÓŃ][a-zżźćńółęąś]+[-][A-ZŻŹĆĄŚĘŁÓŃ][a-zżźćńółęąś]+$");
        //stworzenie matchera i przypisanie wzorowi tekstu wpisanego do pola tekstowego
        Matcher matchSurname = pattern.matcher(textSurname);
        //sprawdzenie czy podany tekst pasuje do wzoru
        return matchSurname.matches();
    }
}
