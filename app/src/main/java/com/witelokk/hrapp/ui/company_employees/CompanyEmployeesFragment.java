package com.witelokk.hrapp.ui.company_employees;

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
import com.witelokk.hrapp.databinding.FragmentCompanyEmployeesBinding;
import com.witelokk.hrapp.ui.BaseFragment;
import com.witelokk.hrapp.ui.EmployeesAdapter;
import com.witelokk.hrapp.ui.company.CompanyFragmentDirections;

import java.util.ArrayList;

public class CompanyEmployeesFragment extends BaseFragment<CompanyEmployeesViewModel> {
    private FragmentCompanyEmployeesBinding binding;
    private CompanyEmployeesFragmentArgs args;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = CompanyEmployeesFragmentArgs.fromBundle(getArguments());
        viewModel = new ViewModelProvider(requireActivity()).get(String.valueOf(args.getCompany().getId()), CompanyEmployeesViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCompanyEmployeesBinding.inflate(inflater, container, false);
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            ((ViewGroup.MarginLayoutParams)binding.fabAddEmployee.getLayoutParams()).bottomMargin+=systemBars.bottom;
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

        EmployeesAdapter adapter = new EmployeesAdapter(new ArrayList<>(), employee -> {
            getNavController().navigate(CompanyEmployeesFragmentDirections.actionCompanyEmployeesFragmentToEmployeeFragment(employee));
        });
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        viewModel.getEmployees().observe(getViewLifecycleOwner(), employees -> {
            adapter.setEmployees(employees);
            binding.textNoEmployees.setVisibility(employees.isEmpty()? View.VISIBLE: View.GONE);
        });

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            binding.progressBar.setVisibility(isLoading? View.VISIBLE: View.GONE);
        });

        binding.fabAddEmployee.setOnClickListener(v ->
                getNavController().navigate(CompanyEmployeesFragmentDirections.actionCompanyEmployeesFragmentToAddEmployeeFragment(args.getCompany().getId())));

        viewModel.loadEmployees(args.getCompany().getId());
    }
}
