package com.witelokk.hrapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.witelokk.hrapp.R;
import com.witelokk.hrapp.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    HomeViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.companiesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

//        binding.toolbar2.setNavigationIcon(R.drawable.baseline_add_24);

//        requireActivity().getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity)requireActivity()).setSupportActionBar(binding.toolbar2);
//        ((AppCompatActivity)requireActivity()).getSupportActionBar().setIcon(R.drawable.baseline_person_24);

        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);

        CompaniesAdapter adapter = new CompaniesAdapter(companyId -> {
            HomeFragmentDirections.ActionHomeFragmentToCompanyFragment action = HomeFragmentDirections.actionHomeFragmentToCompanyFragment(companyId);
            navHostFragment.getNavController().navigate(action);
        });
        binding.companiesRecyclerView.setAdapter(adapter);

        viewModel.getCompanies().observe(requireActivity(), companies -> {
            if (companies.isEmpty()) {
                binding.textViewCreateCompany.setVisibility(View.VISIBLE);
            } else {
                binding.progressBar.setVisibility(View.GONE);
                adapter.companies = new ArrayList<>(companies);
                adapter.notifyDataSetChanged();
            }
        });

        viewModel.loadCompanies();

        binding.fabAddCompany.setOnClickListener(v -> {
            navHostFragment.getNavController().navigate(R.id.action_homeFragment_to_addCompanyFragment);
        });

        binding.textViewCreateCompany.setOnClickListener(v -> {
            navHostFragment.getNavController().navigate(R.id.action_homeFragment_to_addCompanyFragment);
        });

        viewModel.getErrorLiveData().observe(requireActivity(), error -> {
            Snackbar.make(view, error, Snackbar.LENGTH_LONG).show();
        });
    }
}
