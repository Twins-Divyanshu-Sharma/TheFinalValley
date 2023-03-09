package network;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.lwjgl.util.vector.Vector3f;



public class UDPClient implements UDPThing{
   public boolean[] keys = new boolean[65536];

	public DatagramSocket clientSocket;
	public InetAddress IPAddress;
	public int port=7777;

	public float offset=0.008f; 
    public int x;
	
    public String ip = "192.168.137.1";
    
	public byte[] outData;
    public byte[] inData;

    public boolean go = true;
    public float outRotX, outRotY, outRotZ, outSpeed;
    public float inRotX, inRotY, inRotZ ,inSpeed;
    public Vector3f outPosition=new Vector3f(0,0,0), inPosition=new Vector3f(0,0,0);
    
	public UDPClient() throws SocketException, UnknownHostException{
		clientSocket = new DatagramSocket(port);
		IPAddress = InetAddress.getByName(ip);
	//	clientSocket.connect(IPAddress,port);
	}
	
	public void shutdown(){
		go = false;
		clientSocket.close();
	}
	
	public void setOutput(Vector3f position, float rotX, float rotY, float rotZ, float speed){

		this.outPosition.x = position.x;
		this.outPosition.y = position.y;
		this.outPosition.z = position.z;

		this.outRotX = rotX;
		this.outRotY = rotY;
		this.outRotZ = rotZ;
		
		this.outSpeed = speed;
	}
	
	
	public void start() {

		inData = new byte[1024];
		outData = new byte[1024];
		
		new Thread(new Send(this)).start();
		new Thread(new Recieve(this)).start();
	}
	
	public boolean isKeyPressed(int keyCode){
		return keys[keyCode];
	}

	@Override
	public boolean go() {
		// TODO Auto-generated method stub
		return go;
	}

	@Override
	public byte[] getOutData() {
		// TODO Auto-generated method stub
		String sentence = outPosition.x+"_"+outPosition.y+"_"+outPosition.z+"_"+outRotX+"_"+outRotY+"_"+outRotZ+"_"+outSpeed+"_"+"end";
		outData = sentence.getBytes();
       return outData;
	}

	@Override
	public void setInData(DatagramPacket in) {
		// TODO Auto-generated method stub
		String modifiedSentence = new String(in.getData());
		 String[] inputs = modifiedSentence.split("_");
		    inPosition.x = (float)Float.valueOf(inputs[0]);
		    inPosition.y = (float)Float.valueOf(inputs[1]);
		    inPosition.z = (float)Float.valueOf(inputs[2]);
		    inRotX = (float)Float.valueOf(inputs[3]);
		    inRotY = (float)Float.valueOf(inputs[4]);
		    inRotZ = (float)Float.valueOf(inputs[5]);
		    inSpeed = (float)Float.valueOf(inputs[6]);
		    
	}
	
	public InetAddress getIPAddress(){
		return IPAddress;
	}
	
	public int getPort(){
		return port;
	}

	@Override
	public DatagramSocket getSocket() {
		return this.clientSocket;
	}

	
}