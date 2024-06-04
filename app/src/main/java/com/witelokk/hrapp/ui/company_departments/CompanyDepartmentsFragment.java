package com.witelokk.hrapp.ui.company_departments;

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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.witelokk.hrapp.R;
import com.witelokk.hrapp.api.model.Company;
import com.witelokk.hrapp.databinding.FragmentCompanyDepartmentsBinding;
import com.witelokk.hrapp.ui.BaseFragment;

import java.util.ArrayList;

public class CompanyDepartmentsFragment extends BaseFragment<CompanyDepartmentsViewModel> {
    FragmentCompanyDepartmentsBinding binding;
    CompanyDepartmentsFragmentArgs args;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = CompanyDepartmentsFragmentArgs.fromBundle(getArguments());
        viewModel = new ViewModelProvider(requireActivity()).get(String.valueOf(args.getCompany().getId()), CompanyDepartmentsViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCompanyDepartmentsBinding.inflate(inflater, container, false);
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            ((ViewGroup.MarginLayoutParams)binding.fabAddDepartment.getLayoutParams()).bottomMargin+=systemBars.bottom;
            binding.recyclerView.setPaddingRelative(0, 0, 0, systemBars.bottom);
            binding.recyclerView.setClipToPadding(false);
            return insets;
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.toolbar.setTitle(args.getCompany().getName());
        viewModel.setCompanyId(args.getCompany().getId());

        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        DepartmentsAdapter adapter = new DepartmentsAdapter(new ArrayList<>(), department -> {
            CompanyDepartmentsFragmentDirections.ActionCompanyDepartmentsFragmentToDepartmentFragment action = CompanyDepartmentsFragmentDirections.actionCompanyDepartmentsFragmentToDepartmentFragment(department);
            getNavController().navigate(action);
        });
        binding.recyclerView.setAdapter(adapter);

        viewModel.getDepartments().observe(getViewLifecycleOwner(), departments -> {
            adapter.setDepartments(departments);
            binding.textNoDepartments.setVisibility(departments.isEmpty()? View.VISIBLE: View.GONE);
        });

        binding.fabAddDepartment.setOnClickListener(v -> getNavController().navigate(CompanyDepartmentsFragmentDirections.actionCompanyDepartmentsFragmentToAddEditDepartmentFragment(args.getCompany().getId())));

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE));

        viewModel.loadData();
    }

}
