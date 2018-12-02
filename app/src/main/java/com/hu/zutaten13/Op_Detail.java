package com.hu.zutaten13;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.util.ArrayList;

import static android.view.View.VISIBLE;
import static com.hu.zutaten13.Zutat.SOLID;

public class Op_Detail implements AdapterView.OnItemSelectedListener {

    LinearLayout rechnenLl;
    LinearLayout rechnen0Ll;
    LinearLayout datenTl;
    LinearLayout dichteLl;
    LinearLayout hilfsstoffLl;

    Switch  wenigerHeaderSw;
    Switch wenigerNotizenSw;
    Switch wenigerDataSw;
    Switch wenigerHilfsstoffSw;

    EditText bezEt;
    EditText zusatzEt;
    EditText gruppeEt;
    EditText notizEt;
    // Button pasteBt; jua
    Switch linksSw;

    EditText bezNEt;
    EditText zusatzNEt;
    EditText gruppeNEt;
    EditText notizNEt;
    Button pasteNBt;
    Switch  linksNSw;

    EditText portionEt;
    Spinner portionMassSp;
    Spinner stufeSp;

    EditText verlustRuestenEt;
    EditText verlustKochenEt;
    EditText verlustAbgangEt;

    EditText dichteMengeEt;
    Spinner dichteMengeMassSp;
    Spinner dichteProMassSp;

    EditText kochzeitEt;
    EditText temperaturBackenEt;
    EditText temperaturKernEt;

    Button hilfsstoffAddBt;
    EditText hilfsstoffBezEt;
    EditText hilfsstofffMengeEt;
    EditText hilfsstoffProMengeEt;
    Spinner hilfsstoffMassSp;
    Spinner hilfsstoffProMassSp;
    TextView hilfsstoffTv;
    ArrayList<Hilfsstoff> hilfsstoffe;
    SpannableStringBuilder hilfsstoffeSsb = new SpannableStringBuilder ();

    Button rechnenBt;

    EditText personenEt;

    public AlertDialog.Builder alertBuilder;
    public AlertDialog detailDialog;
    public MainActivity mainActivity;
    public View detailLayout;
    public View notizLayout;
    public Zutat workZutat;

    DecimalFormat df = new DecimalFormat ("#####.###");
    String resultat = "";
    String titel = "";

    Mass cm3_mass;
    Mass g_mass;
    String[] gewichtmasse;
    String[] volumenmasse;
    String[] laengenmasse;
    String[] portionensolidmasse;
    String[] stufen;
    String[] hilfsstoffProLiquid;
    String[] hilfsstoffProSolid;

