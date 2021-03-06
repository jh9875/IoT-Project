package com.cookandroid.oreosample;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity  {

    Button btn1, btn2, btn3;
    TextView txt1,txt2,txt3,txt4,txt5,txt6,txt7,txt8,txt9,txt10,txt11,txt12;
    EditText conEtxt1, conEtxt2;
    LinearLayout lay1,lay2,lay3,lay4;
    View connectView;
    String Ip;
    int Port;
    public static Context context;
    String s = null;
    Connect conn = null;
    String onOff;
    float celsius;
    int humidity;
    String str1,str2;
    Msg msg = null;
    StateMsg state = null;
    SharedPreferences sp = null;
    SharedPreferences.Editor editor = null;
    Intent intent;
    int rock;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);

        txt1 = (TextView) findViewById(R.id.txt1);
        txt2 = (TextView) findViewById(R.id.txt2);
        txt3 = (TextView) findViewById(R.id.txt3);
        txt4 = (TextView) findViewById(R.id.txt4);
        txt5 = (TextView) findViewById(R.id.txt5);
        txt6 = (TextView) findViewById(R.id.txt6);
        txt7 = (TextView) findViewById(R.id.txt7);
        txt8 = (TextView) findViewById(R.id.txt8);
        txt9 = (TextView) findViewById(R.id.txt9);
        txt10 = (TextView) findViewById(R.id.txt10);
        txt11 = (TextView) findViewById(R.id.txt11);
        txt12 = (TextView) findViewById(R.id.txt12);

        lay1 = (LinearLayout) findViewById(R.id.lay1);
        lay2 = (LinearLayout) findViewById(R.id.lay2);
        lay3 = (LinearLayout) findViewById(R.id.lay3);
        lay4 = (LinearLayout) findViewById(R.id.lay4);

        sp = getSharedPreferences("ipFile",MODE_PRIVATE);
        editor = sp.edit();
        intent = new Intent(MainActivity.this,SocketService.class);
        rock = 0;


        context = this;

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {    // ????????????

                onOff = "2";
                stopService(intent);

                connectView = (View) View.inflate(MainActivity.this,R.layout.connect,null);    // ip,port????????? ?????????
                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                dlg.setView(connectView);
                dlg.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        conEtxt1 = (EditText) connectView.findViewById(R.id.etxt1);
                        conEtxt2 = (EditText) connectView.findViewById(R.id.etxt2);

                        Ip = conEtxt1.getText().toString();
                        Port = Integer.parseInt(conEtxt2.getText().toString());

                        editor.putString("IP",Ip);    // ip,port ?????? , ?????? ???????????????
                        editor.putInt("PORT",Port);
                        editor.commit();

                        conn = new Connect(Ip,Port);

                        rock = 0;

                        btn2.callOnClick();


                    }
                });
                dlg.setNegativeButton("??????",null);
                dlg.show();


            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {  // ????????????

                if(rock == 0){
                    rock = 1;      // ????????? ?????? ?????? ????????? ?????? ???
                    onOff = "1";    // ?????? ?????? ?????? ???????????? ?????? ??????
                    try {
                        Thread.sleep(500);    // ????????? ????????? ?????? ????????? ????????? ????????? ??????????????? ??????????????? ???????????? ??????
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    startService(intent);}    // ?????? ????????????


            }
        });



        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {    //????????????

                rock = 0;
                onOff = "2";

                try {
                    Thread.sleep(1000);  // ???????????? ??? ????????? ??? ??????????????? ??????
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {  // ?????? ?????????
                        txt1.setText("");
                        txt2.setText("");
                        txt3.setText("");
                        txt4.setText("");
                        txt5.setText("");
                        txt6.setText("");
                        txt7.setText("");
                        txt8.setText("");
                        txt9.setText("");
                        txt10.setText("");
                        txt11.setText("");
                        txt12.setText("");
                        lay1.setBackgroundColor(getResources().getColor(R.color.Good));
                        lay2.setBackgroundColor(getResources().getColor(R.color.Good));
                        lay3.setBackgroundColor(getResources().getColor(R.color.Good));
                        lay4.setBackgroundColor(getResources().getColor(R.color.Good));

                    }
                });

            }
        });

    }


    public void myTextView(){  // ????????? ????????? ?????????

        msg = ((SocketService)SocketService.context2).msg2;
        celsius = msg.getCelsius();
        humidity = msg.getHumidity();

        str1 = Float.toString(celsius);
        str2 = Integer.toString(humidity);

        state = new StateMsg(celsius,humidity);
        s = state.result();
        if(onOff.equals("1")){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    switch (msg.getDevice()){
                        case 1 :
                            txt1.setText("?????? : " + str1 + "???");
                            txt2.setText("?????? : " + str2 + "%");
                            txt3.setText("" + s);
                            if(s.equals("??????")) {lay1.setBackgroundColor(getResources().getColor(R.color.Good)); txt3.setTextColor(Color.WHITE);}
                            if(s.equals("??????")) {lay1.setBackgroundColor(getResources().getColor(R.color.Well)); txt3.setTextColor(Color.WHITE);}
                            if(s.equals("??????")) {lay1.setBackgroundColor(getResources().getColor(R.color.SoSo)); txt3.setTextColor(Color.WHITE);}
                            if(s.equals("??????")) {lay1.setBackgroundColor(getResources().getColor(R.color.Wanning)); txt3.setTextColor(Color.RED);}
                            break;

                        case 2:
                            txt4.setText("?????? : " + str1 + "???");
                            txt5.setText("?????? : " + str2 + "%");
                            txt6.setText("" + s);
                            if(s.equals("??????")) {lay2.setBackgroundColor(getResources().getColor(R.color.Good)); txt6.setTextColor(Color.WHITE);}
                            if(s.equals("??????")) {lay2.setBackgroundColor(getResources().getColor(R.color.Well)); txt6.setTextColor(Color.WHITE);}
                            if(s.equals("??????")) {lay2.setBackgroundColor(getResources().getColor(R.color.SoSo)); txt6.setTextColor(Color.WHITE);}
                            if(s.equals("??????")) {lay2.setBackgroundColor(getResources().getColor(R.color.Wanning)); txt6.setTextColor(Color.RED);}
                            break;

                        case 3:
                            txt7.setText("?????? : " + str1 + "???");
                            txt8.setText("?????? : " + str2 + "%");
                            txt9.setText("" + s);
                            if(s.equals("??????")) {lay3.setBackgroundColor(getResources().getColor(R.color.Good)); txt9.setTextColor(Color.WHITE);}
                            if(s.equals("??????")) {lay3.setBackgroundColor(getResources().getColor(R.color.Well)); txt9.setTextColor(Color.WHITE);}
                            if(s.equals("??????")) {lay3.setBackgroundColor(getResources().getColor(R.color.SoSo)); txt9.setTextColor(Color.WHITE);}
                            if(s.equals("??????")) {lay3.setBackgroundColor(getResources().getColor(R.color.Wanning)); txt9.setTextColor(Color.RED);}
                            break;

                        case 4:
                            txt10.setText("?????? : " + str1 + "???");
                            txt11.setText("?????? : " + str2 + "%");
                            txt12.setText("" + s);
                            if(s.equals("??????")) {lay4.setBackgroundColor(getResources().getColor(R.color.Good)); txt12.setTextColor(Color.WHITE);}
                            if(s.equals("??????")) {lay4.setBackgroundColor(getResources().getColor(R.color.Well)); txt12.setTextColor(Color.WHITE);}
                            if(s.equals("??????")) {lay4.setBackgroundColor(getResources().getColor(R.color.SoSo)); txt12.setTextColor(Color.WHITE);}
                            if(s.equals("??????")) {lay4.setBackgroundColor(getResources().getColor(R.color.Wanning)); txt12.setTextColor(Color.RED);}
                            break;

                        default :

                    }
                }
            }); }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(0,1,0,"????????? ??????");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {  //?????? ???????????? db?????? ??????
        super.onOptionsItemSelected(item);

        switch (item.getItemId()){
            case 1:

                btn3.callOnClick();

                intent = new Intent(getApplicationContext(), com.cookandroid.oreosample.Calendar.class);
                startActivity(intent);


                return true;
        }

        return false;
    }

    protected void onStart() {  // ????????? ????????? ??????????????? ????????? ????????????


        Ip = sp.getString("IP","");
        if(Ip.equals("")){
            btn1.callOnClick();}
        else{
            Port = sp.getInt("PORT",0);
            conn = new Connect(Ip,Port);

            if(rock == 0){
                rock = 1;
                onOff = "1";
                startService(intent);}

        }



        super.onStart();
    }

    protected void onDestroy(){  // ????????? ??????????????? ??????

        btn3.callOnClick();

        super.onDestroy();
    }



}

