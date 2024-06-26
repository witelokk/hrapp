package com.witelokk.hrapp.ui.dismissal_action;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.witelokk.hrapp.R;
import com.witelokk.hrapp.databinding.DialogDeleteActionBinding;
import com.witelokk.hrapp.databinding.FragmentDismissalActionBinding;
import com.witelokk.hrapp.databinding.FragmentSalaryChangeActionBinding;
import com.witelokk.hrapp.ui.BaseFragment;
import com.witelokk.hrapp.ui.salary_change_action.SalaryChangeActionFragmentArgs;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DismissalActionFragment extends BaseFragment<DismissalFragmentViewModel> {
    private FragmentDismissalActionBinding binding;
    private DismissalActionFragmentArgs args;
    private final DateFormat dateFormat = SimpleDateFormat.getDateInstance();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(DismissalFragmentViewModel.class);
        args = DismissalActionFragmentArgs.fromBundle(getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDismissalActionBinding.inflate(inflater, container, false);
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            binding.getRoot().setPadding(0, systemBars.top, 0, 0);
            return insets;
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.toolbar);
        binding.toolbar.setSubtitle(args.getEmployee().getName());
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationIcon(R.drawable.baseline_close_24);

        ((MenuHost) requireActivity()).addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                if (args.getDismissalAction() != null) {
                    menuInflater.inflate(R.menu.menu_edit_action, menu);
                } else {
                    menuInflater.inflate(R.menu.menu_create_action, menu);
                }
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == android.R.id.home) {
                    getNavController().navigateUp();
                } else if (menuItem.getItemId() == R.id.menu_create) {
                    createAction();
                } else if (menuItem.getItemId() == R.id.menu_save) {
                    saveAction();
                } else if (menuItem.getItemId() == R.id.menu_delete) {
                    showDeleteDialog();
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);

        if (args.getDismissalAction() != null) {
            binding.editTextDate.setText(dateFormat.format(args.getDismissalAction().getDate()));
        }

        binding.editTextDate.setOnClickListener(v -> {
            MaterialDatePicker<Long> materialDatePicker = MaterialDatePicker.Builder.datePicker().build();
            materialDatePicker.addOnPositiveButtonClickListener(date -> {
                String formattedDate = dateFormat.format(new Date(date));
                ((EditText)v).setText(formattedDate);
            });
            materialDatePicker.show(requireActivity().getSupportFragmentManager(), null);
        });

        viewModel.getIsDone().observe(getViewLifecycleOwner(), isCreatedEvent -> {
            if (isCreatedEvent != null && isCreatedEvent.getContent() == Boolean.TRUE)
                getNavController().navigateUp();
        });
    }

    private void showDeleteDialog() {
        MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(requireContext());
        DialogDeleteActionBinding dialogBinding = DialogDeleteActionBinding.inflate(getLayoutInflater());
        dialogBuilder.setView(dialogBinding.getRoot());

        dialogBuilder.setTitle(R.string.delete_action);
        dialogBuilder.setPositiveButton(R.string.delete, (dialog, whichButton) -> {
            int actionId = args.getDismissalAction().getId();
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

        viewModel.createAction(args.getEmployee().getId(), date);
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

        viewModel.editAction(args.getDismissalAction().getId(), date);
    }

    private boolean validateFields() {
        return validateNotEmpty(binding.textInputLayoutDate);
    }
}
