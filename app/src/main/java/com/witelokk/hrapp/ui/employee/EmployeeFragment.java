package com.witelokk.hrapp.ui.employee;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.witelokk.hrapp.R;
import com.witelokk.hrapp.databinding.DialogDeleteDepartmentBinding;
import com.witelokk.hrapp.databinding.DialogDeleteEmployeeBinding;
import com.witelokk.hrapp.databinding.FragmentEmployeeBinding;
import com.witelokk.hrapp.ui.BaseFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class EmployeeFragment extends BaseFragment<EmployeeViewModel> {
    private FragmentEmployeeBinding binding;
    private EmployeeFragmentArgs args;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = EmployeeFragmentArgs.fromBundle(getArguments());
        viewModel = new ViewModelProvider(requireActivity()).get(String.valueOf(args.getEmployee().getId()), EmployeeViewModel.class);
        viewModel.setEmployeeId(args.getEmployee().getId());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEmployeeBinding.inflate(inflater, container, false);
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            ((ViewGroup.MarginLayoutParams) binding.fabAddAction.getLayoutParams()).bottomMargin += systemBars.bottom;
            binding.recyclerView.setPaddingRelative(0, 0, 0, systemBars.bottom);
            binding.recyclerView.setClipToPadding(false);
            return insets;
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.toolbar);

        ((MenuHost) requireActivity()).addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.menu_employee, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.menu_delete) {
                    showDeleteDialog();
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);

        binding.toolbar.setTitle(args.getEmployee().getName());
        binding.textViewDepartment.setText(args.getEmployee().getCurrentInfo().getDepartment().getName());
        binding.textViewPosition.setText(args.getEmployee().getCurrentInfo().getPosition());
        binding.textViewSalary.setText(String.format(Locale.getDefault(), "%,d", (int) args.getEmployee().getCurrentInfo().getSalary()));

        ActionsAdapter adapter = new ActionsAdapter(new ArrayList<>(), action -> {
            switch (action.getType()) {
                case "recruitment":
                    getNavController().navigate(EmployeeFragmentDirections.actionEmployeeFragmentToRecruitmentActionFragment(action, viewModel.getEmployee().getValue()));
                    break;
                case "department_transfer":
                    getNavController().navigate(EmployeeFragmentDirections.actionEmployeeFragmentToDepartmentTransferActionFragment(action, viewModel.getEmployee().getValue()));
                    break;
                case "position_transfer":
                    getNavController().navigate(EmployeeFragmentDirections.actionEmployeeFragmentToPositionTransferActionFragment(action, viewModel.getEmployee().getValue()));
                    break;
                case "salary_change":
                    getNavController().navigate(EmployeeFragmentDirections.actionEmployeeFragmentToSalaryChangeActionFragment(action, viewModel.getEmployee().getValue()));
                    break;
                case "dismissal":
                    getNavController().navigate(EmployeeFragmentDirections.actionEmployeeFragmentToDismissalActionFragment(action, viewModel.getEmployee().getValue()));
                    break;
            }
        });
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        viewModel.getEmployee().observe(getViewLifecycleOwner(), employee -> {
            binding.toolbar.setTitle(employee.getName());
            binding.textViewPosition.setText(employee.getCurrentInfo().getPosition());
            binding.textViewSalary.setText(String.format(Locale.getDefault(), "%,d", (int) employee.getCurrentInfo().getSalary()));
            binding.textViewDepartment.setText(String.valueOf(employee.getCurrentInfo().getDepartment().getName()));

            if (employee.getGender().equals("male"))
                binding.imageView.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.male));
            else
                binding.imageView.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.female));

            binding.textViewPersonalInformationData.setText(getString(R.string.personal_information_data, DateFormat.getDateInstance().format(employee.getBirthdate()), employee.getGender(), employee.getAddress(), employee.getSnils(), employee.getInn(), employee.getPassportNumber(), employee.getPasswordIssuer(), DateFormat.getDateInstance().format(employee.getPassportDate())));

            adapter.setActions(employee.getActions());
        });

        viewModel.getIsDeleted().observe(getViewLifecycleOwner(), isDeletedEvent -> {
            if (isDeletedEvent.getContent() == Boolean.TRUE)
                getNavController().navigateUp();
        });

        binding.cardViewPersonalInfo.setOnClickListener(v -> {
            com.witelokk.hrapp.ui.employee.EmployeeFragmentDirections.ActionEmployeeFragmentToEditEmployeeFragment action = EmployeeFragmentDirections.actionEmployeeFragmentToEditEmployeeFragment(viewModel.getEmployee().getValue());
            getNavController().navigate(action);
        });

        binding.fabAddAction.setOnClickListener(v -> {
            EmployeeFragmentDirections.ActionEmployeeFragmentToSelectActionToAddFragment action = EmployeeFragmentDirections.actionEmployeeFragmentToSelectActionToAddFragment(viewModel.getEmployee().getValue());
            getNavController().navigate(action);
        });

        viewModel.loadData();
    }

    private void showDeleteDialog() {
        MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(requireContext());
        DialogDeleteEmployeeBinding dialogBinding = DialogDeleteEmployeeBinding.inflate(getLayoutInflater());
        dialogBuilder.setView(dialogBinding.getRoot());

        dialogBuilder.setTitle(R.string.delete_employee);
        dialogBuilder.setPositiveButton(R.string.delete, (dialog, whichButton) -> {
            viewModel.deleteEmployee();
        });
        dialogBuilder.setNegativeButton(R.string.cancel, (dialog, whichButton) -> {
        });

        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }
}
