package com.hu.zutaten13;


public class Mass {

    int region = -1;
    String[] abk = new String[3];
    String[] bez = new String[3];
    String[] bezPlural = new String[3];
    String unit;
    //    String gewicht2volumen = "";
    boolean eindeutig = false;
    boolean redimension = false;
    float[] internatUnits = new float[3];
    String regionTransform = "";
    String transform2 = "";
    String volumeTransform = "";

    Masse08 masse;

    public Mass(Masse08 masse, int region, boolean eindeutig, String abkD, String bezD, String bezDPlural, String abkE, String bezE, String bezEPlural, String abkF, String bezF, String bezFPlural, String unit, boolean redimension, float anzahlI, float anzahlUS, float anzahlEU, String regionTransform, String volumeTransform, String transform2) {

        this.masse = masse;
        this.region = region;
        this.eindeutig = eindeutig;
        this.redimension = redimension;
        this.abk[0] = abkD;
        this.abk[1] = abkE;
        this.abk[2] = abkF;
        this.bez[0] = bezD;
        this.bez[1] = bezE;
        this.bez[2] = bezF;
        this.bezPlural[0] = bezDPlural;
        this.bezPlural[1] = bezEPlural;
        this.bezPlural[2] = bezFPlural;
        this.unit = unit;
        this.internatUnits[0] = anzahlI;
        this.internatUnits[1] = anzahlUS;
        this.internatUnits[2] = anzahlEU;
        this.regionTransform = regionTransform;
        this.transform2 = transform2;
        this.volumeTransform = volumeTransform;

        if (masse == null) {
            return;
        }

        // Log.e("Mass", "" + region + ";" + regionTransform + ";" + redimension + ";" + abkD + ";" + bezD + ";" + bezDPlural + ";" + abkE + ";" + bezE + ";" + bezEPlural + ";" + abkF + ";" + bezF + ";" + bezFPlural);


        for (int i = 0; i < 3; i++) {
            if (!abk[i].startsWith("_") && !abk[i].startsWith("?")) {
//                Mass dup = masse.massIndexList.get(i).get(abk[i]);
//                if (dup != null) {
//                    Log.e("dup mass: ", dup.abk[i] +" " +i + " " + dup.toString());
//                    Log.e("dup mass: ", abk[i] +" " +i + " " + this.toString());
//                }
                String abkuerzung = masse.einheitSpezial(abk[i]);
                masse.massIndexList.get(i).put(abkuerzung, this);
            }
            if (!bez[i].startsWith("_") && !bez[i].startsWith("?")) {
//                Mass dup = masse.massIndexList.get(i).get(bez[i]);
//                if (dup != null) {
//                    Log.e("dup mass: ", dup.bez[i] +" " +i + " " + dup.toString());
//                    Log.e("dup mass: ", bez[i] +" " +i + " " + this.toString());
//                }
                masse.massIndexList.get(i).put(bez[i], this);
            }
            if (!bezPlural[i].startsWith("_") && !bezPlural[i].startsWith("?")) {
//                Mass dup = masse.massIndexList.get(i).get(bezPlural[i]);
//                if (dup != null) {
//                    Log.e("dup mass: ", dup.bezPlural[i] +" " +i + " " + dup.toString());
//                     Log.e("dup mass: ", bezPlural[i] +" " +i + " " + this.toString());
//                }
                masse.massIndexList.get(i).put(bezPlural[i], this);
            }
        }
    }

    @Override
    public String toString() {
        return "" + region + ";"
                + bez[0] + ";"
                + unit + ";"
                + eindeutig + ";"
                + redimension + ";"
                + internatUnits[0] + ";"
                + internatUnits[1] + ";"
                + internatUnits[2] + ";"
                + regionTransform + ";"
                + transform2 + ";"
                + volumeTransform + ";";
    }
}
