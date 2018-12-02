/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hu.zutaten13;

import java.util.Arrays;

/**
 * @author hu
 */
public class Zutat {

    public int id = 0;
    public String[][] bezStem = new String[3][];
    public String[] bez = new String[]{"", "", ""};
    public String[] props = new String[]{"", "", ""};
    public String gruppe = "";

    public static int SOLID = 0;
    public static int LIQUID = 1;
    public static int NOTIZ = 2;
    public int art = SOLID;
    public int stufe = 0;

    boolean mehrHeader = true;
    boolean mehrNotizen = true;
    boolean mehrData = true;
    boolean mehrHilfsstoff = true;

    String portion = "";
    int portionMass = 0;
    String verlustRuesten = "";
    String verlustKochen = "";
    String verlustAbgang = "";

    int dichteMengeMass = 0;
    String dichteMenge = "";
    int dichteProMass = 0;

    int anzahlPersonen = 4;

    public String hilfsstoffeString = "";
    public String notizString = "";

    public Zutat(int laufnr, int art) {
        this.id = laufnr;
        this.art = art;
    }

    @Override
    public String toString() {
        return "Zutat{" +
                "id=" + id +
                ", bezStem=" + Arrays.toString (bezStem) +
                ", bez=" + Arrays.toString (bez) +
                ", props=" + Arrays.toString (props) +
                ", gruppe='" + gruppe + '\'' +
                ", art=" + art +
                ", stufe=" + stufe +
                ", mehrHeader=" + mehrHeader +
                ", mehrNotizen=" + mehrNotizen +
                ", mehrData=" + mehrData +
                ", mehrHilfsstoff=" + mehrHilfsstoff +
                ", portion='" + portion + '\'' +
                ", portionMass=" + portionMass +
                ", verlustRuesten='" + verlustRuesten + '\'' +
                ", verlustKochen='" + verlustKochen + '\'' +
                ", verlustAbgang='" + verlustAbgang + '\'' +
                ", dichteMengeMass=" + dichteMengeMass +
                ", dichteMenge='" + dichteMenge + '\'' +
                ", dichteProMass=" + dichteProMass +
                ", anzahlPersonen=" + anzahlPersonen +
                ", hilfsstoffeString='" + hilfsstoffeString + '\'' +
                ", notizString=" + notizString +
                '}';
    }


    public void toStringComp(boolean select) {

        boolean print = false;

        if (select) {
            if (!portion.equals ("")) {
                print = true;
            }
            if (!verlustRuesten.equals ("")) {
                print = true;
            }
            if (!verlustKochen.equals ("")) {
                print = true;
            }
            if (dichteMengeMass != 0) {
                print = true;
            }
            if (dichteProMass != 0) {
                print = true;
            }
            if (!dichteMenge.equals ("")) {
                print = true;
            }
            if (!notizString.equals ("")) {
                print = true;
            }

            if (print == false) {
                return;
            }
        }

        System.out.println ("*********toStringComp " +
                "id=" + id + " " +
                gruppe + " " +
                bez[0] + " " +
                " props=" + props[0] +
                ", art=" + art +
                ", stufe=" + stufe);
        System.out.println ("*********toStringComp");
        System.out.println ("*********toStringComp " + Arrays.toString (bezStem[0]));
//                ", mehrHeader=" + mehrHeader +
//                ", mehrNotizen=" + mehrNotizen +
//                ", mehrData=" + mehrData +
//                ", mehrHilfsstoff=" + mehrHilfsstoff +
        System.out.println ("*********toStringComp " +
                "                    portion='" + portion + '\'' +
                ", portionMass=" + portionMass +
                ", Ruesten='" + verlustRuesten + '\'' +
                ", Kochen='" + verlustKochen + '\'' +
                ", Abgang='" + verlustAbgang + '\'');
//                ", dichteMengeMass=" + dichteMengeMass +
//                ", dichteMenge='" + dichteMenge + '\'' +
//                ", dichteProMass=" + dichteProMass +
//                ", temperaturBacken='" + temperaturBacken + '\'' +
//                ", temperaturKern='" + temperaturKern + '\'' +
//                ", kochzeit='" + kochzeit + '\'' +
//                ", anzahlPersonen=" + anzahlPersonen +
        System.out.println ("*********toStringComp " +
                "                    hilfsstoffeString='" + hilfsstoffeString + '\'');
        System.out.println ("*********toStringComp " +
                "                    notizString=" + notizString);
        System.out.println ("*********");
    }

    public Zutat(int laufnr) {

    }
}
