package com.example.expensewallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.expensewallet.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new SummaryFragment());

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.summary:
                    replaceFragment(new SummaryFragment());
                    break;
                case R.id.addRecord:
                    replaceFragment(new AddRecordFragment());
                    break;
                case R.id.viewExpense:
                    replaceFragment(new ViewExpenseFragment());
                    break;
                case R.id.viewIncome:
                    replaceFragment(new ViewIncomeFragment());
                    break;
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragManager = getSupportFragmentManager();
        FragmentTransaction fragTrans = fragManager.beginTransaction();
        fragTrans.replace(R.id.frameLayout,fragment);
        fragTrans.commit();
    }
}