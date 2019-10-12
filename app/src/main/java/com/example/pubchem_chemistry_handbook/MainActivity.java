package com.example.pubchem_chemistry_handbook;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.downloader.PRDownloader;
import com.example.pubchem_chemistry_handbook.data.Compound;
import com.example.pubchem_chemistry_handbook.data.global;
import com.example.pubchem_chemistry_handbook.ui.search.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    global global = new global(0,0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PRDownloader.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_news, R.id.navigation_search, R.id.navigation_favorites)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        File yourFile = new File(getApplication().getFilesDir().toString() + "/favorites.txt");
        try {
            yourFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileOutputStream oFile = new FileOutputStream(yourFile, false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        File yourFile2 = new File(getApplication().getFilesDir().toString() + "/recents.txt");
        try {
            yourFile2.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileOutputStream oFile = new FileOutputStream(yourFile2, false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        loadCompound();
    }

    public global getGlobal() {
        return global;
    }

    public boolean checkFav(int testEID) {
        for (Compound one : global.getFav()) {
            if (one.getEID() == testEID) {
                return true;
            }
        }
        return false;
    }

    public boolean checkRecent(int testEID) {
        for (Compound one : global.getRecents()) {
            if (one.getEID() == testEID) {
                return true;
            }
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addRecent(Compound EID) {
        try {
            Files.write(Paths.get(getApplication().getFilesDir().toString() + "/recents.txt"), Integer.toString(EID.getEID()).getBytes(), StandardOpenOption.APPEND);
        }catch (IOException e) {
            e.printStackTrace();
            //exception handling left as an exercise for the reader
        }
        global.getRecents().add(0, EID);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addFav(Compound EID) {
        try {
            Files.write(Paths.get(getApplication().getFilesDir().toString() + "/favorites.txt"), Integer.toString(EID.getEID()).getBytes(), StandardOpenOption.APPEND);
        }catch (IOException e) {
            e.printStackTrace();
            //exception handling left as an exercise for the reader
        }
        global.getFav().add(0, EID);
        System.out.println("ADDED TO FAV EID: " + EID.getEID());
        System.out.println("NEW: " + global.getFav());
    }

    public void removeRecent(int EID) {
        for (Compound one : global.getRecents()) {
            if (one.getEID() == EID) {
                global.getRecents().remove(one);
                return;
            }
        }
    }

    public void removeFav(int EID) {
        System.out.println("REMOVE TO FAV EID: " + EID);
        for (Compound one : global.getFav()) {
            if (one.getEID() == EID) {
                global.getFav().remove(one);
                System.out.println("NEW: " + global.getFav());
                return;
            }
        }
    }

    void loadCompound() {
        InputStream is = getResources().openRawResource(R.raw.compound);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String line = "";

        try {
            while (((line = reader.readLine()) != null)) {
                String[] tokens = line.split(";");
                if (!tokens[2].substring(1, tokens[2].length()-1).isEmpty() && !tokens[2].substring(1, tokens[2].length()-1).contentEquals(" ")) {
                    global.getCompounds().add(new Compound(Integer.parseInt(tokens[0]), tokens[1].substring(1, tokens[1].length() - 1), tokens[2].substring(1, tokens[2].length() - 1)));
                }
            }
        } catch (IOException e) {
            Log.wtf("MyActivity", "Error reading data file on line " + line, e);
            e.printStackTrace();
        }
    }
}
