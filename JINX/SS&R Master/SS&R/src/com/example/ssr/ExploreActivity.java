package com.example.ssr;

import java.io.File;

import ru.bartwell.exfilepicker.ExFilePicker;
import ru.bartwell.exfilepicker.ExFilePickerParcelObject;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;


public class ExploreActivity extends Activity 
{

	private static final int EX_FILE_PICKER_RESULT = 0;
	int num = 0;
	String str;
	TextView text2;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_explore);
		CheckBox checkbox = (CheckBox)findViewById(R.id.checkBox1); 
		text2 = (TextView) findViewById(R.id.textView2);
		checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if(buttonView.getId()==R.id.checkBox1){
					if(isChecked){
						Toast.makeText(getApplicationContext(), "암호화", 1).show();
					}
					else {
						Toast.makeText(getApplicationContext(), "해제됨", 1).show();
					}
				}
				
			}
		});
	}

	public void document(View v)
	{
		num = 1;
		Intent intent = new Intent(getApplicationContext(), ru.bartwell.exfilepicker.ExFilePickerActivity.class);
		intent.putExtra(ExFilePicker.SET_ONLY_ONE_ITEM, true);
		intent.putExtra(ExFilePicker.SET_FILTER_LISTED, new String[] { "ppt", "pptx" });
		intent.putExtra(ExFilePicker.SET_CHOICE_TYPE, ExFilePicker.CHOICE_TYPE_FILES);

		startActivityForResult(intent, EX_FILE_PICKER_RESULT);
	}
	public void movie(View v)
	{
	}
	public void music(View v)
	{
		num = 3;
		Intent intent = new Intent(getApplicationContext(), ru.bartwell.exfilepicker.ExFilePickerActivity.class);
		intent.putExtra(ExFilePicker.SET_ONLY_ONE_ITEM, true);
		intent.putExtra(ExFilePicker.SET_FILTER_LISTED, new String[] { "mp3", "wmv" });
		intent.putExtra(ExFilePicker.SET_CHOICE_TYPE, ExFilePicker.CHOICE_TYPE_FILES);

		startActivityForResult(intent, EX_FILE_PICKER_RESULT);
	}
	public void btn_create(View v)
	{
		Intent intent = new Intent(this, InRoomActivity.class);
		this.startActivity(intent);
		/*if(num == 0)
		{
			Toast.makeText(getApplicationContext(), "파일 경로를 입력하세요" , Toast.LENGTH_SHORT).show();
		}
		else
		{
			File file = new File(str);
			Intent intent = new Intent(Intent.ACTION_VIEW);	
			if(num == 1) intent.setDataAndType(Uri.fromFile(file),"application/vnd.ms-powerpoint");
			else if(num == 3) intent.setDataAndType(Uri.fromFile(file),"audio/x-wav");
			
			num = 0;
			startActivity(intent);
		}*/
	}
	public void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		if (requestCode == EX_FILE_PICKER_RESULT) 
		{
			if (data != null) 
			{
				ExFilePickerParcelObject object = (ExFilePickerParcelObject) data.getParcelableExtra(ExFilePickerParcelObject.class.getCanonicalName());
				if (object.count > 0) 
				{
					StringBuffer buffer = new StringBuffer();
					for (int i = 0; i < object.count; i++) {
						buffer.append(object.names.get(i));
						if (i < object.count - 1) buffer.append(", ");
					}
					
					str = object.path + buffer.toString();
					text2.setText(str);
				}
			}
		}
	}

}