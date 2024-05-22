package com.witelokk.hrapp.ui.employee;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.witelokk.hrapp.R;
import com.witelokk.hrapp.api.model.Action;
import com.witelokk.hrapp.databinding.ItemDepartmentTransferActionBinding;
import com.witelokk.hrapp.databinding.ItemDismissalActionBinding;
import com.witelokk.hrapp.databinding.ItemPositionTransferActionBinding;
import com.witelokk.hrapp.databinding.ItemRecruitmentActionBinding;
import com.witelokk.hrapp.databinding.ItemSalaryChangeActionBinding;

import java.text.DateFormat;
import java.util.List;
import java.util.Locale;

public class ActionsAdapter extends RecyclerView.Adapter<ActionsAdapter.ViewHolder> {
    private final List<Action> actions;
    private final OnActionItemClick onItemClickListener;

    ActionsAdapter(List<Action> actions, OnActionItemClick onItemClickListener) {
        this.actions = actions;
        this.onItemClickListener = onItemClickListener;
    }

    public abstract static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public abstract void setAction(Action action);
    }

    public static class EmptyViewHolder extends ViewHolder {

        public EmptyViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void setAction(Action action) {}
    }

    public static class RecruitmentActionViewHolder extends ViewHolder {
        ItemRecruitmentActionBinding binding;

        public RecruitmentActionViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemRecruitmentActionBinding.bind(itemView);
        }

        @Override
        public void setAction(Action action) {
            Action.RecruitmentAction recruitment = action.getRecruitment();
            binding.textViewDate.setText(DateFormat.getDateInstance().format(action.getDate()));
            binding.textViewPosition.setText(recruitment.getPosition());
            binding.textViewDepartment.setText(recruitment.getDepartment().getName());
            binding.textViewSalary.setText(String.format(Locale.getDefault(), "%,d", (int) recruitment.getSalary()));
        }
    }

    public static class SalaryChangeActionViewHolder extends ViewHolder {
        ItemSalaryChangeActionBinding binding;

        public SalaryChangeActionViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemSalaryChangeActionBinding.bind(itemView);
        }

        @Override
        public void setAction(Action action) {
            Action.SalaryChangeAction salaryChange = action.getSalaryChange();
            binding.textViewDate.setText(DateFormat.getDateInstance().format(action.getDate()));
            binding.textViewPreviousSalary.setText(String.format(Locale.getDefault(), "%,d", (int) salaryChange.getPreviousSalary()));
            binding.textViewNewSalary.setText(String.format(Locale.getDefault(), "%,d", (int) salaryChange.getNewSalary()));
        }
    }

    public static class DepartmentTransferActionViewHolder extends ViewHolder {
        ItemDepartmentTransferActionBinding binding;

        public DepartmentTransferActionViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemDepartmentTransferActionBinding.bind(itemView);
        }

        @Override
        public void setAction(Action action) {
            Action.DepartmentTransferAction departmentTransfer = action.getDepartmentTransfer();
            binding.textViewDate.setText(DateFormat.getDateInstance().format(action.getDate()));
            binding.textViewPreviousDepartment.setText(String.valueOf(departmentTransfer.getPreviousDepartment().getName()));
            binding.textViewNewDepartment.setText(String.valueOf(departmentTransfer.getNewDepartment().getName()));
        }
    }

    public static class PositionTransferActionViewHolder extends ViewHolder {
        ItemPositionTransferActionBinding binding;

        public PositionTransferActionViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemPositionTransferActionBinding.bind(itemView);
        }

        @Override
        public void setAction(Action action) {
            Action.PositionTransferAction positionTransfer = action.getPositionTransfer();
            binding.textViewDate.setText(DateFormat.getDateInstance().format(action.getDate()));
            binding.textViewPreviousPosition.setText(String.valueOf(positionTransfer.getPreviousPosition()));
            binding.textViewNewPosition.setText(String.valueOf(positionTransfer.getNewPosition()));
        }
    }

    public static class DismissalActionViewHolder extends ViewHolder {
        ItemDismissalActionBinding binding;
        public DismissalActionViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemDismissalActionBinding.bind(itemView);
        }

        @Override
        public void setAction(Action action) {
            binding.textViewDate.setText(DateFormat.getDateInstance().format(action.getDate()));
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recruitment_action, parent, false);
                return new RecruitmentActionViewHolder(view);
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_salary_change_action, parent, false);
                return new SalaryChangeActionViewHolder(view);
            case 3:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_department_transfer_action, parent, false);
                return new DepartmentTransferActionViewHolder(view);
            case 4:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_position_transfer_action, parent, false);
                return new PositionTransferActionViewHolder(view);
            case 5:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dismissal_action, parent, false);
                return new DismissalActionViewHolder(view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_action, parent, false);
                return new EmptyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Action action = actions.get(position);
        holder.setAction(action);

        if (onItemClickListener != null) {
//            holder.binding.getRoot().setOnClickListener(v -> onItemClickListener.onClick(actions.get(position).getId()));
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (actions.get(position).getType()) {
            case "recruitment":
                return 1;
            case "salary_change":
                return 2;
            case "department_transfer":
                return 3;
            case "position_transfer":
                return 4;
            case "dismissal":
                return 5;
            default:
                return 0;
        }
    }

    @Override
    public int getItemCount() {
        return actions.size();
    }
}
