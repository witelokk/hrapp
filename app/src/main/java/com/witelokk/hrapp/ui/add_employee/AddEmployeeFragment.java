package com.witelokk.hrapp.ui.add_employee;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputLayout;
import com.witelokk.hrapp.R;
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
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, Arrays.asList(getString(R.string.male), getString(R.string.female)));
        binding.editTextGender.setAdapter(genderAdapter);

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
            int departmentId = args.getDepartmentId();
            viewModel.addEmployee(departmentId, name, gender, birthdate, inn, snils, address, passportIssuer, passportNumber, passportDate, recruitmentDate, position, salary);
        });

        viewModel.getIsCreated().observe(getViewLifecycleOwner(), isCreated -> {
            if (isCreated.getContent() == Boolean.TRUE) {
                getNavController().navigateUp();
            }
        });
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
        boolean isRecruitmentDateValid = validateNotEmpty(binding.textInputLayoutRecruitmentDate);
        boolean isPositionValid = validateNotEmpty(binding.textInputLayoutPosition);
        boolean isSalaryValid = validateNotEmpty(binding.textInputLayoutSalary);

        return isNameValid && isGenderValid && isBirthdateValid && isInnValid && isSnilsValid && isAddressValid && isPassportNumberValid && isPassportIssuerValid && isPassportDateValid && isRecruitmentDateValid && isPositionValid && isSalaryValid;
    }
}
