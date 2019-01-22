package com.example.ssr;

import com.example.ssr.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity 
{
	EditText edit1;

	public void onCreate(Bundle savedInstanceState) 
	{
	    super.onCreate(savedInstanceState);
	    this.setContentView(R.layout.activity_main);
	    edit1 = (EditText)findViewById(R.id.editText1);
	}
	public void btn_click(View v)
	{
		Intent intent = new Intent(this, RoomlistActivity.class);
		String str = edit1.getText().toString();
		intent.putExtra("key1",str);
		if(str.length()>=4)
		{
			this.startActivity(intent);
		}
		else
		{
			Toast.makeText(getApplicationContext(), "4글자 이상을 입력하시오" , Toast.LENGTH_SHORT).show();
		}
	}
}
