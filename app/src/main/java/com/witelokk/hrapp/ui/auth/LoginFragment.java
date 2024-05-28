package com.witelokk.hrapp.ui.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.snackbar.Snackbar;
import com.witelokk.hrapp.R;
import com.witelokk.hrapp.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    private LoginViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.getIsAuthorized().observe(getViewLifecycleOwner(), isAuthorized -> {
            if (isAuthorized) {
                getNavController().navigate(R.id.action_loginFragment_to_homeFragment);
            }
        });

        viewModel.getInvalidCredentials().observe(getViewLifecycleOwner(), event -> {
            if (event.getContent())
                Snackbar.make(view, R.string.invalid_credentials, Snackbar.LENGTH_SHORT).show();
        });

        viewModel.getUnknownError().observe(getViewLifecycleOwner(), event -> {
            if (event.getContent())
                Snackbar.make(view, R.string.unknown_error, Snackbar.LENGTH_SHORT).show();
        });

        viewModel.getUserAlreadyExists().observe(getViewLifecycleOwner(), event -> {
            if (event.getContent())
                Snackbar.make(view, R.string.user_already_exist, Snackbar.LENGTH_SHORT).show();
        });

        viewModel.checkIfAuthorized();

        binding.buttonLogin.setOnClickListener(v -> {
            String email = binding.editTextEmail.getText().toString();
            String password = binding.editTextPassword.getText().toString();

            viewModel.login(email, password);
        });

        binding.buttonRegister.setOnClickListener(v -> {
            String email = binding.editTextEmail.getText().toString();
            String password = binding.editTextPassword.getText().toString();

            viewModel.registerAndLogin(email, password);
        });
    }

    private NavController getNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        return navHostFragment.getNavController();
    }
}
