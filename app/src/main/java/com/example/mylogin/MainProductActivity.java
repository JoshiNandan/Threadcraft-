package com.example.mylogin;





import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainProductActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_product);

        recyclerView = findViewById(R.id.recyclerViewProducts);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // 1 columns for grid layout

        productList = new ArrayList<>();
        productList.add(new Product("Red T-Shirt", R.drawable.tshirt_red, "Comfortable red cotton T-shirt", "₹399"));
        productList.add(new Product("Blue T-Shirt", R.drawable.tshirt_blue, "Stylish blue T-shirt", "₹499"));
        productList.add(new Product("Black T-Shirt", R.drawable.tshirt_black, "Classic black T-shirt", "₹599"));
        productList.add(new Product("White T-Shirt", R.drawable.tshirt_white, "Simple white T-shirt", "₹499"));
        productList.add(new Product("Cream colour T-Shirt", R.drawable.tshirt_cream, "oversize cream colour T-shirt", "₹499"));

        adapter = new ProductAdapter(this, productList, product -> {
            Intent intent = new Intent(MainProductActivity.this, CustomizationActivity.class);
            intent.putExtra("productName", product.getName());
            intent.putExtra("productImage", product.getImageResource());
            intent.putExtra("productDescription",product.getDescription());
            intent.putExtra("productPrice",product.getPrice());
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);
    }
}
