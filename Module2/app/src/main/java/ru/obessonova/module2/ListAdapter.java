package ru.obessonova.module2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {
    
    private List<String> mStackInfo;
    private Context mContext;
    
    public ListAdapter(Context ctx, List<String> str) {
        mContext = ctx;
        mStackInfo = str;
    }
    
    public static class ListViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        
        public ListViewHolder(TextView view) {
            super(view);
            textView = view;
        }
    }
    
    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        TextView view = (TextView) LayoutInflater.from(mContext)
                .inflate(R.layout.list_item, viewGroup, false);
        ListViewHolder listViewHolder = new ListViewHolder(view);
        return listViewHolder;
    }
    
    @Override
    public void onBindViewHolder(@NonNull ListViewHolder listViewHolder, int position) {
        listViewHolder.textView.setText(mStackInfo.get(position));
    }
    
    @Override
    public int getItemCount() {
        return mStackInfo.size();
    }
}
