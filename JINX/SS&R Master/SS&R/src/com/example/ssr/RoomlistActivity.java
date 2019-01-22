
package com.example.ssr;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

public class RoomlistActivity extends Activity implements OnItemClickListener
{
	String age[] = new String[]{"A Room-[1]", "B Room-[2]", "C Room-[3]", "D Room-[4]", "E Room-[5]", "F Room-[6]", "G Room-[7]", "H Room-[8]"};
	
	public void onCreate(Bundle savedInstanceState) 
	{
	    super.onCreate(savedInstanceState);
	    this.setContentView(R.layout.activity_roomlist);
     
	    final ListView list = (ListView)findViewById(R.id.listView1);
	    final ScrollView sv = (ScrollView)findViewById(R.id.scrollView1);
	    final ArrayAdapter<String> adapter;
	    adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, age);
	    list.setAdapter(adapter);
        list.setOnItemClickListener(this);
       
        list.setOnTouchListener(new OnTouchListener() 
        {
        	public boolean onTouch(View v, MotionEvent event) 
        	{
        		sv.requestDisallowInterceptTouchEvent(true);
        		return false;
        	}
        });
        list.setOnItemClickListener(new OnItemClickListener(){   //방에 입장클릭

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				String str = (String)adapter.getItem(arg2);
				Toast.makeText(getBaseContext(), str, Toast.LENGTH_SHORT).show();
				list.setSelector(new PaintDrawable(0xFFFFFF00)); 
			}
        });
        Intent intent = this.getIntent();
	    String str = intent.getStringExtra("key1");
	    ActionBar ac_bar = getActionBar();
	    ac_bar.setSubtitle(str+" 님");
	}
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
	}
	public void btn_enter(View v)
	{
		Intent intent = new Intent(this, InRoomslaveActivity.class);
		this.startActivity(intent); 
	}
	public void create_room(View v)
	{
		Intent intent = new Intent(this, ExploreActivity.class);
		this.startActivity(intent);
	}
	public void change_name(View v)
	{
		final EditText ev = new EditText(this);
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("Name Change")
		.setMessage("Typing change Name")
		.setPositiveButton("Change", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				ActionBar ac_bar = getActionBar();
				String name = ev.getText().toString();
				if(name.length()>=4)
				{
					ac_bar.setSubtitle(name+" 님");
					Toast.makeText(getApplicationContext(), "Succeed!" , Toast.LENGTH_SHORT).show();
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Please enter more than four letters" , Toast.LENGTH_SHORT).show();
				}
					
			}
		})
		.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		})
		.setView(ev)
		.show();
	}
	public void search_room(View v)
	{
		final EditText ev2 = new EditText(this);
		AlertDialog.Builder dialog2 = new AlertDialog.Builder(this);
		dialog2.setTitle("Search")
		.setMessage("Typing search word")
		.setPositiveButton("Search", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				String search = ev2.getText().toString();
				int i,j;
				for(i=0;i<age.length;i++)
				{
					if(search.equals(age[i].substring(0, 1)))
					{
						j=i+1;
						Toast.makeText(getApplicationContext(), "["+j+"]"+"번 Room" , Toast.LENGTH_SHORT).show();
					}
					else if(search.equals(age[i].substring(0,2)))
					{
						j=i+1;
						Toast.makeText(getApplicationContext(), "["+j+"]"+"번 Room" , Toast.LENGTH_SHORT).show();
					}
					else if(search.equals(age[i].substring(0,3)))
					{
						j=i+1;
						Toast.makeText(getApplicationContext(), "["+j+"]"+"번 Room" , Toast.LENGTH_SHORT).show();
					}
					else if(search.equals(age[i].substring(0,4)))
					{
						j=i+1;
						Toast.makeText(getApplicationContext(), "["+j+"]"+"번 Room" , Toast.LENGTH_SHORT).show();
					}
					else if(search.equals(age[i].substring(0,5)))
					{
						j=i+1;
						Toast.makeText(getApplicationContext(), "["+j+"]"+"번 Room" , Toast.LENGTH_SHORT).show();
					}
					else if(search.equals(age[i].substring(0,6)))
					{
						j=i+1;
						Toast.makeText(getApplicationContext(), "["+j+"]"+"번 Room" , Toast.LENGTH_SHORT).show();
					}
					else 
					{
						
					}
				}
					
			}
		})
		.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		})
		.setView(ev2)
		.show();
	}
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.action, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	public boolean onOptionsItemSelected(MenuItem item)
	{
		
		switch(item.getItemId()){
			case R.id.action_help:
				new AlertDialog.Builder(this)
				.setTitle("Help")
				.setMessage("[1] Creat Room 을 누르시면 방을 생성할 수 있습니다.\n공유하고 싶은 파일을 선택한 후 Create 버튼을 누르세요!\n\n[2] Change Name을 누르면 대화명을 변경 할 수 있습니다.\n\n" +
						"[3] Search Room을 누르시면 원하는 방을 검색 할 수 있습니다.\n\n[4] 방 목록을 클릭하면 그 방에 들어가 방장의 화면을 공유 할 수 있습니다.\n\n마지막으로 저희 앱을 사용해 주셔서 감사합니다!")
				.setNeutralButton("Exit", new DialogInterface.OnClickListener() 
				{
					public void onClick(DialogInterface dialog, int which) 
					{
					}
				})
				.show();
				return true;
		}
		return super.onOptionsItemSelected(item);	
	}
}
