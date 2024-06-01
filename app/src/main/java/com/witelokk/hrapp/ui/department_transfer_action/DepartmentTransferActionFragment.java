package com.witelokk.hrapp.ui.department_transfer_action;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.witelokk.hrapp.R;
import com.witelokk.hrapp.api.model.Department;
import com.witelokk.hrapp.databinding.DialogDeleteActionBinding;
import com.witelokk.hrapp.databinding.FragmentDepartmentTransferActionBinding;
import com.witelokk.hrapp.ui.BaseFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DepartmentTransferActionFragment extends BaseFragment<DepartmentTransferActionFragmentViewModel> {
    private FragmentDepartmentTransferActionBinding binding;
    private DepartmentTransferActionFragmentArgs args;
    private final DateFormat dateFormat = SimpleDateFormat.getDateInstance();
    private int departmentPosition = -1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(DepartmentTransferActionFragmentViewModel.class);
        args = DepartmentTransferActionFragmentArgs.fromBundle(getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDepartmentTransferActionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.toolbar);
        binding.toolbar.setSubtitle(args.getEmployee().getName());

        ((MenuHost) requireActivity()).addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                if (args.getDepartmentTransferAction() != null) {
                    menuInflater.inflate(R.menu.menu_edit_action, menu);
                } else {
                    menuInflater.inflate(R.menu.menu_create_action, menu);
                }
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.menu_create) {
                    createAction();
                } else if (menuItem.getItemId() == R.id.menu_save) {
                    saveAction();
                } else if (menuItem.getItemId() == R.id.menu_delete) {
                    showDeleteDialog();
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);

        if (args.getDepartmentTransferAction() != null) {
            binding.editTextDepartment.setText(args.getDepartmentTransferAction().getDepartmentTransfer().getNewDepartment().getName());
            binding.editTextDate.setText(dateFormat.format(args.getDepartmentTransferAction().getDate()));
        }

        binding.editTextDate.setOnClickListener(v -> {
            MaterialDatePicker<Long> materialDatePicker = MaterialDatePicker.Builder.datePicker().build();
            materialDatePicker.addOnPositiveButtonClickListener(date -> {
                String formattedDate = dateFormat.format(new Date(date));
                ((EditText)v).setText(formattedDate);
            });
            materialDatePicker.show(requireActivity().getSupportFragmentManager(), null);
        });

        binding.editTextDepartment.setOnItemClickListener((parent, arg1, pos, id) -> {
            departmentPosition = pos;
        });

        viewModel.getDepartments().observe(getViewLifecycleOwner(), departments -> {
            Object[] companyNames = departments.stream().map(Department::getName).toArray();
            ArrayAdapter<Object> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, companyNames);
            binding.editTextDepartment.setAdapter(adapter);
        });

        viewModel.getIsDone().observe(getViewLifecycleOwner(), isCreatedEvent -> {
            if (isCreatedEvent != null && isCreatedEvent.getContent() == Boolean.TRUE)
                getNavController().navigateUp();
        });

        viewModel.loadDepartments(args.getEmployee().getCurrentInfo().getDepartment().getCompanyId());
    }

    private void showDeleteDialog() {
        MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(requireContext());
        DialogDeleteActionBinding dialogBinding = DialogDeleteActionBinding.inflate(getLayoutInflater());
        dialogBuilder.setView(dialogBinding.getRoot());

        dialogBuilder.setTitle(R.string.delete_action);
        dialogBuilder.setPositiveButton(R.string.delete, (dialog, whichButton) -> {
            int actionId = args.getDepartmentTransferAction().getId();
            viewModel.deleteAction(actionId);
        });
        dialogBuilder.setNegativeButton(R.string.cancel, (dialog, whichButton) -> {
        });

        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }

    void createAction() {
        if (!validateFields()) {
            return;
        }

        Date date;
        try {
            date = dateFormat.parse(binding.editTextDate.getText().toString());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        int newDepartmentId = viewModel.getDepartments().getValue().get(departmentPosition).getId();

        viewModel.createAction(args.getEmployee().getId(), date, newDepartmentId);
    }


    void saveAction() {
        if (!validateFields()) {
            return;
        }

        Date date;
        try {
            date = dateFormat.parse(binding.editTextDate.getText().toString());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        int newDepartmentId = viewModel.getDepartments().getValue().get(departmentPosition).getId();

        viewModel.editAction(args.getEmployee().getId(), date, newDepartmentId);
    }

    private boolean validateFields() {
        boolean isNewDepartmentValid = validateNotEmpty(binding.textInputLayoutDepartment);
        boolean isDateValid = validateNotEmpty(binding.textInputLayoutDate);
        return isNewDepartmentValid && isDateValid;
    }
}
