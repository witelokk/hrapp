package com.witelokk.hrapp.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.witelokk.hrapp.Error;
import com.witelokk.hrapp.R;

public class BaseFragment<T extends BaseViewModel> extends Fragment {
    protected T viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected NavController getNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        return navHostFragment.getNavController();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.getError().observe(getViewLifecycleOwner(), errorEvent -> {
            Error error = errorEvent.getContent();
            if (error instanceof Error.Network)
                Snackbar.make(view, R.string.network_error, Snackbar.LENGTH_LONG).show();
            else if (error instanceof Error.Unauthorized) {
                Snackbar.make(view, R.string.session_expired, Snackbar.LENGTH_SHORT).show();
                getNavController().navigate(R.id.action_to_loginFragment);
            } else if (error != null)
                Snackbar.make(view, R.string.unknown_error, Snackbar.LENGTH_LONG).show();
        });
    }


    protected boolean validateNotEmpty(TextInputLayout inputLayout) {
        boolean isValid = inputLayout.getEditText().getText().length() != 0;
        if (!isValid) {
            inputLayout.setError(getText(R.string.empty_field_error));
        } else {
            inputLayout.setErrorEnabled(false);
        }
        return isValid;
    }

    protected boolean validateLength(TextInputLayout inputLayout, int length) {
        boolean isValid = inputLayout.getEditText().getText().length() == length;
        if (!isValid) {
            inputLayout.setError(getString(R.string.field_length_error, length));
        } else {
            inputLayout.setErrorEnabled(false);
        }
        return isValid;
    }
}
