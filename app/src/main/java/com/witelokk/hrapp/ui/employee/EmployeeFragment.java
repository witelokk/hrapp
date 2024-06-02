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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

public class EmployeeFragment extends BaseFragment<EmployeeViewModel> {
    private FragmentEmployeeBinding binding;
    private EmployeeFragmentArgs args;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = EmployeeFragmentArgs.fromBundle(getArguments());
        viewModel = new ViewModelProvider(requireActivity()).get(String.valueOf(args.getEmployee().getId()), EmployeeViewModel.class);
        viewModel.setEmployeeId(args.getEmployee().getId());
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

        binding.textViewEmployeeName.setText(args.getEmployee().getName());
        binding.textViewDepartment.setText(args.getEmployee().getCurrentInfo().getDepartment().getName());
        binding.textViewPosition.setText(args.getEmployee().getCurrentInfo().getPosition());
        binding.textViewSalary.setText(String.format(Locale.getDefault(), "%,d", (int) args.getEmployee().getCurrentInfo().getSalary()));

        viewModel.getEmployee().observe(getViewLifecycleOwner(), employee -> {
            binding.textViewEmployeeName.setText(employee.getName());
            binding.textViewPosition.setText(employee.getCurrentInfo().getPosition());
            binding.textViewSalary.setText(String.format(Locale.getDefault(), "%,d", (int) employee.getCurrentInfo().getSalary()));
            binding.textViewDepartment.setText(String.valueOf(employee.getCurrentInfo().getDepartment().getName()));

            if (employee.getGender().equals("male"))
                binding.imageView.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.male));
            else
                binding.imageView.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.female));

            binding.textViewPersonalInformationData.setText(getString(R.string.personal_information_data, DateFormat.getDateInstance().format(employee.getBirthdate()), employee.getGender(), employee.getAddress(), employee.getSnils(), employee.getInn(), employee.getPassportNumber(), employee.getPasswordIssuer(), DateFormat.getDateInstance().format(employee.getPassportDate())));

            ActionsAdapter adapter = new ActionsAdapter(employee.getActions(), action -> {
                switch (action.getType()) {
                    case "department_transfer":
                        getNavController().navigate(EmployeeFragmentDirections.actionEmployeeFragmentToDepartmentTransferActionFragment(action, viewModel.getEmployee().getValue()));
                        break;
                    case "position_transfer":
                        getNavController().navigate(EmployeeFragmentDirections.actionEmployeeFragmentToPositionTransferActionFragment(action, viewModel.getEmployee().getValue()));
                        break;
                }
            });
            binding.recyclerView.setAdapter(adapter);
        });

        binding.cardViewPersonalInfo.setOnClickListener(v -> {
            com.witelokk.hrapp.ui.employee.EmployeeFragmentDirections.ActionEmployeeFragmentToEditEmployeeFragment action = EmployeeFragmentDirections.actionEmployeeFragmentToEditEmployeeFragment(viewModel.getEmployee().getValue());
            getNavController().navigate(action);
        });

        binding.fabAddAction.setOnClickListener(v -> {
            EmployeeFragmentDirections.ActionEmployeeFragmentToSelectActionToAddFragment action = EmployeeFragmentDirections.actionEmployeeFragmentToSelectActionToAddFragment(viewModel.getEmployee().getValue());
            getNavController().navigate(action);
        });

//
//        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading ->
//                binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE));

        viewModel.loadData();
    }
}
