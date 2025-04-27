package com.example.mylogin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CartCheckoutActivity extends AppCompatActivity {

    private EditText edtName, edtAddress, edtCity, edtPostalCode, edtPhoneNumber;
    private Button btnCheckout;
    private ImageView imgTshirt;
    private TextView txtCustomization;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_checkout);

        // Initialize Views
        edtName = findViewById(R.id.edtName);
        edtAddress = findViewById(R.id.edtAddress);
        edtCity = findViewById(R.id.edtCity);
        edtPostalCode = findViewById(R.id.edtPostalCode);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        btnCheckout = findViewById(R.id.btnCheckout);
        imgTshirt = findViewById(R.id.imgTshirt);
        txtCustomization = findViewById(R.id.txtCustomization);

        // Retrieve data passed from CustomizationActivity
        Intent intent = getIntent();
        int productImage = intent.getIntExtra("productImage", R.drawable.tshirt_red);  // Default image if not passed
        String customText = intent.getStringExtra("customText");

        // Set the T-shirt image and custom text
        imgTshirt.setImageResource(productImage);
        txtCustomization.setText(customText);

        // Handle checkout button click
        btnCheckout.setOnClickListener(v -> {
            // Get the user's address input
            String name = edtName.getText().toString();
            String address = edtAddress.getText().toString();
            String city = edtCity.getText().toString();
            String postalCode = edtPostalCode.getText().toString();
            String PhoneNumber = edtPhoneNumber.getText().toString();


            if (!PhoneNumber.matches("\\d{10}")) {
                // If not 10 digits, show a validation message
                edtPhoneNumber.setError("Please enter a valid 10-digit mobile number.");
                return;
            }

            // Check for valid input (basic validation)
            if (name.isEmpty() || address.isEmpty() || city.isEmpty() || postalCode.isEmpty() || PhoneNumber.isEmpty()) {
                // Show error message (you can use Toast or a Snackbar)
                return;
            }


            // Proceed to the Thank You screen
            Intent thankYouIntent = new Intent(CartCheckoutActivity.this, ThankYouActivity.class);
            thankYouIntent.putExtra("CUSTOMIZATION_TEXT", txtCustomization.getText().toString());
            startActivity(thankYouIntent);
        });
    }
}
