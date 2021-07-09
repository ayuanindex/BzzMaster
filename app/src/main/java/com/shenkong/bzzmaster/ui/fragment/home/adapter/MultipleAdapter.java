package com.shenkong.bzzmaster.ui.fragment.home.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class MultipleAdapter extends RecyclerView.Adapter<MultipleAdapter.MultipleBaseViewHolder> {
    private FragmentActivity fragmentActivity;
    private List<LayoutType> dataList = new ArrayList<>();
    private List<LayoutType> productPlanBeanList;
    public static final int HEAD = 0;

    public MultipleAdapter(FragmentActivity fragmentActivity) {
        this.fragmentActivity = fragmentActivity;
    }

    @NonNull
    @Override
    public abstract MultipleAdapter.MultipleBaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position).getLayoutType();
    }

    @Override
    public void onBindViewHolder(@NonNull MultipleAdapter.MultipleBaseViewHolder holder, int position) {
        holder.load(this, position);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public Object getBean(int position) {
        return dataList.get(position);
    }

    public FragmentActivity getFragmentActivity() {
        return fragmentActivity;
    }

    public void setFragmentActivity(AppCompatActivity fragmentActivity) {
        this.fragmentActivity = fragmentActivity;
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

    public void resetDataList(List<LayoutType> productPlanBeanList) {
        if (this.productPlanBeanList == null) {
            this.productPlanBeanList = new ArrayList<>();
        }

        dataList.removeAll(this.productPlanBeanList);
        this.productPlanBeanList.clear();
        this.productPlanBeanList.addAll(productPlanBeanList);

        dataList.addAll(productPlanBeanList);
        notifyDataSetChanged();
    }

    /**
     * 在指定位置添加
     *
     * @param layoutType 添加数据
     * @param position   指定位置，靠前的位置
     */
    public void addData(LayoutType layoutType, int position) {
       /* if (dataList.size() >= 2) {
            dataList.add(position, layoutType);
        } else {
            dataList.add(0, layoutType);
        }*/
        dataList.add(position, layoutType);
        notifyDataSetChanged();
    }

    public void addData(LayoutType layoutType) {
        dataList.add(layoutType);
        notifyDataSetChanged();
    }

    public static abstract class MultipleBaseViewHolder extends RecyclerView.ViewHolder {
        public MultipleBaseViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public abstract void load(MultipleAdapter multipleAdapter, int position);
    }

    public interface LayoutType {
        int getLayoutType();
    }
}
