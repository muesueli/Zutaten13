package com.hu.zutaten13;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.common.collect.ArrayListMultimap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static com.hu.zutaten13.Zutat.LIQUID;
import static com.hu.zutaten13.Zutat.NOTIZ;
import static com.hu.zutaten13.Zutat.SOLID;


/***************************************************************

 CD player Boot F2 > BIOS Pfeil rechts > SATA > escape

 completion ctrl + shift + enter
 block edit alt + shift + insert
 back ctrl + alt + links
 parameter ctrl + alt + links
 detach tab drag outside oder shaft + F4
 format JSON  ctrl + alt + L
 zoom  ctrl + rädli
 screen shot android home + power
 setting local history + alt + links

 Open project structure dialog	Control + Alt + Shift + S

 Find	Control + F
 Find next	F3
 Find previous	Shift + F3
 Replace	Control + R	Command + R

 Find in path	Control + Shift + F

 Surround with (if...else / try...catch / etc.)
 Complete statement	Control + Shift + Enter
 Quick documentation lookup	Control + Q	Control + J
 Show parameters for selected method	Control + P
 Comment/uncomment with line comment	Control +
 Comment/uncomment with block comment	Control + Shift + /

 Move to code block start	Control + [
 Move to code block end	Control + ]
 Select to the code block start	Control + Shift + [
 Select to the code block end	Control + Shift + ]

 Reformat code	Control + Alt + L

 Step over	F8
 Step into	F7
 Smart step into	Shift + F7
 Step out	Shift + F8
 Run to cursor	Alt + F9
 Evaluate expression	Alt + F8
 Resume program	F9
 Toggle breakpoint	Control + F8

 https://developer.android.com/studio/intro/keyboard-shortcuts.html
 *
 */

public class MainActivity extends AppCompatActivity {


// Sony google drive + Button hochladen (: menu links)interner speicher  android/data/hu.com.zutaten12

// emulator.exe -avd Pixel_XL_API_25_1 -netspeed full -netdelay none"


//  path emulator   ******************* Zutat zutatFile save /storage/sdcard/Android/data/hu.com.zutaten12/files/zutatlist.json
//  path vom Windows: Dieser PC\Galaxy Tab A\Card\Android\data\hu.com.zutaten12\files

// Zutaten20: Version mit Keep Interface

    Masse08 masse = new Masse08 (this);
    Op_Detail op_Detail = null;
    String filenameZutatList = "zutatlist.json";
    String rawZutat = "zutatlist_0214a";

    int maxId = 0;
    String lineBreak = "";

    SearchView searchView;
    //+ ListView list;

    public ListView zutatListSearch;
    public ArrayList<HashMap<String, String>> auswahl = new ArrayList<> ();

    int selektion = 0xFF03A9F4;

    public SimpleAdapter myAdapter;
    public Zutat pending_zutat = null;
    int zutatListPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById (R.id.toolbar);
        setSupportActionBar (toolbar);

        System.out.println ("******** onCreate ******** " + getLN () + " " + pending_zutat);

        /*** list view ***********************************************************************/

