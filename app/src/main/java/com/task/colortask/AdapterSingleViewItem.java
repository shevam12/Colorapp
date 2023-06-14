package com.task.colortask;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class AdapterSingleViewItem extends RecyclerView.Adapter<AdapterSingleViewItem.MyAdapter>
{
    public List<String> arr_color_name;
    public List<String> arr_color_code;


    public AdapterSingleViewItem(List<String> colorNames, List<String> colorCodes)
    {
        this.arr_color_name=colorNames;
        this.arr_color_code=colorCodes;
    }
    private int adapterCount = 0;
    @NonNull
    @Override
    public MyAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view= layoutInflater.inflate(R.layout.single_items,parent,false);
        Log.d("Adapter Execution Count", "Count: " + adapterCount);
        adapterCount++;
        return new MyAdapter(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyAdapter holder, @SuppressLint("RecyclerView") int position) {
        holder.color_name.setText(arr_color_name.get(position));

        String colorCode = arr_color_code.get(position);
        try {
            int color = Color.parseColor("#"+colorCode);
            holder.color.setBackgroundColor(color);
        } catch (IllegalArgumentException e) {
            holder.color.setBackgroundColor(Color.GRAY); // Set a default color for invalid color codes
            Log.e("Color Error", "Invalid color code: " + colorCode);
        }

        holder.color_code.setText(colorCode);

        holder.color.setOnClickListener(view -> {
            // Show an alert dialog when color is clicked
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            // Create a custom view for the dialog
            LayoutInflater inflater = LayoutInflater.from(view.getContext());
            View dialogView = inflater.inflate(R.layout.diaglog_layout, null);
            TextView colorNameTextView = dialogView.findViewById(R.id.color_name);
            LinearLayout dialogLayout = dialogView.findViewById(R.id.dialog_layout);

            colorNameTextView.setText(arr_color_name.get(position) +"\n"+ colorCode);

            try
            {
                int color = Color.parseColor("#"+colorCode);
                dialogLayout.setBackgroundColor(color);
            }
            catch (IllegalArgumentException e)
            {
                dialogLayout.setBackgroundColor(Color.GREEN); // Set a default color for invalid color codes
                Log.e("Color Error", "Invalid color code: " + colorCode);
            }

            builder.setView(dialogView).show();
        });
    }



    @Override
    public int getItemCount()
    {
        return arr_color_name.size();
    }



    static class MyAdapter extends RecyclerView.ViewHolder
    {
        private final TextView color_name;
        private final TextView color;
        private final TextView color_code;

        public MyAdapter(@NonNull View itemView) {
            super(itemView);
            color_name=itemView.findViewById(R.id.color_name);
            color=itemView.findViewById(R.id.color);
            color_code=itemView.findViewById(R.id.color_code);
        }
    }
}

