import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;

public class ScreenServer {

	/**
	 * @practice server
	 */
	static byte[] buf;
	private static String getTime(){
		SimpleDateFormat fmt = new SimpleDateFormat("[yyyy-mm-dd hh:mm:ss");
		return fmt.format(new Date());
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			ServerSocket srv = new ServerSocket(9722);
			System.out.println(getTime()+"Server Wait..");
			Socket Msck = srv.accept();
			System.out.println(getTime()+"Master connected...");
			Socket Ssck = srv.accept();
			System.out.println(getTime()+"Slave connected...");

			InputStream mis = Msck.getInputStream();
			InputStream sis = Ssck.getInputStream();
			OutputStream mos = Msck.getOutputStream();
			OutputStream sos = Ssck.getOutputStream();

			DataInputStream mdis = new DataInputStream(mis);
			DataInputStream sdis = new DataInputStream(sis);
			DataOutputStream mdos = new DataOutputStream(mos);
			DataOutputStream sdos = new DataOutputStream(sos);
			int sizebuf;
			int state = 0;
			while(true){
				
				mdos.writeInt(1);	//�����Ϳ��� ���� ���� �غ� �϶�� 1�� ����
				while(state !=2){
					state = mdis.readInt();//�����Ͱ� ���� ���� �غ� �� �Ǹ� 2�� ����
					Thread.sleep(100);
				}
				sizebuf = mdis.readInt();//����Ʈ �迭�� ����
				buf = new byte[sizebuf];
				mdis.readFully(buf);
	
				sdos.writeInt(3);
				while(state !=4){
					state = sdis.readInt();
					Thread.sleep(100);
				}
				sdos.writeInt(buf.length);
				sdos.write(buf);
				
				state = sdis.readInt();
				while(state != 5)
					Thread.sleep(100);
				System.out.println(sizebuf);
			}

			//Msck.close();
			//Ssck.close();
			//srv.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
