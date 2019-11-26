package com.example.pubchem_chemistry_handbook.ui.compound;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.example.pubchem_chemistry_handbook.MainActivity;
import com.example.pubchem_chemistry_handbook.R;
import com.example.pubchem_chemistry_handbook.data.Compound;
import com.example.pubchem_chemistry_handbook.data.SafetyItem;
import com.example.pubchem_chemistry_handbook.ui.AsyncTaskLoadImage;
import com.webviewtopdf.PdfView;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import static java.lang.Thread.sleep;

public class CompoundFragment extends Fragment {
    private Compound currentCompound = new Compound(0, "", "");
    public static Boolean fragExists;
    private static String internalFilesDir=null;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        internalFilesDir = getContext().getFilesDir().toString();
        fragExists = true;
        final ScrollView compoundView;
        currentCompound = (((MainActivity) getActivity()).getGlobalCompound());
        final View view = inflater.inflate(R.layout.fragment_comp, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        compoundView = view.findViewById(R.id.compound_scrollView);
        final TextView compoundView_name = view.findViewById(R.id.compoundView_name);
        final TextView compoundView_formula = view.findViewById(R.id.compoundView_formula);
        final ImageView compoundView_2dImage = view.findViewById(R.id.compoundView_2dImage);
        compoundView_2dImage.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View v) {
                showImage(compoundView_2dImage, v, container, 2);
            }
        });
        final ImageView compoundView_3dImage = view.findViewById(R.id.compoundView_3dImage);
        compoundView_3dImage.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View v) {
                showImage(compoundView_3dImage, v, container, 3);
            }
        });
        final ImageView compoundView_crystal = view.findViewById(R.id.compoundView_crystal);
        compoundView_crystal.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View v) {
                showImage(compoundView_crystal, v, container, 4);
            }
        });
        final TableLayout PhysicalProperties = view.findViewById(R.id.PhysicalProperties);
        final LinearLayout SafetyItems_Images = view.findViewById(R.id.SafetyItems_Images);
        final LinearLayout SafetyItems_Text = view.findViewById(R.id.SafetyItems_Text);
        final ImageView[] HazardImages = new ImageView[9];
        final TextView[] HazardTexts = new TextView[9];
        final Button favButton = view.findViewById(R.id.favButton);
        final TextView Summary = view.findViewById(R.id.Summary);
        HazardImages[0] = view.findViewById(R.id.SafetyItems_Images_GHS01);
        HazardTexts[0] = view.findViewById(R.id.SafetyItems_Text_GHS01);
        HazardImages[1] = view.findViewById(R.id.SafetyItems_Images_GHS02);
        HazardTexts[1] = view.findViewById(R.id.SafetyItems_Text_GHS02);
        HazardImages[2] = view.findViewById(R.id.SafetyItems_Images_GHS03);
        HazardTexts[2] = view.findViewById(R.id.SafetyItems_Text_GHS03);
        HazardImages[3] = view.findViewById(R.id.SafetyItems_Images_GHS04);
        HazardTexts[3] = view.findViewById(R.id.SafetyItems_Text_GHS04);
        HazardImages[4] = view.findViewById(R.id.SafetyItems_Images_GHS05);
        HazardTexts[4] = view.findViewById(R.id.SafetyItems_Text_GHS05);
        HazardImages[5] = view.findViewById(R.id.SafetyItems_Images_GHS06);
        HazardTexts[5] = view.findViewById(R.id.SafetyItems_Text_GHS06);
        HazardImages[6] = view.findViewById(R.id.SafetyItems_Images_GHS07);
        HazardTexts[6] = view.findViewById(R.id.SafetyItems_Text_GHS07);
        HazardImages[7] = view.findViewById(R.id.SafetyItems_Images_GHS08);
        HazardTexts[7] = view.findViewById(R.id.SafetyItems_Text_GHS08);
        HazardImages[8] = view.findViewById(R.id.SafetyItems_Images_GHS09);
        HazardTexts[8] = view.findViewById(R.id.SafetyItems_Text_GHS09);
        final TextView nullSafetyItems = view.findViewById(R.id.Safety_NULL);
        final LinearLayout StructureImageLayout = view.findViewById(R.id.compoundView_images);
        final LinearLayout StructureTextLayout = view.findViewById(R.id.compoundView_images_names);
        final ImageView[] StructureImages = new ImageView[3];
        final TextView[] StructureTexts = new TextView[3];
        final HorizontalScrollView SafetyItems = view.findViewById(R.id.SafetyItems_raw);
        final TextView SafetyHeader = view.findViewById(R.id.Safety_Header);
        StructureImages[0] = view.findViewById(R.id.compoundView_2dImage);
        StructureTexts[0] = view.findViewById(R.id.compoundView_images_names_2d);
        StructureImages[1] = view.findViewById(R.id.compoundView_3dImage);
        StructureTexts[1] = view.findViewById(R.id.compoundView_images_names_3d);
        StructureImages[2] = view.findViewById(R.id.compoundView_crystal);
        StructureTexts[2] = view.findViewById(R.id.compoundView_images_names_crystal);
        final Button shareButton = view.findViewById(R.id.shareButton);
        final TextView notes = view.findViewById(R.id.notes);
        notes.setText(currentCompound.getNotes());
        final Button notesButton = view.findViewById(R.id.notes_button);
        final Button downloadButton = view.findViewById(R.id.downloadButton);
        final WebView webView = view.findViewById(R.id.web_view);

        ((MainActivity) getActivity()).loadNotes();

        notesButton.setText("Edit Notes");
        notesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext()).setNeutralButton("Clear", null);
                builder.setTitle("Edit Notes");
                final EditText input = new EditText(v.getContext());
                builder.setView(input);
                input.setText(notes.getText().toString());
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        notes.setText(input.getText().toString());
                        ((MainActivity) getActivity()).setNote(currentCompound, notes.getText().toString());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });
                final AlertDialog dialog = builder.create();
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEUTRAL);
                        button.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                input.setText("");
                            }
                        });
                    }
                });
                dialog.show();
            }
        });

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetJavaScriptEnabled")
            @Override
            public void onClick(View v) {
                String LCSSLink = "https://pubchem.ncbi.nlm.nih.gov/compound/" + currentCompound.getCID() + "#datasheet=LCSS";
                final File directory = getContext().getExternalFilesDir(null);
                final String fileName = "Compound" + currentCompound.getCID() + ".pdf";
                File fullPath = new File(directory + "/" + fileName);
                if (fullPath.exists()) {
                    PdfView.openPdfFile(getActivity(), getString(R.string.app_name), "PDF already downloaded! Do you want to open the pdf file? If this file needs to be deleted, long press the download button.\n" + fileName, fullPath.toString());
                } else {
                    final ProgressDialog progressDialog = new ProgressDialog(getContext());
                    progressDialog.setMessage("Downloading LCSS PDF, pleas wait");
                    progressDialog.show();
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.loadUrl(LCSSLink);
                    webView.setWebViewClient(new WebViewClient() {

                        public boolean shouldOverrideUrlLoading(WebView view, String url) {
                            return false;
                        }

                        @Override
                        public void onPageFinished(WebView view, String url) {
                            Log.i("webview", "page finished loading " + url);
                            PdfView.createWebPrintJob(getActivity(), view, directory, fileName, new PdfView.Callback() {

                                @Override
                                public void success(String path) {
                                    progressDialog.dismiss();
                                    PdfView.openPdfFile(getActivity(), getString(R.string.app_name), "Do you want to open the pdf file?" + fileName, path);
                                }

                                @Override
                                public void failure() {
                                    progressDialog.dismiss();

                                }
                            });
                        }
                    });
                }
            }
        });
        downloadButton.setOnLongClickListener(new View.OnLongClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public boolean onLongClick(View v) {
                PopupMenu menu = new PopupMenu(getContext(), v);
                menu.setGravity(v.getTop());
                menu.getMenu().add("Delete current pdf?");
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if((item.getTitle().toString()).equals("Delete current pdf?"))
                        {
                            final File directory = getContext().getExternalFilesDir(null);
                            final String fileName = "Compound" + currentCompound.getCID() + ".pdf";
                            File fullPath = new File(directory + "/" + fileName);
                            if (fullPath.exists()) {
                                fullPath.delete();
                            }
                        } else {
                            Toast.makeText(getContext(),"File not found!",Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });
                menu.show();
                return true;
            }});

        shareButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            public void onClick(View v) {
                String url = "https://pubchem.ncbi.nlm.nih.gov/compound/" + currentCompound.getCID();
                String shareNotes = notes.getText().toString();
                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.setType("text/plain");
                sendIntent.putExtra(Intent.EXTRA_TEXT, url);
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL");
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out " + currentCompound.getName() + " on PubChem:\n" + url + "\n\nNotes:\n" + shareNotes);
                Intent viewIntent = new Intent(Intent.ACTION_VIEW);
                viewIntent.setData(Uri.parse(url));
                Intent chooserIntent = Intent.createChooser(sendIntent, "Share Compound Info");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{viewIntent});
                startActivity(chooserIntent);
            }
        });


        favButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onClick(View v) {
                if (((MainActivity) getActivity()).checkFav(((currentCompound.getCID())))) {
                    ((MainActivity) getActivity()).removeFav(((currentCompound.getCID())));
                    favButton.setBackground(getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp));
                } else {
                    ((MainActivity) getActivity()).addFav(((currentCompound)));
                    favButton.setBackground(getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
                }
            }
        });
        PhysicalProperties.setVisibility(View.VISIBLE);
        PhysicalProperties.removeAllViews();
        ((MainActivity) getActivity()).getGlobal().setSafetyItems(0);
        favButton.setBackground(getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ((MainActivity) getActivity()).addRecent(currentCompound);
        }

        InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        notes.setText(currentCompound.getNotes());
        SafetyItems_Images.removeAllViews();
        SafetyItems_Text.removeAllViews();
        StructureImageLayout.removeAllViews();
        StructureTextLayout.removeAllViews();
        File fileCheck = new File(getContext().getFilesDir().toString() + "/Compounds/" + currentCompound.getCID() + "/compound-" + currentCompound.getCID() + ".json");
        if(fileCheck.exists())
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                parseCompound(Summary, PhysicalProperties,  StructureImageLayout,
                        StructureTextLayout, StructureImages, StructureTexts,
                        compoundView_2dImage,  compoundView_3dImage,  compoundView_crystal,
                        SafetyItems_Images,  SafetyItems_Text,  SafetyItems,
                        nullSafetyItems, SafetyHeader, HazardImages, HazardTexts);
            }
        }
        int downloadId = PRDownloader.download("https://pubchem.ncbi.nlm.nih.gov/rest/pug_view/data/compound/" + currentCompound.getCID() + "/JSON/?response_type=save&response_basename=compound_CID_" + currentCompound.getCID(), internalFilesDir + "/Compounds/" + currentCompound.getCID(), "compound-" + currentCompound.getCID() + ".json")
                .build()
                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                    @Override
                    public void onStartOrResume() {

                    }
                })
                .setOnPauseListener(new OnPauseListener() {
                    @Override
                    public void onPause() {

                    }
                })
                .setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel() {

                    }
                })
                .setOnProgressListener(new OnProgressListener() {
                    @Override
                    public void onProgress(Progress progress) {

                    }
                })
                .start(new OnDownloadListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onDownloadComplete() {
                        final String url = "https://pubchem.ncbi.nlm.nih.gov/rest/pug/compound/cid/" + currentCompound.getCID() + "/description/JSON";
                        int downloadId = PRDownloader.download(url, internalFilesDir, "/Compounds/" + currentCompound.getCID() + "/compound-description-" + currentCompound.getCID() + ".json")
                                .build()
                                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                                    @Override
                                    public void onStartOrResume() {

                                    }
                                })
                                .setOnPauseListener(new OnPauseListener() {
                                    @Override
                                    public void onPause() {

                                    }
                                })
                                .setOnCancelListener(new OnCancelListener() {
                                    @Override
                                    public void onCancel() {

                                    }
                                })
                                .setOnProgressListener(new OnProgressListener() {
                                    @Override
                                    public void onProgress(Progress progress) {

                                    }
                                })
                                .start(new OnDownloadListener() {
                                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                    @Override
                                    public void onDownloadComplete() {
                                        final String url = "https://pubchem.ncbi.nlm.nih.gov/rest/pug/compound/cid/" + currentCompound.getCID() + "/property/MolecularWeight,XLogP,HBondDonorCount,HBondAcceptorCount,RotatableBondCount,ExactMass,MonoisotopicMass,TPSA,HeavyAtomCount,Charge,Complexity,IsotopeAtomCount,DefinedAtomStereoCount,UndefinedAtomStereoCount,DefinedBondStereoCount,UndefinedBondStereoCount,CovalentUnitCount/CSV";
                                        int downloadId = PRDownloader.download(url, internalFilesDir, "/Compounds/" + currentCompound.getCID() + "/compound-properties-" + currentCompound.getCID() + ".csv")
                                                .build()
                                                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                                                    @Override
                                                    public void onStartOrResume() {

                                                    }
                                                })
                                                .setOnPauseListener(new OnPauseListener() {
                                                    @Override
                                                    public void onPause() {

                                                    }
                                                })
                                                .setOnCancelListener(new OnCancelListener() {
                                                    @Override
                                                    public void onCancel() {

                                                    }
                                                })
                                                .setOnProgressListener(new OnProgressListener() {
                                                    @Override
                                                    public void onProgress(Progress progress) {

                                                    }
                                                })
                                                .start(new OnDownloadListener() {
                                                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                                    @Override
                                                    public void onDownloadComplete() {
                                                        System.out.println(url);
                                                        parseCompound(Summary, PhysicalProperties,  StructureImageLayout,
                                                                StructureTextLayout, StructureImages, StructureTexts,
                                                                compoundView_2dImage,  compoundView_3dImage,  compoundView_crystal,
                                                                SafetyItems_Images,  SafetyItems_Text,  SafetyItems,
                                                                nullSafetyItems, SafetyHeader, HazardImages, HazardTexts);
                                                    }

                                                    @Override
                                                    public void onError(Error error) {
                                                        Toast.makeText(getActivity(), "ERROR: " + error.toString(), Toast.LENGTH_LONG).show();
                                                        Log.d("PRDownloader", "onError: " + error.toString() + " " + url);
                                                    }
                                                });
                                    }

                                    @Override
                                    public void onError(Error error) {
                                        Toast.makeText(getContext(), "ERROR: " + error.toString(), Toast.LENGTH_LONG).show();
                                        Log.d("PRDownloader", "onError: " + error.toString() + " " + url);
                                    }
                                });
                    }

                    @Override
                    public void onError(Error error) {
                        Toast.makeText(getContext(), "ERROR: " + error.toString(), Toast.LENGTH_LONG).show();
                        Log.d("PRDownloader", "onError: " + error.toString());
                    }
                });
        if (((MainActivity) getActivity()).checkFav(currentCompound.getCID())) {
            favButton.setBackground(getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
        } else {
            favButton.setBackground(getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp));
        }
        compoundView_name.setText(" " + currentCompound.getName());
        compoundView_formula.setText("  " + currentCompound.getFormula());
        compoundView.setVisibility(View.VISIBLE);

        return view;

    }

    public static void onBackPressed(View view) {
        {
            fragExists = false;
            view.setVisibility(View.INVISIBLE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void showImage(ImageView compoundImage, View v, ViewGroup container, int whichOne) {
        final Dialog settingsDialog = new Dialog(v.getContext(), R.style.DialogTheme);
        View v2 = getLayoutInflater().inflate(R.layout.image_layout
                , container, false);
        ImageButton close = v2.findViewById(R.id.x_button);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsDialog.dismiss();
            }
        });
        ImageView img = v2.findViewById(R.id.imgvlayout);
        Drawable draw = compoundImage.getDrawable();
        if (draw != null) {
            if (whichOne == 2) {
                v2.setBackgroundColor(Color.parseColor("#f5f5f5"));
            } else {
                v2.setBackgroundColor(Color.parseColor("#ffffff"));
            }
            Bitmap bitmap = ((BitmapDrawable) draw).getBitmap();
            try {
                Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 1200, 1200, true));
                img.setImageDrawable(d);
                settingsDialog.setContentView(v2);
                settingsDialog.show();
            }catch(NullPointerException e){e.printStackTrace();}
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressWarnings("ConstantConditions")
    public void parseCompound(TextView Summary,TableLayout PhysicalProperties, LinearLayout StructureImageLayout,
                              LinearLayout StructureTextLayout, ImageView[] StructureImages,TextView[] StructureTexts,
                              ImageView compoundView_2dImage, ImageView compoundView_3dImage, ImageView compoundView_crystal,
                              LinearLayout SafetyItems_Images, LinearLayout SafetyItems_Text, HorizontalScrollView SafetyItems,
                              TextView nullSafetyItems,TextView SafetyHeader,ImageView[] HazardImages, TextView[] HazardTexts){

        JSONParser jsonParserDescription = new JSONParser();

        try(FileReader readerDescription = new FileReader(internalFilesDir + "/Compounds/" +currentCompound.getCID() + "/compound-description-" + currentCompound.getCID() + ".json"))

        {
            JSONObject obj = (JSONObject) jsonParserDescription.parse(readerDescription);
            JSONObject informationList = (JSONObject) obj.get("InformationList");
            JSONArray information = (JSONArray) informationList.get("Information");
            Summary.setText("");
            for (int i = 1; i < information.size(); i++) {
                JSONObject item = (JSONObject) information.get(i);
                if (Summary.getText() == "") {
                    //Summary.setText((String) item.get("Description"));
                    Summary.setText((String) item.get("Description") + "\nSource: " + (String) item.get("DescriptionSourceName"));
                    //Summary.setText((String) item.get("Description") + "\nSource: " + (String) item.get("DescriptionSourceName") + "\nSource URL: " + (String) item.get("DescriptionURL") + " ");
                } else {
                    //Summary.setText(Summary.getText() + "\n\n" + (String) item.get("Description"));
                    Summary.setText(Summary.getText() + "\n\n" + (String) item.get("Description") + "\nSource: " + (String) item.get("DescriptionSourceName"));
                    //Summary.setText(Summary.getText() + "\n\n" + (String) item.get("Description") + "\nSource: " + (String) item.get("DescriptionSourceName") + "\nSource URL: " + (String) item.get("DescriptionURL") + " ");

                }
            }
            if (Summary.getText() == "") {
                Summary.setText("No Description Found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        File file = new File(internalFilesDir + "/Compounds/" +currentCompound.getCID() + "/compound-properties-" + currentCompound.getCID() + ".csv");
        try {
            Scanner sc = new Scanner(file);
            currentCompound.getnProperties().clear();
            String headerLine = sc.nextLine();
            String dataLine = sc.nextLine();
            String[] headertokens = headerLine.split(",");
            String[] datatokens = dataLine.split(",");
            for (int i = 1; i < headertokens.length; i++) {
                currentCompound.getvProperties().add(datatokens[i]);
                switch (i) {
                    case 1:
                        currentCompound.getnProperties().add("Molecular Weight");
                        currentCompound.getuProperties().add("g/mol");
                        break;
                    case 2:
                        currentCompound.getnProperties().add("XLogP3-AA");
                        currentCompound.getuProperties().add("");
                        break;
                    case 3:
                        currentCompound.getnProperties().add("Hydrogen Bond Donor Count");
                        currentCompound.getuProperties().add("");
                        break;
                    case 4:
                        currentCompound.getnProperties().add("Hydrogen Bond Acceptor Count");
                        currentCompound.getuProperties().add("");
                        break;
                    case 5:
                        currentCompound.getnProperties().add("Rotatable Bond Count");
                        currentCompound.getuProperties().add("");
                        break;
                    case 6:
                        currentCompound.getnProperties().add("Exact Mass");
                        currentCompound.getuProperties().add("g/mol");
                        break;
                    case 7:
                        currentCompound.getnProperties().add("Monoisotopic Mass");
                        currentCompound.getuProperties().add("g/mol");
                        break;
                    case 8:
                        currentCompound.getnProperties().add("Topological Polar Surface Area");
                        currentCompound.getuProperties().add("Å²");
                        break;
                    case 9:
                        currentCompound.getnProperties().add("Heavy Atom Count");
                        currentCompound.getuProperties().add("");
                        break;
                    case 10:
                        currentCompound.getnProperties().add("Formal Charge");
                        currentCompound.getuProperties().add("");
                        break;
                    case 11:
                        currentCompound.getnProperties().add("Complexity");
                        currentCompound.getuProperties().add("");
                        break;
                    case 12:
                        currentCompound.getnProperties().add("Isotope Atom Count");
                        currentCompound.getuProperties().add("");
                        break;
                    case 13:
                        currentCompound.getnProperties().add("Defined Atom Stereocenter Count");
                        currentCompound.getuProperties().add("");
                        break;
                    case 14:
                        currentCompound.getnProperties().add("Undefined Atom Stereocenter Count");
                        currentCompound.getuProperties().add("");
                        break;
                    case 15:
                        currentCompound.getnProperties().add("Defined Bond Stereocenter Count");
                        currentCompound.getuProperties().add("");
                        break;
                    case 16:
                        currentCompound.getnProperties().add("Undefined Bond Stereocenter Count");
                        currentCompound.getuProperties().add("");
                        break;
                    case 17:
                        currentCompound.getnProperties().add("Covalently-Bonded Unit Count");
                        currentCompound.getuProperties().add("");
                        break;
                    default:
                        currentCompound.getnProperties().add("");
                        currentCompound.getuProperties().add("");
                        break;
                }
            }
            PhysicalProperties.setStretchAllColumns(true);
            PhysicalProperties.bringToFront();
            PhysicalProperties.removeAllViews();
            for (int i = 0; i < currentCompound.getnProperties().size(); i++) {
                TableRow tr = new TableRow(getContext());
                TextView c1 = new TextView(getContext());
                c1.setText(currentCompound.getnProperties().get(i));
                TextView c2 = new TextView(getContext());
                if (currentCompound.getuProperties().get(i) != null) {
                    c2.setText(currentCompound.getvProperties().get(i) + " " + currentCompound.getuProperties().get(i));
                } else {
                    c2.setText(currentCompound.getvProperties().get(i));
                }
                tr.addView(c1);
                tr.addView(c2);
                PhysicalProperties.addView(tr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        JSONParser jsonParser = new JSONParser();

        try(FileReader reader = new FileReader(getContext().getFilesDir().toString() + "/Compounds/" + currentCompound.getCID() + "/compound-" + currentCompound.getCID() + ".json"))

        {

            JSONObject obj = (JSONObject) jsonParser.parse(reader);
            JSONObject record = (JSONObject) obj.get("Record");
            JSONArray section = (JSONArray) record.get("Section");
            JSONObject section_0 = (JSONObject) section.get(0);
            JSONArray structure_section = (JSONArray) section_0.get("Section");
            StructureImageLayout.removeAllViews();
            StructureTextLayout.removeAllViews();
            try {
                for (int i = 0; i < 3; i++) {
                    if (structure_section.size() > i) {
                        JSONObject struct_1 = (JSONObject) structure_section.get(i);
                        String struct_name = (String) struct_1.get("TOCHeading");
                        if (struct_name.equals("2D Structure")) {
                            StructureImageLayout.addView(StructureImages[0]);
                            StructureTextLayout.addView(StructureTexts[0]);
                            final File directory = getContext().getFilesDir();
                            File fullPath = new File(directory + "/Compounds/" + currentCompound.getCID() + "/" + "images/2d.jpg");
                            WeakReference<ImageView> weakCV2D = new WeakReference<ImageView>(compoundView_2dImage);
                            if(!fullPath.exists()) {
                                AsyncTaskLoadImage image_Loader = new AsyncTaskLoadImage(weakCV2D);
                                image_Loader.execute("https://pubchem.ncbi.nlm.nih.gov/image/imgsrv.fcgi?cid=" + currentCompound.getCID() + "&t=s");
                                new getBitmapFromURL().execute("https://pubchem.ncbi.nlm.nih.gov/image/imgsrv.fcgi?cid=" + currentCompound.getCID() + "&t=s", "2d");
                            }
                            else{
                                Bitmap myBitmap = BitmapFactory.decodeFile(fullPath.toString());
                                weakCV2D.get().setImageBitmap(myBitmap);
                            }
                        }
                        if (struct_name.equals("3D Conformer")) {
                            StructureImageLayout.addView(StructureImages[1]);
                            StructureTextLayout.addView(StructureTexts[1]);
                            final File directory = getContext().getFilesDir();
                            File fullPath = new File(directory + "/Compounds/" + currentCompound.getCID() + "/" + "images/3d.jpg");
                            WeakReference<ImageView> weakCV3D = new WeakReference<ImageView>(compoundView_3dImage);
                            if(!fullPath.exists()) {
                                AsyncTaskLoadImage image_Loader = new AsyncTaskLoadImage(weakCV3D);
                                image_Loader.execute("https://pubchem.ncbi.nlm.nih.gov/image/img3d.cgi?cid=" + currentCompound.getCID() + "&t=s");
                                new getBitmapFromURL().execute("https://pubchem.ncbi.nlm.nih.gov/image/img3d.cgi?cid=" + currentCompound.getCID() + "&t=s", "3d");
                            }
                            else
                            {
                                Bitmap myBitmap = BitmapFactory.decodeFile(fullPath.toString());
                                weakCV3D.get().setImageBitmap(myBitmap);
                            }
                        }
                        if (struct_name.equals("Crystal Structures")) {
                            JSONArray temp = (JSONArray) struct_1.get("Section");
                            JSONObject temp_1 = (JSONObject) temp.get(1);
                            JSONArray temp_2 = (JSONArray) temp_1.get("Information");
                            JSONObject temp_3 = (JSONObject) temp_2.get(1);
                            JSONObject value = (JSONObject) temp_3.get("Value");
                            JSONArray contents = (JSONArray) value.get("ExternalDataURL");
                            String ExternalURLData = (String) contents.get(0);
                            StructureImageLayout.addView(StructureImages[2]);
                            StructureTextLayout.addView(StructureTexts[2]);
                            final File directory = getContext().getFilesDir();
                            File fullPath = new File(directory + "/Compounds/" + currentCompound.getCID() + "/" + "images/crystal.jpg");
                            WeakReference<ImageView> weakCompViewCrystal;
                            weakCompViewCrystal = new WeakReference<>(compoundView_crystal);
                            if(!fullPath.exists()) {
                                AsyncTaskLoadImage image_Loader = new AsyncTaskLoadImage(weakCompViewCrystal);
                                image_Loader.execute(ExternalURLData);
                                new getBitmapFromURL().execute(ExternalURLData, "crystal");
                            }
                            else
                            {
                                Bitmap myBitmap = BitmapFactory.decodeFile(fullPath.toString());
                                weakCompViewCrystal.get().setImageBitmap(myBitmap);
                            }
                        }
                    }
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (IndexOutOfBoundsException i) {
                i.printStackTrace();
            }
            SafetyItems_Images.removeAllViews();
            SafetyItems_Text.removeAllViews();
            SafetyItems.setVisibility(View.GONE);
            nullSafetyItems.setVisibility(View.VISIBLE);
            try {
                boolean[] safety = new boolean[9];
                JSONObject section_1 = (JSONObject) section.get(1);
                JSONArray Information_1 = (JSONArray) section_1.get("Information");
                JSONObject sub_Information_1 = (JSONObject) Information_1.get(0);
                JSONObject Value_1 = (JSONObject) sub_Information_1.get("Value");
                JSONArray StringWithMarkup_1 = (JSONArray) Value_1.get("StringWithMarkup");
                JSONObject sub_StringWithMarkup_1 = (JSONObject) StringWithMarkup_1.get(0);
                JSONArray Markup = (JSONArray) sub_StringWithMarkup_1.get("Markup");
                for (int i = 0; i < 9; i++) {
                    safety[i] = false;
                }
                for (int i = 0; i < Markup.size(); i++) {
                    JSONObject sub_Markup = (JSONObject) Markup.get(i);
                    String url = (String) sub_Markup.get("URL");
                    String name = (String) sub_Markup.get("Extra");
                    currentCompound.addSafetyItem(name, url);
                    //System.out.println("Added Safety: " + name + ", " + url);
                }
                for (SafetyItem item : currentCompound.getSafetyItems()) {
                    int n = Integer.parseInt(String.valueOf(item.getUrl().charAt(48)));
                    safety[n - 1] = true;
                    ((MainActivity) getActivity()).getGlobal().setSafetyItems(1);
                }
                for (int i = 0; i < 9; i++) {
                    if (safety[i]) {
                        SafetyItems_Images.addView(HazardImages[i]);
                        SafetyItems_Text.addView(HazardTexts[i]);
                        SafetyHeader.setVisibility(View.VISIBLE);
                        SafetyItems.setVisibility(View.VISIBLE);
                        nullSafetyItems.setVisibility(View.GONE);
                    }
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } catch(
                FileNotFoundException e)

        {
            e.printStackTrace();
        } catch(
                IOException e)

        {
            e.printStackTrace();
        } catch(
                ParseException e)

        {
            e.printStackTrace();
        } catch(
                NullPointerException e)

        {
            e.printStackTrace();
        } catch(
                IndexOutOfBoundsException e)

        {
            e.printStackTrace();
        }
    }

    private class getBitmapFromURL extends AsyncTask<String, Void, Bitmap> {
        String type;
        Bitmap myBitmap;
        @Override
        protected Bitmap doInBackground(String... strings) {
            type = strings[1];
            String src = strings[0];
            try {
                java.net.URL url = new java.net.URL(src);
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        @SuppressLint("WrongThread")
        @Override
        protected void onPostExecute(Bitmap bitmap) {

            String savedImagePath = null;
            String imageFileName = type + ".jpg";
            if(getActivity()!=null) {
                ConnectivityManager cm =
                        (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();
                if(isConnected){
                final File directory = getContext().getFilesDir();

                    File fullPath = new File(directory + "/Compounds/" + currentCompound.getCID() + "/" + "images/");
                    boolean success = true;
                    if (!fullPath.exists()) {
                        success = fullPath.mkdirs();
                    }
                    if (success) {
                        File imageFile = new File(fullPath, imageFileName);
                        savedImagePath = imageFile.getAbsolutePath();
                        try {
                            OutputStream fOut = new FileOutputStream(imageFile);
                            myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                            fOut.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
    private List<String> split(String input) {
        boolean inP = false;
        boolean hadP = false;
        int last = -1;
        List<String> out = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '\"') {
                hadP = true;
                if (inP == true) {
                    inP = false;
                } else {
                    if (inP == false) {
                        inP = true;
                    }
                }

            }
            if (input.charAt(i) == ',' && inP == false) {
                if (hadP) {
                    if (input.substring(last + 2, i-1).length() == 0) {
                        out.add(null);
                    } else {
                        out.add(input.substring(last + 2, i-1));
                    }

                } else {
                    if (input.substring(last + 1, i).length() == 0) {
                        out.add(null);
                    } else {
                        out.add(input.substring(last + 1, i));
                    }
                }
                last = i;
                hadP = false;
            }
        }
        if (hadP) {
            out.add(input.substring(last + 2, input.length()-1));
        } else {
            out.add(input.substring(last + 1, input.length()));
        }
        return out;
    }
}

