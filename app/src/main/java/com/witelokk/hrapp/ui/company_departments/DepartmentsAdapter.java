package com.witelokk.hrapp.ui.company_departments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.witelokk.hrapp.R;
import com.witelokk.hrapp.api.model.Department;
import com.witelokk.hrapp.databinding.ItemDepartmentBinding;

import java.util.List;

public class DepartmentsAdapter extends RecyclerView.Adapter<DepartmentsAdapter.ViewHolder> {
    private List<Department> departments;
    private final OnDepartmentItemClickListener onItemClickListener;

    DepartmentsAdapter(List<Department> departments, OnDepartmentItemClickListener onItemClickListener) {
        this.departments = departments;
        this.onItemClickListener = onItemClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemDepartmentBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemDepartmentBinding.bind(itemView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_department, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.textViewName.setText(departments.get(position).getName());

        if (onItemClickListener != null) {
            holder.binding.getRoot().setOnClickListener(v -> onItemClickListener.onClick(departments.get(position)));
        }
    }

    @Override
    public int getItemCount() {
        return departments.size();
    }

    public void setDepartments(List<Department> departments) {
        DiffUtil.Callback callback = new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return DepartmentsAdapter.this.departments.size();
            }

            @Override
            public int getNewListSize() {
                return departments.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                Department oldCompany = DepartmentsAdapter.this.departments.get(oldItemPosition);
                Department newCompany = departments.get(newItemPosition);
                return oldCompany.getId() == newCompany.getId();
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                Department oldCompany = DepartmentsAdapter.this.departments.get(oldItemPosition);
                Department newCompany = departments.get(newItemPosition);
                return oldCompany.equals(newCompany);
            }
        };

        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(callback);
        this.departments.clear();
        this.departments.addAll(departments);
        diffResult.dispatchUpdatesTo(this);
    }
}
