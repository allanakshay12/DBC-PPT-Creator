package com.example.choirpptcreator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Collections;
import java.util.List;

public class Reading_Adapter extends RecyclerView.Adapter<Reading_Adapter.MyViewHolder> implements ItemTouchHelperAdapter{

    public List<Homepage_ListItem> homepagelist;
    Activity activity;
    Context context;
    Homepage_ListItem newitem;

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
        public Button view_reading;





        public MyViewHolder(View view)
        {
            super(view);
            title = view.findViewById(R.id.reading_title);
            add_but = view.findViewById(R.id.add_reading_button);
            del_but = view.findViewById(R.id.delete_reading_button);
            view_reading = view.findViewById(R.id.View_Reading_Button);
        }

    }

    public Reading_Adapter(List<Homepage_ListItem> homepagelist, Activity activity, Context context)
    {
        this.homepagelist = homepagelist;
        this.activity = activity;
        this.context = context;
        this.homepagelist = homepagelist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.readings_listitem, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        if(homepagelist.get(holder.getAdapterPosition()).getTitle().trim().equals("")){
            holder.title.setText("");
        } else {
            holder.title.setText(homepagelist.get(holder.getAdapterPosition()).getTitle().trim());
        }

        holder.view_reading.setOnClickListener(new View.OnClickListener() {
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

        holder.title.addTextChangedListener(new TextWatcher() {
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
                Reading_Adapter.super.notifyItemInserted(holder.getAdapterPosition()+1);
                //Toast.makeText(activity, Integer.toString(position), Toast.LENGTH_SHORT).show();
            }
        });

        holder.del_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(homepagelist.size()==1) {
                    Snackbar.make(view, "Atleast one reading must exist", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    homepagelist.remove(holder.getAdapterPosition());
                    Reading_Adapter.super.notifyItemRemoved(holder.getAdapterPosition());
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
