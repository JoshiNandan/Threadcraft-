package com.example.mylogin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CartCheckoutActivity extends AppCompatActivity {

    private EditText edtName, edtAddress, edtCity, edtPostalCode, edtPhoneNumber;
    private Button btnCheckout;
    private ImageView imgTshirt;
    private TextView txtCustomization;

    private DatabaseReference rootDatabaseReference;

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

        rootDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Orders");

        // Retrieve data passed from CustomizationActivity
        Intent intent = getIntent();
        int productImage = intent.getIntExtra("productImage", R.drawable.tshirt_red);
        String customText = intent.getStringExtra("customText");

        imgTshirt.setImageResource(productImage);
        txtCustomization.setText(customText);

        btnCheckout.setOnClickListener(v -> {
            String name = edtName.getText().toString();
            String address = edtAddress.getText().toString();
            String city = edtCity.getText().toString();
            String postalCode = edtPostalCode.getText().toString();
            String phoneNumber = edtPhoneNumber.getText().toString();
            String customDesign = txtCustomization.getText().toString();

            if (!phoneNumber.matches("\\d{10}")) {
                edtPhoneNumber.setError("Please enter a valid 10-digit mobile number.");
                return;
            }

            if (name.isEmpty() || address.isEmpty() || city.isEmpty() || postalCode.isEmpty() || phoneNumber.isEmpty()) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create an Order object
            Order order = new Order(name, address, city, postalCode, phoneNumber, customDesign, productImage);

            // Save order with a unique ID
            DatabaseReference newOrderRef = rootDatabaseReference.push();
            newOrderRef.setValue(order)
                    .addOnSuccessListener(unused -> {
                        Toast.makeText(this, "Order placed successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(CartCheckoutActivity.this, ThankYouActivity.class));
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Failed to place order: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });
    }
}
