package com.witelokk.hrapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.witelokk.hrapp.R;
import com.witelokk.hrapp.api.model.Company;
import com.witelokk.hrapp.databinding.FragmentHomeBinding;
import com.witelokk.hrapp.ui.BaseFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends BaseFragment<HomeViewModel> {
    FragmentHomeBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
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

        viewModel.getCompanies().observe(requireActivity(), companies -> {
            if (companies.isEmpty()) {
                binding.textViewCreateCompany.setVisibility(View.VISIBLE);
            } else {
                CompaniesAdapter adapter = new CompaniesAdapter(companies, HomeFragment.this::navigateToCompanyFragment);
                binding.companiesRecyclerView.setAdapter(adapter);
            }
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
}
