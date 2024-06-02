package com.witelokk.hrapp.ui.department;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.witelokk.hrapp.R;
import com.witelokk.hrapp.api.model.Employee;
import com.witelokk.hrapp.databinding.ItemEmployeeBinding;

import java.util.List;
import java.util.Locale;

public class EmployeesAdapter extends RecyclerView.Adapter<EmployeesAdapter.ViewHolder> {
    private final List<Employee> employees;
    private final OnEmployeeItemClickListener onItemClickListener;

    EmployeesAdapter(List<Employee> employees, OnEmployeeItemClickListener onItemClickListener) {
        this.employees = employees;
        this.onItemClickListener = onItemClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemEmployeeBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemEmployeeBinding.bind(itemView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_employee, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Employee employee = employees.get(position);
        holder.binding.textViewName.setText(employee.getName());
        if (employee.getCurrentInfo() != null) {
            holder.binding.textViewPosition.setText(employee.getCurrentInfo().getPosition());
            holder.binding.textViewSalary.setText(String.format(Locale.getDefault(), "%,d", (int) employee.getCurrentInfo().getSalary()));
        }

        if (onItemClickListener != null) {
            holder.binding.getRoot().setOnClickListener(v -> onItemClickListener.onClick(employees.get(position)));
        }
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    public void setEmployees(List<Employee> employees) {
        DiffUtil.Callback callback = new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return EmployeesAdapter.this.employees.size();
            }

            @Override
            public int getNewListSize() {
                return employees.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                Employee oldCompany = EmployeesAdapter.this.employees.get(oldItemPosition);
                Employee newCompany = employees.get(newItemPosition);
                return oldCompany.getId() == newCompany.getId();
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                Employee oldCompany = EmployeesAdapter.this.employees.get(oldItemPosition);
                Employee newCompany = employees.get(newItemPosition);
                return oldCompany.equals(newCompany);
            }
        };

        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(callback);
        this.employees.clear();
        this.employees.addAll(employees);
        diffResult.dispatchUpdatesTo(this);
    }
}
