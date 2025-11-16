package com.madelyn.wilkins;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class OrdersActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        bottomNav = findViewById(R.id.bottomNav);

        // Highlight Orders
        bottomNav.setSelectedItemId(R.id.nav_orders);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_orders) {
                return true; // Already here
            }

            if (id == R.id.nav_home) {
                startActivity(new Intent(OrdersActivity.this, HomeActivity.class));
                overridePendingTransition(0, 0);
                return true;
            }

            if (id == R.id.nav_table) {
                startActivity(new Intent(OrdersActivity.this, TableLayoutActivity.class));
                overridePendingTransition(0, 0);
                return true;
            }

            if (id == R.id.nav_payment) {
                startActivity(new Intent(OrdersActivity.this, PaymentActivity.class));
                overridePendingTransition(0, 0);
                return true;
            }

            return false;
        });
    }
}
