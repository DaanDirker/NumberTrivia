package com.example.daan.numbertrivia;

import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class NumberAdapter extends RecyclerView.Adapter<NumberAdapter.ViewHolder> {

    public List<Number> mNumberList;
    private final int layoutLeftToRight = R.layout.number_info;
    private final int layoutRightToLeft = R.layout.number_info_rtl;
    private int layout;

    public NumberAdapter(List<Number> numberObjects) {
        this.mNumberList = numberObjects;
        this.layout = layoutLeftToRight;
    }

    public void switchDisplayDirection() {
        switch (layout) {
            case layoutLeftToRight:
                this.layout = layoutRightToLeft;
                break;
            case layoutRightToLeft:
                this.layout = layoutLeftToRight;
                break;
        }
    }

    @Override
    public NumberAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(this.layout, parent, false);
        return new NumberAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NumberAdapter.ViewHolder holder, int position) {
        // Gets a single item in the list from its position
        Number number = mNumberList.get(position);
        // The holder argument is used to reference the views inside the viewHolder
        // Populate the views with the data from the list
        holder.requestedNumber.setText(number.getNumber().toString());
        holder.requestedText.setText(number.getText());
    }

    @Override
    public int getItemCount() {
        return mNumberList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView requestedNumber;
        public TextView requestedText;

        public ViewHolder(View itemView) {
            super(itemView);
            this.requestedNumber = itemView.findViewById(R.id.requestedNumber);
            this.requestedText = itemView.findViewById(R.id.requestedText);
        }
    }
}

