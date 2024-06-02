package com.witelokk.hrapp.ui.add_edit_department;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.witelokk.hrapp.R;
import com.witelokk.hrapp.databinding.FragmentAddEditDepartmentBinding;
import com.witelokk.hrapp.ui.BaseFragment;

public class AddEditDepartmentFragment extends BaseFragment<AddEditDepartmentViewModel> {
    FragmentAddEditDepartmentBinding binding;
    AddEditDepartmentFragmentArgs args;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(AddEditDepartmentViewModel.class);
        args = AddEditDepartmentFragmentArgs.fromBundle(getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddEditDepartmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (args.getDepartment() != null) {
            binding.toolbar.setTitle(R.string.edit_company);
            binding.buttonEditCreate.setText(R.string.to_edit);
            binding.editTextName.setText(args.getDepartment().getName());
        }

        ((AppCompatActivity)requireActivity()).setSupportActionBar(binding.toolbar);

        viewModel.getIsDone().observe(getViewLifecycleOwner(), isDoneEvent -> {
            if (isDoneEvent.getContent() == Boolean.TRUE) {
                getNavController().navigateUp();
            }
        });

        binding.editTextName.requestFocus();

        binding.buttonEditCreate.setOnClickListener(v -> {
            boolean isNameValid = validateName();

            if (isNameValid) {
                String name = binding.editTextName.getText().toString();

                if (args.getDepartment() != null) {
                    viewModel.editDepartment(args.getDepartment().getCompanyId(), args.getCompanyId(), name);
                } else {
                    viewModel.createDepartment(args.getCompanyId(), name);
                }
            }
        });
    }

    private boolean validateName() {
        boolean isValid = !binding.editTextName.getText().toString().isEmpty();
        if (!isValid) {
            binding.textInputLayoutName.setError(getString(R.string.empty_field_error));
        } else {
            binding.textInputLayoutName.setErrorEnabled(false);
        }
        return isValid;
    }
}
