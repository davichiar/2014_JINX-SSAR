package com.example.ssr;

import java.io.*;
import java.net.Socket;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class InRoomActivity extends Activity {

	ImageView img1;
	ProgressDialog progres;
	NetThread nth = new NetThread();
	DisplayHandler h;
	byte[] buf = new byte[10000];
	Bitmap bit;
	int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	    this.setContentView(R.layout.activity_inroom);
        
        img1 = (ImageView)this.findViewById(R.id.imageView1);
        img1.setImageResource(R.drawable.test);
        h = new DisplayHandler();
    	progres = ProgressDialog.show(this, null, "wating...");
    	nth.start();
    }

    
    class NetThread extends Thread{
    	public void run(){
	    		try{
	    			//socket생성
	    			Socket sck = new Socket("220.149.119.118", 9722);
	    			InputStream is = sck.getInputStream();
	    			OutputStream os = sck.getOutputStream();
	    			DataInputStream dis = new DataInputStream(is);
	    			DataOutputStream dos = new DataOutputStream(os);

	    			h.sendEmptyMessage(0);

	    			int state = 0;
		    		while(true){
		    			state = 0;
		    	    	while(state != 1){	  //1이 아니면 계속 멈추게
			    	    	state = dis.readInt();//서버에서 받을 준비가 되면 마스터에게 1을 보냄
		    	    		Thread.sleep(100);
		    	    	}
		    	    	state++;
		    	    	dos.writeInt(state);//state를 2로 바꾸고 서버에게 버퍼 받을 준비 하라고 2를 보냄
		    	    	
		    			dos.writeInt(buf.length);//사진 크기와 정보를 보냄
		    			dos.write(buf);
		    			i++;
		    			Thread.sleep(3000);
		    			h.sendEmptyMessage(0);
		    			if(i == 4)
		    				break;
		    		}
	    			//socket 해제
		    		sck.close();
	    		}catch(Exception e){
	    			e.printStackTrace();
	    			h.handleMessage_fail();
	    		}
    	}
    }
    public byte[] bitmapToByteArray( Bitmap $bitmap ) {  
        ByteArrayOutputStream stream = new ByteArrayOutputStream() ;  
        $bitmap.compress( Bitmap.CompressFormat.JPEG, 50, stream) ;  
        byte[] byteArray = stream.toByteArray();
        return byteArray ;  
    }  

    class DisplayHandler extends Handler{

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			progres.cancel();
			if(i == 1)
				img1.setImageResource(R.drawable.test3);
			else if(i == 2)
				img1.setImageResource(R.drawable.test2);
	    	bit = takeScreenshot();
			buf = bitmapToByteArray(bit);
		}
		public void handleMessage_fail() {
			// TODO Auto-generated method stub
			progres.cancel();
			Toast.makeText(getApplication(), "Connect error", 3000);
		}
    	
    }
    
    public Bitmap takeScreenshot() {
  	   View rootView = findViewById(android.R.id.content).getRootView();
  	   rootView.setDrawingCacheEnabled(true);
  	   return rootView.getDrawingCache();
  	}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
