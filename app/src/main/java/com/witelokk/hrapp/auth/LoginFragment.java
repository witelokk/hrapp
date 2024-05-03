package com.witelokk.hrapp.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.witelokk.hrapp.MainActivity;
import com.witelokk.hrapp.R;
import com.witelokk.hrapp.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {
    FragmentLoginBinding binding;
    LoginViewModel viewModel;

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

        viewModel.getIsAuthorized().observe(requireActivity(), isAuthorized -> {
            if (isAuthorized) {
                ((MainActivity) requireActivity()).navController.navigate(R.id.action_loginFragment_to_homeFragment);
            }
        });

        binding.buttonLogin.setOnClickListener(v -> {
            String email = binding.editTextEmail.getText().toString();
            String password = binding.editTextPassword.getText().toString();

            viewModel.login(email, password);
        });
    }
}
