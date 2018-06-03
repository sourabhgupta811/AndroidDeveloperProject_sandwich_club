package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {
    TextView alsoKnownAsTextView;
    TextView ingredientsTextView;
    TextView placeOfOriginTextView;
    TextView descriptionTextView;
    Sandwich sandwich;
    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        alsoKnownAsTextView=findViewById(R.id.also_known_tv);
        ingredientsTextView=findViewById(R.id.ingredients_tv);
        placeOfOriginTextView=findViewById(R.id.origin_tv);
        descriptionTextView=findViewById(R.id.description_tv);
        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);
        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        List<String> ingredients = sandwich.getIngredients();
        String description = sandwich.getDescription();
        List<String> alsoKnownAs = sandwich.getAlsoKnownAs();
        if(placeOfOrigin!=null && !placeOfOrigin.equals(""))
            placeOfOriginTextView.setText(placeOfOrigin);
        else
            placeOfOriginTextView.setText(R.string.no_origin);
        if(ingredients.size()>0)
        for(String s:ingredients)
            ingredientsTextView.append(s+"\n");
        else
            ingredientsTextView.setText(R.string.no_ingredients);
        if(description!=null && !description.equals(""))
            descriptionTextView.setText(description);
        else
            descriptionTextView.setText(R.string.no_description);
        if(alsoKnownAs.size()>0)
        for(String s:alsoKnownAs)
            alsoKnownAsTextView.append(s+"\n");
        else
            alsoKnownAsTextView.setText(R.string.no_other_names);
    }
}
