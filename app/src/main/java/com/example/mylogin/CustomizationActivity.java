package com.example.mylogin;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CustomizationActivity extends AppCompatActivity {

    private ImageView imageViewTshirt, tshirtPreview;
    private EditText editTextCustomText;
    private TextView textViewCustomText;
    private Button btnAddText, btnConfirmDesign;
    private FrameLayout customizationArea;

    private float xDelta, yDelta; // Variables to track touch movement

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customization);

        imageViewTshirt = findViewById(R.id.imageViewTshirt);
        editTextCustomText = findViewById(R.id.editTextCustomText);
        textViewCustomText = findViewById(R.id.textViewCustomText);
        btnAddText = findViewById(R.id.btnAddText);
        btnConfirmDesign = findViewById(R.id.btnConfirmDesign);
        customizationArea = findViewById(R.id.customizationArea);
        tshirtPreview = findViewById(R.id.tshirtPreview);

        // Get selected T-shirt image from intent
        int imageRes = getIntent().getIntExtra("productImage", R.drawable.tshirt_red);
        tshirtPreview.setImageResource(imageRes);
        imageViewTshirt.setImageResource(imageRes);


        // Add text to the T-shirt
        btnAddText.setOnClickListener(v -> {
            String customText = editTextCustomText.getText().toString();
            if (!customText.isEmpty()) {
                textViewCustomText.setText(customText);
                textViewCustomText.setVisibility(View.VISIBLE);
            }
        });

        // Make text draggable on T-shirt
        textViewCustomText.setOnTouchListener((v, event) -> {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    xDelta = event.getRawX() - textViewCustomText.getX();
                    yDelta = event.getRawY() - textViewCustomText.getY();
                    break;

                case MotionEvent.ACTION_MOVE:
                    textViewCustomText.setX(event.getRawX() - xDelta);
                    textViewCustomText.setY(event.getRawY() - yDelta);
                    break;
            }
            return true;
        });

        // Confirm design and go to Cart
        btnConfirmDesign.setOnClickListener(v -> {
            Intent intent = new Intent(CustomizationActivity.this, CartCheckoutActivity.class);
            intent.putExtra("productName", getIntent().getStringExtra("productName"));
            intent.putExtra("productImage", imageRes);
            intent.putExtra("customText", textViewCustomText.getText().toString());
            startActivity(intent);
        });
    }
}
