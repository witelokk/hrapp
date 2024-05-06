package com.witelokk.hrapp.ui.company;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.witelokk.hrapp.R;
import com.witelokk.hrapp.api.model.Company;
import com.witelokk.hrapp.api.model.Department;
import com.witelokk.hrapp.databinding.ItemCompanyBinding;
import com.witelokk.hrapp.databinding.ItemDepartmentBinding;
import com.witelokk.hrapp.ui.home.OnCompanyItemClickListener;

import java.util.ArrayList;

public class DepartmentsAdapter extends RecyclerView.Adapter<DepartmentsAdapter.ViewHolder> {
    ArrayList<Department> departments = new ArrayList<>();
    OnDepartmentItemClickListener onItemClickListener;

    DepartmentsAdapter(OnDepartmentItemClickListener onItemClickListener) {
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
            holder.binding.getRoot().setOnClickListener(v -> onItemClickListener.onClick(departments.get(position).getId()));
        }
    }

    @Override
    public int getItemCount() {
        return departments.size();
    }
}
