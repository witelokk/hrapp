package com.witelokk.hrapp.ui.add_edit_company;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

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

import com.witelokk.hrapp.R;
import com.witelokk.hrapp.databinding.FragmentAddEditCompanyBinding;
import com.witelokk.hrapp.ui.BaseFragment;

public class AddEditCompanyFragment extends BaseFragment<AddEditCompanyViewModel> {
    FragmentAddEditCompanyBinding binding;
    AddEditCompanyFragmentArgs args;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(AddEditCompanyViewModel.class);
        args = AddEditCompanyFragmentArgs.fromBundle(getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddEditCompanyBinding.inflate(inflater, container, false);
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

        if (args.getCompanyId() != -1) {
            binding.toolbar.setTitle(R.string.edit_company);
            binding.buttonEditCreate.setText(R.string.to_edit);
            binding.editTextName.setText(args.getCompanyName());
            binding.editTextInn.setText(args.getCompanyInn());
            binding.editTextKpp.setText(args.getCompanyKpp());
        }

        ((AppCompatActivity)requireActivity()).setSupportActionBar(binding.toolbar);

        viewModel.getIsDone().observe(getViewLifecycleOwner(), isCompanyCreatedEvent -> {
            if (isCompanyCreatedEvent.getContent() == Boolean.TRUE) {
                getNavController().navigateUp();
            }
        });

        binding.editTextName.requestFocus();

        binding.buttonEditCreate.setOnClickListener(v -> {
            boolean isNameValid = validateName();
            boolean isInnValid = validateInn();
            boolean isKppValid = validateKpp();

            if (isNameValid && isInnValid && isKppValid) {
                String name = binding.editTextName.getText().toString();
                String inn = binding.editTextInn.getText().toString();
                String kpp = binding.editTextKpp.getText().toString();

                if (args.getCompanyId() != -1) {
                    viewModel.editCompany(args.getCompanyId(), name, inn, kpp);
                } else {
                    viewModel.createCompany(name, inn, kpp);
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

    private boolean validateInn() {
        boolean isValid = binding.editTextInn.length() == 10;
        if (!isValid) {
            binding.textInputLayoutInn.setError(getString(R.string.inn_length_error));
        } else {
            binding.textInputLayoutInn.setErrorEnabled(false);
        }
        return isValid;
    }

    private boolean validateKpp() {
        boolean isValid = binding.editTextKpp.length() == 9;
        if (!isValid) {
            binding.textInputLayoutKpp.setError(getString(R.string.kpp_length_error));
        } else {
            binding.textInputLayoutKpp.setErrorEnabled(false);
        }
        return isValid;
    }
}
