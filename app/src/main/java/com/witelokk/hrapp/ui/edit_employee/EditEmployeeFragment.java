package com.witelokk.hrapp.ui.edit_employee;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputLayout;
import com.witelokk.hrapp.R;
import com.witelokk.hrapp.databinding.FragmentEditEmployeeBinding;
import com.witelokk.hrapp.ui.BaseFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class EditEmployeeFragment extends BaseFragment<EditEmployeeViewModel> {
    private FragmentEditEmployeeBinding binding;
    private EditEmployeeFragmentArgs args;
    private final DateFormat dateFormat = SimpleDateFormat.getDateInstance();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(EditEmployeeViewModel.class);
        args = EditEmployeeFragmentArgs.fromBundle(getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditEmployeeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.editTextName.setText(args.getEmployee().getName());
        binding.editTextGender.setText(Objects.equals(args.getEmployee().getGender(), "male") ? R.string.male: R.string.female);
        binding.editTextBirthdate.setText(dateFormat.format(args.getEmployee().getBirthdate()));
        binding.editTextInn.setText(args.getEmployee().getInn());
        binding.editTextSnils.setText(args.getEmployee().getSnils());
        binding.editTextAddress.setText(args.getEmployee().getAddress());
        binding.editTextPassportNumber.setText(args.getEmployee().getPassportNumber());
        binding.editTextPassportIssuer.setText(args.getEmployee().getPasswordIssuer());
        binding.editTextPassportDate.setText(dateFormat.format(args.getEmployee().getPassportDate()));

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

        binding.buttonEdit.setOnClickListener(v -> {
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
            Date birthdate;
            Date passportDate;
            try {
                birthdate = dateFormat.parse(binding.editTextBirthdate.getText().toString());
                passportDate = dateFormat.parse(binding.editTextPassportDate.getText().toString());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            viewModel.editEmployee(args.getEmployee().getId(), name, gender, birthdate, inn, snils, address, passportIssuer, passportNumber, passportDate);
        });

        viewModel.getIsChanged().observe(getViewLifecycleOwner(), isChanged -> {
            if (isChanged.getContent() == Boolean.TRUE)
                getNavController().navigateUp();
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

        return isNameValid && isGenderValid && isBirthdateValid && isInnValid && isSnilsValid && isAddressValid && isPassportNumberValid && isPassportIssuerValid && isPassportDateValid;
    }
}
