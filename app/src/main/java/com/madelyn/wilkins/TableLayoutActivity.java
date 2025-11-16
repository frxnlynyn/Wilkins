package com.madelyn.wilkins;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TableLayoutActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_layout);

        bottomNav = findViewById(R.id.bottomNav);

        // Highlight Table
        bottomNav.setSelectedItemId(R.id.nav_table);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_table) {
                return true; // Already here
            }

            if (id == R.id.nav_home) {
                startActivity(new Intent(TableLayoutActivity.this, HomeActivity.class));
                overridePendingTransition(0, 0);
                return true;
            }

            if (id == R.id.nav_orders) {
                startActivity(new Intent(TableLayoutActivity.this, OrdersActivity.class));
                overridePendingTransition(0, 0);
                return true;
            }

            if (id == R.id.nav_payment) {
                startActivity(new Intent(TableLayoutActivity.this, PaymentActivity.class));
                overridePendingTransition(0, 0);
                return true;
            }

            return false;
        });
    }
}
