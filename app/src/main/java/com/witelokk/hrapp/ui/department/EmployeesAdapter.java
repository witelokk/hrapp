package com.witelokk.hrapp.ui.department;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.witelokk.hrapp.R;
import com.witelokk.hrapp.api.model.Employee;
import com.witelokk.hrapp.databinding.ItemEmployeeBinding;

import java.util.List;

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
            holder.binding.textViewSalary.setText(String.valueOf(employee.getCurrentInfo().getSalary()));
        }

        if (onItemClickListener != null) {
            holder.binding.getRoot().setOnClickListener(v -> onItemClickListener.onClick(employees.get(position).getId()));
        }
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }
}
