package com.witelokk.hrapp.ui.reports;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

import com.witelokk.hrapp.R;
import com.witelokk.hrapp.databinding.FragmentReportsBinding;
import com.witelokk.hrapp.ui.BaseFragment;

public class ReportsFragment extends BaseFragment<ReportsViewModel> {
    FragmentReportsBinding binding;
    ReportsFragmentArgs args;
    byte[] generatedReport = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(ReportsViewModel.class);
        viewModel.setContentResolver(requireActivity().getContentResolver());
        args = ReportsFragmentArgs.fromBundle(getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentReportsBinding.inflate(inflater, container, false);
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            binding.getRoot().setPadding(0, systemBars.top, 0, 0);
            return insets;
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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

        ActivityResultLauncher<String> filePickerLauncher = registerForActivityResult(new ActivityResultContracts.CreateDocument("application/pdf"), uri -> {
            viewModel.saveReport(uri, this.generatedReport);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setData(uri);
            startActivity(intent);
        });

        viewModel.getReport().observe(getViewLifecycleOwner(), reportEvent -> {
            byte[] responseBody = reportEvent.getContent();

            if (responseBody != null) {
                this.generatedReport = responseBody;
                filePickerLauncher.launch(getString(R.string.report_filename));
            }
        });

        binding.buttonGenerate.setOnClickListener(v -> {
            viewModel.generateReport(args.getCompanyId());
        });
    }
}
