package com.example.mylogin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CustomizationActivity extends AppCompatActivity {

    private ImageView imageViewTshirt, tshirtPreview;
    private EditText editTextCustomText;
    private TextView textViewCustomText;
    private Button btnAddText, btnConfirmDesign;
    private FrameLayout customizationArea;
    private Spinner spinnerFontStyle;

    private float xDelta, yDelta; // Variables to track touch movement
    private String selectedFont = "default"; // Default font

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
        spinnerFontStyle = findViewById(R.id.spinnerFontStyle);

        // Get selected T-shirt image from intent
        int imageRes = getIntent().getIntExtra("productImage", R.drawable.tshirt_red);
        tshirtPreview.setImageResource(imageRes);
        imageViewTshirt.setImageResource(imageRes);

        // Set up spinner for font selection
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.font_styles, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFontStyle.setAdapter(adapter);

        // Spinner listener to update selected font style
        spinnerFontStyle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String font = parentView.getItemAtPosition(position).toString();
                selectedFont = font; // Update selected font style
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                selectedFont = "default"; // Default font
            }
        });

        // Add text to the T-shirt
        btnAddText.setOnClickListener(v -> {
            String customText = editTextCustomText.getText().toString();
            if (!customText.isEmpty()) {
                textViewCustomText.setText(customText);

                // Set the font style based on the selected option
                switch (selectedFont) {
                    case "Bold":
                        textViewCustomText.setTypeface(null, android.graphics.Typeface.BOLD);
                        break;
                    case "Italic":
                        textViewCustomText.setTypeface(null, android.graphics.Typeface.ITALIC);
                        break;
                    case "Bold Italic":
                        textViewCustomText.setTypeface(null, android.graphics.Typeface.BOLD_ITALIC);
                        break;
                    default:
                        textViewCustomText.setTypeface(null, android.graphics.Typeface.NORMAL);
                        break;
                }

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
