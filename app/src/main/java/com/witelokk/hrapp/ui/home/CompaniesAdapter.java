package com.witelokk.hrapp.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.witelokk.hrapp.R;
import com.witelokk.hrapp.api.model.Company;
import com.witelokk.hrapp.databinding.ItemCompanyBinding;

import java.util.ArrayList;

public class CompaniesAdapter extends RecyclerView.Adapter<CompaniesAdapter.ViewHolder> {
    ArrayList<Company> companies = new ArrayList<>();
    OnCompanyItemClickListener onItemClickListener;

    CompaniesAdapter(OnCompanyItemClickListener onItemClickListener) {
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
        holder.binding.textViewName.setText(companies.get(position).getName());
        holder.binding.textViewInn.setText(companies.get(position).getInn());
        holder.binding.textViewKpp.setText(companies.get(position).getKpp());

        if (onItemClickListener != null) {
            holder.binding.getRoot().setOnClickListener(v -> onItemClickListener.onClick(companies.get(position).getId()));
        }
    }

    @Override
    public int getItemCount() {
        return companies.size();
    }
}
