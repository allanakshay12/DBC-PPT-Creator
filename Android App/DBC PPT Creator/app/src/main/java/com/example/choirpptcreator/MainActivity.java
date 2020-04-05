package com.example.choirpptcreator;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.recyclerview.widget.ItemTouchHelper.Callback.makeMovementFlags;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("URL");

    private FirebaseAnalytics mFirebaseAnalytics;

    Button today_reading, sunday_reading;

    SwitchMaterial switchMaterial;

    StorageReference riversRef;

    EditText Main_Title;
    EditText Main_Subtitle;



    TextView back_img_path;

    Activity activity;
    Context context;

    ImageView pictureshow;

    ImageView title_colour;

    Uri filePath;

    UploadTask uploadTask;
    ImageView content_colour;

    View fabview;

    private static final int PICK_IMAGE_REQUEST = 234;

    protected RecyclerView entrance_list;
    List<Homepage_ListItem> entrance_song_list = new ArrayList<>();
    HomePage_Adapter Entrance_Adapter;

    protected RecyclerView kyrie_list;
    List<Homepage_ListItem> kyrie_song_list = new ArrayList<>();
    HomePage_Adapter Kyrie_Adapter;

    protected RecyclerView gloria_list;
    List<Homepage_ListItem> gloria_song_list = new ArrayList<>();
    HomePage_Adapter Gloria_Adapter;

    protected RecyclerView acclamation_list;
    List<Homepage_ListItem> acclamation_song_list = new ArrayList<>();
    HomePage_Adapter Acclamation_Adapter;

    protected RecyclerView offertory_list;
    List<Homepage_ListItem> offertory_song_list = new ArrayList<>();
    HomePage_Adapter Offertory_Adapter;

    protected RecyclerView holy_list;
    List<Homepage_ListItem> holy_song_list = new ArrayList<>();
    HomePage_Adapter Holy_Adapter;

    protected RecyclerView proclamation_list;
    List<Homepage_ListItem> proclamation_song_list = new ArrayList<>();
    HomePage_Adapter Proclamation_Adapter;

    protected RecyclerView communion_list;
    List<Homepage_ListItem> communion_song_list = new ArrayList<>();
    HomePage_Adapter Communion_Adapter;

    protected RecyclerView recessional_list;
    List<Homepage_ListItem> recessional_song_list = new ArrayList<>();
    HomePage_Adapter Recessional_Adapter;

    protected RecyclerView reading_list;
    List<Homepage_ListItem> reading_content_list = new ArrayList<>();
    Reading_Adapter reading_Adapter;

    protected RecyclerView gospel_list;
    List<Homepage_ListItem> gospel_content_list = new ArrayList<>();
    Gospel_Adapter gospel_Adapter;

    ProgressDialog progressDialog;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        activity = this;
        context = this;

        Main_Title = findViewById(R.id.main_slide_title);
        Main_Subtitle = findViewById(R.id.main_slide_subtitle);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    LyricsRetroFitClientInstance.BASE_URL = dataSnapshot.getValue(String.class);
                } catch (Exception e) {
                    LyricsRetroFitClientInstance.BASE_URL = "http://null/";
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        pictureshow = findViewById(R.id.back_picture_show);


        RecyclerView.LayoutManager entranceLayoutManager = new LinearLayoutManager(getApplicationContext());
        entrance_list = findViewById(R.id.Entrance_List);
        Entrance_Adapter = new HomePage_Adapter(entrance_song_list, this, this);
        entrance_list.setLayoutManager(entranceLayoutManager);
        entrance_list.setAdapter(Entrance_Adapter);
        Entrance_Adapter.notifyDataSetChanged();


        ItemTouchHelper.Callback entrancecallback = new SimpleItemTouchHelperCallback(Entrance_Adapter);
        ItemTouchHelper entrancetouchHelper = new ItemTouchHelper(entrancecallback);
        entrancetouchHelper.attachToRecyclerView(entrance_list);




        RecyclerView.LayoutManager gloriaLayoutManager = new LinearLayoutManager(getApplicationContext());
        gloria_list = findViewById(R.id.Gloria_List);
        Gloria_Adapter = new HomePage_Adapter(gloria_song_list, this, this);
        gloria_list.setLayoutManager(gloriaLayoutManager);
        gloria_list.setAdapter(Gloria_Adapter);
        Gloria_Adapter.notifyDataSetChanged();

        ItemTouchHelper.Callback gloriacallback = new SimpleItemTouchHelperCallback(Gloria_Adapter);
        ItemTouchHelper gloriatouchHelper = new ItemTouchHelper(gloriacallback);
        gloriatouchHelper.attachToRecyclerView(gloria_list);

        RecyclerView.LayoutManager kyrieLayoutManager = new LinearLayoutManager(getApplicationContext());
        kyrie_list = findViewById(R.id.Kyrie_List);
        Kyrie_Adapter = new HomePage_Adapter(kyrie_song_list, this, this);
        kyrie_list.setLayoutManager(kyrieLayoutManager);
        kyrie_list.setAdapter(Kyrie_Adapter);
        Kyrie_Adapter.notifyDataSetChanged();

        ItemTouchHelper.Callback kyriecallback = new SimpleItemTouchHelperCallback(Kyrie_Adapter);
        ItemTouchHelper kyrietouchHelper = new ItemTouchHelper(kyriecallback);
        kyrietouchHelper.attachToRecyclerView(kyrie_list);

        RecyclerView.LayoutManager acclamationLayoutManager = new LinearLayoutManager(getApplicationContext());
        acclamation_list = findViewById(R.id.Acclamation_List);
        Acclamation_Adapter = new HomePage_Adapter(acclamation_song_list, this, this);
        acclamation_list.setLayoutManager(acclamationLayoutManager);
        acclamation_list.setAdapter(Acclamation_Adapter);
        Acclamation_Adapter.notifyDataSetChanged();

        ItemTouchHelper.Callback acclamationcallback = new SimpleItemTouchHelperCallback(Acclamation_Adapter);
        ItemTouchHelper acclamationtouchHelper = new ItemTouchHelper(acclamationcallback);
        acclamationtouchHelper.attachToRecyclerView(acclamation_list);

        RecyclerView.LayoutManager offertoryLayoutManager = new LinearLayoutManager(getApplicationContext());
        offertory_list = findViewById(R.id.Offertory_List);
        Offertory_Adapter = new HomePage_Adapter(offertory_song_list, this, this);
        offertory_list.setLayoutManager(offertoryLayoutManager);
        offertory_list.setAdapter(Offertory_Adapter);
        Offertory_Adapter.notifyDataSetChanged();

        ItemTouchHelper.Callback offertorycallback = new SimpleItemTouchHelperCallback(Offertory_Adapter);
        ItemTouchHelper offertorytouchHelper = new ItemTouchHelper(offertorycallback);
        offertorytouchHelper.attachToRecyclerView(offertory_list);

        RecyclerView.LayoutManager holyLayoutManager = new LinearLayoutManager(getApplicationContext());
        holy_list = findViewById(R.id.Holy_List);
        Holy_Adapter = new HomePage_Adapter(holy_song_list, this, this);
        holy_list.setLayoutManager(holyLayoutManager);
        holy_list.setAdapter(Holy_Adapter);
        Holy_Adapter.notifyDataSetChanged();

        ItemTouchHelper.Callback holycallback = new SimpleItemTouchHelperCallback(Holy_Adapter);
        ItemTouchHelper holytouchHelper = new ItemTouchHelper(holycallback);
        holytouchHelper.attachToRecyclerView(holy_list);

        RecyclerView.LayoutManager proclamationLayoutManager = new LinearLayoutManager(getApplicationContext());
        proclamation_list = findViewById(R.id.Proclamation_List);
        Proclamation_Adapter = new HomePage_Adapter(proclamation_song_list, this, this);
        proclamation_list.setLayoutManager(proclamationLayoutManager);
        proclamation_list.setAdapter(Proclamation_Adapter);
        Proclamation_Adapter.notifyDataSetChanged();

        ItemTouchHelper.Callback proclamationcallback = new SimpleItemTouchHelperCallback(Proclamation_Adapter);
        ItemTouchHelper proclamationtouchHelper = new ItemTouchHelper(proclamationcallback);
        proclamationtouchHelper.attachToRecyclerView(proclamation_list);

        RecyclerView.LayoutManager communionLayoutManager = new LinearLayoutManager(getApplicationContext());
        communion_list = findViewById(R.id.Communion_List);
        Communion_Adapter = new HomePage_Adapter(communion_song_list, this, this);
        communion_list.setLayoutManager(communionLayoutManager);
        communion_list.setAdapter(Communion_Adapter);
        Communion_Adapter.notifyDataSetChanged();

        ItemTouchHelper.Callback communioncallback = new SimpleItemTouchHelperCallback(Communion_Adapter);
        ItemTouchHelper communiontouchHelper = new ItemTouchHelper(communioncallback);
        communiontouchHelper.attachToRecyclerView(communion_list);

        RecyclerView.LayoutManager recessionalLayoutManager = new LinearLayoutManager(getApplicationContext());
        recessional_list = findViewById(R.id.Recessional_List);
        Recessional_Adapter = new HomePage_Adapter(recessional_song_list, this, this);
        recessional_list.setLayoutManager(recessionalLayoutManager);
        recessional_list.setAdapter(Recessional_Adapter);
        Recessional_Adapter.notifyDataSetChanged();

        ItemTouchHelper.Callback recessionalcallback = new SimpleItemTouchHelperCallback(Recessional_Adapter);
        ItemTouchHelper recessionaltouchHelper = new ItemTouchHelper(recessionalcallback);
        recessionaltouchHelper.attachToRecyclerView(recessional_list);

        RecyclerView.LayoutManager readingLayoutManager = new LinearLayoutManager(getApplicationContext());
        reading_list = findViewById(R.id.Reading_List);
        Homepage_ListItem reading_content = new Homepage_ListItem("", "", 0);
        reading_content_list.add(reading_content);
        reading_Adapter = new Reading_Adapter(reading_content_list, this, this);
        reading_list.setLayoutManager(readingLayoutManager);
        reading_list.setAdapter(reading_Adapter);
        reading_Adapter.notifyDataSetChanged();

        ItemTouchHelper.Callback readingcallback = new ReadingSimpleItemTouchHelperCallback(reading_Adapter);
        ItemTouchHelper readingtouchHelper = new ItemTouchHelper(readingcallback);
        readingtouchHelper.attachToRecyclerView(reading_list);

        RecyclerView.LayoutManager gospelLayoutManager = new LinearLayoutManager(getApplicationContext());
        gospel_list = findViewById(R.id.Gospel_List);
        Homepage_ListItem gospel_content = new Homepage_ListItem("", "", 0);
        gospel_content_list.add(reading_content);
        gospel_Adapter = new Gospel_Adapter(gospel_content_list, this, this);
        gospel_list.setLayoutManager(gospelLayoutManager);
        gospel_list.setAdapter(gospel_Adapter);
        gospel_Adapter.notifyDataSetChanged();


        ItemTouchHelper.Callback gospelcallback = new GospelSimpleItemTouchHelperCallback(gospel_Adapter);
        ItemTouchHelper gospeltouchHelper = new ItemTouchHelper(gospelcallback);
        gospeltouchHelper.attachToRecyclerView(gospel_list);

        title_colour = findViewById(R.id.title_colour);
        content_colour = findViewById(R.id.content_colour);


        title_colour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorDrawable drawable = (ColorDrawable) title_colour.getBackground();
                ColorPickerDialogBuilder
                        .with(context)
                        .setTitle("Choose Title Text Colour")
                        .initialColor(drawable.getColor())
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(12)
                        .setOnColorSelectedListener(new OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(int selectedColor) {

                            }
                        })
                        .setPositiveButton("ok", new ColorPickerClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                                title_colour.setBackgroundColor(selectedColor);
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .build()
                        .show();
            }
        });

        content_colour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorDrawable drawable = (ColorDrawable) content_colour.getBackground();
                ColorPickerDialogBuilder
                        .with(context)
                        .setTitle("Choose Content Text Colour")
                        .initialColor(drawable.getColor())
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(12)
                        .setOnColorSelectedListener(new OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(int selectedColor) {

                            }
                        })
                        .setPositiveButton("ok", new ColorPickerClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                                content_colour.setBackgroundColor(selectedColor);
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .build()
                        .show();
            }
        });

        switchMaterial = findViewById(R.id.colour_preference_switch);
        final LinearLayout colour_options = findViewById(R.id.colour_preference_options);
        switchMaterial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    colour_options.setVisibility(View.GONE);
                } else {
                    colour_options.setVisibility(View.VISIBLE);
                }
            }
        });


        Button Back_Img_Button = findViewById(R.id.background_image_chooser_button);
        back_img_path = findViewById(R.id.background_image_chosen_path);
        Back_Img_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentfile = new Intent();
                intentfile.setType("image/*");
                intentfile.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intentfile, "Select Picture"), PICK_IMAGE_REQUEST);

            }
        });

        today_reading = findViewById(R.id.fetch_today_reading_button);
        sunday_reading = findViewById(R.id.fetch_sunday_reading_button);


        today_reading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Fetching readings.");
                progressDialog.setCancelable(false);
                progressDialog.show();

                /*Create handle for the RetrofitInstance interface*/
                LyricsGetDataService service = LyricsRetroFitClientInstance.getRetrofitInstance().create(LyricsGetDataService.class);
                Call<RetroReadings> call = service.getReadings("today");
                call.enqueue(new Callback<RetroReadings>() {
                    @Override
                    public void onResponse(Call<RetroReadings> call, Response<RetroReadings> response) {
                        progressDialog.dismiss();
                        try {
                            gospel_content_list.clear();
                            reading_content_list.clear();
                            List<String> gospel_reading_list = new ArrayList<>();
                            gospel_reading_list = response.body().getGospels();
                            List<String> reading_reading_list = new ArrayList<>();
                            reading_reading_list = response.body().getReadings();
                            List<String> response_reading_list = new ArrayList<>();
                            response_reading_list = response.body().getResponse();
                            Homepage_ListItem item;

                            for(int i =0; i<reading_reading_list.size(); i++) {
                                String title = "Reading";
                                if(i==0) {title = "First Reading";}
                                if(i==1) {title = "Second Reading";}
                                if(i==2) {title = "Third Reading";}
                                if(i==3) {title = "Fourth Reading";}
                                if(i==4) {title = "Fifth Reading";}
                                if(i==5) {title = "Sixth Reading";}
                                item = new Homepage_ListItem(title, reading_reading_list.get(i).trim(), 0);
                                reading_content_list.add(item);
                            }

                            for(int i =0; i<response_reading_list.size(); i++) {
                                String title = "Responsorial Psalm";
                                if(i==0) {title = "First Responsorial Psalm";}
                                if(i==1) {title = "Second Responsorial Psalm";}
                                if(i==2) {title = "Third Responsorial Psalm";}
                                if(i==3) {title = "Fourth Responsorial Psalm";}
                                if(i==4) {title = "Fifth Responsorial Psalm";}
                                if(i==5) {title = "Sixth Responsorial Psalm";}
                                item = new Homepage_ListItem(title, response_reading_list.get(i).trim(), 0);
                                reading_content_list.add(item);
                            }

                            for(int i =0; i<gospel_reading_list.size(); i++) {
                                String title = "Gospel Reading";
                                if(i==0) {title = "First Gospel Reading";}
                                if(i==1) {title = "Second Gospel Reading";}
                                if(i==2) {title = "Third Gospel Reading";}
                                if(i==3) {title = "Fourth Gospel Reading";}
                                if(i==4) {title = "Fifth Gospel Reading";}
                                if(i==5) {title = "Sixth Gospel Reading";}
                                item = new Homepage_ListItem(title, gospel_reading_list.get(i).trim(), 0);
                                gospel_content_list.add(item);
                            }

                            reading_Adapter.notifyDataSetChanged();
                            gospel_Adapter.notifyDataSetChanged();

                            Snackbar.make(view, "Readings found for today!", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        } catch (Exception e) {
                            Snackbar.make(view, "Unable to find readings. Please fill in the readings manually.", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RetroReadings> call, Throwable t) {
                        progressDialog.dismiss();
                        Snackbar.make(view, "Unable to find readings. Please fill in the readings manually.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        //Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        sunday_reading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Fetching readings.");
                progressDialog.setCancelable(false);
                progressDialog.show();

                /*Create handle for the RetrofitInstance interface*/
                LyricsGetDataService service = LyricsRetroFitClientInstance.getRetrofitInstance().create(LyricsGetDataService.class);
                Call<RetroReadings> call = service.getReadings("sunday");
                call.enqueue(new Callback<RetroReadings>() {
                    @Override
                    public void onResponse(Call<RetroReadings> call, Response<RetroReadings> response) {
                        progressDialog.dismiss();
                        try {
                            gospel_content_list.clear();
                            reading_content_list.clear();
                            List<String> gospel_reading_list = new ArrayList<>();
                            gospel_reading_list = response.body().getGospels();
                            List<String> reading_reading_list = new ArrayList<>();
                            reading_reading_list = response.body().getReadings();
                            List<String> response_reading_list = new ArrayList<>();
                            response_reading_list = response.body().getResponse();
                            Homepage_ListItem item;

                            for(int i =0; i<reading_reading_list.size(); i++) {
                                String title = "Reading";
                                if(i==0) {title = "First Reading";}
                                if(i==1) {title = "Second Reading";}
                                if(i==2) {title = "Third Reading";}
                                if(i==3) {title = "Fourth Reading";}
                                if(i==4) {title = "Fifth Reading";}
                                if(i==5) {title = "Sixth Reading";}
                                item = new Homepage_ListItem(title, reading_reading_list.get(i).trim(), 0);
                                reading_content_list.add(item);
                            }

                            for(int i =0; i<response_reading_list.size(); i++) {
                                String title = "Responsorial Psalm";
                                if(i==0) {title = "First Responsorial Psalm";}
                                if(i==1) {title = "Second Responsorial Psalm";}
                                if(i==2) {title = "Third Responsorial Psalm";}
                                if(i==3) {title = "Fourth Responsorial Psalm";}
                                if(i==4) {title = "Fifth Responsorial Psalm";}
                                if(i==5) {title = "Sixth Responsorial Psalm";}
                                item = new Homepage_ListItem(title, response_reading_list.get(i).trim(), 0);
                                reading_content_list.add(item);
                            }

                            for(int i =0; i<gospel_reading_list.size(); i++) {
                                String title = "Gospel Reading";
                                if(i==0) {title = "First Gospel Reading";}
                                if(i==1) {title = "Second Gospel Reading";}
                                if(i==2) {title = "Third Gospel Reading";}
                                if(i==3) {title = "Fourth Gospel Reading";}
                                if(i==4) {title = "Fifth Gospel Reading";}
                                if(i==5) {title = "Sixth Gospel Reading";}
                                item = new Homepage_ListItem(title, gospel_reading_list.get(i).trim(), 0);
                                gospel_content_list.add(item);
                            }

                            reading_Adapter.notifyDataSetChanged();
                            gospel_Adapter.notifyDataSetChanged();

                            Snackbar.make(view, "Readings found for Sunday!", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        } catch (Exception e) {
                            Snackbar.make(view, "Unable to find readings. Please fill in the readings manually.", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RetroReadings> call, Throwable t) {
                        progressDialog.dismiss();
                        Snackbar.make(view, "Unable to find readings. Please fill in the readings manually.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        //Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });




        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                if(Main_Title.getText().toString().trim().equals("")) {
                    Snackbar.make(view, "Please provide a main title.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {

                    fabview = view;
                    checkStoragePermission();
                }

            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                pictureshow.setVisibility(View.VISIBLE);
                pictureshow.setImageBitmap(bitmap);
                back_img_path.setText(filePath.getLastPathSegment() +"." + getContentResolver().getType(filePath).substring(getContentResolver().getType(filePath).lastIndexOf('/')+1));


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, dbc_information.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void checkStoragePermission() {
        // if android version >= 6.0
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(
                    context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                // if permission was not granted initially, ask the user again.
                requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);
            } else {
                // if android >= 6.0 and permission already granted, continue to download.
                startDownload();
            }
        } else {

            // if android < 6.0 continue to download, no need to ask permission again.
            startDownload();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // if permission allowed by the user, continue to download.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startDownload();
                } else {

                    // permission not granted, download can't take place.
                    Toast.makeText(context,
                            "Permission Denied to Write to External storage",
                            Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    void startDownload() {

        String Image_Path = "";

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Generating PPT. Kindly Wait.");
        progressDialog.setCancelable(false);
        progressDialog.show();

        //Generate JSON Data to send to server
        boolean back_exist = false;
        if(!back_img_path.getText().toString().equals("No background image chosen.")) {
            back_exist = true;
            FirebaseStorage storage = FirebaseStorage.getInstance();
            final StorageReference storageRef = storage.getReference();

            UUID uuid = UUID.randomUUID();
            String randomUUIDString = uuid.toString();

            riversRef = storageRef.child(Image_Path = ("images/" + randomUUIDString.trim() + back_img_path.getText().toString().trim().substring(back_img_path.getText().toString().trim().lastIndexOf('.'), back_img_path.getText().toString().trim().length())));
            uploadTask = riversRef.putFile(filePath);

            // Register observers to listen for when the download is done or if it fails
            final boolean finalBack_exist = back_exist;
            final String finalImage_Path = Image_Path;
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    progressDialog.dismiss();
                    Snackbar.make(fabview, "Unable to upload image.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...

                    ColorDrawable cd = (ColorDrawable) title_colour.getBackground();
                    int color = cd.getColor();
                    int[] title_colour_array = new int[3];
                    title_colour_array[0]= Color.red(color);
                    title_colour_array[1] = Color.green(color);
                    title_colour_array[2] = Color.blue(color);
                    cd = (ColorDrawable) content_colour.getBackground();
                    color = cd.getColor();
                    int[] content_colour_array = new int[3];
                    content_colour_array[0]= Color.red(color);
                    content_colour_array[1] = Color.green(color);
                    content_colour_array[2] = Color.blue(color);
                    PPTData data_to_send = new PPTData(Main_Title.getText().toString().trim(), Main_Subtitle.getText().toString().trim(), true, finalImage_Path, switchMaterial.isChecked(), title_colour_array, content_colour_array, reading_Adapter.getList(), gospel_Adapter.getList(), Entrance_Adapter.getList(), Kyrie_Adapter.getList(), Gloria_Adapter.getList(), Acclamation_Adapter.getList(), Offertory_Adapter.getList(), Holy_Adapter.getList(), Proclamation_Adapter.getList(), Communion_Adapter.getList(), Recessional_Adapter.getList());

                    /*Create handle for the RetrofitInstance interface*/
                    LyricsGetDataService service = LyricsRetroFitClientInstance.getRetrofitInstance().create(LyricsGetDataService.class);
                    Call<Download_Link> call = service.sendPPTData(data_to_send);
                    call.enqueue(new Callback<Download_Link>() {
                        @Override
                        public void onResponse(Call<Download_Link> call, Response<Download_Link> response) {
                            progressDialog.dismiss();
                            try {

                                String DownloadURL = response.body().getDownload_url();

                                FirebaseStorage filestorage = FirebaseStorage.getInstance();
                                final StorageReference fileRef = filestorage.getReference();

                                final StorageReference pathReference = fileRef.child(DownloadURL);

                                File rootPath = new File(Environment.getExternalStorageDirectory() + "/" + "DBC PPTs/");
                                if(!rootPath.exists()) {
                                    rootPath.mkdirs();
                                }

                                final File localFile = new File(rootPath,Main_Title.getText().toString().trim() + ".pptx");

                                pathReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                        Toast.makeText(MainActivity.this, "PPT stored in " + localFile.toString(), Toast.LENGTH_LONG).show();
                                        Log.e("firebase ",";local tem file created  created " +localFile.toString());
                                        //  updateDb(timestamp,localFile.toString(),position);
                                        Intent pptOpenintent = new Intent(Intent.ACTION_VIEW);
                                        pptOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        pptOpenintent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                        pptOpenintent.setDataAndType(FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", localFile), "application/vnd.ms-powerpoint");
                                        try {
                                            startActivity(pptOpenintent);
                                        }
                                        catch (ActivityNotFoundException e) {
                                            Toast.makeText(MainActivity.this, "Unable to open the PPT.", Toast.LENGTH_SHORT).show();
                                        }
                                        pathReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                // File deleted successfully
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception exception) {
                                                // Uh-oh, an error occurred!
                                            }
                                        });
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        Log.e("firebase ",";local tem file not created  created " +exception.toString());
                                        Toast.makeText(MainActivity.this, exception.toString(), Toast.LENGTH_LONG).show();
                                    }
                                });



                                riversRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // File deleted successfully
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Uh-oh, an error occurred!
                                    }
                                });

                                Snackbar.make(fabview, "PPT generated successfully.", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();



                            } catch (Exception e) {
                                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                                Snackbar.make(fabview, "Error generating PPT. Please try again later.", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<Download_Link> call, Throwable t) {
                            progressDialog.dismiss();
                            Snackbar.make(fabview, "Error generating PPT. Please try again later.", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            try {
                                riversRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // File deleted successfully
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Uh-oh, an error occurred!
                                    }
                                });
                            } catch (Exception e) {

                            }
                            //Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            });
        } else {
            ColorDrawable cd = (ColorDrawable) title_colour.getBackground();
            int color = cd.getColor();
            int[] title_colour_array = new int[3];
            title_colour_array[0] = Color.red(color);
            title_colour_array[1] = Color.green(color);
            title_colour_array[2] = Color.blue(color);
            cd = (ColorDrawable) content_colour.getBackground();
            color = cd.getColor();
            int[] content_colour_array = new int[3];
            content_colour_array[0] = Color.red(color);
            content_colour_array[1] = Color.green(color);
            content_colour_array[2] = Color.blue(color);
            PPTData data_to_send = new PPTData(Main_Title.getText().toString().trim(), Main_Subtitle.getText().toString().trim(), false, back_img_path.getText().toString(), switchMaterial.isChecked(), title_colour_array, content_colour_array, reading_Adapter.getList(), gospel_Adapter.getList(), Entrance_Adapter.getList(), Kyrie_Adapter.getList(), Gloria_Adapter.getList(), Acclamation_Adapter.getList(), Offertory_Adapter.getList(), Holy_Adapter.getList(), Proclamation_Adapter.getList(), Communion_Adapter.getList(), Recessional_Adapter.getList());

            /*Create handle for the RetrofitInstance interface*/
            LyricsGetDataService service = LyricsRetroFitClientInstance.getRetrofitInstance().create(LyricsGetDataService.class);
            Call<Download_Link> call = service.sendPPTData(data_to_send);
            call.enqueue(new Callback<Download_Link>() {
                @Override
                public void onResponse(Call<Download_Link> call, Response<Download_Link> response) {
                    progressDialog.dismiss();
                    try {

                        String DownloadURL = response.body().getDownload_url();

                        FirebaseStorage filestorage = FirebaseStorage.getInstance();
                        final StorageReference fileRef = filestorage.getReference();

                        final StorageReference pathReference = fileRef.child(DownloadURL);

                        File rootPath = new File(Environment.getExternalStorageDirectory() + "/" + "DBC PPTs/");
                        if (!rootPath.exists()) {
                            rootPath.mkdirs();
                        }

                        final File localFile = new File(rootPath, Main_Title.getText().toString().trim() + ".pptx");

                        pathReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                Toast.makeText(MainActivity.this, "PPT stored in " + localFile.toString(), Toast.LENGTH_LONG).show();
                                Log.e("firebase ", ";local tem file created  created " + localFile.toString());
                                //  updateDb(timestamp,localFile.toString(),position);
                                Intent pptOpenintent = new Intent(Intent.ACTION_VIEW);
                                pptOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                pptOpenintent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                pptOpenintent.setDataAndType(FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", localFile), "application/vnd.ms-powerpoint");
                                try {
                                    startActivity(pptOpenintent);
                                }
                                catch (ActivityNotFoundException e) {
                                    Toast.makeText(MainActivity.this, "Unable to open the PPT.", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                                }
                                pathReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // File deleted successfully
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Uh-oh, an error occurred!
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Log.e("firebase ", ";local tem file not created  created " + exception.toString());
                                Toast.makeText(MainActivity.this, exception.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });


                        Snackbar.make(fabview, "PPT generated successfully.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();



                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        Snackbar.make(fabview, "Error generating PPT. Please try again later.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    }
                }

                @Override
                public void onFailure(Call<Download_Link> call, Throwable t) {
                    progressDialog.dismiss();
                    Snackbar.make(fabview, "Error generating PPT. Please try again later.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    //Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }







    }
