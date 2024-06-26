package com.witelokk.hrapp.ui.add_employee;

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
import com.google.android.material.textfield.TextInputLayout;
import com.witelokk.hrapp.R;
import com.witelokk.hrapp.api.model.Department;
import com.witelokk.hrapp.databinding.FragmentAddEmployeeBinding;
import com.witelokk.hrapp.ui.BaseFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class AddEmployeeFragment extends BaseFragment<AddEmployeeViewModel> {
    private FragmentAddEmployeeBinding binding;
    private final DateFormat dateFormat = SimpleDateFormat.getDateInstance();

    AddEmployeeFragmentArgs args;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(AddEmployeeViewModel.class);
        args = AddEmployeeFragmentArgs.fromBundle(getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddEmployeeBinding.inflate(inflater, container, false);
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            binding.scrollView.setPaddingRelative(0, 0, 0, systemBars.bottom);
            binding.scrollView.setClipToPadding(false);
            binding.getRoot().setPadding(0, systemBars.top, 0, 0);
            return insets;
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, Arrays.asList(getString(R.string.male), getString(R.string.female)));
        binding.editTextGender.setAdapter(genderAdapter);

        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationIcon(R.drawable.baseline_close_24);
        ((MenuHost) requireActivity()).addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == android.R.id.home) {
                    getNavController().navigateUp();
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);

        View.OnClickListener dateClickListener = v -> {
            MaterialDatePicker<Long> materialDatePicker = MaterialDatePicker.Builder.datePicker().build();
            materialDatePicker.addOnPositiveButtonClickListener(date -> {
                String formattedDate = dateFormat.format(new Date(date));
                ((EditText)v).setText(formattedDate);
            });
            materialDatePicker.show(requireActivity().getSupportFragmentManager(), null);
        };

        binding.editTextBirthdate.setOnClickListener(dateClickListener);
        binding.editTextPassportDate.setOnClickListener(dateClickListener);
        binding.editTextRecruitmentDate.setOnClickListener(dateClickListener);

        binding.buttonEditCreate.setOnClickListener(v -> {
            if (!validateFields()) {
                return;
            }

            String name = binding.editTextName.getText().toString();
            String gender = binding.editTextGender.getText().toString().equals(getString(R.string.male)) ? "male": "female";
            String inn = binding.editTextInn.getText().toString();
            String snils = binding.editTextSnils.getText().toString();
            String address = binding.editTextAddress.getText().toString();
            String passportIssuer = binding.editTextPassportIssuer.getText().toString();
            String passportNumber = binding.editTextPassportNumber.getText().toString();
            String position = binding.editTextPosition.getText().toString();
            float salary = Float.parseFloat(binding.editTextSalary.getText().toString());
            Date birthdate;
            Date passportDate;
            Date recruitmentDate;
            try {
                birthdate = dateFormat.parse(binding.editTextBirthdate.getText().toString());
                passportDate = dateFormat.parse(binding.editTextPassportDate.getText().toString());
                recruitmentDate = dateFormat.parse(binding.editTextRecruitmentDate.getText().toString());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            int departmentId;
            if (args.getDepartment() != null) {
                departmentId = args.getDepartment().getId();
            } else {
                int departmentPosition = Arrays.asList(viewModel.getDepartments().getValue().stream().map(Department::getName).toArray()).indexOf(binding.editTextDepartment.getText().toString());
                departmentId = viewModel.getDepartments().getValue().get(departmentPosition).getId();
            }
            viewModel.addEmployee(departmentId, name, gender, birthdate, inn, snils, address, passportIssuer, passportNumber, passportDate, recruitmentDate, position, salary);
        });

        viewModel.getIsCreated().observe(getViewLifecycleOwner(), isCreated -> {
            if (isCreated.getContent() == Boolean.TRUE) {
                getNavController().navigateUp();
            }
        });

        viewModel.getDepartments().observe(getViewLifecycleOwner(), departments -> {
            Object[] departmentNames = departments.stream().map(Department::getName).toArray();
            ArrayAdapter<Object> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, departmentNames);
            binding.editTextDepartment.setAdapter(adapter);
        });

        if (args.getDepartment() != null) {
            binding.textInputLayoutDepartment.setVisibility(View.GONE);
        } else {
            viewModel.loadDepartments(args.getCompanyId());
            binding.textInputLayoutDepartment.setVisibility(View.VISIBLE);
        }
    }

    private boolean validateFields() {
        boolean isNameValid = validateNotEmpty(binding.textInputLayoutName);
        boolean isGenderValid = validateNotEmpty(binding.textInputLayoutGender);
        boolean isBirthdateValid = validateNotEmpty(binding.textInputLayoutBirthdate);
        boolean isInnValid = validateLength(binding.textInputLayoutInn, 12);
        boolean isSnilsValid = validateLength(binding.textInputLayoutSnils, 11);
        boolean isAddressValid = validateNotEmpty(binding.textInputLayoutAddress);
        boolean isPassportNumberValid = validateLength(binding.textInputLayoutPassportNumber, 10);
        boolean isPassportIssuerValid = validateNotEmpty(binding.textInputLayoutPassportIssuer);
        boolean isPassportDateValid = validateNotEmpty(binding.textInputLayoutPassportDate);
        boolean isDepartmentValid = args.getDepartment() != null || validateNotEmpty(binding.textInputLayoutDepartment);
        boolean isRecruitmentDateValid = validateNotEmpty(binding.textInputLayoutRecruitmentDate);
        boolean isPositionValid = validateNotEmpty(binding.textInputLayoutPosition);
        boolean isSalaryValid = validateNotEmpty(binding.textInputLayoutSalary);

        return isNameValid && isGenderValid && isBirthdateValid && isInnValid && isSnilsValid && isAddressValid && isPassportNumberValid && isPassportIssuerValid && isPassportDateValid && isRecruitmentDateValid && isPositionValid && isSalaryValid && isDepartmentValid;
    }
}
