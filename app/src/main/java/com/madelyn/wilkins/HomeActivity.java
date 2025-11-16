package com.madelyn.wilkins;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNav = findViewById(R.id.bottomNav);

        // 🔥 Highlight the "Home" tab when this screen loads
        bottomNav.setSelectedItemId(R.id.nav_home);

        bottomNav.setOnItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.nav_home) {
                return true; // Already on this page
            }

            if (id == R.id.nav_table) {
                startActivity(new Intent(HomeActivity.this, TableLayoutActivity.class));
                overridePendingTransition(0, 0); // No animation
                return true;
            }

            if (id == R.id.nav_orders) {
                startActivity(new Intent(HomeActivity.this, OrdersActivity.class));
                overridePendingTransition(0, 0);
                return true;
            }

            if (id == R.id.nav_payment) {
                startActivity(new Intent(HomeActivity.this, PaymentActivity.class));
                overridePendingTransition(0, 0);
                return true;
            }

            return false;
        });
    }
}
