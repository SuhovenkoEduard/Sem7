package com.uni.lab3.IO.productsReader;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.uni.lab3.model.Product;

import java.io.BufferedReader;
import java.io.IOException;

public class ProductsReader extends AsyncTask<Void, Void, Product[]> {
    private final ProgressDialog progressDialog;
    private final BufferedReader bufferedReader;
    private final ProductsHandlerCallback onStopHandler;

    public ProductsReader(Context context, BufferedReader bufferedReader, ProductsHandlerCallback onStopHandler) {
        progressDialog = new ProgressDialog(context);
        this.bufferedReader = bufferedReader;
        this.onStopHandler = onStopHandler;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Data is loading");
        progressDialog.show();
    }

    @Override
    protected Product[] doInBackground(Void... arg0) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            Thread.sleep(1500);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        String text = stringBuilder.toString();
        return new Gson().fromJson(text, Product[].class);
    }

    @Override
    protected void onPostExecute(Product[] result) {
        super.onPostExecute(result);
        onStopHandler.handleProducts(result);
        progressDialog.dismiss();
    }
}