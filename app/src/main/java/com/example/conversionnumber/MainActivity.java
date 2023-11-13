package com.example.conversionnumber;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.math.BigInteger;

public class MainActivity extends AppCompatActivity {
    private Button Resetbtn;
    private Button Convertbtn;
    private Button Swap;
    private EditText input_number;
    private TextView Result;
    TextView label_input_sysnumber;
    TextView Result_label;
    Spinner Number_System1_list,Number_System2_list;
    private int sys_number2,sys_number1;
    void Attach_labels_tosystem(int element_selected,Spinner spinner){
        label_input_sysnumber = (TextView) findViewById(R.id.system_number_label);
        Result_label = (TextView) findViewById(R.id.system_number_Result_label);

        if(spinner == Number_System1_list && element_selected == 0){
            label_input_sysnumber.setText("Binary Number");
            sys_number1 = 2;
            return;
        }else if(spinner == Number_System1_list && element_selected == 1){
            label_input_sysnumber.setText("Decimal Number");
            sys_number1 = 10;
            return;
        }else if(spinner == Number_System1_list && element_selected == 2){
            label_input_sysnumber.setText("Octal Number");
            sys_number1 = 8;
            return;
        }else if(spinner == Number_System1_list && element_selected == 3){
            label_input_sysnumber.setText("Hexadecimal Number");
            sys_number1 = 16;
            return;
        }

        if(spinner == Number_System2_list && element_selected == 0){
            Result_label.setText("Binary Number");
            sys_number2 = 2;
        }else if(spinner == Number_System2_list && element_selected == 1){
            Result_label.setText("Decimal Number");
            sys_number2 = 10;
        }else if(spinner == Number_System2_list && element_selected == 2){
            Result_label.setText("Octal Number");
            sys_number2 = 8;
        }else if(spinner == Number_System2_list && element_selected == 3){
            Result_label.setText("Hexadecimal Number");
            sys_number2 = 16;
        }

    }
    void Hide_keyboards(){
        LinearLayout keyboard_lay = (LinearLayout) findViewById(R.id.keyboard_decimal_lay);
        keyboard_lay.setVisibility(View.GONE);
        keyboard_lay = (LinearLayout) findViewById(R.id.keyboard_binary_lay);
        keyboard_lay.setVisibility(View.GONE);
        keyboard_lay = (LinearLayout) findViewById(R.id.keyboard_octal_lay);
        keyboard_lay.setVisibility(View.GONE);
        keyboard_lay = (LinearLayout) findViewById(R.id.keyboard_hexadecimal_lay);
        keyboard_lay.setVisibility(View.GONE);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*BigInteger one, two;
        one = new BigInteger("5000000000000000000");
        two = new BigInteger("1111010010", 2);
        String str = one.toString(2);
        BigInteger bigInt = new BigInteger(str, 2);*/
        String[] Number_System = { "Binary", "Decimal","Octal","Hexadecimal"};
        Number_System1_list = (Spinner) findViewById(R.id.from_option);
        Number_System2_list = (Spinner) findViewById(R.id.to_option);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, Number_System);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Number_System1_list.setAdapter(arrayAdapter);
        Number_System2_list.setAdapter(arrayAdapter);
        Resetbtn = (Button) findViewById(R.id.Resetbtn);
        input_number = (EditText) findViewById(R.id.input_number);
        Result = (TextView) findViewById(R.id.Result);

        View.OnTouchListener otl = new View.OnTouchListener() {
            public boolean onTouch (View v, MotionEvent event) {
                Hide_keyboards();
                LinearLayout keyboard_lay = null;
                if(sys_number1 == 2){
                    Binary_Keyboard keyboard = (Binary_Keyboard) findViewById(R.id.keyboard_binary);
                    InputConnection ic = input_number.onCreateInputConnection(new EditorInfo());
                    keyboard.setInputConnection(ic);
                    keyboard_lay = (LinearLayout) findViewById(R.id.keyboard_binary_lay);
                    keyboard_lay.setVisibility(View.VISIBLE);
                }else if(sys_number1 == 10){
                    Decimal_Keyboard keyboard = (Decimal_Keyboard) findViewById(R.id.keyboard_decimal);
                    InputConnection ic = input_number.onCreateInputConnection(new EditorInfo());
                    keyboard.setInputConnection(ic);
                    keyboard_lay = (LinearLayout) findViewById(R.id.keyboard_decimal_lay);
                    keyboard_lay.setVisibility(View.VISIBLE);
                }else if(sys_number1 == 8){
                    Octal_Keyboard keyboard = (Octal_Keyboard) findViewById(R.id.keyboard_octal);
                    InputConnection ic = input_number.onCreateInputConnection(new EditorInfo());
                    keyboard.setInputConnection(ic);
                    keyboard_lay = (LinearLayout) findViewById(R.id.keyboard_octal_lay);
                    keyboard_lay.setVisibility(View.VISIBLE);
                }else if(sys_number1 == 16){
                    Hexadecimal_Keyboard keyboard = (Hexadecimal_Keyboard) findViewById(R.id.keyboard_hexadecimal);
                    InputConnection ic = input_number.onCreateInputConnection(new EditorInfo());
                    keyboard.setInputConnection(ic);
                    keyboard_lay = (LinearLayout) findViewById(R.id.keyboard_hexadecimal_lay);
                    keyboard_lay.setVisibility(View.VISIBLE);
                }

                return true; // the listener has consumed the event
            }
        };
        input_number.setOnTouchListener(otl);


        Resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input_number.setText("");
                Result.setText("");
            }
        });


        Convertbtn = (Button) findViewById(R.id.Convertbtn);
        Convertbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BigInteger decimal = new BigInteger(input_number.getText().toString(),sys_number1);
                Result.setText(decimal.toString(sys_number2));
            }
        });


        AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Attach_labels_tosystem(position, (Spinner) parent);
                Hide_keyboards();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        Number_System1_list.setOnItemSelectedListener(listener);
        Number_System2_list.setOnItemSelectedListener(listener);


        Swap = (Button) findViewById(R.id.Swapbtn);
        Swap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hide_keyboards();
                int temp_value_list = Number_System1_list.getSelectedItemPosition();
                Number_System1_list.setSelection(Number_System2_list.getSelectedItemPosition());
                Number_System2_list.setSelection(temp_value_list);
                String temp_val = input_number.getText().toString();
                input_number.setText(Result.getText().toString());
                Result.setText(temp_val);
                int pos = input_number.getText().length();
                input_number.setSelection(pos);
            }
        });


    }
}