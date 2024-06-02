package com.witelokk.hrapp.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.witelokk.hrapp.R;
import com.witelokk.hrapp.api.model.Company;
import com.witelokk.hrapp.databinding.ItemCompanyBinding;

import java.util.List;

public class CompaniesAdapter extends RecyclerView.Adapter<CompaniesAdapter.ViewHolder> {
    private List<Company> companies;
    private final OnCompanyItemClickListener onItemClickListener;

    CompaniesAdapter(List<Company> companies, OnCompanyItemClickListener onItemClickListener) {
        this.companies = companies;
        this.onItemClickListener = onItemClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemCompanyBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemCompanyBinding.bind(itemView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_company, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Company company = companies.get(position);
        holder.binding.textViewName.setText(company.getName());
        holder.binding.textViewInn.setText(holder.itemView.getContext().getString(R.string.inn, company.getInn()));
        holder.binding.textViewKpp.setText(holder.itemView.getContext().getString(R.string.kpp, company.getKpp()));

        if (onItemClickListener != null) {
            holder.binding.getRoot().setOnClickListener(v -> onItemClickListener.onClick(companies.get(position)));
        }
    }

    @Override
    public int getItemCount() {
        return companies.size();
    }

    public void setCompanies(List<Company> companies) {
        DiffUtil.Callback callback = new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return CompaniesAdapter.this.companies.size();
            }

            @Override
            public int getNewListSize() {
                return companies.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                Company oldCompany = CompaniesAdapter.this.companies.get(oldItemPosition);
                Company newCompany = companies.get(newItemPosition);
                return oldCompany.getId() == newCompany.getId();
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                Company oldCompany = CompaniesAdapter.this.companies.get(oldItemPosition);
                Company newCompany = companies.get(newItemPosition);
                return oldCompany.equals(newCompany);
            }
        };

        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(callback);
        this.companies.clear();
        this.companies.addAll(companies);
        diffResult.dispatchUpdatesTo(this);
    }
}
