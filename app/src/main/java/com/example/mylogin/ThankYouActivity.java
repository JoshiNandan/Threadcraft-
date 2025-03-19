package com.example.mylogin;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;

public class ThankYouActivity extends AppCompatActivity {

    private RadioGroup paymentOptionsGroup;
    private Button btnPayNow;
    private ImageView txtThankYou;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);

        // Initialize Views
        paymentOptionsGroup = findViewById(R.id.paymentOptionsGroup);
        btnPayNow = findViewById(R.id.btnPayNow);
        txtThankYou = findViewById(R.id.txtThankYou);

        // Set the Thank You Image (if needed)
        txtThankYou.setImageResource(R.drawable.gpay); // Replace with your image

        // Handle Pay Now Button Click
        btnPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // You can check which payment option is selected
                int selectedPaymentOption = paymentOptionsGroup.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = findViewById(selectedPaymentOption);

                // Log or handle the selected payment option (optional)
                if (selectedRadioButton != null) {
                    String selectedPaymentMethod = selectedRadioButton.getText().toString();
                    // You can add any logic based on selected payment method here
                }

                // Redirect to the Main Product Page (first page)
                Intent intent = new Intent(ThankYouActivity.this, LastPage.class); // Replace with your Main Activity class
                startActivity(intent);
                finish(); // Optional: Finish the current activity to prevent going back to it
            }
        });
    }
}
