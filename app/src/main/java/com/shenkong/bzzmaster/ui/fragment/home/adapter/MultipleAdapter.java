package com.shenkong.bzzmaster.ui.fragment.home.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public abstract class MultipleAdapter extends RecyclerView.Adapter<MultipleAdapter.MultipleBaseViewHolder> {
    private FragmentActivity fragmentActivity;
    private List<LayoutType> listBeans;

    public MultipleAdapter(FragmentActivity fragmentActivity, List<LayoutType> listBeans) {
        this.fragmentActivity = fragmentActivity;
        this.listBeans = listBeans;
    }

    @NonNull
    @Override
    public abstract MultipleAdapter.MultipleBaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

    @Override
    public int getItemViewType(int position) {
        return listBeans.get(position).getLayoutType();
    }

    @Override
    public void onBindViewHolder(@NonNull MultipleAdapter.MultipleBaseViewHolder holder, int position) {
        holder.load(this, position);
    }

    @Override
    public int getItemCount() {
        return listBeans.size();
    }

    public Object getBean(int position) {
        return listBeans.get(position);
    }

    public FragmentActivity getFragmentActivity() {
        return fragmentActivity;
    }

    public void setFragmentActivity(AppCompatActivity fragmentActivity) {
        this.fragmentActivity = fragmentActivity;
    }

    public List<LayoutType> getListBeans() {
        return listBeans;
    }

    public void setListBeans(List<LayoutType> listBeans) {
        this.listBeans = listBeans;
    }

    public static abstract class MultipleBaseViewHolder extends RecyclerView.ViewHolder {
        public MultipleBaseViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public abstract void load(MultipleAdapter multipleAdapter, int position);
    }

    public static abstract interface LayoutType {
        int getLayoutType();
    }
}
