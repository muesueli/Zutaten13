package com.hu.zutaten13;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import org.apache.lucene.analysis.de.GermanLightStemmer;
import org.apache.lucene.analysis.fr.FrenchLightStemmer;
import org.tartarus.snowball.ext.PorterStemmer;

import java.text.Collator;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public final class Masse08 {

    public int limitStandardMasse = 0;
    public ArrayList<Mass> ml = new ArrayList<> ();
    public ArrayList<SortedMap<String, Mass>> massIndexList = new ArrayList<> ();
    public Multimap<String, Zutat> searchIndex = ArrayListMultimap.create ();
    public ArrayList<Zutat> zutatenList = new ArrayList<> ();// tree
    public GermanLightStemmer stg = new GermanLightStemmer ();
    public FrenchLightStemmer stf = new FrenchLightStemmer ();
    public PorterStemmer ste = new PorterStemmer ();
    public int D = 0;
    public int E = 1;
    public int F = 2;
    public int INT = 0;
    public int US = 1;
    public int EU = 2;

    int laufnr = 0;
    // Mass m_stk;
    MainActivity mainActivity;

    public Masse08(MainActivity mainActivity) {

        this.mainActivity = mainActivity;

        SortedMap<String, Mass> massIdx0 = new TreeMap<> (new Vergleicher (Locale.GERMAN));
        massIndexList.add (massIdx0);
        SortedMap<String, Mass> massIdx1 = new TreeMap<> (new Vergleicher (Locale.US));
        massIndexList.add (massIdx1);
        SortedMap<String, Mass> massIdx2 = new TreeMap<> (new Vergleicher (Locale.FRENCH));
        massIndexList.add (massIdx2);

        // Pattern.compile("[\\p{InCombiningDiacriticalMarks}\\p{IsLm}\\p{IsSk}\\p{Z}]+");
        /**
         * *********************************************************************
         * *****************
         * alle Textzeilen zitieren checked zellinhalt wie angezeigt nicht checked
         * ********************************************************************* *****************
         */
        ml.add (new Mass (this, INT, true, "Stk", "Stück", "Stück", "pc", "piece", "pieces", "pc", "pièce", "pièces", "stk", false, 1f, 1f, 1f, "", "", ""));
        /**
         * **********************************************************************************************************
         */
        ml.add (new Mass (this, INT, true, "g", "Gramm", "Gramm", "g", "gram", "grams", "g", "gramme", "grammes", "g", true, 1f, -1f, 1f, "oz", "cm3", ""));
        ml.add (new Mass (this, INT, true, "dag", "Dekagramm", "Dekagramm", "dag", "decagram", "decagrams", "dag", "décagramme", "décagrammes", "g", false, 10f, -1f, 10f, "oz", "cm3", ""));
        // ml.add(new Mass(this, EU, false, "Pf", "Pfund", "Pfund", "?", "_pound", "_pounds", "lb", "livre", "livres", "g", false, 500, -1, 500, "#", "l", ""));
        ml.add (new Mass (this, INT, true, "kg", "Kilogramm", "Kilogramme", "kg", "kilogram", "kilograms", "kg", "kilogramme", "kilogrammes", "g", true, 1000f, -1f, 1000f, "#", "l", ""));
        /**
         * **********************************************************************************************************
         */
        ml.add (new Mass (this, INT, true, "mm", "Millimeter", "Millimeter", "mm", "millimeter", "millimeters", "mm", "millimètre", "millimètres", "m", true, 0.001f, -1f, 0.001f, "in", "", ""));
        ml.add (new Mass (this, INT, true, "cm", "Zentimeter", "Zentimeter", "cm", "centimeter", "centimeters", "cm", "centimètre", "centimètres", "m", true, 0.01f, -1f, 0.01f, "in", "", ""));
        ml.add (new Mass (this, INT, true, "dm", "Dezimeter", "Dezimeter", "dm", "decimeter", "decimeters", "dm", "décimètre", "décimètres", "m", false, 0.1f, -1f, 0.1f, "ft", "", ""));
        ml.add (new Mass (this, INT, true, "m", "Meter", "Meter", "m", "meter", "meters", "m", "mètre", "mètres", "m", true, 1, -1, 1, "yd", "", ""));
        /**
         * **********************************************************************************************************
         */
        ml.add (new Mass (this, INT, true, "ml", "Millil", "Millil", "ml", "millil", "millils", "ml", "millilitre", "millilitres", "cm3", false, 1, -1, 1, "fl oz", "", ""));
        ml.add (new Mass (this, INT, true, "cm3", "Kubikzentimeter", "Kubikzentimeter", "cm3", "cubic centimeter", "cubic centimeters", "cm3", "centimètre cube", "centimètres cube", "cm3", true, 1, -1, 1, "fl oz", "", ""));
        ml.add (new Mass (this, EU, true, "KL", "KL", "KL", "_t", "_teaspoon", "_teaspoons", "cuil. à café", "cuillère à café", "cuillères à café", "cm3", false, 4.929f, -1f, 4.929f, "tsp", "", ""));
        ml.add (new Mass (this, INT, true, "cl", "Zentil", "Zentil", "cl", "centil", "centils", "cl", "centilitre", "centilitres", "cm3", false, 10, -1, 10, "fl oz", "", ""));
        // T nur gross wegen TL
        ml.add (new Mass (this, EU, true, "EL", "EL", "EL", "_T", "_tablespoon", "_tablespoons", "cuil. à soupe", "cuillère à soupe", "cuillères à soupe", "cm3", false, 14.79f, -1f, 14.79f, "T", "", ""));
        ml.add (new Mass (this, INT, true, "dl", "Deziliter", "Deziliter", "dl", "deciliter", "deciliters", "dl", "décilitre", "décilitres", "cm3", true, 100, -1, 100, "fl oz", "", ""));
        ml.add (new Mass (this, EU, false, "Ta", "Ta", "Tan", "_c", "_cup", "_cups", "?", "Ta", "Tas", "cm3", true, 250, -1, 250, "c", "", "")); // nicht spez
        ml.add (new Mass (this, INT, true, "l", "l", "l", "l", "l", "ls", "l", "litre", "litres", "cm3", true, 1000, -1, 1000, "pt", "", ""));

        limitStandardMasse = ml.size ();
    }


    public String einheitSpezial(String einheit) {

        if (einheit.equals ("#")) {
            return einheit;
        }
        if (einheit.equals ("@")) {
            return einheit;
        }
        if (einheit.equals ("DL")) {
            return "DL_spezial";
        }
        if (einheit.equals ("T")) {
            return "T_spezial";
        }
        return normalisieren (einheit);
    }

//            "Verlust", "Verlust Garen (%)", "Verlust Glasieren (%)", "Verlust Rüsten (%)", "Verlust Schmoren (%) ", "Verlust Sieden (%)"};

    public Zutat loadDummy(String csv) {

        laufnr++;
        Zutat zutat = new Zutat (laufnr);

/*

        loadDummy("1;0;test;bez;3;4;5;gruppe;me;100;200;props;11;12;13;14;15;16;False;False;False;False");

                      0  java
                      1  "art;" +
                      2  "bez__001;" +
                      3  "gruppe;
                      4  "me" +
                      5  "portion_klein_gross__001" +
                      6  "portion_klein_gross__002;" +
                      7  "props__001;" +
                      8  "verlustKochen_von_bis__001;" +
                      9  "verlustKochen_von_bis__002;" +
 */

        // loadDummy("1;0;2;3;4;0;6;7;8;9;10;11;12;13;14;15;16;False;False;False;False");
        String[] s = csv.split (";", -1);
        // String[] s = csv.split(";");
        zutat.props[0] = s[7];
        zutat.gruppe = s[3];
        zutat.art = Integer.parseInt (s[1]);
        zutat.bez[0] = s[2];
        if (zutat.art == 1) {
            zutat.portionMass = 4;
            zutat.mehrData = true;
        } else {
            zutat.portionMass = 0;
        }

        zutat.portion = durchschnitt (s[5], s[6]);
        zutat.verlustRuesten = s[8];
        zutat.verlustKochen = s[10];
        zutat.verlustAbgang = s[12];

        zutatenList.add (zutat);
        indexIt (zutat);
        return zutat;
    }

    private String durchschnitt(String von, String bis) {

        String resultat;
        float vonFloat;
        float bisFloat;
        try {
            if (von.equals ("")) {
                vonFloat = 0f;
            } else {
                vonFloat = Float.parseFloat (von);
            }
            if (bis.equals ("")) {
                bisFloat = 0f;
            } else {
                bisFloat = Float.parseFloat (bis);
            }
            float f = (vonFloat + bisFloat) / 2;
            if (f == 0f) {
                return "";
            } else {
                return "" + f;
            }
        } catch (Exception e) {
            System.out.println ("jjjjjj portion " + von + " " + bis);
            return "";
        }
    }

    public void indexIt(Zutat sg) {
        setupSearch (sg);
        for (int j = 0; j < sg.bezStem[0].length; j++) {
            char[] c = new char[sg.bezStem[0][j].length ()];
            for (int k = 0; k < sg.bezStem[0][j].length (); k++) {
                c[k] = sg.bezStem[0][j].charAt (k);
            }
            int laenge = stg.stem (c, sg.bezStem[0][j].length ());
            sg.bezStem[0][j] = normalisieren (sg.bezStem[0][j].substring (0, laenge)); //ueberfluessig mit primary collator
            searchIndex.put (sg.bezStem[0][j], sg);
        }
        // System.out.println ("********* indexIt " + sg.gruppe + " " + sg.bez[0] + " " + Arrays.toString (sg.bezStem[0]));

/* englisch
        setupSearch_D_E(1, sg);
        for (int j = 0; j < sg.bezStem[1].length; j++) {
            ste.setCurrent(sg.bezStem[1][j]);
            ste.stem();
            sg.bezStem[1][j] = normalisieren(ste.getCurrent());
            // Dichte (TL, EL (g))List.get(1).put(sg.bezStem[1][j], sg);
        }

*/

  /*   welsch
        ware[0] = ware[0].replaceAll("à", "");
        ware[0] = ware[0].replaceAll("à la", "");
        ware[0] = ware[0].replaceAll("au", "");
        ware[0] = ware[0].replaceAll("aux", "");
        ware[0] = ware[0].replaceAll("de", "");
        ware[0] = ware[0].replaceAll("d'", "");
        ware[0] = ware[0].replaceAll("des", "");
        sg.bez[2] = ware[0];
        sg.bezStem[2] = getNormalizedStems(2, sg.bez[2]);  // F
        for (int i = 0; i < sg.bezStem[2].length; i++) {
            // Dichte (TL, EL (g))List.get(2).put(sg.bezStem[2][i], sg);
        }
*/
    }

    public void setupSearch(Zutat zutat) {

        try {

            Set<String> uniques = new HashSet<> ();

            String suchString = zutat.bez[0];
            if (!zutat.gruppe.equals ("")) {
                suchString += " " + zutat.gruppe;
            }

            String temp0 = suchString.replace ("-", " ");
            String temp1 = suchString.replace ("-", "");
            // jua String[] suchStringArray = (suchString + " " + temp0 + " " + temp1).split (" ");
            String[] suchStringArray = (suchString + " " + temp0).split (" ");
            for (int i = 0; i < suchStringArray.length; i++) {
                if (!uniques.contains (suchStringArray[i]))
                    uniques.add (suchStringArray[i]);
            }
            zutat.bezStem[0] = uniques.toArray (new String[uniques.size ()]);

        } catch (Exception e) {
            e.printStackTrace ();
        }
    }

    public void setupSearch_D_E(Zutat sg) {

        String s0 = sg.gruppe;
        if (!sg.gruppe.equals ("")) {
            s0 += " ";
        }
        s0 += sg.bez[0];

        String s2[] = s0.split ("");
        for (String aS2 : s2) {
            String s3[] = aS2.split ("-");
            if (s3.length > 1) {
                for (String aS3 : s3) {
                    s0 += " " + aS3;
                }
            }
        }

        sg.bezStem[0] = s0.split (" ");
        // System.out.println("********* setupSearch_fix " + sg.gruppe + "/" + sg.bez[0] + " --- " + Arrays.toString(sg.bezStem[0]));

/* jua
        String[] woerter = s0.split("");
        Set<String> uniqueWords = new HashSet<String>(Arrays.asList(woerter));
        sg.bezStem[0] = uniqueWords.toArray(new String[uniqueWords.size()]);
        yyySystem.out.println("setupSearch_xxx " + sg.gruppe + "/" + sg.bez[0] + " --- " + Arrays.toString(sg.bezStem[0]));
*/

    }


 /* alt: mehrere suchworte
    public String[] getNormalizedStems(String query) {
        try {

            int sprache = 0;
            String[] worte = query.split("");
            String[] stems = new String[worte.length];
            for (int i = 0; i < worte.length; i++) {
                int laenge = -1;
                if (sprache == 1) {
                    ste.setCurrent(worte[i]);
                    ste.stem();
                    stems[i] = normalisieren(ste.getCurrent());
                } else {
                    char[] c = new char[worte[i].length()];
                    for (int j = 0; j < worte[i].length(); j++) {
                        c[j] = worte[i].charAt(j);
                    }
                    if (sprache == 0) {
                        laenge = stg.stem(c, worte[i].length());
                    }
                    if (sprache == 2) {
                        laenge = stf.stem(c, worte[i].length());
                    }
                    stems[i] = normalisieren(worte[i].substring(0, laenge));
                }
            }
            return stems;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
*/

    public String normalisieren(String s) {

        String s0 = Normalizer.normalize (s, Normalizer.Form.NFD);
        String s1 = s0.replaceAll ("\\p{M}", "");
        s1 = s1.replaceAll ("[^a-zA-Z0-9 ]", "");
        return s1.toLowerCase ();
    }

    public void zutatListInit() {

        Zutat zutat = loadDummy(";0;Link aaa;Beispiel;g;;;;;;;;;;");
        zutat = loadDummy(";0;Notiz;Beispiel;g;;;;;;;;;;");
        zutat = loadDummy(";0;Aesche;Fisch;g;200;250;;10;;;;;;");
        zutat = loadDummy(";0;Aesche;Fisch;g;130;180;Filet;40;;;;;;");
        zutat = loadDummy(";0;allgemein, ganz;Fisch;g;200;250;ganz;;;;;;;");
        zutat = loadDummy(";0;Egli;Fisch;g;200;250;;12;;;;;;");
        zutat = loadDummy(";0;Egli;Fisch;g;130;180;Filet;65;;;;;;");
        zutat = loadDummy(";0;Felche;Fisch;g;200;250;;10;;;;;;");
        zutat = loadDummy(";0;Felche;Fisch;g;130;180;Filet;40;;;;;;");
        zutat = loadDummy(";0;Forelle;Fisch;g;130;180;Filet;;;;;;;");
        zutat = loadDummy(";0;Hecht;Fisch;g;200;250;Kopf 15%;15;;;;;;");
        zutat = loadDummy(";0;Hecht;Fisch;g;130;180;Filet;55;;;;;;");
        zutat = loadDummy(";0;Kabeljau;Fisch;g;200;250;ohne Kopf;13;;;;;;");
        zutat = loadDummy(";0;Kabeljau;Fisch;g;130;180;Filet;35;;;;;;");
        zutat = loadDummy(";0;Karpfen;Fisch;g;200;250;;15;;;;;;");
        zutat = loadDummy(";0;Karpfen;Fisch;g;130;180;Filet;60;;;;;;");
        zutat = loadDummy(";0;Salm;Fisch;g;200;250;;10;;;;;;");
        zutat = loadDummy(";0;Salm;Fisch;g;130;180;Filet;45;;;;;;");
        zutat = loadDummy(";0;Seezunge;Fisch;g;200;250;;37;;;;;;");
        zutat = loadDummy(";0;Seezunge;Fisch;g;130;180;Filet;50;;;;;;");
        zutat = loadDummy(";0;Steinbutt;Fisch;g;200;250;;40;;;;;;");
        zutat = loadDummy(";0;Steinbutt;Fisch;g;130;180;Filet;70;;;;;;");
        zutat = loadDummy(";0;Zander;Fisch;g;130;180;Filet;50;;;;;;");
        zutat = loadDummy(";0;Zander;Fisch ;g;200;250;;15;;;;;;");
        zutat = loadDummy(";0;Ente;Geflügel-Fleisch;g;300;400;mit Knochen;;;;;;;");
        zutat = loadDummy(";0;Poulet;Geflügel-Fleisch;g;250;350;mit Knochen;;;;;;;");
        zutat = loadDummy(";0;Truthahn;Geflügel-Fleisch;g;300;400;mit Knochen;;;;;;;");
        zutat = loadDummy(";0;allgemein;Gemüse;g;125;165;Butter;;;;;;;");
        zutat = loadDummy(";0;allgemein;Gemüse;g;100;150;;33;;;;;;");
        zutat = loadDummy(";0;allgemein, tiefgekühlt;Gemüse;g;100;150;;;;;;;;");
        zutat = loadDummy(";0;Blumenkohl;Gemüse;g;125;165;;30;;;;;;");
        zutat = loadDummy(";0;Bohnen;Gemüse;g;125;165;;5;;;;;;");
        zutat = loadDummy(";0;Broccoli;Gemüse;g;125;165;;60;;;;;;");
        zutat = loadDummy(";0;Dörrbohnen;Gemüse;g;25;35;;;;;;;;");
        zutat = loadDummy(";0;Erbsen;Gemüse;g;125;165;;50;;;;;;");
        zutat = loadDummy(";0;Fenchel;Gemüse;g;125;165;;15;;;;;;");
        zutat = loadDummy(";0;gerüstet;Gemüse;g;100;150;;;;;;;;");
        zutat = loadDummy(";0;Gurken;Gemüse;g;125;165;;10;;;;;;");
        zutat = loadDummy(";0;Karotte;Gemüse;g;125;165;;20;;;;;;");
        zutat = loadDummy(";0;Kefen;Gemüse;g;125;165;;5;;;;;;");
        zutat = loadDummy(";0;Knollensellerie;Gemüse;g;125;165;;30;;;;;;");
        zutat = loadDummy(";0;Kohlrabi;Gemüse;g;125;165;;15;;;;;;");
        zutat = loadDummy(";0;Lattich;Gemüse;g;125;165;;20;;;;;;");
        zutat = loadDummy(";0;Lauch;Gemüse;g;125;165;;20;;;;;;");
        zutat = loadDummy(";0;Nüssler-Salat;Gemüse;g;125;165;;10;;;;;;");
        zutat = loadDummy(";0;Peperoni;Gemüse;g;125;165;;15;;;;;;");
        zutat = loadDummy(";0;Randen;Gemüse;g;125;165;;10;;;;;;");
        zutat = loadDummy(";0;Rhabarber;Gemüse;g;125;165;;15;;;;;;");
        zutat = loadDummy(";0;Rosenkohl;Gemüse;g;125;165;;15;;;;;;");
        zutat = loadDummy(";0;Rotkraut;Gemüse;g;125;165;;15;;;;;;");
        zutat = loadDummy(";0;Schwarzwurzeln;Gemüse;g;125;165;;30;;;;;;");
        zutat = loadDummy(";0;Spargeln;Gemüse;g;125;165;;30;;;;;;");
        zutat = loadDummy(";0;Spinat;Gemüse;g;125;165;;10;;;;;;");
        zutat = loadDummy(";0;Tomaten;Gemüse;g;125;165;geschält;50;;;;;;");
        zutat = loadDummy(";0;Trockengemüse (Bohnen usw.);Gemüse;g;25;35;;;;;;;;");
        zutat = loadDummy(";0;Weisskabis;Gemüse;g;125;165;;15;;;;;;");
        zutat = loadDummy(";0;Zucchetti;Gemüse;g;125;165;;5;;;;;;");
        zutat = loadDummy(";0;Zwiebeln;Gemüse;g;125;165;;10;;;;;;");
        zutat = loadDummy(";0;Hülsenfrüchte;Getreide-Produkte;g;30;40;;;;;;;;");
        zutat = loadDummy(";0;Maisgriess;Getreide-Produkte;g;30;40;;;;;;;;");
        zutat = loadDummy(";0;Reis;Getreide-Produkte;g;50;80;;;;;;;;");
        zutat = loadDummy(";0;Teigwaren;Getreide-Produkte;g;60;80;;;;;;;;");
        zutat = loadDummy(";0;Braten  Carré;Kalb-Fleisch;g;;;;;;22;;;;");
        zutat = loadDummy(";0;Braten  Nuss;Kalb-Fleisch;g;;;;;;22;;;;");
        zutat = loadDummy(";0;Braten Frikassee;Kalb-Fleisch;g;160;200;;;;25;;;;");
        zutat = loadDummy(";0;Braten Schulter;Kalb-Fleisch;g;160;200;;;;25;;;;");
        zutat = loadDummy(";0;Braten Stotzen;Kalb-Fleisch;g;160;200;;;;25;;;;");
        zutat = loadDummy(";0;Geschnetzeltes;Kalb-Fleisch;g;120;150;à la minute;;;;;;;");
        zutat = loadDummy(";0;Kalbsbrust;Kalb-Fleisch;g;;;;;;25;;;;");
        zutat = loadDummy(";0;Leber;Kalb-Fleisch;g;120;150;;;;;;;;");
        zutat = loadDummy(";0;Schnitzel;Kalb-Fleisch;g;130;160;;;;;;;;");
        zutat = loadDummy(";0;allgemein;Kartoffeln;g;100;150;gerüstet;;;;;;;");
        zutat = loadDummy(";0;allgemein;Kartoffeln;g;;;Verlust in der Schale gekocht;10;;;;;;");
        zutat = loadDummy(";0;allgemein;Kartoffeln;g;;;Verlust mit der Maschine geschält;30;;;;;;");
        zutat = loadDummy(";0;allgemein;Kartoffeln;g;;;Verlust von Hand geschält;20;;;;;;");
        zutat = loadDummy(";0;allgemein;Kartoffeln;g;100;150;;;;;;;;");
        zutat = loadDummy(";0;Kartoffel-Salat;Kartoffeln;g;200;200;;20;;;;;;");
        zutat = loadDummy(";0;Kartoffel-Stock;Kartoffeln;g;250;250;;20;;;;;;");
        zutat = loadDummy(";0;Rösti;Kartoffeln;g;250;250;;20;;;;;;");
        zutat = loadDummy(";0;Salz-Kartoffel;Kartoffeln;g;200;200;;20;;;;;;");
        zutat = loadDummy(";0;Fondue;Käse;g;200;200;;;;;;;;");
        zutat = loadDummy(";0;Raclette;Käse;g;200;200;;;;;;;;");
        zutat = loadDummy(";0;Gigot;Lamm-Fleisch;g;250;350;mit Knochen;;;16;;;;");
        zutat = loadDummy(";0;Karrée;Lamm-Fleisch;g;;;;;;16;;;;");
        zutat = loadDummy(";0;Kotelett;Lamm-Fleisch;g;150;200;mit Knochen;;;;;;;");
        zutat = loadDummy(";0;Rehrücken ;Reh-Fleisch;g;;;;;;12;;;;");
        zutat = loadDummy(";0;Rehschlegel;Reh-Fleisch;g;;;;;;14;;;;");
        zutat = loadDummy(";0; Gulasch, Ragout;Rind-Fleisch;g;160;200;;;;;;;;");
        zutat = loadDummy(";0;Braten ältere Tiere;Rind-Fleisch;g;;;;;;40;;;;");
        zutat = loadDummy(";0;Braten Stotzen;Rind-Fleisch;g;;;;;;34;;;;");
        zutat = loadDummy(";0;Entrecôte;Rind-Fleisch;g;140;180;;;;;;;;");
        zutat = loadDummy(";0;Geschnetzeltes (à la minute);Rind-Fleisch;g;120;150;;;;;;;;");
        zutat = loadDummy(";0;Gulasch, Filet;Rind-Fleisch;g;120;160;;;;;;;;");
        zutat = loadDummy(";0;Hackfleisch;Rind-Fleisch;g;100;150;;;;;;;;");
        zutat = loadDummy(";0;Rindsfilet;Rind-Fleisch;g;;;;;;18;;;;");
        zutat = loadDummy(";0;Rindshuft;Rind-Fleisch;g;;;;;;18;;;;");
        zutat = loadDummy(";0;Roastbeef englisch;Rind-Fleisch;g;140;180;;;;18;;;;");
        zutat = loadDummy(";0;Schmorbraten;Rind-Fleisch;g;160;180;;;;;;;;");
        zutat = loadDummy(";0;Schnitzel;Rind-Fleisch;g;130;160;;;;;;;;");
        zutat = loadDummy(";0;Siedfleisch;Rind-Fleisch;g;160;200;;;;;;;;");
        zutat = loadDummy(";0;Siedfleisch - Brust;Rind-Fleisch;g;;;;;;34;;;;");
        zutat = loadDummy(";0;Siedfleisch - Schulter;Rind-Fleisch;g;;;;;;34;;;;");
        zutat = loadDummy(";0;Siedfleisch – ältere Tiere ;Rind-Fleisch;g;;;;;;40;;;;");
        zutat = loadDummy(";0;Siedfleisch – Federstück;Rind-Fleisch;g;;;;;;34;;;;");
        zutat = loadDummy(";0;Zunge ;Rind-Fleisch;g;;;;;;30;;;;");
        zutat = loadDummy(";0;Gemüse-Salat;Salat;g;100;100;gerüstet;;;;;;;");
        zutat = loadDummy(";0;Kopf-Salat;Salat;g;50;50;gerüstet;;;;;;;");
        zutat = loadDummy(";0;Nüssler;Salat;g;40;40;gerüstet;;;;;;;");
        zutat = loadDummy(";0;Salat-Teller;Salat;g;200;200;gerüstet;;;;;;;");
        zutat = loadDummy(";0;allgemein;Salat ;g;40;40;;;;;;;;");
        zutat = loadDummy(";0;Saucen Schwitze (Bindung);Salat ;g;;;;;;;;;;");
        zutat = loadDummy(";1;Salat-Sauce Saucen  50 g pro Liter;Sauce;dl;0.3;0.4;;;;;;;;");
        zutat = loadDummy(";0;von Geräuchertem;Schweine-Fleisch;g;;;;;;25;;;;");
        zutat = loadDummy(";0;Braten;Schweine-Fleisch;g;160;200;;;;22;;;;");
        zutat = loadDummy(";0;Geschnetzeltes;Schweine-Fleisch;g;120;150;à la minute;;;;;;;");
        zutat = loadDummy(";0;Kotelett;Schweine-Fleisch;g;150;200;;;;;;;;");
        zutat = loadDummy(";0;pochieren Schinken ;Schweine-Fleisch;g;;;;;;25;;;;");
        zutat = loadDummy(";0;Rippli;Schweine-Fleisch;g;150;180;;;;25;;;;");
        zutat = loadDummy(";0;Saucisson;Schweine-Fleisch;g;120;160;;;;;;;;");
        zutat = loadDummy(";0;Speck;Schweine-Fleisch;g;;;;;;34;;;;");
        zutat = loadDummy(";0;Speck  geräuchert;Schweine-Fleisch;g;;;;;;26;;;;");
        zutat = loadDummy(";0;Steak vom Nierstück;Schweine-Fleisch;g;130;170;;;;;;;;");
        zutat = loadDummy(";1;Bouillon;Suppe;dl;1.5;2;;;;;;;;");
        zutat = loadDummy(";1;Crème-Suppe;Suppe;dl;2;2.5;;;;;;;;");
        zutat = loadDummy(";1;Gemüse-Suppe Gemüse für Gemüsesuppen (ungerüstet) 400 g pro Liter;Suppe;dl;2;2.5;;;;;;;;");
        zutat = loadDummy(";1;Gemüse-zu Bouillon;Suppe;dl;2;2.5;;;;;;;;");
        zutat = loadDummy(";1;Gersten- Suppe Gerste 40 g pro Liter;Suppe;dl;2;2.5;;;;;;;;");
        zutat = loadDummy(";1;Griess-Suppe Griess 50 g pro Liter;Suppe;dl;2;2.5;;;;;;;;");
        zutat = loadDummy(";1;Haferflocken-Suppe Haferflocken 60 g pro Liter ;Suppe;dl;2;2.5;;;;;;;;");
        zutat = loadDummy(";1;Klare Suppe Gemüse als Einlage für klare Suppen (ungerüstet) 200 g pro Liter ;Suppe;dl;1.5;2;;;;;;;;");
        zutat = loadDummy(";1;klare Suppe mit Teigwaren ;Suppe;dl;2;2.5;;;;;;;;");
        zutat = loadDummy(";1;Kraftbrühe;Suppe;dl;1.5;2;;;;;;;;");
        zutat = loadDummy(";1;Purée-Suppe;Suppe;dl;2;2.5;;;;;;;;");
        zutat = loadDummy(";1;Purée-Suppe mit Hülsenfrüchten ;Suppe;dl;2;2.5;;;;;;;;");
        zutat = loadDummy(";1;Reis-Suppe Reis 40 g pro Liter;Suppe;dl;2;2.5;;;;;;;;");
        zutat = loadDummy(";1;Spezial-Suppe;Suppe;dl;1.5;2;;;;;;;;");
        zutat = loadDummy(";1;Crème-Suppe Mehl / Reismehl für Crèmesuppen (Bindung) 40 g pro Liter;Suppe;dl;2;2.5;;;;;;;;");

        System.out.println ("setup index " + searchIndex.keySet ().toString ());

    }

    public final class Vergleicher implements Comparator {

        Locale locale = null;

        public Vergleicher(Locale locale) {
            this.locale = locale;
        }

        @Override
        public int compare(Object o1, Object o2) {
            Collator collator = Collator.getInstance (locale);
            collator.setStrength (Collator.PRIMARY);
            return collator.compare (o1, o2);
        }
    }
}



/* jua mist
sprache system d = 0, e = 1, f = 2
        tabelle d = 1, e = 2, f = 0
*/

