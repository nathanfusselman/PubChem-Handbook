package com.example.pubchem_chemistry_handbook;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.downloader.PRDownloader;
import com.example.pubchem_chemistry_handbook.data.Compound;
import com.example.pubchem_chemistry_handbook.data.Element;
import com.example.pubchem_chemistry_handbook.data.global;
import com.example.pubchem_chemistry_handbook.ui.compound.CompoundFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    global global = new global(0,0);
    public static Compound globalCompound = new Compound(0,"0","0");
    public static int globalCurPos;
    public static String pSearchQuery="";
    public boolean updating = false;

    @Override
    public void onBackPressed() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onBackPressed();
        try{
          if(((getWindow().getCurrentFocus())!=null)&&CompoundFragment.fragExists) {
              CompoundFragment.onBackPressed(getWindow().getCurrentFocus());
          }
        }catch(Exception e){}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PRDownloader.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_news, R.id.navigation_search, R.id.navigation_favorites, R.id.navigation_ptable)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        loadCompound();
        loadFav();
        loadRecents();
        loadElements();
        loadNotes();
        global.getCompoundListFull().clear();
        global.getCompoundListFull().addAll(global.getCompounds());
    }

    public global getGlobal() {
        return global;
    }

    public void setCompViewInfo(Compound c,int i)
    {
        globalCompound=c;
        globalCurPos=i;
    }
    public int getGlobalCurPos(){return globalCurPos;}
    public Compound getGlobalCompound(){return globalCompound;}

    public void setPSearchQuery(String str) { pSearchQuery=str;}
    public String getPSearchQuery(){return pSearchQuery;}

    public boolean checkFav(int testCID) {
        for (Compound one : global.getFav()) {
            if (one.getCID() == testCID) {
                return true;
            }
        }
        return false;
    }

    public boolean checkRecent(int testCID) {
        for (Compound one : global.getRecents()) {
            if (one.getCID() == testCID) {
                return true;
            }
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addRecent(Compound CID) {
        try {
            File file = new File(getApplication().getFilesDir().toString() + "/recents.txt");
            FileWriter fr = new FileWriter(file, true);
            fr.write("\n" + Integer.toString(CID.getCID()));
            fr.close();
        } catch (IOException e) {
        }
        global.getRecents().add(0, CID);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addFav(Compound CID) {
        try {
            File file = new File(getApplication().getFilesDir().toString() + "/favorites.txt");
            FileWriter fr = new FileWriter(file, true);
            fr.write("\n" + Integer.toString(CID.getCID()));
            fr.close();
        } catch (IOException e) {

        }
        global.getFav().add(0, CID);
        System.out.println("ADDED TO FAV CID: " + CID.getCID());
        System.out.println("NEW: " + global.getFav());
    }

    public void removeRecent(int CID) {
        try {
            File file = new File(getApplication().getFilesDir().toString() + "/recents.txt");
            File temp = File.createTempFile("temp-recents", ".txt", file.getParentFile());
            String charset = "UTF-8";
            String delete = Integer.toString(CID) + "\n";
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(temp), charset));

            for (String line; (line = reader.readLine()) != null;) {
                if (!line.startsWith(delete)) {
                    writer.println(line);
                }
            }

            reader.close();
            writer.close();

            file.delete();
            temp.renameTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Compound one : global.getRecents()) {
            if (one.getCID() == CID) {
                global.getRecents().remove(one);
                return;
            }
        }
    }

    public void removeFav(int CID) {
        try {
            File file = new File(getApplication().getFilesDir().toString() + "/favorites.txt");
            File temp = File.createTempFile("temp-fav", ".txt", file.getParentFile());
            String charset = "UTF-8";
            String delete = Integer.toString(CID);
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(temp), charset));

            for (String line; (line = reader.readLine()) != null;) {
                if (!line.startsWith(delete)) {
                    writer.println(line);
                }
            }

            reader.close();
            writer.close();

            file.delete();
            temp.renameTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("REMOVE TO FAV CID: " + CID);
        for (Compound one : global.getFav()) {
            if (one.getCID() == CID) {
                global.getFav().remove(one);
                System.out.println("NEW: " + global.getFav());
                return;
            }
        }

    }
    public void loadFav() {
        File file = new File(getApplication().getFilesDir().toString() + "/favorites.txt");
        try {
            Scanner sc = new Scanner(file);
            if (sc.hasNextLine()) {
                sc.nextLine();
            }
            while (sc.hasNextLine()) {
                try {
                    global.getFav().add(findCompound(Integer.parseInt(sc.nextLine())));
                } catch (Exception e) {
                    Log.e("Main Activity","Exception in loadFav");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadRecents() {
        File file = new File(getApplication().getFilesDir().toString() + "/recents.txt");
        try {
            Scanner sc = new Scanner(file);
            if (sc.hasNextLine()) {
                sc.nextLine();
            }
            while (sc.hasNextLine()) {
                try {
                    global.getRecents().add(findCompound(Integer.parseInt(sc.nextLine())));
                } catch (Exception e) {
                    Log.e("Main Activity","Exception in loadRecents");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Compound findCompound(int CID) {
        for (Compound item : global.getCompounds()) {
            if (item.getCID() == CID) {
                return item;
            }
        }
        return null;
    }

    public void loadCompound() {
        InputStream is = getResources().openRawResource(R.raw.compound);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String line = "";

        try {
            while (((line = reader.readLine()) != null)) {
                String[] tokens = line.split(";");
                if (!tokens[2].substring(1, tokens[2].length()-1).isEmpty() && !tokens[2].substring(1, tokens[2].length()-1).contentEquals(" ")) {
                    global.getCompoundListFull().add(new Compound(Integer.parseInt(tokens[0]), tokens[1].substring(1, tokens[1].length() - 1), tokens[2].substring(1, tokens[2].length() - 1)));
                    global.getCompounds().add(new Compound(Integer.parseInt(tokens[0]), tokens[1].substring(1, tokens[1].length() - 1), tokens[2].substring(1, tokens[2].length() - 1)));
                }
            }
        } catch (IOException e) {
            Log.wtf("MyActivity", "Error reading data file on line " + line, e);
            e.printStackTrace();
        }
    }

    public void loadElements() {
        InputStream is = getResources().openRawResource(R.raw.elements);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String line = "";

        try {
            while (((line = reader.readLine()) != null)) {
                String[] tokens = line.split(";");
                global.getElements().add(new Element(getInt(tokens[0]), tokens[1], tokens[2], tokens[3], getDouble(tokens[4]), tokens[5], getDouble(tokens[6]), getInt(tokens[7]), getDouble(tokens[8]), getDouble(tokens[9]), tokens[10], tokens[11], getDouble(tokens[12]), getDouble(tokens[13]), getDouble(tokens[14]), tokens[15], tokens[16]));
            }
        } catch (IOException e) {
            Log.wtf("MyActivity", "Error reading data file on line " + line, e);
            e.printStackTrace();
        }
    }

    public void loadNotes() {
        File file = new File(getApplication().getFilesDir().toString() + "/notes.txt");
        try {
            Scanner sc = new Scanner(file);
            if (sc.hasNextLine()) {
                sc.nextLine();
            }
            while (sc.hasNextLine()) {
                try {
                    int CID = Integer.parseInt(sc.nextLine());
                    String note = sc.nextLine();
                    findCompound(CID).setNotes(note);
                    System.out.println("Added Note '" + findCompound(CID).getNotes() + "' to: " + findCompound(CID).getName());
                } catch (Exception e) {

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setNote(Compound CID, String note) {
        try {
            File file = new File(getApplication().getFilesDir().toString() + "/notes.txt");
            FileWriter fr = new FileWriter(file, true);
            fr.write("\n" + CID.getCID() + "\n" + note);
            fr.close();
            CID.setNotes(note);
        } catch (IOException e) {

        }
    }

    public double getDouble(String in) {
        if (in.compareTo("null") != 0) {
            return Double.parseDouble(in);
        } else {
            return -10;
        }
    }

    public int getInt(String in) {
        if (in.compareTo("null") != 0) {
            return Integer.parseInt(in);
        } else {
            return -10;
        }
    }

    public void clearKeyboard(){
        InputMethodManager inputMethodManager =
                (InputMethodManager) getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        try{
        inputMethodManager.hideSoftInputFromWindow(
                getCurrentFocus().getWindowToken(), 0);}catch(Exception e){}
    }
}
