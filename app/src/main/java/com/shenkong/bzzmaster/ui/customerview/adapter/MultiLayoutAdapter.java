package com.shenkong.bzzmaster.ui.customerview.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class MultiLayoutAdapter extends RecyclerView.Adapter<MultiLayoutAdapter.MultipleLayoutViewHolder> {
    private static final String TAG = "MultipleAdapter";
    private List<LayoutType> dataList = new ArrayList<>();

    @NonNull
    @Override
    public abstract MultipleLayoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position).getLayoutType();
    }

    @Override
    public void onBindViewHolder(@NonNull MultipleLayoutViewHolder holder, int position) {
        holder.load(this, position);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public <T> T getBean(Class<T> mClass, int position) {
        return mClass.cast(dataList.get(position));
    }

    public List<LayoutType> getDataList() {
        return dataList;
    }

    public void setDataList(List<LayoutType> dataList) {
        this.dataList = dataList;
    }

    public void addAllData(List<LayoutType> layoutTypeList) {
        dataList.addAll(layoutTypeList);
        notifyDataSetChanged();
    }

    public void updateData(List<LayoutType> layoutTypeList) {
        dataList.clear();
        dataList.addAll(layoutTypeList);
        notifyDataSetChanged();
    }

    public static abstract class MultipleLayoutViewHolder extends RecyclerView.ViewHolder {
        public MultipleLayoutViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public abstract void load(MultiLayoutAdapter multiLayoutAdapter, int position);
    }

    public interface LayoutType {
        int getLayoutType();
    }
}
