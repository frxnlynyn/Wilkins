package com.madelyn.wilkins;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PaymentActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        bottomNav = findViewById(R.id.bottomNav);

        // Highlight Payment
        bottomNav.setSelectedItemId(R.id.nav_payment);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_payment) {
                return true; // Already here
            }

            if (id == R.id.nav_home) {
                startActivity(new Intent(PaymentActivity.this, HomeActivity.class));
                overridePendingTransition(0, 0);
                return true;
            }

            if (id == R.id.nav_table) {
                startActivity(new Intent(PaymentActivity.this, TableLayoutActivity.class));
                overridePendingTransition(0, 0);
                return true;
            }

            if (id == R.id.nav_orders) {
                startActivity(new Intent(PaymentActivity.this, OrdersActivity.class));
                overridePendingTransition(0, 0);
                return true;
            }

            return false;
        });
    }
}
