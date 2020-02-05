package com.repelliuss.npuzzle.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.repelliuss.npuzzle.R;

public final class RecyclerViewAdapter
        extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private String[] data;
    private LayoutInflater inflater;

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView number;

        ViewHolder(View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.txt_number);
        }

        TextView getTextView() {
            return number;
        }
    }

    public RecyclerViewAdapter(Context context, String[] arg_data) {
        inflater = LayoutInflater.from(context);
        data = arg_data;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.number_block, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getTextView().setText(data[position]);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }


}
