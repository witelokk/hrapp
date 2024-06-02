package com.witelokk.hrapp.ui.home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.witelokk.hrapp.R;
import com.witelokk.hrapp.api.model.Company;
import com.witelokk.hrapp.databinding.DialogLogoutBinding;
import com.witelokk.hrapp.databinding.FragmentHomeBinding;
import com.witelokk.hrapp.ui.BaseFragment;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends BaseFragment<HomeViewModel> {
    private FragmentHomeBinding binding;
    @Inject
    SharedPreferences sharedPreferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(sharedPreferences.getString("access_token", ""), HomeViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.companiesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        ((AppCompatActivity)requireActivity()).setSupportActionBar(binding.toolbar);
        ((MenuHost)requireActivity()).addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.menu_home, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.menu_logout) {
                    showLogoutDialog();
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);

        CompaniesAdapter adapter = new CompaniesAdapter(new ArrayList<>(), HomeFragment.this::navigateToCompanyFragment);
        binding.companiesRecyclerView.setAdapter(adapter);

        viewModel.getCompanies().observe(requireActivity(), companies -> {
            adapter.setCompanies(companies);
            binding.textViewCreateCompany.setVisibility(companies.isEmpty()? View.VISIBLE: View.GONE);
        });

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading ->
                binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE));

        binding.fabAddCompany.setOnClickListener(v ->
                getNavController().navigate(R.id.action_homeFragment_to_addCompanyFragment));

        binding.textViewCreateCompany.setOnClickListener(v ->
                getNavController().navigate(R.id.action_homeFragment_to_addCompanyFragment));

        viewModel.loadCompanies();
    }

    private void navigateToCompanyFragment(Company company) {
        HomeFragmentDirections.ActionHomeFragmentToCompanyFragment action = HomeFragmentDirections.actionHomeFragmentToCompanyFragment(company.getId(), company.getName());
        getNavController().navigate(action);
    }

    void showLogoutDialog() {
        MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(requireContext());
        DialogLogoutBinding dialogBinding = DialogLogoutBinding.inflate(getLayoutInflater());
        dialogBuilder.setView(dialogBinding.getRoot());

        dialogBuilder.setTitle(R.string.logout);
        dialogBuilder.setPositiveButton(R.string.to_logout, (dialog, whichButton) -> {
            viewModel.logout();
            getNavController().navigate(R.id.action_to_loginFragment);
        });
        dialogBuilder.setNegativeButton(R.string.cancel, (dialog, whichButton) -> {});

        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }
}
