package com.witelokk.hrapp.ui.select_action_to_add;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.witelokk.hrapp.api.model.Action;
import com.witelokk.hrapp.databinding.FragmentSelectActionTypeBinding;
import com.witelokk.hrapp.ui.BaseFragment;
import com.witelokk.hrapp.ui.BaseViewModel;

public class SelectActionToAddFragment extends BaseFragment<BaseViewModel> {
    FragmentSelectActionTypeBinding binding;
    SelectActionToAddFragmentArgs args;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(SelectActionToAddViewModel.class);
        args = SelectActionToAddFragmentArgs.fromBundle(getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSelectActionTypeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.materialToolbar.setSubtitle(args.getEmployee().getName());

        binding.cardViewDepartmentTransfer.setOnClickListener(v -> getNavController().navigate(SelectActionToAddFragmentDirections.actionSelectActionToAddFragmentToDepartmentTransferActionFragment(args.getEmployee())));

        binding.cardViewPositionTransfer.setOnClickListener(v -> getNavController().navigate(SelectActionToAddFragmentDirections.actionSelectActionToAddFragmentToPositionTransferActionFragment(args.getEmployee())));

        binding.cardViewSalaryChange.setOnClickListener(v -> getNavController().navigate(SelectActionToAddFragmentDirections.actionSelectActionToAddFragmentToSalaryChangeActionFragment(args.getEmployee())));
    }
}
