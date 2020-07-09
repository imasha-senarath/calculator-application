package com.imasha.ds.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView settingBtn;
    AdView adView;

    HorizontalScrollView pDisplayScrollView, sDisplayScrollView;
    TextView pDisplay, sDisplay, welcomeText;

    Button n0,n00,n1,n2,n3,n4,n5,n6,n7,n8,n9,dot,equals,add,sub,mul,div,clear,reset;
    ImageButton gift;

    private String inputString="";
    private String finalSymbol="";
    private boolean isFinalOperator = false;
    private int stringLength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this,"app-id");
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        settingBtn = findViewById(R.id.setting_btn);
        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenSettingPage();
            }
        });

        welcomeText = findViewById(R.id.welcomeText);
        pDisplay = findViewById(R.id.primary_display);
        sDisplay = findViewById(R.id.secondary_display);
        pDisplayScrollView = findViewById(R.id.primary_display_scrollview);
        sDisplayScrollView = findViewById(R.id.secondary_display_scrollview);
        n00 = findViewById(R.id.no00);
        n0 = findViewById(R.id.no0);
        n1 = findViewById(R.id.no1);
        n2 = findViewById(R.id.no2);
        n3 = findViewById(R.id.no3);
        n4 = findViewById(R.id.no4);
        n5 = findViewById(R.id.no5);
        n6 = findViewById(R.id.no6);
        n7 = findViewById(R.id.no7);
        n8 = findViewById(R.id.no8);
        n9 = findViewById(R.id.no9);
        dot = findViewById(R.id.dot);
        equals = findViewById(R.id.equals);
        add = findViewById(R.id.addition);
        sub = findViewById(R.id.subtraction);
        mul = findViewById(R.id.multiplication);
        div = findViewById(R.id.division);
        clear = findViewById(R.id.clear);
        reset = findViewById(R.id.reset);
        gift = findViewById(R.id.gift);

        gift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupGiftDialog();
            }
        });

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
        String time = simpleDateFormat.format(new Date());
        if(Integer.parseInt(time) < 12) {
            welcomeText.setText("Hey, Good Morning!");
        } else if (Integer.parseInt(time) >= 12 && Integer.parseInt(time) < 17) {
            welcomeText.setText("Hey, Good Afternoon!");
        } else {
            welcomeText.setText("Hey, Good Evening!");
        }

        final View.OnClickListener onClickListener = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final int id = v.getId();
                switch (id){
                    case R.id.no0:
                        HideWelcomeScreen();
                        NumberClickProcess("0");
                        break;

                    case R.id.no00:
                        HideWelcomeScreen();
                        NumberClickProcess("00");
                        break;

                    case R.id.no1:
                        NumberClickProcess("1");
                        break;

                    case R.id.no2:
                        NumberClickProcess("2");
                        break;

                    case R.id.no3:
                        NumberClickProcess("3");
                        break;

                    case R.id.no4:
                        NumberClickProcess("4");
                        break;

                    case R.id.no5:
                        NumberClickProcess("5");
                        break;

                    case R.id.no6:
                        NumberClickProcess("6");
                        break;

                    case R.id.no7:
                        NumberClickProcess("7");
                        break;
                    case R.id.no8:
                        NumberClickProcess("8");
                        break;

                    case R.id.no9:
                        NumberClickProcess("9");
                        break;

                    case R.id.dot:
                        HideWelcomeScreen();
                        if(!finalSymbol.equals("."))
                        {
                            NumberClickProcess(".");
                        }
                        break;

                    case R.id.addition:
                        if(!isFinalOperator) {
                            HideWelcomeScreen();
                            pDisplayScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                            SetMainDisplayAsPrimaryDisplay();
                            pDisplay.append("+");
                            inputString = inputString + " + ";
                            finalSymbol="+";
                            isFinalOperator = true;
                        }
                        break;

                    case R.id.subtraction:
                        HideWelcomeScreen();
                        if(!isFinalOperator) {
                            pDisplayScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                            SetMainDisplayAsPrimaryDisplay();
                            pDisplay.append("-");
                            inputString = inputString + " - ";
                            finalSymbol="-";
                            isFinalOperator = true;
                        }
                        break;

                    case R.id.multiplication:
                        HideWelcomeScreen();
                        if(!inputString.equals("") && !isFinalOperator) {
                            pDisplayScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                            SetMainDisplayAsPrimaryDisplay();
                            pDisplay.append("×");
                            inputString = inputString + " * ";
                            finalSymbol="*";
                            isFinalOperator = true;
                        }
                        break;

                    case R.id.division:
                        HideWelcomeScreen();
                        if(!inputString.equals("") && !isFinalOperator) {
                            pDisplayScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                            SetMainDisplayAsPrimaryDisplay();
                            pDisplay.append("÷");
                            inputString = inputString + " / ";
                            finalSymbol="/";
                            isFinalOperator = true;
                        }
                        break;

                    case R.id.equals:
                        HideWelcomeScreen();
                        if(!inputString.equals("") && !isFinalOperator)
                        {
                            SetMainDisplayAsSecondaryDisplay();
                            stringLength = inputString.length();
                            if(finalSymbol.equals(".") && stringLength >= 4)
                            {
                                if((String.valueOf(inputString.charAt(stringLength-3)).equals("+")) || (String.valueOf(inputString.charAt(stringLength-3)).equals("-")) ||
                                        (String.valueOf(inputString.charAt(stringLength-3)).equals("*")) || (String.valueOf(inputString.charAt(stringLength-3)).equals("/")))
                                {
                                    sDisplay.setText("error");
                                    finalSymbol=".";
                                }
                                else
                                {
                                    prepareInput(inputString);
                                    finalSymbol="equals";
                                }
                            }
                            else
                            {
                                if(finalSymbol.equals(".") && stringLength <= 1)
                                {
                                    sDisplay.setText("error");
                                    finalSymbol=".";
                                }
                                else
                                {
                                    prepareInput(inputString);
                                    finalSymbol="equals";
                                }
                            }
                            isFinalOperator = false;
                        }
                        break;

                    case R.id.clear:
                        SetMainDisplayAsPrimaryDisplay();
                        pDisplay.setText("");
                        sDisplay.setText("");
                        inputString = "";
                        finalSymbol="";
                        isFinalOperator = false;
                        HideWelcomeScreen();
                        break;

                    case R.id.reset:
                        ShowWelcomeScreen();
                        pDisplay.setText("");
                        sDisplay.setText("");
                        inputString = "";
                        finalSymbol="";
                        isFinalOperator = false;
                        break;
                }
            }
        };
        n0.setOnClickListener(onClickListener);
        n00.setOnClickListener(onClickListener);
        n1.setOnClickListener(onClickListener);
        n2.setOnClickListener(onClickListener);
        n3.setOnClickListener(onClickListener);
        n4.setOnClickListener(onClickListener);
        n5.setOnClickListener(onClickListener);
        n6.setOnClickListener(onClickListener);
        n7.setOnClickListener(onClickListener);
        n8.setOnClickListener(onClickListener);
        n9.setOnClickListener(onClickListener);
        dot.setOnClickListener(onClickListener);
        equals.setOnClickListener(onClickListener);
        add.setOnClickListener(onClickListener);
        sub.setOnClickListener(onClickListener);
        mul.setOnClickListener(onClickListener);
        div.setOnClickListener(onClickListener);
        clear.setOnClickListener(onClickListener);
        reset.setOnClickListener(onClickListener);
    }

    private void PopupGiftDialog()
    {
        final Dialog giftDialog = new Dialog(this);
        giftDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        giftDialog.setContentView(R.layout.gift_dialog);
        giftDialog.setTitle("Gift Window");
        giftDialog.show();

        Button closeBtn = giftDialog.findViewById(R.id.gift_close_button);
        closeBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                giftDialog.dismiss();
            }
        });
    }

    private void SetMainDisplayAsSecondaryDisplay()
    {
        pDisplay.setTextSize(TypedValue.COMPLEX_UNIT_SP,40);
        pDisplay.setTextColor(getResources().getColor(R.color.secondaryTextColor));
        sDisplay.setTextSize(TypedValue.COMPLEX_UNIT_SP,60);
        sDisplay.setTextColor(getResources().getColor(R.color.primaryTextColor));
    }

    private void SetMainDisplayAsPrimaryDisplay()
    {
        pDisplay.setTextSize(TypedValue.COMPLEX_UNIT_SP,60);
        pDisplay.setTextColor(getResources().getColor(R.color.primaryTextColor));
        sDisplay.setTextSize(TypedValue.COMPLEX_UNIT_SP,40);
        sDisplay.setTextColor(getResources().getColor(R.color.secondaryTextColor));
    }

    private void NumberClickProcess(String value)
    {
        HideWelcomeScreen();

        if(finalSymbol.equals("equals"))
        {
            SetMainDisplayAsPrimaryDisplay();
            pDisplay.setText("");
            sDisplay.setText("");
            inputString = "";
        }

        SetMainDisplayAsPrimaryDisplay();
        pDisplayScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
        pDisplay.append(value);
        inputString = inputString + value;
        finalSymbol=value;
        isFinalOperator = false;
    }

    private void HideWelcomeScreen()
    {
        welcomeText.setVisibility(View.GONE);
        pDisplayScrollView.setVisibility(View.VISIBLE);
        sDisplayScrollView.setVisibility(View.VISIBLE);
    }

    private void ShowWelcomeScreen()
    {
        welcomeText.setVisibility(View.VISIBLE);
        pDisplayScrollView.setVisibility(View.GONE);
        sDisplayScrollView.setVisibility(View.GONE);
    }

    private void OpenSettingPage()
    {
        Intent settingIntent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(settingIntent);
    }

    private void prepareInput(String input) {
        if(input.startsWith(" ")) {
            String firstChar = String.valueOf(input.charAt(1));
            if(firstChar.contains("/")|| firstChar.contains("*")) {
                pDisplay.setText("error");
                return;
            } else {
                if(firstChar.contains("+")) {
                    input = input.replace(" + ", "");
                }
                if(firstChar.contains("-")) {
                    input = input.replace(" - ", "-");
                }
            }
        }
        calculate(input);
    }

    private void calculate(String input) {
        String[] arr = input.split(" ");
        if(arr.length > 1) {
            for(int i=0; i<arr.length; i++) {
                double temp = 0;
                try {
                    if(arr[i].contains("/")) {
                        Double op1 = Double.parseDouble(arr[i-1]);
                        Double op2 = Double.parseDouble(arr[i+1]);
                        if(op2 == 0) {
                            System.out.println("Not allowed");
                            sDisplay.setText("error");
                            input = "";
                            return;
                        }
                        temp = op1 / op2;
                        arr[i-1] = " ";
                        arr[i] = String.valueOf(temp);
                        arr[i+1] = " ";
                    }
                    if(arr[i].contains("*")) {
                        Double op1 = Double.parseDouble(arr[i-1]);
                        Double op2 = Double.parseDouble(arr[i+1]);
                        temp = op1 * op2;
                        arr[i-1] = " ";
                        arr[i] = String.valueOf(temp);
                        arr[i+1] = " ";
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            for(int i=0; i<arr.length; i++) {
                double temp = 0;
                try {
                    if(arr[i].contains("+")) {
                        double op1 = Double.parseDouble(arr[i-1]);
                        double op2 = Double.parseDouble(arr[i+1]);
                        temp = op1 + op2;
                        arr[i-1] = " ";
                        arr[i] = String.valueOf(temp);
                        arr[i+1] = " ";
                    }
                    if(arr[i].contains("-")) {
                        Double op1 = Double.parseDouble(arr[i-1]);
                        Double op2 = Double.parseDouble(arr[i+1]);
                        temp = op1 - op2;
                        arr[i-1] = " ";
                        arr[i] = String.valueOf(temp);
                        arr[i+1] = " ";
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            inputString = "";
            for(String s : arr) {
                if(s != " ") {
                    inputString += s;
                    inputString += " ";
                }
            }
            calculate(inputString);
        }
        else
        {
            inputString = arr[0];
            BigDecimal bigDecimal = new BigDecimal(arr[0]);
            String result = bigDecimal.stripTrailingZeros().toPlainString();

            String decimal = result.substring(result.indexOf('.') + 1);

            int decimalLength = decimal.length();
            if(decimalLength > 10)
            {
                Double finalResult = Double.parseDouble(result);
                sDisplay.setText(String.format("%.10f", finalResult));
            }
            else
            {
                sDisplay.setText(result);
            }
        }
    }
}
