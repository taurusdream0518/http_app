package com.example.http_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class ThingSpeakActivity extends AppCompatActivity {

    private Context context;
    private String webAddress = "https://api.thingspeak.com/";
    private String field1 = "&field1=";
    private String field2 = "&field2=";
    private String getDataApiKey = "update?api_key=QH5XMQVV1H8KES2Y";
    private String postDataApiKey = "api_key=QH5XMQVV1H8KES2Y";
    private String channelStatus = "channels/1226551/status.json";
    private String channelLastData_field1 = "channels/1226551/fields/1/last.json";//取最後一筆

    private String channelData_field1 = "channels/1226551/fields/1.json?results=2";
    private EditText editTextField1;
    private EditText editTextField2;
    private TextView textViewResult;
    private Button buttonCancel;
    private Button buttonSendGet,buttonSendPost,buttonStatus,buttonData;
    private StringBuilder thingSpeakURL;
    private String field1Data,field2Data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thing_speak);

        context = this;
        setTitle("ThingSpeak");
        ActionBar bar = getSupportActionBar(); //增加返回箭頭
        bar.setDisplayHomeAsUpEnabled(true);

        editTextField1 = (EditText) findViewById(R.id.editText_thingField1);
        editTextField2 = (EditText) findViewById(R.id.editText_thingField2);
        textViewResult = (TextView) findViewById(R.id.textView_thingResult);
        textViewResult.setText("");

        buttonCancel = (Button) findViewById(R.id.button_thingCancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextField1.setText("");
                editTextField2.setText("");
            }
        });

        buttonSendGet = (Button) findViewById(R.id.button_thingSendGet);
        buttonSendPost = (Button) findViewById(R.id.button_thingSendPost);
        buttonStatus = (Button) findViewById(R.id.button_thingStatus);
        buttonData = (Button) findViewById(R.id.button_thingData);

        buttonSendGet.setOnClickListener(new MyButton());
        buttonSendPost.setOnClickListener(new MyButton());
        buttonStatus.setOnClickListener(new MyButton());
        buttonData.setOnClickListener(new MyButton());

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home://返回功能
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private class MyButton implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.button_thingSendGet:
                    if(editTextField1.length()==0){
                        editTextField1.setText("0");
                        Toast.makeText(context,"Please input field 1",Toast.LENGTH_SHORT).show();
                        field1Data="0";
                    } else {
                       field1Data = editTextField1.getText().toString();
                    }
                    if(editTextField2.length()==0){
                        editTextField2.setText("0");
                        Toast.makeText(context,"Please input field 2",Toast.LENGTH_SHORT).show();
                        field2Data="0";
                    } else {
                        field2Data = editTextField2.getText().toString();
                    }

                    thingSpeakURL = new StringBuilder();
                    thingSpeakURL.append(webAddress);
                    String param = getDataApiKey + field1 +field1Data + field2 + field2Data;
                    Log.d("thing","param = "+param);
                    thingSpeakURL.append(param);
                    Log.d("thing","thingSpeakURL = "+thingSpeakURL);
                    new HttpGetAction().start();
                    break;
                    
                case R.id.button_thingSendPost:
                    if(editTextField1.length()==0){
                        editTextField1.setText("0");
                        Toast.makeText(context,"Please input field 1",Toast.LENGTH_SHORT).show();
                        field1Data="0";
                    } else {
                        field1Data = editTextField1.getText().toString();
                    }
                    if(editTextField2.length()==0){
                        editTextField2.setText("0");
                        Toast.makeText(context,"Please input field 2",Toast.LENGTH_SHORT).show();
                        field2Data="0";
                    } else {
                        field2Data = editTextField2.getText().toString();
                    }

                    thingSpeakURL = new StringBuilder();
                    thingSpeakURL.append(webAddress);
                    String param1 = getDataApiKey + field1 +field1Data + field2 + field2Data;
                    Log.d("thing","param = "+param1);
                    thingSpeakURL.append(param1);
                    Log.d("thing","thingSpeakURL = "+thingSpeakURL);
                    new HttpSendData_Post().start();
                    
                    break;

                case R.id.button_thingStatus:
                    thingSpeakURL = new StringBuilder();
                    thingSpeakURL.append(webAddress);
                    thingSpeakURL.append(channelStatus);
                    Log.d("thing","thongSpeakURL = "+thingSpeakURL);
                    new HttpGetAction().start();

                    break;

                case R.id.button_thingData:
                    thingSpeakURL = new StringBuilder();
                    thingSpeakURL.append(webAddress);
                    thingSpeakURL.append(channelLastData_field1);
                    Log.d("thing","thongSpeakURL = "+thingSpeakURL);
                    new HttpGetAction().start();
                    break;
            }


        }
    }

    private class HttpGetAction extends Thread{

        private URL url;
        private HttpURLConnection conn;
        private int code;
        private InputStream inputStream;
        private String responseData;
        private String dataString;

        @Override
        public void run() {
            super.run();
            try {
                url = new URL(thingSpeakURL.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {
                conn = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                conn.setRequestMethod("GET");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }

            try {
                code = conn.getResponseCode();//連網
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("thing","code = "+code);

            if(code == HttpURLConnection.HTTP_OK){
                Log.d("thing","OK");
                try {
                    inputStream = conn.getInputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                InputStreamReader reader = new InputStreamReader(inputStream);
                BufferedReader stringReader = new BufferedReader(reader);
                try {
                    dataString = stringReader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d("thing","dataString = "+dataString);
                //網站回傳是bite的時候使用
//                char[] buffer = new char[1024];
//
//                try {
//                    int number = reader.read(buffer);
//                    Log.d("thing","number = "+number);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                responseData = String.valueOf(buffer);
//                Log.d("thing","responseData = "+responseData);
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        textViewResult.setText(responseData);
                        textViewResult.setText(dataString);
                    }
                });

            }//end OK
        }// end run
    }//end Thread

    private class HttpSendData_Post extends Thread{

        private URL url;
        private HttpURLConnection conn;
        private OutputStream outputStream = null;
        private int code;
        private InputStream inputStream = null;
        private String dataString;
        private String responseData;

        @Override
        public void run() {
            super.run();
            thingSpeakURL = new StringBuilder();
            thingSpeakURL.append(webAddress);
            thingSpeakURL.append("update");
            try {
                url = new URL(thingSpeakURL.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {
                conn = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }

            conn.setDoOutput(true);
            try {
                conn.setRequestMethod("POST");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }

            try {
                outputStream = conn.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            OutputStreamWriter writer = new OutputStreamWriter(outputStream);
            String postParam = postDataApiKey+field1+field1Data+field2+field2Data;
            Log.d("thing","postParam = "+postParam);

            try {
                writer.write(postParam);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                code = conn.getResponseCode();
                Log.d("thing","code = "+code);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(code == HttpURLConnection.HTTP_OK){
                Log.d("thing","OK");
                try {
                    inputStream = conn.getInputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                InputStreamReader reader = new InputStreamReader(inputStream);
//                BufferedReader stringReader = new BufferedReader(reader);
//                try {
//                    dataString = stringReader.readLine();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                Log.d("thing","dataString = "+dataString);
                //網站回傳是bite的時候使用
                char[] buffer = new char[10];

                try {
                    int number = reader.read(buffer);
                    Log.d("thing","number = "+number);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                responseData = String.valueOf(buffer);
                Log.d("thing","responseData = "+responseData);
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textViewResult.setText(responseData);
//                        textViewResult.setText(dataString);
                    }
                });

            }//end OK

        }//end run
    }//end Thread
}