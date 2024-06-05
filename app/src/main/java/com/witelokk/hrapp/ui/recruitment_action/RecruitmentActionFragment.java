package com.witelokk.hrapp.ui.recruitment_action;

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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.witelokk.hrapp.R;
import com.witelokk.hrapp.api.model.Department;
import com.witelokk.hrapp.databinding.FragmentRecruitmentActionBinding;
import com.witelokk.hrapp.ui.BaseFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class RecruitmentActionFragment extends BaseFragment<RecruitmentActionFragmentViewModel> {
    private FragmentRecruitmentActionBinding binding;
    private RecruitmentActionFragmentArgs args;
    private final DateFormat dateFormat = SimpleDateFormat.getDateInstance();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(RecruitmentActionFragmentViewModel.class);
        args = RecruitmentActionFragmentArgs.fromBundle(getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRecruitmentActionBinding.inflate(inflater, container, false);
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
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationIcon(R.drawable.baseline_close_24);

        binding.toolbar.setSubtitle(args.getEmployee().getName());

        ((MenuHost) requireActivity()).addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.menu_edit_recruitment_action, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == android.R.id.home) {
                    getNavController().navigateUp();
                } else if (menuItem.getItemId() == R.id.menu_save) {
                    saveAction();
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);

        binding.editTextDepartment.setText(args.getRecruitmentAction().getRecruitment().getDepartment().getName());
        binding.editTextPosition.setText(args.getRecruitmentAction().getRecruitment().getPosition());
        binding.editTextSalary.setText(String.format(String.valueOf(args.getRecruitmentAction().getRecruitment().getSalary())));
        binding.editTextDate.setText(dateFormat.format(args.getRecruitmentAction().getDate()));

        binding.editTextDate.setOnClickListener(v -> {
            MaterialDatePicker<Long> materialDatePicker = MaterialDatePicker.Builder.datePicker().build();
            materialDatePicker.addOnPositiveButtonClickListener(date -> {
                String formattedDate = dateFormat.format(new Date(date));
                ((EditText)v).setText(formattedDate);
            });
            materialDatePicker.show(requireActivity().getSupportFragmentManager(), null);
        });

        viewModel.getDepartments().observe(getViewLifecycleOwner(), departments -> {
            Object[] departmentNames = departments.stream().map(Department::getName).toArray();
            ArrayAdapter<Object> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, departmentNames);
            binding.editTextDepartment.setAdapter(adapter);
        });

        viewModel.getIsDone().observe(getViewLifecycleOwner(), isCreatedEvent -> {
            if (isCreatedEvent != null && isCreatedEvent.getContent() == Boolean.TRUE)
                getNavController().navigateUp();
        });

        viewModel.loadDepartments(args.getEmployee().getCompanyId());
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
        int departmentPosition = Arrays.asList(viewModel.getDepartments().getValue().stream().map(Department::getName).toArray()).indexOf(binding.editTextDepartment.getText().toString());
        int departmentId = viewModel.getDepartments().getValue().get(departmentPosition).getId();
        String position = binding.editTextPosition.getText().toString();
        float salary = Float.parseFloat(binding.editTextSalary.getText().toString());

        viewModel.editAction(args.getRecruitmentAction().getId(), date, departmentId, position, salary);
    }

    private boolean validateFields() {
        boolean isNewDepartmentValid = validateNotEmpty(binding.textInputLayoutDepartment);
        boolean isDateValid = validateNotEmpty(binding.textInputLayoutDate);
        return isNewDepartmentValid && isDateValid;
    }
}
