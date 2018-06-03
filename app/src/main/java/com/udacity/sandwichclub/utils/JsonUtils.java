package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject fullData=new JSONObject(json);
            JSONObject name=fullData.getJSONObject("name");
            String mainName=name.getString("mainName");
            JSONArray alsoKnownAsJson=name.getJSONArray("alsoKnownAs");
            String placeOfOrigin = fullData.getString("placeOfOrigin");
            String description =fullData.getString("description");
            String image=fullData.getString("image");
            JSONArray ingredientsJson = fullData.getJSONArray("ingredients");
            ArrayList<String> ingredients=new ArrayList<>();
            ArrayList<String> alsoKnownAs=new ArrayList<>();
            for(int i=0;i<ingredientsJson.length();i++){
                ingredients.add(ingredientsJson.getString(i));
            }
            for(int i=0;i<alsoKnownAsJson.length();i++){
                alsoKnownAs.add(alsoKnownAsJson.getString(i));
            }
            return new Sandwich(mainName,alsoKnownAs,placeOfOrigin,description,image,ingredients);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
