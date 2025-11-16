package com.madelyn.wilkins;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Tables extends AppCompatActivity {

    TextView tvWelcome, tvQRInfo;
    EditText etName;
    Button btnConfirm;

    int selectedPersons = 0;   // selected persons per table
    boolean[] tableOccupied = new boolean[16]; // 16 tables max

    CardView Cardnum1, Cardnum2, Cardnum3, Cardnum4, Cardnum5, Cardnum6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables);

        // UI references
        tvWelcome = findViewById(R.id.tvWelcome);
        tvQRInfo = findViewById(R.id.tvQRInfo);
        etName = findViewById(R.id.etName);
        btnConfirm = findViewById(R.id.btnConfirm);

        Cardnum1 = findViewById(R.id.Cardnum1);
        Cardnum2 = findViewById(R.id.Cardnum2);
        Cardnum3 = findViewById(R.id.Cardnum3);
        Cardnum4 = findViewById(R.id.Cardnum4);
        Cardnum5 = findViewById(R.id.Cardnum5);
        Cardnum6 = findViewById(R.id.Cardnum6);

        // Disable confirm until card selected
        btnConfirm.setEnabled(false);
        btnConfirm.setAlpha(0.4f);

        // QR + name pre-fill
        String scannedData = getIntent().getStringExtra("qr_result");
        String userName = getIntent().getStringExtra("user_name");

        if (userName != null) etName.setText(userName);
        if (scannedData != null) tvQRInfo.setText("Scanned QR: " + scannedData);

        // Setup card actions
        setupCard(Cardnum1, 1);
        setupCard(Cardnum2, 2);
        setupCard(Cardnum3, 3);
        setupCard(Cardnum4, 4);
        setupCard(Cardnum5, 5);
        setupCard(Cardnum6, 6);

        // Confirm button logic
        btnConfirm.setOnClickListener(v -> {

            String name = etName.getText().toString().trim();

            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter your name!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (selectedPersons == 0) {
                Toast.makeText(this, "Please select how many persons per table!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Assign available table
            int tableNumber = -1;
            for (int i = 0; i < tableOccupied.length; i++) {
                if (!tableOccupied[i]) {
                    tableOccupied[i] = true;
                    tableNumber = i + 1;
                    break;
                }
            }

            if (tableNumber == -1) {
                Toast.makeText(this, "All tables are full!", Toast.LENGTH_LONG).show();
                return;
            }

            // Successful → Go to HomeActivity
            Intent intent = new Intent(Tables.this, HomeActivity.class);
            intent.putExtra("selected_name", name);
            intent.putExtra("selected_persons", selectedPersons);
            intent.putExtra("selected_table", tableNumber);
            startActivity(intent);
        });
    }


    // CARD SETUP FUNCTION (persons card)

    private void setupCard(CardView card, int persons) {
        card.setOnClickListener(v -> {

            selectedPersons = persons;

            // highlight the selected card
            clearHighlights();
            card.setCardBackgroundColor(getColor(R.color.green));

            // bounce animation (scale up slightly)
            animateCard(card);

            Toast.makeText(this, persons + " persons selected", Toast.LENGTH_SHORT).show();

            // enable confirm button after selecting a card
            btnConfirm.setEnabled(true);
            btnConfirm.setAlpha(1f);
        });
    }


    // Remove highlight from all cards

    private void clearHighlights() {
        Cardnum1.setCardBackgroundColor(getColor(android.R.color.white));
        Cardnum2.setCardBackgroundColor(getColor(android.R.color.white));
        Cardnum3.setCardBackgroundColor(getColor(android.R.color.white));
        Cardnum4.setCardBackgroundColor(getColor(android.R.color.white));
        Cardnum5.setCardBackgroundColor(getColor(android.R.color.white));
        Cardnum6.setCardBackgroundColor(getColor(android.R.color.white));
    }


    // Bounce animation on selection

    private void animateCard(CardView card) {
        ScaleAnimation scale = new ScaleAnimation(
                0.95f, 1.05f,
                0.95f, 1.05f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        scale.setDuration(180);
        scale.setRepeatCount(1);
        scale.setRepeatMode(Animation.REVERSE);
        card.startAnimation(scale);
    }
}
