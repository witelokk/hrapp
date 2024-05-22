package com.witelokk.hrapp.ui.employee;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.witelokk.hrapp.R;
import com.witelokk.hrapp.databinding.FragmentEmployeeBinding;
import com.witelokk.hrapp.ui.BaseFragment;

import java.util.Locale;

public class EmployeeFragment extends BaseFragment<EmployeeViewModel> {
    FragmentEmployeeBinding binding;
    EmployeeFragmentArgs args;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = EmployeeFragmentArgs.fromBundle(getArguments());
        viewModel = new ViewModelProvider(requireActivity()).get(String.valueOf(args.getEmployeeId()), EmployeeViewModel.class);
        viewModel.setEmployeeId(args.getEmployeeId());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEmployeeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        viewModel.getEmployee().observe(getViewLifecycleOwner(), employee -> {
            binding.textViewEmployeeName.setText(employee.getName());
            binding.textViewPosition.setText(employee.getCurrentInfo().getPosition());
            binding.textViewSalary.setText(String.format(Locale.getDefault(), "%,d", (int) employee.getCurrentInfo().getSalary()));
            binding.textViewDepartment.setText(String.valueOf(employee.getCurrentInfo().getDepartment().getName()));

            if (employee.getGender().equals("male"))
                binding.imageView.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.male));
            else
                binding.imageView.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.female));

            binding.textViewPersonalInformationData.setText(getString(R.string.personal_information_data, employee.getBirthdate().toString(), employee.getGender(), employee.getAddress(), employee.getSnils(), employee.getInn(), employee.getPassportNumber(), employee.getPasswordIssuer(), employee.getPassportDate().toString()));

            ActionsAdapter adapter = new ActionsAdapter(employee.getActions(), actionId -> {});
            binding.recyclerView.setAdapter(adapter);
        });

//
//        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading ->
//                binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE));

        viewModel.loadData();
    }
}
