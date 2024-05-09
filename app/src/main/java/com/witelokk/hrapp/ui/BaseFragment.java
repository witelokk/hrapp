package com.witelokk.hrapp.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.snackbar.Snackbar;
import com.witelokk.hrapp.NavigationDirections;
import com.witelokk.hrapp.R;

public class BaseFragment<T extends BaseViewModel> extends Fragment {
    protected T viewModel;

    protected NavController getNavController() {
        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        return navHostFragment.getNavController();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.getUnauthorizedError().observe(getViewLifecycleOwner(), error -> {
            Snackbar.make(view, R.string.session_expired, Snackbar.LENGTH_SHORT).show();
            getNavController().navigate(NavigationDirections.actionToLoginFragment(true));
        });

        viewModel.getUnknownError().observe(getViewLifecycleOwner(), error ->
                Snackbar.make(view, R.string.unknown_error, Snackbar.LENGTH_SHORT).show());

        viewModel.getNetworkError().observe(getViewLifecycleOwner(), error ->
                Snackbar.make(view, R.string.network_error, Snackbar.LENGTH_SHORT).show());
    }
}
