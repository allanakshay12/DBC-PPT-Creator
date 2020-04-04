package com.example.choirpptcreator;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePage_Adapter extends RecyclerView.Adapter<HomePage_Adapter.MyViewHolder> implements ItemTouchHelperAdapter{

    public List<Homepage_ListItem> homepagelist = new ArrayList<>();
    public TextWatcher textWatcher;
    Activity activity;
    Context context;
    Homepage_ListItem newitem;
    ProgressDialog progressDialog;
    //Hashtable<Integer, Homepage_ListItem> my_dict = new Hashtable<Integer, Homepage_ListItem>();

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(homepagelist, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(homepagelist, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onClear() {
        //notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public EditText title;
        public ImageView add_but, del_but;
        public Button find_lyrics, view_lyrics;



        public MyViewHolder(View view)
        {
            super(view);
            title = view.findViewById(R.id.song_title);
            add_but = view.findViewById(R.id.add_song_button);
            del_but = view.findViewById(R.id.delete_song_button);
            find_lyrics = view.findViewById(R.id.Find_Lyrics_Button);
            view_lyrics = view.findViewById(R.id.View_Lyrics_Button);
        }

    }

    public HomePage_Adapter(List<Homepage_ListItem> homepagelist, Activity activity, Context context)
    {
        this.activity = activity;
        this.context = context;
        newitem = new Homepage_ListItem("", "", 0);
        this.homepagelist.add(newitem);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.homepage_listitem, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        if(homepagelist.get(holder.getAdapterPosition()).getTitle().trim().equals("")){
            holder.title.setText("");
        } else {
            holder.title.setText(homepagelist.get(holder.getAdapterPosition()).getTitle().trim());
        }


        holder.find_lyrics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                try {
                    progressDialog = new ProgressDialog(context);
                    progressDialog.setMessage("Fetching lyrics.");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    /*Create handle for the RetrofitInstance interface*/
                    LyricsGetDataService service = LyricsRetroFitClientInstance.getRetrofitInstance().create(LyricsGetDataService.class);
                    Call<RetroLyrics> call = service.getLyrics(homepagelist.get(holder.getAdapterPosition()).getTitle());
                    call.enqueue(new Callback<RetroLyrics>() {
                        @Override
                        public void onResponse(Call<RetroLyrics> call, Response<RetroLyrics> response) {
                            progressDialog.dismiss();
                            try {
                                homepagelist.get(holder.getAdapterPosition()).setContent(response.body().getLyrics().trim());
                                Snackbar.make(view, "Lyrics Found! - " + homepagelist.get(holder.getAdapterPosition()).getTitle(), Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            } catch (Exception e) {
                                Snackbar.make(view, "Unable to find lyrics. Please try with a different title.", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<RetroLyrics> call, Throwable t) {
                            progressDialog.dismiss();
                            Snackbar.make(view, "Unable to find lyrics. Please try with a different title.", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            //Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    Snackbar.make(view, "Unable to find lyrics. Please try with a different title.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        holder.view_lyrics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // create an alert builder
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(homepagelist.get(holder.getAdapterPosition()).getTitle());
                // set the custom layout
                final View customLayout = activity.getLayoutInflater().inflate(R.layout.lyrics_reading_layout, null);
                final EditText editText = customLayout.findViewById(R.id.lyrics_reading_edittext);
                editText.setText(homepagelist.get(holder.getAdapterPosition()).getContent());
                builder.setView(customLayout);
                // add a button
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // send data from the AlertDialog to the Activity

                        homepagelist.get(holder.getAdapterPosition()).setContent(editText.getText().toString().trim());
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });



        holder.title.addTextChangedListener(textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                homepagelist.get(holder.getAdapterPosition()).setTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        holder.add_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newitem = new Homepage_ListItem("", "", position+1);
                homepagelist.add(holder.getAdapterPosition()+1, newitem);
                HomePage_Adapter.super.notifyItemInserted(holder.getAdapterPosition()+1);
                //Toast.makeText(activity, Integer.toString(position), Toast.LENGTH_SHORT).show();

            }
        });

        holder.del_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(homepagelist.size()==1) {
                    Snackbar.make(view, "Atleast one song must exist", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    homepagelist.remove(holder.getAdapterPosition());
                    HomePage_Adapter.super.notifyItemRemoved(holder.getAdapterPosition());
                    //Toast.makeText(activity, Integer.toString(position), Toast.LENGTH_SHORT).show();
                    //HomePage_Adapter.super.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return homepagelist.size();
    }

    public List<Homepage_ListItem> getList() {return  homepagelist;}
}
