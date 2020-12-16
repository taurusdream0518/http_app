package com.example.http_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

public class PHPActivity extends AppCompatActivity {

    private PHPActivity context;
    private String webAddress = "http://192.168.63.23:8080/11-14_api/";
    private String readData = "read_data.php";
    private String addDataGet = "api_add_get.php?";
    private String addDataPost = "api_add_post.php";
    private String updateGet = "api_update_get.php?";
    private String deleteGet = "api_delete_get.php?cID=";

    private String nameString = "cName=";
    private String birthdayString = "cBirthday=";
    private String sexString = "cSex=";
    private String emailString = "cEmail=";
    private String phoneString = "cPhone=";
    private String addressString = "cAddr=";
    private String idString = "cID=";
    private EditText editTextName,editTextBirth,editTextEmail,editTextAddr,editTextPhone,editTextID;
    private TextView textViewResult;
    private Switch switchSex;
    private String sexData;
    private Button buttonCancel;
    private Button buttonAdd_get,buttonUpdate,buttonAdd_post,buttonDelete,buttonReadData;
    private StringBuilder myAddress;
    private String nameData,birthData,emailData,phoneData,addrData,idData;
    private String param;
    private String postParam;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_h_p);

        context = this;
        setTitle("PHP");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextName = (EditText) findViewById(R.id.editText_phpName);
        editTextBirth = (EditText) findViewById(R.id.editText_phpBirth);
        editTextEmail = (EditText) findViewById(R.id.editText_phpEmail);
        editTextPhone = (EditText) findViewById(R.id.editText_phpPhone);
        editTextAddr = (EditText) findViewById(R.id.editText_phpAddress);
        editTextID = (EditText) findViewById(R.id.editText_phpID);

        textViewResult = (TextView) findViewById(R.id.textView_phpResult);
        textViewResult.setText("");

        switchSex = (Switch) findViewById(R.id.switch_phpSex);
        switchSex.setChecked(false);
        sexData = "M";
        switchSex.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    sexData = "F";
                } else {
                    sexData = "M";
                }
            }
        });

        buttonCancel = (Button) findViewById(R.id.button_phpCancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextName.setText("");
                editTextBirth.setText("");
                editTextEmail.setText("");
                editTextAddr.setText("");
                editTextPhone.setText("");
                editTextID.setText("");
                switchSex.setChecked(false);
                sexData="M";
                textViewResult.setText("");
            }
        });

        buttonAdd_get = (Button) findViewById(R.id.button_phpAddGet);
        buttonAdd_post = (Button) findViewById(R.id.button_phpAddPost);
        buttonUpdate = (Button) findViewById(R.id.button_phpUpdate);
        buttonDelete = (Button) findViewById(R.id.button_phpDelete);
        buttonReadData = (Button) findViewById(R.id.button_phpRead);

        buttonAdd_get.setOnClickListener(new MyButton());
        buttonAdd_post.setOnClickListener(new MyButton());
        buttonUpdate.setOnClickListener(new MyButton());
        buttonDelete.setOnClickListener(new MyButton());
        buttonReadData.setOnClickListener(new MyButton());



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    private class MyButton implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.button_phpAddGet:
                    if(editTextName.length() ==0){
                        Toast.makeText(context,"Please input Name",Toast.LENGTH_SHORT).show();
                        break;
                    } else {
                        nameData = editTextName.getText().toString();
                    }

                    if(editTextBirth.length()==0){
                        Toast.makeText(context,"Please input Birth",Toast.LENGTH_SHORT).show();
                        break;
                    } else {
                        birthData = editTextBirth.getText().toString();
                    }

                    if(editTextEmail.length()==0){
                        Toast.makeText(context,"Please input Email",Toast.LENGTH_SHORT).show();
                        break;
                    } else {
                        emailData = editTextEmail.getText().toString();
                    }

                    if(editTextPhone.length()==0){
                        Toast.makeText(context,"Please input Phone number",Toast.LENGTH_SHORT).show();
                        break;
                    } else {
                        phoneData = editTextPhone.getText().toString();
                    }

                    if(editTextAddr.length()==0){
                        Toast.makeText(context,"Please input Address",Toast.LENGTH_SHORT).show();
                        break;
                    } else {
                        addrData = editTextAddr.getText().toString();
                    }

                    myAddress = new StringBuilder();
                    myAddress.append(webAddress);
                    myAddress.append(addDataGet);
                    param = nameString+nameData+"&"+sexString+sexData+"&"+birthdayString+birthData+"&"+emailString+emailData+"&"+phoneString+phoneData+"&"+addressString+addrData;
                    myAddress.append(param);
                    Log.d("php","send data = "+myAddress);

                    new PHPGetAction().start();

                    break;
                case R.id.button_phpAddPost:
                    if(editTextName.length() ==0){
                        Toast.makeText(context,"Please input Name",Toast.LENGTH_SHORT).show();
                        break;
                    } else {
                        nameData = editTextName.getText().toString();
                    }

                    if(editTextBirth.length()==0){
                        Toast.makeText(context,"Please input Birth",Toast.LENGTH_SHORT).show();
                        break;
                    } else {
                        birthData = editTextBirth.getText().toString();
                    }

                    if(editTextEmail.length()==0){
                        Toast.makeText(context,"Please input Email",Toast.LENGTH_SHORT).show();
                        break;
                    } else {
                        emailData = editTextEmail.getText().toString();
                    }

                    if(editTextPhone.length()==0){
                        Toast.makeText(context,"Please input Phone number",Toast.LENGTH_SHORT).show();
                        break;
                    } else {
                        phoneData = editTextPhone.getText().toString();
                    }

                    if(editTextAddr.length()==0){
                        Toast.makeText(context,"Please input Address",Toast.LENGTH_SHORT).show();
                        break;
                    } else {
                        addrData = editTextAddr.getText().toString();
                    }

                    myAddress = new StringBuilder();
                    myAddress.append(webAddress);
                    myAddress.append(addDataPost);
                    postParam = nameString+nameData+"&"+sexString+sexData+"&"+birthdayString+birthData+"&"+emailString+emailData+"&"+phoneString+phoneData+"&"+addressString+addrData;


                    Log.d("php","send data = "+myAddress);
                    Log.d("php","postparam = "+postParam);

                    new PHPPostAction().start();

                    break;
                case R.id.button_phpUpdate:

                    if(editTextName.length() ==0){
                        Toast.makeText(context,"Please input Name",Toast.LENGTH_SHORT).show();
                        break;
                    } else {
                        nameData = editTextName.getText().toString();
                    }

                    if(editTextBirth.length()==0){
                        Toast.makeText(context,"Please input Birth",Toast.LENGTH_SHORT).show();
                        break;
                    } else {
                        birthData = editTextBirth.getText().toString();
                    }

                    if(editTextEmail.length()==0){
                        Toast.makeText(context,"Please input Email",Toast.LENGTH_SHORT).show();
                        break;
                    } else {
                        emailData = editTextEmail.getText().toString();
                    }

                    if(editTextPhone.length()==0){
                        Toast.makeText(context,"Please input Phone number",Toast.LENGTH_SHORT).show();
                        break;
                    } else {
                        phoneData = editTextPhone.getText().toString();
                    }

                    if(editTextAddr.length()==0){
                        Toast.makeText(context,"Please input Address",Toast.LENGTH_SHORT).show();
                        break;
                    } else {
                        addrData = editTextAddr.getText().toString();
                    }

                    if(editTextID.length() == 0){
                        Toast.makeText(context,"Please input ID",Toast.LENGTH_SHORT).show();
                    } else {
                        idData = editTextID.getText().toString();
                    }

                    myAddress = new StringBuilder();
                    myAddress.append(webAddress);
                    myAddress.append(updateGet);
                    param = nameString+nameData+"&"+sexString+sexData+"&"+birthdayString+birthData+"&"+emailString+emailData+"&"+phoneString+phoneData+"&"+addressString+addrData+"&"+idString+idData;
                    myAddress.append(param);
                    Log.d("php","send data = "+myAddress);

                    new PHPGetAction().start();

                    break;
                case R.id.button_phpDelete:
                    if(editTextID.length() == 0){
                        Toast.makeText(context,"Please input ID",Toast.LENGTH_SHORT).show();
                    } else {
                        idData = editTextID.getText().toString();
                    }
                    myAddress = new StringBuilder();
                    myAddress.append(webAddress);
                    myAddress.append(deleteGet);
                    myAddress.append(idData);
                    Log.d("php","addr = "+myAddress);

                    new PHPGetAction().start();
                    break;
                case R.id.button_phpRead:
                    new PHPReadData().start();

                    break;
            }

        }
    }

    private class PHPReadData extends Thread{
        private URL url;
        private HttpURLConnection conn;
        private int code;
        private InputStream inputStream = null;
        private String dataString;
        private JSONArray jsonArray = null;
        private StringBuffer userData;

        @Override
        public void run() {
            super.run();
            myAddress = new StringBuilder();
            myAddress.append(webAddress);
            myAddress.append(readData);

            try {
                url =new URL(myAddress.toString());
                Log.d("php","url = "+url);
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
                code = conn.getResponseCode();
                Log.d("php","code = "+code);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(code == HttpURLConnection.HTTP_OK){
                Log.d("php","OK");
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
                Log.d("php","dataString = "+dataString);
                //網站回傳是bite的時候使用
//                char[] buffer = new char[10];
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
//                        textViewResult.setText(dataString);
                        //用JSON格式取值
                        if(dataString.length() == 0){
                            return;
                        }

                        textViewResult.setText("");
                        try {
                            jsonArray = new JSONArray(dataString);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        int count = jsonArray.length();
                        Log.d("php","count = "+count);
                        userData = new StringBuffer();
                        for(int i=0; i<count;i++){

                            JSONObject jsonData = null;
                            try {
                                jsonData = jsonArray.getJSONObject(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                String myID = jsonData.getString("cID");
                                Log.d("php","myID = "+myID);
                                userData.append("ID = "+myID+ " , ");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                String myName = jsonData.getString("cName");
                                Log.d("php","myName = "+myName);
                                userData.append("Name = "+myName+"\n");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                String mySex = jsonData.getString("cSex");
                                Log.d("php","mySex = "+mySex);
                                userData.append("Sex = "+mySex+"\n");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            try {
                                String myBirth = jsonData.getString("cBirthday");
                                Log.d("php","myBirth = "+myBirth);
                                userData.append("Birthday = "+myBirth+"\n");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            try {
                                String myEmail = jsonData.getString("cEmail");
                                Log.d("php","myEmail = "+myEmail);
                                userData.append("Email = "+myEmail+"\n");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            try {
                                String myPhone = jsonData.getString("cPhone");
                                Log.d("php","myPhone = "+myPhone);
                                userData.append("Phone = "+myPhone+"\n");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                String myAddr = jsonData.getString("cAddr");
                                Log.d("php","myAddr = "+myAddr);
                                userData.append("Address = "+myAddr+"\n");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }




                        }//end for
                        textViewResult.append(userData.toString());

                    }
                });

            }//end OK
        }//end run
    }//end Thread

    private class PHPGetAction extends Thread{
        private URL url;
        private HttpURLConnection conn;
        private int code;
        private InputStream inputStream = null;
        private String dataString;


        @Override
        public void run() {
            super.run();
            try {
                url =new URL(myAddress.toString());
                Log.d("php","url = "+url);
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
                code = conn.getResponseCode();
                Log.d("php","code = "+code);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(code == HttpURLConnection.HTTP_OK){
                Log.d("php","OK");
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
                Log.d("php","dataString = "+dataString);
                //網站回傳是bite的時候使用
//                char[] buffer = new char[10];
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
        }//end run
    }//end Thread

    private class PHPPostAction extends Thread{
        private URL url;
        private HttpURLConnection conn;
        private OutputStream outputStream = null;
        private int code;
        private InputStream inputStream = null;
        private String dataString;

        @Override
        public void run() {
            super.run();
            try {
                url = new URL(myAddress.toString());
                Log.d("php","url = "+url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try {
                conn = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                conn.setRequestMethod("POST");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }
            conn.setDoOutput(true);

            try {
                outputStream = conn.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            OutputStreamWriter writer = new OutputStreamWriter(outputStream);
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
                Log.d("php","code = "+code);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(code == HttpURLConnection.HTTP_OK){
                Log.d("php","OK");
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
                Log.d("php","dataString = "+dataString);
                //網站回傳是bite的時候使用
//                char[] buffer = new char[10];
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
        }//end run
    }//end Thread
}