        zutatListSearch = findViewById (R.id.search_result_list); // ...adapterList
        zutatListSearch.setOnItemClickListener (new AdapterView.OnItemClickListener () {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                op_Detail = new Op_Detail (MainActivity.this);
                HashMap<String, String> zutatMap = (auswahl.get (position));
                int i = Integer.parseInt (zutatMap.get ("id"));
                for (Zutat zutat : masse.zutatenList) {
                    if (i == zutat.id) {
                        pending_zutat = zutat;
                        view.setBackgroundColor (selektion);
                        op_Detail.workZutat = pending_zutat;
                        if (pending_zutat.art == NOTIZ) {
                            op_Detail.dialog_buildNotiz ();
                        } else {
                            op_Detail.dialog_build ();
                        }
                        break;
                    }
                }
                zutatListPosition = position;
            }
        });

        /*** searchView **************************************************************************/

        searchView = findViewById (R.id.searchView);
        searchView.setOnQueryTextListener (new SearchView.OnQueryTextListener () {

            @Override
            public boolean onQueryTextSubmit(String query) {

                // System.out.println("********* onQueryTextSubmit " + query);
                if (query.equals ("")) {
                    refresh ();
                    return true;
                }

                if (zutatLookup (query, masse)) {
                    searchView.clearFocus ();
                    return true;
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                System.out.println ("********* onQueryTextChange |" + newText + "|");
                if (searchView.getQuery ().length () == 0) {
                    refresh ();
                    InputMethodManager imm = (InputMethodManager) getSystemService (Context.INPUT_METHOD_SERVICE);
                    if (imm.isAcceptingText ()) { // verify if the soft keyboard is open
                        imm.hideSoftInputFromWindow (getCurrentFocus ().getWindowToken (), 0);
                    }
                }
                return false;
            }
        });


        /*** share **************************************************************************

         Intent intent = getIntent();
         String action = intent.getAction();
         String type = intent.getType();

         if (Intent.ACTION_SEND.equals(action) && type != null) { // KEEP: geht nur mit Text receive from

         if (Intent.ACTION_SEND.equals(action)) {
         if ("text/plain".equals(type)) {
         sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
         ablage_zutatTv.setText(sharedText);
         //pending_zutat.daten += ("\n" + sharedText + "\n");
         System.out.println(sharedText);
         } else if (type.startsWith("image/")) {
         errorMsg("error", "toast02");
         // do nothing // Handle single image being sent
         } else if (Intent.ACTION_SEND_MULTIPLE.equals(action)) {
         if (type.startsWith("image/")) {
         errorMsg("error", "toast02");
         // handleSendMultipleImages(intent); // Handle multiple
         // images
         // being sent
         }
         } else {
         errorMsg("error", "toast02");
         // Handle other intents, such as being started from the home
         // screen
         }
         }
         }

         *****************************************************************************/

    }


    /**
     * @param teilText send to
     */

    public void menuShare(String teilText) {
        Intent sendIntent = new Intent ();
        sendIntent.setAction (Intent.ACTION_SEND);
        sendIntent.putExtra (Intent.EXTRA_TEXT, teilText);
        sendIntent.setType ("text/plain");
        startActivity (sendIntent);
    }

   /*=======================================================================*/

    public void refresh() {
        System.out.println ("******** refresh start");
        auswahl = new ArrayList<> ();
        masse.searchIndex = ArrayListMultimap.create ();
        for (Zutat zutat : masse.zutatenList) {
            masse.indexIt (zutat);
            zutatListBuildMap (zutat);
            if (maxId < zutat.id) {
                maxId = zutat.id;
            }
        }
        zutatListFuellen ();
        // fix searchView.setQuery("", false);
        System.out.println ("******** refresh end");
    }

    /*=======================================================================*/

    @Override
    public void onResume() {
        super.onResume ();  // Always call the superclass method first
        System.out.println ("******** onResume start ********" + getLN ());

        /* Checks if external storage is available for read and write */
        String state = Environment.getExternalStorageState ();
        if (!Environment.MEDIA_MOUNTED.equals (state)) {
            System.out.println ("******************* storage media not available");
            return;
        }

        File zutatFile = new File (getExternalFilesDir (null), filenameZutatList);

/*  produktion
        if (!zutatFile.exists ()) {
            try {
                Resources res = getResources ();
                int zutatId = this.getResources ().getIdentifier (rawZutat, "raw", getPackageName ());
                InputStream in_sp = res.openRawResource (zutatId);
                byte[] bz = new byte[in_sp.available ()];
                in_sp.read (bz);
                String sz = new String (bz);
                zutatenSave (sz);
            } catch (Exception e) {
                e.printStackTrace ();
            }
        }
        System.out.println ("setup index " + masse.searchIndex.keySet ().toString ());
        zutatenLoad ();
        refresh ();

*/
///////////////////////////////

// init masse
/*
        masse.zutatListInit();
        zutatFile.delete ();
        zutatenSave(null); // init basis
        for (Zutat zutat : masse.zutatenList) {
            maxId++;
            zutat.id = maxId;
            zutat.toStringComp (false);
        }
        refresh ();
*/
///////////////////////////////

/* load raw */
        zutatFile.delete ();
        if (!zutatFile.exists ()) {
            try {
                Resources res = getResources ();
                int zutatId = this.getResources ().getIdentifier (rawZutat, "raw", getPackageName ());
                InputStream in_sp = res.openRawResource (zutatId);
                byte[] bz = new byte[in_sp.available ()];
                in_sp.read (bz);
                String sz = new String (bz);
                zutatenSave (sz);
            } catch (Exception e) {
                e.printStackTrace ();
            }
        }
        // System.out.println("setup index " + masse.searchIndex.keySet().toString());
        zutatenLoad ();
        refresh ();


///////////////////////////////

        System.out.println ("******** onResume end =======" + getLN () + " " + pending_zutat);
    }

    /**
     *
     */
    protected void onPause() {
        super.onPause ();
        System.out.println ("******** onPause start ******** " + getLN () + " " + pending_zutat);
    }

    /* not used ???
        public void launchLookup() {
            searchView.setIconified(false);
        }
    */
    public boolean zutatLookup(String query, Masse08 masse) {

        char[] c = new char[query.length ()];
        for (int j = 0; j < query.length (); j++) {
            c[j] = query.charAt (j);
        }
        int laenge = masse.stg.stem (c, query.length ());
        String stem = masse.normalisieren (query.substring (0, laenge));
        Collection<Zutat> foundList = masse.searchIndex.get (stem);  // get collection

        if (foundList.size () == 0) {
            return false;
        }

        auswahl = new ArrayList<> ();

        for (Zutat foundZutat : foundList) {
            zutatListBuildMap (foundZutat);
        }

        /**----------------------------------------------------------------*/

        zutatListFuellen ();

        return true;
    }

    public void zutatListBuildMap(Zutat zutat) {
        HashMap<String, String> map = new HashMap<> ();
        map.put ("id", Integer.toString (zutat.id)); // zum auswahl suchen
        String s = getTitel (zutat);
        map.put ("bez", s + "\n");
        auswahl.add (map);
    }

    @NonNull
    public String getTitel(Zutat zutat) {
        String s = "";
        if (!zutat.gruppe.equals ("")) {
            s += zutat.gruppe + ": ";
        }
        s += zutat.bez[0];
        if (!zutat.props[0].equals ("")) {
            s += ", " + zutat.props[0];
        }
        return s;
    }

    public void zutatListFuellen() {

        Collections.sort (auswahl, new ZutatVergleicher ());

        myAdapter = new SimpleAdapter (this, auswahl, R.layout.zutat_list_row,
                new String[]{"bez", "props"}, new int[]{R.id.bezTv, R.id.propsTv});
        zutatListSearch.setAdapter (myAdapter);
        zutatListSearch.setSelection (zutatListPosition);
    }

    public final class ZutatVergleicher implements Comparator {

        Locale[] locals = {Locale.FRENCH, Locale.GERMAN, Locale.US};

        public ZutatVergleicher() {

        }

        @Override
        public int compare(Object o1, Object o2) {
            Collator collator = Collator.getInstance (locals[0]);
            collator.setStrength (Collator.PRIMARY);
            HashMap<String, String> h1 = (HashMap<String, String>) o1;
            HashMap<String, String> h2 = (HashMap<String, String>) o2;
            String s1 = h1.get ("bez");
            String s2 = h2.get ("bez");
            int comparison = collator.compare (s1, s2);
            return comparison;
        }
    }

    /*=======================================================================*/

    public String zutatenSave(String jsonZutatenList) {

        try {
            if (jsonZutatenList == null) {
                Gson gson = new GsonBuilder ()
                        .create ();
                jsonZutatenList = gson.toJson (masse.zutatenList);
            }
            File outputFile = new File (getExternalFilesDir (null), filenameZutatList);
            System.out.println ("******************* Zutat zutatFile save " + outputFile.getCanonicalPath ());
            // System.out.println ("******************* Zutat zutatFile save " + jsonZutatenList);
            Writer out = new BufferedWriter (new OutputStreamWriter (
                    new FileOutputStream (outputFile), "UTF-8"));
            out.write (jsonZutatenList);
            out.flush ();
            out.close ();
        } catch (Exception e) {
            e.printStackTrace ();
        }

        return jsonZutatenList;
    }

    public void zutatenLoad() {

        System.out.println ("******** load start ********" + getLN ());
        final Gson gson = new Gson ();

        try {

            masse.zutatenList.clear ();
            auswahl.clear ();
            if (myAdapter != null) {
                myAdapter.notifyDataSetChanged ();
            }

            File inputFile = new File (getExternalFilesDir (null), filenameZutatList);
            Type Zutat_TYPE = new TypeToken<List<Zutat>>() {}.getType();
            JsonReader reader = new JsonReader (new FileReader (inputFile));
            masse.zutatenList = gson.fromJson(reader, Zutat_TYPE);

            System.out.println ("******************* Zutat zutatFile load " + inputFile.getCanonicalPath ());
/*
            InputStream inputStream = new FileInputStream (inputFile);
            BufferedReader r = new BufferedReader (new InputStreamReader (inputStream));
            StringBuilder jsonTable = new StringBuilder ();
            String line;
            while ((line = r.readLine ()) != null) {
                jsonTable.append (line);
            }
            inputStream.close ();
            // System.out.println ("******************* Zutat zutatFile load " + jsonTable.toString ());
            Type collectionType2 = new TypeToken<List<Zutat>> () {
            }.getType ();
            masse.zutatenList = gson.fromJson (jsonTable.toString (), collectionType2); // abort
*/

            maxId = -1;

            for (Zutat zutat : masse.zutatenList) {
                maxId++;
                zutat.id = maxId;
                zutat.toStringComp (false);
            }

        } catch (Exception e) {
            e.printStackTrace ();
        }
    }


