package com.example.conversionnumber;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.LinearLayout;

public class Binary_Keyboard extends LinearLayout implements View.OnClickListener {

    // constructors
    public Binary_Keyboard(Context context) {
        this(context, null, 0);
    }

    public Binary_Keyboard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Binary_Keyboard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    // keyboard keys (buttons)
    private Button mButton1;
    private Button mButton0;
    private Button mButtonDelete;
    private Button mButtonEnter;

    // This will map the button resource id to the String value that we want to
    // input when that button is clicked.
    SparseArray<String> keyValues = new SparseArray<>();

    // Our communication link to the EditText
    InputConnection inputConnection;

    private void init(Context context, AttributeSet attrs) {

        // initialize buttons
        LayoutInflater.from(context).inflate(R.layout.binary_keyboard, this, true);
        mButton1 = (Button) findViewById(R.id.button_1);
        mButton0 = (Button) findViewById(R.id.button_0);
        mButtonDelete = (Button) findViewById(R.id.button_delete);
        mButtonEnter = (Button) findViewById(R.id.button_enter);

        // set button click listeners
        mButton1.setOnClickListener(this);
        mButton0.setOnClickListener(this);
        mButtonDelete.setOnClickListener(this);
        mButtonEnter.setOnClickListener(this);

        // map buttons IDs to input strings
        keyValues.put(R.id.button_1, "1");
        keyValues.put(R.id.button_0, "0");
    }

    @Override
    public void onClick(View v) {

        // do nothing if the InputConnection has not been set yet
        if (inputConnection == null) return;
        if(v.getId() == R.id.button_enter){
            LinearLayout keyboard_lay = (LinearLayout) this.getParent();
            keyboard_lay.setVisibility(View.GONE);
            return;
        }
        // Delete text or input key value
        // All communication goes through the InputConnection
        if (v.getId() == R.id.button_delete) {
            CharSequence selectedText = inputConnection.getSelectedText(0);
            if (TextUtils.isEmpty(selectedText)) {
                // no selection, so delete previous character
                inputConnection.deleteSurroundingText(1, 0);
            } else {
                // delete the selection
                inputConnection.commitText("", 1);
            }
        }else {
            String value = keyValues.get(v.getId());
            inputConnection.commitText(value, 1);
        }
    }

    // The activity (or some parent or controller) must give us
    // a reference to the current EditText's InputConnection
    public void setInputConnection(InputConnection ic) {
        this.inputConnection = ic;
    }
}
