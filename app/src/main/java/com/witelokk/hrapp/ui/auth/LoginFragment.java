package com.witelokk.hrapp.ui.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.witelokk.hrapp.R;
import com.witelokk.hrapp.databinding.FragmentLoginBinding;
import com.witelokk.hrapp.ui.BaseFragment;

public class LoginFragment extends BaseFragment<LoginViewModel> {
    FragmentLoginBinding binding;

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

        viewModel.getInvalidCredentials().observe(getViewLifecycleOwner(), none ->
                Snackbar.make(view, R.string.invalid_credentials, Snackbar.LENGTH_SHORT).show());

        if (getArguments() != null && getArguments().getBoolean("remove_access_token")) {
            viewModel.removeAccessToken();
            getArguments().remove("remove_access_token");
        }
        viewModel.checkIfAuthorized();

        binding.buttonLogin.setOnClickListener(v -> {
            String email = binding.editTextEmail.getText().toString();
            String password = binding.editTextPassword.getText().toString();

            viewModel.login(email, password);
        });
    }
}
