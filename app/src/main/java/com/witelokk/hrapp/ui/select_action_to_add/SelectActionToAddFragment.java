package com.witelokk.hrapp.ui.select_action_to_add;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.witelokk.hrapp.databinding.FragmentSelectActionTypeBinding;
import com.witelokk.hrapp.ui.BaseFragment;
import com.witelokk.hrapp.ui.BaseViewModel;

public class SelectActionToAddFragment extends BaseFragment<BaseViewModel> {
    FragmentSelectActionTypeBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(SelectActionToAddViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSelectActionTypeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