    public Op_Detail(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void dialog_build() {

        cm3_mass = mainActivity.masse.massIndexList.get (0).get ("cm3");
        g_mass = mainActivity.masse.massIndexList.get (0).get ("g");
        gewichtmasse = mainActivity.getResources ().getStringArray (R.array.gewichtmasse);
        volumenmasse = mainActivity.getResources ().getStringArray (R.array.volumenmasse);
        laengenmasse = mainActivity.getResources ().getStringArray (R.array.laengenmasse);
        portionensolidmasse = mainActivity.getResources ().getStringArray (R.array.portionensolidmasse);
        stufen = mainActivity.getResources ().getStringArray (R.array.stufen);

        hilfsstoffProLiquid = mainActivity.getResources ().getStringArray (R.array.hilfsstoffProLiquid);
        hilfsstoffProSolid = mainActivity.getResources ().getStringArray (R.array.hilfsstoffProSolid);

        LayoutInflater inflater = mainActivity.getLayoutInflater ();
        detailLayout = inflater.inflate (R.layout.detail_landscape, null);

        bezEt = (EditText) detailLayout.findViewById (R.id.bezEt);
        zusatzEt = (EditText) detailLayout.findViewById (R.id.zusatzEt);
        gruppeEt = (EditText) detailLayout.findViewById (R.id.gruppeEt);
        portionMassSp = (Spinner) detailLayout.findViewById (R.id.portionMassSp);
        stufeSp = (Spinner) detailLayout.findViewById (R.id.stufeSp);

        notizEt = (EditText) detailLayout.findViewById (R.id.notizenEt);
        notizEt.setHorizontallyScrolling (true);

        verlustRuestenEt = (EditText) detailLayout.findViewById (R.id.verlustRuestenEt);
        verlustKochenEt = (EditText) detailLayout.findViewById (R.id.verlustKochenEt);
        verlustAbgangEt = (EditText) detailLayout.findViewById (R.id.verlustAbgangEt);

        dichteMengeMassSp = (Spinner) detailLayout.findViewById (R.id.dichteMengeMassSp);
        dichteProMassSp = (Spinner) detailLayout.findViewById (R.id.dichteProMassSp);
        dichteMengeEt = (EditText) detailLayout.findViewById (R.id.dichteMengeEt);

        hilfsstofffMengeEt = (EditText) detailLayout.findViewById (R.id.hilfsstoffMengeEt);
        hilfsstoffProMengeEt = (EditText) detailLayout.findViewById (R.id.hilfsstoffProMengeEt);
        hilfsstoffBezEt = (EditText) detailLayout.findViewById (R.id.hilfsstoffBezEt);
        hilfsstoffMassSp = (Spinner) detailLayout.findViewById (R.id.hilfsstoffMassSp);
        hilfsstoffProMassSp = (Spinner) detailLayout.findViewById (R.id.hilfsstoffProSp);
        hilfsstoffProMassSp.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener () {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String item = (String) hilfsstoffProMassSp.getSelectedItem ();
                if (item.equals ("Portion") || item.equals ("Essen")) {
                    hilfsstoffProMengeEt.setText ("1");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
        portionEt = (EditText) detailLayout.findViewById (R.id.portionEt);
        hilfsstoffTv = (TextView) detailLayout.findViewById (R.id.hilfsstoffTv);
        hilfsstoffLl = detailLayout.findViewById (R.id.hilfsstoffLl);

        ArrayAdapter<CharSequence> hilfsstoffMassAdapter;
        hilfsstoffMassAdapter = ArrayAdapter.createFromResource (mainActivity.getBaseContext (), R.array.hilfsstoffMasse, android.R.layout.simple_spinner_item);
        hilfsstoffMassAdapter.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
        hilfsstoffMassSp.setAdapter (hilfsstoffMassAdapter);

        hilfsstoffAddBt = (Button) detailLayout.findViewById (R.id.hilfsstoffAddBt);
        hilfsstoffAddBt.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {

                String text = hilfsstoffBezEt.getText ().toString ();
                String mengeString = hilfsstofffMengeEt.getText ().toString ();
                if (mengeString.isEmpty ()) {
                    hilfsstofffMengeEt.setError ("Fehler Menge ''");
                    return;
                }
                String proMengeString = hilfsstoffProMengeEt.getText ().toString ();
                if (proMengeString.isEmpty ()) {
                    hilfsstoffProMengeEt.setError ("Fehler Menge ''");
                    return;
                }
                String massString = hilfsstoffMassSp.getSelectedItem ().toString ();
                String proMassString = hilfsstoffProMassSp.getSelectedItem ().toString ();
                String zeile = text + ": "
                        + mengeString + " " + massString
                        + " für " + proMengeString + " " + proMassString;
                hilfsstoffeSsb.append (zeile + "\n");
                spansAufbauen (); // hs
            }
        });

        personenEt = (EditText) detailLayout.findViewById (R.id.personenEt);

        linksSw = (Switch) detailLayout.findViewById (R.id.linksSw);
        notizEt.setMovementMethod (LinkMovementMethod.getInstance ());
        notizEt.setAutoLinkMask (0);
        notizEt.setText (workZutat.notizString);
        linksSw.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String s = notizEt.getText ().toString ();
                if (isChecked) {
                    notizEt.setAutoLinkMask (Linkify.WEB_URLS);
                } else {
                    notizEt.setAutoLinkMask (0);
                }
                notizEt.setText (s);
            }
        });

        rechnenLl = (LinearLayout) detailLayout.findViewById (R.id.rechnenLl);
        rechnen0Ll = (LinearLayout) detailLayout.findViewById (R.id.rechnen0Ll);
        datenTl = (TableLayout) detailLayout.findViewById (R.id.datenTl);
        dichteLl = (LinearLayout) detailLayout.findViewById (R.id.dichteLl);

        wenigerHeaderSw = (Switch ) detailLayout.findViewById (R.id.mehrHeaderSw);
        wenigerHeaderSw.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    workZutat.mehrHeader = true;
                    rechnenLl.setVisibility (View.VISIBLE);
                    rechnen0Ll.setVisibility (View.VISIBLE);
                } else {
                    workZutat.mehrHeader = false;
                    rechnenLl.setVisibility (View.GONE);
                    rechnen0Ll.setVisibility (View.GONE);
                }
                detailLayout.invalidate();
            }
        });

        wenigerNotizenSw = (Switch ) detailLayout.findViewById (R.id.wenigerNotizenSw);
        wenigerNotizenSw.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    workZutat.mehrNotizen = true;
                    notizEt.setVisibility (VISIBLE);
                } else {
                    workZutat.mehrNotizen = false;
                    notizEt.setVisibility (View.GONE);
                }
                detailLayout.invalidate();
            }
        });

        wenigerDataSw = (Switch ) detailLayout.findViewById (R.id.mehrDataSw);
        wenigerDataSw.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    workZutat.mehrData = true;
                    datenTl.setVisibility (View.VISIBLE);
                    dichteLl.setVisibility (View.VISIBLE);
                } else {
                    workZutat.mehrData = false;
                    datenTl.setVisibility (View.GONE);
                    dichteLl.setVisibility (View.GONE);
                }
                detailLayout.invalidate();
            }
        });

        wenigerHilfsstoffSw = (Switch ) detailLayout.findViewById (R.id.mehrHilfsstoffSw);
        wenigerHilfsstoffSw.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    workZutat.mehrHilfsstoff = true;
                    hilfsstoffLl.setVisibility (VISIBLE);
                    hilfsstoffTv.setVisibility (VISIBLE);
                } else {
                    workZutat.mehrHilfsstoff = false;
                    hilfsstoffLl.setVisibility (View.GONE);
                    hilfsstoffTv.setVisibility (View.GONE);
                }
                detailLayout.invalidate();
            }
        });

        rechnenBt = (Button) detailLayout.findViewById (R.id.rechnenBt);
        rechnenBt.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {

                rechnen ();

                WebView myWebView = null;
                try {
                    String fnHTML = "resultat.html";
                    File outputFileHTML = new File (mainActivity.getFilesDir (), fnHTML);
                    Writer out = new BufferedWriter (new OutputStreamWriter (
                            new FileOutputStream (outputFileHTML), "UTF-8"));
                    out.write (resultat);
                    out.close ();

                    myWebView = new WebView (mainActivity.getApplicationContext ());
                    myWebView.loadUrl ("file:///" + outputFileHTML.getAbsolutePath ());
                } catch (Exception e) {
                    e.printStackTrace ();
                }

                AlertDialog resultatDialog = new AlertDialog.Builder (mainActivity).create ();

                resultatDialog.setTitle (titel);
                // resultatDialog.setMessage(message); braucht platz - schiebt buttons hinaus
                resultatDialog.setView (myWebView);
                resultatDialog.setButton (AlertDialog.BUTTON_NEUTRAL, mainActivity.getResources ().getString (R.string.back),
                        new DialogInterface.OnClickListener () {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss ();
                            }
                        });
                resultatDialog.setButton (AlertDialog.BUTTON_POSITIVE, mainActivity.getResources ().getString (R.string.share),
                        new DialogInterface.OnClickListener () {
                            public void onClick(DialogInterface dialog, int which) {
                                String rs0 = resultat.replace ("<p>", "\n");
                                String rs1 = rs0.replace ("</p>", "\n");
                                String rs2 = rs1.replace ("<br>", "\n");
                                mainActivity.menuShare (rs2);
                            }
                        });
                resultatDialog.setCanceledOnTouchOutside (false);
                resultatDialog.show ();

            }
        });

        bezEt.setText (workZutat.bez[0]);
        zusatzEt.setText (workZutat.props[0]);
        gruppeEt.setText (workZutat.gruppe);
        if (workZutat == mainActivity.pending_zutat) {
            notizEt.setText (workZutat.notizString);
        }

        portionEt.setText (workZutat.portion);

        /************************************************************/

        ArrayAdapter stufeArrayAdapter = null;
        ArrayAdapter<String> portionMassArrayAdapter = null;
        ArrayAdapter dichteMengeMassAdapter;
        ArrayAdapter dichteProMassAdapter;
        ArrayAdapter hilfsstoffProAdapter;

        if (workZutat.art == SOLID) {
            stufeArrayAdapter = new ArrayAdapter<> (mainActivity, android.R.layout.simple_spinner_dropdown_item, stufen);
            stufeSp.setAdapter (stufeArrayAdapter);
            stufeSp.setSelection (workZutat.stufe);

            portionMassArrayAdapter = new ArrayAdapter<> (mainActivity, android.R.layout.simple_spinner_dropdown_item,
                    portionensolidmasse);
            dichteMengeMassAdapter = new ArrayAdapter<> (mainActivity, android.R.layout.simple_spinner_dropdown_item,
                    volumenmasse);
            dichteProMassAdapter = new ArrayAdapter<> (mainActivity, android.R.layout.simple_spinner_dropdown_item,
                    gewichtmasse);
            hilfsstoffProAdapter = new ArrayAdapter<> (mainActivity, android.R.layout.simple_spinner_dropdown_item,
                    hilfsstoffProSolid);

        } else {
            stufeSp.setVisibility (View.GONE);

            portionMassArrayAdapter = new ArrayAdapter<> (mainActivity, android.R.layout.simple_spinner_dropdown_item,
                    volumenmasse);
            dichteMengeMassAdapter = new ArrayAdapter<> (mainActivity, android.R.layout.simple_spinner_dropdown_item,
                    gewichtmasse);
            dichteProMassAdapter = new ArrayAdapter<> (mainActivity, android.R.layout.simple_spinner_dropdown_item,
                    volumenmasse);
            hilfsstoffProAdapter = new ArrayAdapter<> (mainActivity, android.R.layout.simple_spinner_dropdown_item,
                    hilfsstoffProLiquid);
        }

        portionMassSp.setAdapter (portionMassArrayAdapter);
        portionMassSp.setSelection (workZutat.portionMass);

        dichteMengeEt.setText (workZutat.dichteMenge);

        dichteMengeMassSp.setAdapter (dichteMengeMassAdapter);
        dichteMengeMassSp.setSelection (workZutat.dichteMengeMass);

        dichteProMassSp.setAdapter (dichteProMassAdapter);
        dichteProMassSp.setSelection (workZutat.dichteProMass);

        hilfsstoffProMassSp.setAdapter (hilfsstoffProAdapter);
        hilfsstoffProMassSp.setSelection (1);

        /************************************************************/

        verlustRuestenEt.setText (workZutat.verlustRuesten);
        verlustKochenEt.setText (workZutat.verlustKochen);
        verlustAbgangEt.setText (workZutat.verlustAbgang);

        personenEt.setText (Integer.toString (workZutat.anzahlPersonen));

        hilfsstoffeSsb.append (workZutat.hilfsstoffeString);
        spansAufbauen (); // hs

        alertBuilder = new AlertDialog.Builder (mainActivity, R.style.DialogTheme);

        
        detailDialog = dialogInit (alertBuilder, detailLayout);
        detailDialog.setOnShowListener (new DialogInterface.OnShowListener () {
            @Override
            public void onShow(DialogInterface dialog) {
                Button bneg = detailDialog.getButton (AlertDialog.BUTTON_NEGATIVE); // speichern
                bneg.setOnClickListener (new View.OnClickListener () {
                    @Override
                    public void onClick(View view) {

                        getScreenData ();

                        if (workZutat != mainActivity.pending_zutat) { // insert
                            mainActivity.pending_zutat = workZutat;
                            mainActivity.maxId++;
                            workZutat.id = mainActivity.maxId;
                            mainActivity.masse.zutatenList.add (workZutat);
                        }

                        detailDialog.dismiss ();
                        mainActivity.zutatenSave (null);
                        mainActivity.refresh ();
                    }
                });
                Button bneu = detailDialog.getButton (AlertDialog.BUTTON_NEUTRAL); // zurück
                bneu.setOnClickListener (new View.OnClickListener () {
                    @Override
                    public void onClick(View view) {
                        mainActivity.refresh (); // loescht selektion
                        detailDialog.dismiss ();
                    }
                });
                Button bpos = detailDialog.getButton (AlertDialog.BUTTON_POSITIVE); // löschen
                bpos.setOnClickListener (new View.OnClickListener () {
                    @Override
                    public void onClick(View view) {
                        mainActivity.masse.zutatenList.remove (workZutat);
                        mainActivity.zutatenSave (null);
                        mainActivity.refresh (); // loescht selektion
                        detailDialog.dismiss ();
                    }
                });
            }
        });
        detailDialog.show ();

    }

    private AlertDialog dialogInit(AlertDialog.Builder alertBuilder, View layout) {
        alertBuilder = new AlertDialog.Builder (mainActivity, R.style.DialogTheme);
        alertBuilder.setView (layout);
        alertBuilder.setCancelable (false); // click neben fenster scliesst dialog
        alertBuilder.setNeutralButton ("zurück", null);
        alertBuilder.setPositiveButton ("löschen", null);
        alertBuilder.setNegativeButton ("speichern", null);

        AlertDialog dialog = alertBuilder.create ();
        dialog.requestWindowFeature (Window.FEATURE_NO_TITLE);
        dialog.getWindow ().setSoftInputMode (WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.getWindow ().setBackgroundDrawable (new ColorDrawable (Color.TRANSPARENT));
        return dialog;
    }

    public void dialog_buildNotiz() {

        LayoutInflater inflater = mainActivity.getLayoutInflater ();
        notizLayout = inflater.inflate (R.layout.notiz, null);

        bezNEt = (EditText) notizLayout.findViewById (R.id.bezNEt);
        zusatzNEt = (EditText) notizLayout.findViewById (R.id.zusatzNEt);
        gruppeNEt = (EditText) notizLayout.findViewById (R.id.gruppeNEt);
        notizNEt = (EditText) notizLayout.findViewById (R.id.notizenNEt);
        notizNEt.setHorizontallyScrolling (true);

        bezNEt.setText (workZutat.bez[0]);
        zusatzNEt.setText (workZutat.props[0]);
        gruppeNEt.setText (workZutat.gruppe);

        linksNSw = (Switch) notizLayout.findViewById (R.id.linksNSw);
        notizNEt.setMovementMethod (LinkMovementMethod.getInstance ());
        notizNEt.setAutoLinkMask (0);
        notizNEt.setText (workZutat.notizString);
        linksNSw.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String s = notizNEt.getText ().toString ();
                if (isChecked) {
                    notizNEt.setAutoLinkMask (Linkify.WEB_URLS);
                } else {
                    notizNEt.setAutoLinkMask (0);
                }
                notizNEt.setText (s);
            }
        });

        alertBuilder = new AlertDialog.Builder (mainActivity, R.style.DialogTheme);
        detailDialog = dialogInit (alertBuilder, notizLayout);
        detailDialog.setOnShowListener (new DialogInterface.OnShowListener () {

            @Override
            public void onShow(DialogInterface dialog) {
                Button bneg = detailDialog.getButton (AlertDialog.BUTTON_NEGATIVE); // speichern
                bneg.setOnClickListener (new View.OnClickListener () {
                    @Override
                    public void onClick(View view) {
                        try {

                            getNotizScreenData ();

                            if (workZutat != mainActivity.pending_zutat) { // insert
                                mainActivity.pending_zutat = workZutat;
                                mainActivity.maxId++;
                                workZutat.id = mainActivity.maxId;
                                mainActivity.masse.zutatenList.add (workZutat);
                            }

                            detailDialog.dismiss ();
                            mainActivity.zutatenSave (null);
                            mainActivity.refresh ();

                        } catch (Exception e) {
                            e.printStackTrace ();
                        }
                    }
                });
                Button bneu = detailDialog.getButton (AlertDialog.BUTTON_NEUTRAL); // zurück
                bneu.setOnClickListener (new View.OnClickListener () {
                    @Override
                    public void onClick(View view) {
                        detailDialog.dismiss ();
                        mainActivity.refresh (); // loescht selektion
                    }
                });
                Button bpos = detailDialog.getButton (AlertDialog.BUTTON_POSITIVE); // löschen
                bpos.setOnClickListener (new View.OnClickListener () {
                    @Override
                    public void onClick(View view) {
                        mainActivity.masse.zutatenList.remove (workZutat);
                        detailDialog.dismiss ();
                        mainActivity.zutatenSave (null);
                        mainActivity.refresh (); // loescht selektion
                    }
                });
            }
        });
        detailDialog.show ();
    }

    private void getNotizScreenData() {

        workZutat.bez[0] = bezNEt.getText ().toString ();
        workZutat.props[0] = zusatzNEt.getText ().toString ();
        workZutat.gruppe = gruppeNEt.getText ().toString ();
        workZutat.notizString = notizNEt.getText ().toString ();
    }

    private void getScreenData() {

        workZutat.bez[0] = bezEt.getText ().toString ();
        if (workZutat.bez[0].equals ("")) {
            workZutat.bez[0] = "???";
        }
        workZutat.props[0] = zusatzEt.getText ().toString ();
        workZutat.gruppe = gruppeEt.getText ().toString ();
        workZutat.hilfsstoffeString = hilfsstoffeSsb.toString ();
        workZutat.notizString = notizEt.getText ().toString ();

        workZutat.portion = portionEt.getText ().toString ();
        workZutat.portionMass = portionMassSp.getSelectedItemPosition ();

        workZutat.stufe = stufeSp.getSelectedItemPosition ();

        workZutat.verlustRuesten = verlustRuestenEt.getText ().toString ();
        workZutat.verlustKochen = verlustKochenEt.getText ().toString ();
        workZutat.verlustAbgang = verlustAbgangEt.getText ().toString ();

        workZutat.dichteMengeMass = dichteMengeMassSp.getSelectedItemPosition ();
        workZutat.dichteProMass = dichteProMassSp.getSelectedItemPosition ();
        workZutat.dichteMenge = dichteMengeEt.getText ().toString ();

        String s1 = personenEt.getText ().toString ();
        if (s1.equals ("")) {
            workZutat.anzahlPersonen = 4;
        } else {
            workZutat.anzahlPersonen = Integer.parseInt (s1);
        }
    }

    private int validInteger(String msg, EditText et) {
        String modString = et.getText ().toString ();
        int i;
        try {
            i = Integer.parseInt (modString);
        } catch (Exception e) {
            et.setError (msg);
            return Integer.MIN_VALUE;
        }
        if (msg.equals ("Fehler Inhalt") && i < 1) {
            et.setError (msg);
            return Integer.MIN_VALUE;
        }

        return i;
    }


    private float parseData(String daten) {
        float floatWert = -1.f;
        if (!daten.equals ("")) {
            floatWert = Float.parseFloat (daten);
        }
        return floatWert;
    }

    public void rechnen() {

        getScreenData ();


        float personen = (float) workZutat.anzahlPersonen;

        float portionData = parseData (workZutat.portion);

        if (portionData == -1f) {
            resultat = "<p>Portion nicht vorhanden</p>";
            return;
        }

        titel = mainActivity.getTitel (workZutat);

        resultat = "<p>" + workZutat.anzahlPersonen + " Personen<br>";

        String abk = null;
        String titelstufe = "";
        if (workZutat.art == SOLID) {
            abk = portionensolidmasse[workZutat.portionMass];
            titelstufe = " (" + stufen[workZutat.stufe] + ")";
        } else {
            abk = volumenmasse[workZutat.portionMass];
        }
        Mass mass0 = mainActivity.masse.massIndexList.get (0).get (abk); // bis einheit gefunden wird
        float menge0 = personen * portionData;

        resultat += "..." + df.format (portionData)
                + " " + mass0.abk[0] + titelstufe
                + " * " + df.format (personen) + " = ";
        einheitMod (menge0, mass0);
        resultat += "</p>";

        resultat += "<br>********************************<br>";

        rechnen_hilfsstoff_Essen ();
        rechnen_hilfsstoff_portion ();

        float prozRuesten = parseData (workZutat.verlustRuesten);
        float prozKochen = parseData (workZutat.verlustKochen);
        float prozAbgang = parseData (workZutat.verlustAbgang);

        float mengeGerüstet = menge0;

        verlustTitel = false;
        float mengeCurrent = menge0;
        Verlust verlustRuesten = new Verlust (mass0, prozRuesten);
        Verlust verlustKochen = new Verlust (mass0, prozKochen);
        Verlust verlustAbgang = new Verlust (mass0, prozAbgang);

        switch (workZutat.stufe) {
            case 0: {
                mengeCurrent = rechnen_netto (verlustRuesten, mengeCurrent);
                mengeGerüstet = mengeCurrent;
                mengeCurrent = rechnen_netto (verlustKochen, mengeCurrent);
                mengeCurrent = rechnen_netto (verlustAbgang, mengeCurrent);
                break;
            }
            case 1: {
                mengeGerüstet = mengeCurrent;
                rechnen_brutto (verlustRuesten, mengeCurrent);
                mengeCurrent = rechnen_netto (verlustKochen, mengeGerüstet);
                mengeCurrent = rechnen_netto (verlustAbgang, mengeCurrent);
                break;
            }
            case 2: {
                float mengeGekocht = mengeCurrent;
                mengeCurrent = rechnen_brutto (verlustKochen, mengeCurrent);
                mengeGerüstet = mengeCurrent;
                mengeCurrent = rechnen_brutto (verlustRuesten, mengeCurrent);
                mengeCurrent = rechnen_netto (verlustAbgang, mengeGekocht);
                break;
            }
            case 3: {
                mengeCurrent = rechnen_brutto (verlustAbgang, mengeCurrent);
                mengeCurrent = rechnen_brutto (verlustKochen, mengeCurrent);
                mengeGerüstet = mengeCurrent;
                mengeCurrent = rechnen_brutto (verlustRuesten, mengeCurrent);
                break;
            }
        }

        verlust_drucken (verlustRuesten, "Rüsten");
        verlust_drucken (verlustKochen, "Kochen");
        verlust_drucken (verlustAbgang, "Abgang");

        if (verlustTitel) {
            resultat += "</p>";
        }

        rechnen_dichte (mengeGerüstet, mass0);
        rechnen_hilfsstoff_menge (mengeGerüstet, mass0);

        resultat += "<br>********************************<br>";

    }


    private void einheitMod(float menge0, Mass mass0) {

        int idx = mainActivity.masse.ml.indexOf (mass0);

        if (menge0 < 1.0d) {

            System.out.println ("faktorMasse hinab ===================================");

            Mass massNext = mass0;
            float mengeNext = menge0;

//            System.out.println("faktorMasse"
//                    + " " + mass0.abk[0]
//                    + "/" + mass0.internatUnits[mass0.region]
//                    + " " + menge0);

            for (int i = idx - 1; i >= 0; i--) {

                Mass massTemp = mainActivity.masse.ml.get (i);
                if (!massTemp.redimension) { // auf dieses Mass redimensionieren?
                    System.out.println ("faktorMasse continue redimension " + massTemp.abk[0]);
                    continue;
                }
                if (!massTemp.unit.equals (mass0.unit)) { // keine verkleinerung mehr, cm3, ml, m
                    System.out.println ("faktorMasse continue unit " + massTemp.unit + " --- " + mass0.unit);
                    continue;
                }

                massNext = massTemp;
                float faktorMasse = mass0.internatUnits[mass0.region] / massNext.internatUnits[massNext.region];
                mengeNext = (float) (faktorMasse * menge0);

//                System.out.println("faktorMasse"
//                        + " ------ " + massNext.abk[0]
//                        + "/" + massNext.internatUnits[massNext.region]
//                        + " " + mengeNext
//                        + " HHH " + faktorMasse);

                if (mengeNext >= 1.0d) {
                    break;
                }
            }
            if (menge0 == mengeNext) {
                String format0 = "%.2f %s";
                resultat += String.format (format0, menge0, mass0.abk[0]);
            } else {
                String format0 = "%.2f %s = %.2f %s";
                resultat += String.format (format0, menge0, mass0.abk[0], mengeNext, massNext.abk[0]);
            }
            return;

        } else {

            System.out.println ("faktorMasse hinauf ===================================");

            Mass massNext = mass0;
            float mengeNext = menge0;
            Mass massMod = mass0;
            float mengeMod = menge0;

            for (int i = idx + 1; i < mainActivity.masse.limitStandardMasse; i++) {

                massNext = mainActivity.masse.ml.get (i);

                if (!massNext.redimension) { // auf dieses Mass redimensionieren?
                    continue;
                }
                if (!massNext.unit.equals (mass0.unit)) { // keine vergroesserung mehr, cm3, ml, m
                    // Log.e("einheitVergroessern", "return p04");
                    continue;
                }

                float faktorMasse = mass0.internatUnits[mass0.region] / massNext.internatUnits[massNext.region];
                mengeNext = (float) (faktorMasse * menge0);

                if (mengeNext < 1.0d) {
                    break;
                }
                mengeMod = mengeNext;
                massMod = massNext;
            }

            if (menge0 == mengeMod) {
                String format0 = "%.2f %s";
                resultat += String.format (format0, menge0, mass0.abk[0]);
            } else {
                String format0 = "%.2f %s = %.2f %s";
                resultat += String.format (format0, menge0, mass0.abk[0], mengeMod, massMod.abk[0]);
            }
            return;
        }
    }

    public class Verlust {
        float menge_vor = 0f;
        float menge_nach = 0f;
        float verlust = 0f;
        float proz = 0f;
        float faktor = 0f;
        Mass mass;

        public Verlust(Mass mass0, float proz) {
            this.proz = proz;
            this.mass = mass0;
        }
    }

    boolean verlustTitel = false;

    public void verlust_drucken(Verlust verlust, String verlustArt) {

        if (verlust.proz == -1) {
            return;
        }

        if (verlustTitel == false) {
            resultat += "<p>Verluste <br>";
            verlustTitel = true;
        }
        resultat += "<br>" + verlustArt;
        resultat += ": " + df.format (verlust.proz)
                + " % von "
                + df.format (verlust.menge_vor)
                + " " + verlust.mass.abk[0] + " = ";
        einheitMod (verlust.verlust, verlust.mass);
        resultat += "<br>...Rest ";
        einheitMod (verlust.menge_nach, verlust.mass);
    }

    public float rechnen_brutto(Verlust verlust, float mengeCurrent) {

        if (verlust.proz == -1f) {
            return mengeCurrent;
        }

        verlust.menge_nach = mengeCurrent;
        verlust.faktor = 100 / (100f - verlust.proz);
        verlust.menge_vor = verlust.menge_nach * verlust.faktor;
        verlust.verlust = verlust.menge_vor - verlust.menge_nach;
        return verlust.menge_vor;
    }

    public float rechnen_netto(Verlust verlust, float mengeCurrent) {
        if (verlust.proz == -1f) {
            return mengeCurrent;
        }

        verlust.menge_vor = mengeCurrent;
        verlust.faktor = (100f - verlust.proz) / 100;
        verlust.verlust = verlust.menge_vor * (verlust.proz / 100f);
        verlust.menge_nach = verlust.menge_vor - verlust.verlust;

        return verlust.menge_nach;
    }

    public void spansList() {
        for (BackgroundColorSpan span : hilfsstoffeSsb.getSpans (0, hilfsstoffeSsb.length (), BackgroundColorSpan.class)) {
            int spanStart = hilfsstoffeSsb.getSpanStart (span);
            int spanEnd = hilfsstoffeSsb.getSpanEnd (span);
            int spanFlags = hilfsstoffeSsb.getSpanFlags (span);
            //System.out.println ("spansList Background span " + spanStart + "/" + spanEnd + "/" + spanFlags + " " + hilfsstoffeSsb.subSequence (spanStart, spanEnd));
        }
        //System.out.println ("spansList Background hilfsstoffeSsb " + hilfsstoffeSsb.length () + " " + hilfsstoffeSsb.toString ());
        for (ForegroundColorSpan span : hilfsstoffeSsb.getSpans (0, hilfsstoffeSsb.length (), ForegroundColorSpan.class)) {
            int spanStart = hilfsstoffeSsb.getSpanStart (span);
            int spanEnd = hilfsstoffeSsb.getSpanEnd (span);
            int spanFlags = hilfsstoffeSsb.getSpanFlags (span);
        }
    }


    private void rechnen_hilfsstoff_menge(float menge_netto, Mass mass) {

        boolean titelPrinted = false;

        for (Hilfsstoff hilfsstoff : hilfsstoffe) {

            if (hilfsstoff.proMassString.equals ("Essen")) {
                continue;
            }
            if (hilfsstoff.proMassString.equals ("Portion")) {
                continue;
            }
            if (titelPrinted == false) {
                resultat += "<p>Hilfsstoffe gemäss Menge " + df.format (menge_netto) + " " + mass.abk[0] + "<br>";
                titelPrinted = true;
            }


            float wareUnits = mass.internatUnits[mass.region];
            String massAbk = mass.abk[0];
            float proUnits = hilfsstoff.proMass.internatUnits[hilfsstoff.proMass.region];
            String massProAbk = hilfsstoff.proMass.abk[0];
            float faktor1 = wareUnits / proUnits;
            float faktor2 = menge_netto * faktor1;
            float faktor3 = faktor2 / hilfsstoff.proMenge;
            float mengeHilfsstoff = hilfsstoff.menge * faktor3;

           resultat += "..." + hilfsstoff.text + ": " + df.format (hilfsstoff.menge)
                    + " " + hilfsstoff.mass.abk[0]
                    + " für " + df.format (hilfsstoff.proMenge) + " " + hilfsstoff.proMass.abk[0] + "<br>";

            resultat +=
                    "........Masse: " + massAbk + " / " + massProAbk + " = "
                            + wareUnits + "/" + proUnits + " = " + faktor1 + "<br>";

            resultat +=
                    "........Menge in " + massProAbk + ": = " + df.format (menge_netto) + " * " + df.format (faktor1)
                            + " = " + df.format (faktor2) + "<br>";

            resultat +=
                    "........Dosen: " + df.format (faktor2) + " " + massProAbk + " / " + df.format (hilfsstoff.proMenge) + " " + massProAbk
                            + " = " + df.format (faktor3) + "<br>";

            resultat +=
                    "........Hilfsstoff: " + df.format (hilfsstoff.menge) + " " + hilfsstoff.massString + " * " + df.format (faktor3) + "<br>";

            resultat +=
                    "........= ";
            einheitMod (mengeHilfsstoff, hilfsstoff.mass);

            resultat += "<br><br>";
        }

        if (hilfsstoffe.size () > 0) { // jua
            resultat += "</p>";
        }

    }


    /**
     * *** spans aufbauen ***********************************************************
     */

    /**
     * *** spans aufbauen ***********************************************************
     */

    void spansAufbauen() {

        int start = 0;
        int end;
        String[] zeilen = hilfsstoffeSsb.toString ().split ("\n");
        hilfsstoffe = new ArrayList<> ();
        hilfsstoffeSsb = new SpannableStringBuilder ();

        for (String zeile : zeilen) {

            if (zeile.equals ("")) {
                continue;
            }

            Hilfsstoff hs = new Hilfsstoff (zeile);
            hilfsstoffe.add (hs);

            hilfsstoffeSsb.append (zeile);
            end = start + zeile.length ();
            hs.start = start;
            hs.end = end;
            hilfsstoffeSsb.append ("\n");
            System.out.println ("spansAufbauen " + zeile.length () + " " + start + "/" + end + " " + zeile);
            hilfsstoffeSsb.setSpan (hs, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            // workZutat.hilfsstoffeSsb.setSpan (new ForegroundColorSpan (Color.CYAN), start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            start = end;
            start++;
        }
        hilfsstoffTv.setMovementMethod (LinkMovementMethod.getInstance ());
        hilfsstoffTv.setText (hilfsstoffeSsb, TextView.BufferType.SPANNABLE);
        spansList ();
    }

    private void rechnen_hilfsstoff_Essen() {

        // jua fehlercheck

        boolean titelPrinted = false;
        for (Hilfsstoff hilfsstoff : hilfsstoffe) {

            if (!hilfsstoff.proMassString.equals ("Essen")) {
                continue;
            }

            if (titelPrinted == false) {
                resultat += "<p>Hilfsstoffe pro Essen<br>";
                titelPrinted = true;
            }

            resultat += "..." + hilfsstoff.text
                    + " " + df.format (hilfsstoff.menge)
                    + " " + hilfsstoff.massString;
            resultat += "<br>";
        }
        if (titelPrinted) {
            resultat += "</p>";
        }
    }

    private void rechnen_hilfsstoff_portion() {

        // jua fehlercheck

        boolean titelPrinted = false;
        for (Hilfsstoff hilfsstoff : hilfsstoffe) {

            if (!hilfsstoff.proMassString.equals ("Portion")) {
                continue;
            }

            if (titelPrinted == false) {
                resultat += "<p>Hilfsstoffe pro Portion<br>";
                titelPrinted = true;
            }

            float ergebnis = hilfsstoff.menge * ((float) workZutat.anzahlPersonen);
            resultat += "..." + hilfsstoff.text
                    + " " + df.format (workZutat.anzahlPersonen)
                    + " * " + df.format (hilfsstoff.menge)
                    + " " + hilfsstoff.mass.abk[0] + " ---> ";
            einheitMod (ergebnis, hilfsstoff.mass);
            resultat += "<br>";
        }
        if (titelPrinted) {
            resultat += "</p>";
        }
    }

    private void rechnen_dichte(float menge0, Mass mass0) { // 500 g

        if (workZutat.dichteMenge.equals ("")) {
            return;
        }

        String dichteProMass_abk;
        String dichteMengeMass_abk;
        String dichteText0;
        String dichteText1;
        if (workZutat.art == SOLID) {
            dichteProMass_abk = gewichtmasse[workZutat.dichteProMass];
            dichteMengeMass_abk = volumenmasse[workZutat.dichteMengeMass];
            dichteText1 = " cm3/g";
            dichteText0 = " Volumen";
        } else {
            dichteProMass_abk = volumenmasse[workZutat.dichteProMass];
            dichteMengeMass_abk = gewichtmasse[workZutat.dichteMengeMass];
            dichteText1 = " g/cm3";
            dichteText0 = " Gewicht";
        }
        Mass dichteProMass = mainActivity.masse.massIndexList.get (0).get (dichteProMass_abk);
        float intUnitDichteProMass = dichteProMass.internatUnits[dichteProMass.region];

        Mass dichteMengeMass = mainActivity.masse.massIndexList.get (0).get (dichteMengeMass_abk);
        float intUnitdichteMengeMass = dichteMengeMass.internatUnits[dichteMengeMass.region];

        float dichteMenge = Float.parseFloat (workZutat.dichteMenge);
        float dichteMenge_in_intUnits = dichteMenge * intUnitdichteMengeMass;
        float dichte = dichteMenge_in_intUnits / intUnitDichteProMass;

        float intUnitMass0 = mass0.internatUnits[mass0.region];
        float menge0_in_proUnits = (menge0 * intUnitMass0) / intUnitDichteProMass;
        float volumen = dichteMenge * menge0_in_proUnits;

        resultat += "<p>" + dichteText0 + "<br>";
        resultat += "...Dichte: " + df.format (dichteMenge);
        if (workZutat.art == SOLID) {
            resultat += " " + volumenmasse[workZutat.dichteMengeMass];
            resultat += " / " + gewichtmasse[workZutat.dichteProMass];
        } else {
            resultat += " " + gewichtmasse[workZutat.dichteMengeMass];
            resultat += " / " + volumenmasse[workZutat.dichteProMass];
        }
        resultat += " ---> " + df.format (dichte) + dichteText1;
        resultat += "<br>..." + df.format (menge0)
                + " " + mass0.abk[0]
                + " = " + df.format (menge0_in_proUnits) + " " + dichteProMass_abk;

        resultat += "<br>..." + df.format (menge0_in_proUnits)
                + " " + dichteProMass_abk + " * " + df.format (dichteMenge) + " = ";
        einheitMod (volumen, dichteMengeMass);

        resultat += "</p>";
    }

    private float validFloat(String msg, EditText et) {
        String modString = et.getText ().toString ();
        float f;
        try {
            f = Float.parseFloat (modString);
        } catch (Exception e) {
            et.setError (msg);
            return Float.MIN_VALUE;
        }
        return f;
    }

    @Override // notwendig
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }

    @Override  // notwendig
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    public class Hilfsstoff extends ClickableSpan {

        String text;

        float menge;
        Mass mass = null;
        String massString;

        float proMenge;
        Mass proMass = null;
        String proMassString;

        String zeile;
        int start = -1;
        int end = -1;

        public Hilfsstoff(String zeile) {
            this.zeile = zeile;
            // text + ": " + mengeString + " " + massString + " für " + proMengeString + " " + proMassString;
            String[] s0 = zeile.split (": ");
            text = s0[0];
            String[] s1 = s0[1].split (" für ");
            String[] links = s1[0].split (" ");
            String[] rechts = s1[1].split (" ");

            menge = Float.parseFloat (links[0].trim ());
            massString = links[1].trim ();
            if (!massString.equals ("Stück")) {
                mass = mainActivity.masse.massIndexList.get (0).get (massString);
            }

            proMenge = Float.parseFloat (rechts[0].trim ());
            proMassString = rechts[1].trim ();
            if (!proMassString.equals ("Stück")) {
                proMass = mainActivity.masse.massIndexList.get (0).get (proMassString);
            }
        }

        @Override
        public String toString() {
            return "Hilfsstoff{" +
                    ", start=" + start +
                    ", end=" + end +
                    ", zeile='" + zeile + '\'' +
                    " menge=" + menge +
                    ", mass=" + mass +
                    ", massString='" + massString + '\'' +
                    "  proMenge=" + proMenge +
                    ", proMass=" + proMass +
                    ", proMassString='" + proMassString + '\'' +
                    ", text='" + text +
                    '}';
        }

        @Override
        public void onClick(View widget) {
            System.out.println ("ClickableSpan");
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener () {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            System.out.println ("span  onClick Hilfsstoff " + this.toString ());
                            System.out.println ("spansList onClick " + hilfsstoffeSsb.length () + " " + hilfsstoffeSsb.toString ());
                            System.out.println ("spansList onClick span " + start + "/" + end + " " + hilfsstoffeSsb.subSequence (start, end));
                            hilfsstoffeSsb.delete (start, end + 1);
                            System.out.println ("spansList onClick " + hilfsstoffeSsb.length () + " " + hilfsstoffeSsb.toString ());
                            spansAufbauen (); // hs 
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };
            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder (mainActivity);
            builder.setMessage ("Zeile '" + zeile + "' löschen?").setPositiveButton ("Ja", dialogClickListener)
                    .setNegativeButton ("Nein", dialogClickListener).show ();

            System.out.println ("ClickableSpan");
        }
    }
}