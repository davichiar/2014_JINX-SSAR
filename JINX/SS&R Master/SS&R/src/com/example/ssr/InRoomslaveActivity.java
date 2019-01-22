package com.example.ssr;

import java.io.*;
import java.net.Socket;

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

public class InRoomslaveActivity extends Activity {

	ImageView img1;
	ProgressDialog progres;
	NetThread nth = new NetThread();
	DisplayHandler h;
	byte[] buf = new byte[10000];
	Bitmap bit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	    this.setContentView(R.layout.activity_inroomslave);
        
        img1 = (ImageView)this.findViewById(R.id.imageView1);
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

    				int sizebuf;
    				int state = 0;
    				int i = 0;
	    			while(true){
	    				while(state != 3){	  //3이 아니면 계속 멈추게
			    	    	state = dis.readInt();//서버에서 보낼 준비가 되면 슬레이브에게 3을 보냄
		    	    		Thread.sleep(100);
		    	    	}
		    	    	state++;
		    	    	dos.writeInt(state);//state를 4로 바꾸고 서버에게 버퍼 보낼 준비 하라고 4를 보냄
		    	    	
		    			sizebuf = dis.readInt();
		    			buf = new byte[sizebuf];
		    			dis.readFully(buf);
		    			
		    			bit = byteArrayToBitmap(buf);
		    			h.sendEmptyMessage(0);
		    			Thread.sleep(1000);
		    			dos.writeInt(5);
		    			i++;
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
    public Bitmap byteArrayToBitmap( byte[] $byteArray ) {  
        Bitmap bitmap = BitmapFactory.decodeByteArray( $byteArray, 0, $byteArray.length ) ;  
        return bitmap ;  
    }

    class DisplayHandler extends Handler{

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			progres.cancel();
			img1.setImageBitmap(bit);
		}
		public void handleMessage_fail() {
			// TODO Auto-generated method stub
			progres.cancel();
			Toast.makeText(getApplication(), "Connect error", 3000);
		}    	
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}