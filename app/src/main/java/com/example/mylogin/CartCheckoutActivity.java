package com.example.mylogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CartCheckoutActivity extends AppCompatActivity {

    private EditText edtName, edtAddress, edtCity, edtPostalCode;
    private Button btnCheckout;
    private ImageView imgTshirt;
    private TextView txtCustomization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_checkout);

        // Initialize Views
        edtName = findViewById(R.id.edtName);
        edtAddress = findViewById(R.id.edtAddress);
        edtCity = findViewById(R.id.edtCity);
        edtPostalCode = findViewById(R.id.edtPostalCode);
        btnCheckout = findViewById(R.id.btnCheckout);
        imgTshirt = findViewById(R.id.imgTshirt);
        txtCustomization = findViewById(R.id.txtCustomization);

        // Assume the T-shirt image and customization text come from the previous page
        // For now, we'll hardcode it (you can pass the actual data from Customization Page)

        // Handle checkout button click
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the user's address input
                String name = edtName.getText().toString();
                String address = edtAddress.getText().toString();
                String city = edtCity.getText().toString();
                String postalCode = edtPostalCode.getText().toString();

                // Check for valid input (just basic validation)
                if (name.isEmpty() || address.isEmpty() || city.isEmpty() || postalCode.isEmpty()) {
                    // Show error message (you can use Toast or a Snackbar)
                    return;
                }

                // Proceed to the Thank You screen
                Intent intent = new Intent(CartCheckoutActivity.this, ThankYouActivity.class);
                intent.putExtra("CUSTOMIZATION_TEXT", txtCustomization.getText().toString());
                startActivity(intent);
            }
        });
    }
}
