package com.witelokk.hrapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

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

        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);

        CompaniesAdapter adapter = new CompaniesAdapter(companyId -> {
            HomeFragmentDirections.ActionHomeFragmentToCompanyFragment action = HomeFragmentDirections.actionHomeFragmentToCompanyFragment(companyId);
            navHostFragment.getNavController().navigate(action);
        });
        binding.companiesRecyclerView.setAdapter(adapter);

        viewModel.getCompanies().observe(requireActivity(), companies -> {
            adapter.companies = new ArrayList<>(companies);
            adapter.notifyDataSetChanged();
        });

        viewModel.loadCompanies();

        binding.fabAddCompany.setOnClickListener(v -> {
            navHostFragment.getNavController().navigate(R.id.action_homeFragment_to_addCompanyFragment);
        });
    }
}
