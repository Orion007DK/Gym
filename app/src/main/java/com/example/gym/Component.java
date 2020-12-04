package com.example.gym;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Component {


    private int id;
    private String name;
    private String quantity;
    private String amount;

    public Component(JSONObject jsonObject) throws JSONException {
    this.id=jsonObject.getInt("mealProductId");
    this.name=jsonObject.getString("mealName");
    this.quantity=jsonObject.getString("quantity");
    if(!jsonObject.isNull("amount"))
        this.amount=jsonObject.getString("amount");
    else
        this.amount=null;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getAmount() {
        return amount;
    }

    public String getQuantityWithAmount(){
        if(amount!=null && amount!="null")
            return quantity+"g("+amount+")";
        return quantity+"g";
    }
}