/*
    public void cleanfiles(View view) {  // delete app

        deleteData();
        String msg = "";

        // internal storage
        File rootPathInt = new File(getFilesDir(), "medi");
        try {
            File[] files = rootPathInt.listFiles();
            if (files != null) {
                for (File zutatFile : files) {
                    boolean b = zutatFile.delete();
                    if (b) {
                        msg = "delete zutatFile " + zutatFile.getAbsolutePath() + "\n";
                    }
                }
            }
            boolean b = rootPathInt.delete();
            msg += " rootPathInt " + rootPathInt.getAbsolutePath() + " " + b + "\n";
        } catch (Exception e) {
            msg += " exception " + e + "\n";
        }

        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            msg += "Cannot use storage \n";
            msgFiles.setText(msg);
            return;
        }

        File rootPathExt = new File(Environment.getExternalStorageDirectory(), "medi");
        try {

            File[] files = rootPathExt.listFiles();
            if (files != null) {
                for (File zutatFile : files) {
                    boolean b = zutatFile.delete();
                    if (b) {
                        msg += "delete zutatFile " + zutatFile.getAbsolutePath() + "\n";
                    }
                }
            }
            boolean b = rootPathExt.delete();
            msg += " rootPathExt " + rootPathExt.getAbsolutePath() + " " + b + "\n";
        } catch (Exception e) {
            msg += " exception " + e + "\n";
        }
        msgFiles.setText(msg);
    }
*/

     /*=======================================================================*/

    public Zutat getZutatById(int id) {
        for (Zutat zutat : masse.zutatenList) {
            if (zutat.id == id) {
                return zutat;
            }
        }
        return null;
    }

    /*=======================================================================*/
    public void errorMsg(String title, String msg) {

        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder (this).create ();
        alertDialog.setTitle (title);
        alertDialog.setMessage (msg);
        alertDialog.setButton (android.app.AlertDialog.BUTTON_NEUTRAL, "zurück",
                new DialogInterface.OnClickListener () {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss ();
                    }
                });
        alertDialog.show ();
    }

    public void toastIt(String msg) {
        Toast toast = Toast.makeText (this, msg, Toast.LENGTH_LONG);
        // toast.setGravity(Gravity.TOP | Gravity.LEFT, 500, 500);
        toast.show ();
    }

    /*========================================================*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.clear ();
        getMenuInflater ().inflate (R.menu.menu_main, menu);
        System.out.println ("******** onCreateOptionsMenu ======= " + getLN ());

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId ()) {
            case R.id.helpTop:
            case R.id.neuTop: {
                return true;
            }
        }
        switch (item.getItemId ()) {
            case R.id.neuSolid: {
                op_Detail = new Op_Detail (this);
                op_Detail.workZutat = new Zutat (99999, SOLID);
                op_Detail.dialog_build ();
                break;
            }
            case R.id.neuLiquid: {
                op_Detail = new Op_Detail (this);
                op_Detail.workZutat = new Zutat (99999, LIQUID);
                op_Detail.workZutat.mehrData = true;
                op_Detail.dialog_build ();
                break;
            }
            case R.id.neuNotiz: {
                op_Detail = new Op_Detail (this);
                op_Detail.workZutat = new Zutat (99999, NOTIZ);
                op_Detail.dialog_buildNotiz ();
                break;
            }

            case R.id.hilfe: {
                displayMenuDialog ("helpd.html", "Hilfe");
                break;
            }

            case R.id.dichte: {
                displayMenuDialog ("dichte.html", "Dichten");
                return true;
            }
            case R.id.masseD: {
                displayMenuDialog ("masseD.html", "Internationale Masse");
                return true;
            }
            case R.id.masseF: {
                displayMenuDialog ("masseF.html", "Mesures françaises");
                return true;
            }
            case R.id.masseE: {
                displayMenuDialog ("mengen.html", "US Measures");
                return true;
            }
        }

        return super.onOptionsItemSelected (item);
    }

    /*========================================================*/

    public void displayMenuDialog(String fn, String titel) {

        String localeSprache = Locale.getDefault ().getLanguage ();
        WebView myWebView = new WebView (MainActivity.this);
        myWebView.getSettings ().setBuiltInZoomControls (true);
        myWebView.getSettings ().setSupportZoom (true);
        myWebView.loadUrl ("File:///android_asset/" + fn);

        new AlertDialog.Builder (MainActivity.this).setView (myWebView).setTitle (titel).setPositiveButton ("OK", new DialogInterface.OnClickListener () {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel ();
            }
        }).show ();
    }

    /**********************************************************/

    public static String getLN() {
        String s = " " + Thread.currentThread ().getStackTrace ()[3].getLineNumber ()
                + " " + Thread.currentThread ().getStackTrace ()[4].getLineNumber ();
        return s;
    }

    public Bitmap screenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap (view.getWidth (),
                view.getHeight (), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas (bitmap);
        view.draw (canvas);
        return bitmap;

    }

    /**********************************************************/


}
