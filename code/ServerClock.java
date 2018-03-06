import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class ServerClock {

//	private static final char[][] Boolean = null;

	public static void main(String[] args) throws Exception 
	{
		ServerSocket serversocket = null;
		Socket s1 = null,s2 = null,s3 = null,s4 = null;
		
			//Intializing the server counter variable for storing the server counter value
			int ServerCounter = 0;
			
			//Array
			int[]ClientCounter = new int[4];
			for(int g = 0 ; g < 4 ; g++)
			{
				ClientCounter[g]=0;
			}
			//array
			int[] ClientCounterEncrpyted = new int[4]; 
			for(int g = 0 ; g < 4 ; g++)
			{
				ClientCounterEncrpyted[g]=0;
			}
			
			//Offset
			int Offset;
			//Creating four string variables  for reading the message from client
		//	String ClientSMS1 = " " ,ClientSMS2 = " ",ClientSMS3 = " ", ClientSMS4 =" ";
			
			//Average
			int average = 0;
		try
		{
			//Creating the server socket
			 serversocket =new ServerSocket(1377);
			 Thread.sleep(500);
			
			
			//Printing starting the server
			System.out.println("********************Server Started********************");
			
			//Creating four sockets for accepting the connections from four different clients
			
			
			System.out.println("Waiting for clients");
			//server accept the each clients connection request at different sockets created
			s1=serversocket.accept();
			System.out.println("********************Client 1 started********************");
			
			s2=serversocket.accept();
			System.out.println("********************Client 2 started********************");

			s3=serversocket.accept();
			System.out.println("********************Client 3 started********************");

			s4=serversocket.accept();
			System.out.println("********************Client 4 started********************");

			
			//Checking if all the clients are connected or not
			if(s1 == null || s2 == null || s3 == null || s4 == null)
				System.out.print("not connected");
			
			System.out.print("connected");
			// instantiating the four BufferedReader objects for four clients
			BufferedReader Buffer1 = new BufferedReader(new InputStreamReader(s1.getInputStream()));
		    BufferedReader Buffer2 = new BufferedReader(new InputStreamReader(s2.getInputStream()));
		    BufferedReader Buffer3 = new BufferedReader(new InputStreamReader(s3.getInputStream()));
		    BufferedReader Buffer4 = new BufferedReader(new InputStreamReader(s4.getInputStream()));

		    // instantiating the four PrintWriter objects for four clients
		    PrintWriter Print1 = new PrintWriter(s1.getOutputStream(), true);
		    PrintWriter Print2 = new PrintWriter(s2.getOutputStream(), true);
		    PrintWriter Print3 = new PrintWriter(s3.getOutputStream(), true);
		    PrintWriter Print4 = new PrintWriter(s4.getOutputStream(), true);
			    
		    //Sending the meassage signal to all the clients which are connected to send the value of their counters 
		    Print1.println("CounterValue");
		    Print2.println("CounterValue");
		    Print3.println("CounterValue");
		    Print4.println("CounterValue");
			System.out.println("Server is Running now");
			    
		    //while(true)
			for(int l=0;l<10000;l++)
		    {
		   	//Printing the servers original counter value
				System.out.print("Original Server Counter: "+ServerCounter);
                //Creating four string variables  for reading the message from client
				String[] ClientSMS = new String[4];
		    	//Reading the encrypted values at the server
		    	//ClientSMS1 = Buffer1.readLine();
				System.out.print("a");
				ClientSMS[0] = Buffer1.readLine();
		     	System.out.print("c");
		    	ClientSMS[1] = Buffer2.readLine();
		    	//System.out.print("Client2"+ClientSMS[1]);
		    	ClientSMS[2] = Buffer3.readLine();
		    	//System.out.print("Client3"+ClientSMS[2]);
		    	ClientSMS[3] = Buffer4.readLine();
		    	//System.out.print("Client4"+ClientSMS[3]);
		
		    	boolean[] sendCounterValueToUser = new boolean[4];
		        Arrays.fill(sendCounterValueToUser, false);
		        boolean recievingCounterValueFromClient = false;
		        System.out.println("Upto boolean values");
		        for(int i = 0 ; i < 4 ; i++)
			        {
			        if(ClientSMS[i]!=null)
			        {
			        	if(ClientSMS[i].equals("RECEIVE"))
			        	{
			        		sendCounterValueToUser[i] = true;
			        		recievingCounterValueFromClient = true;
			       		}
			       		else
			       		{
			       			ClientCounterEncrpyted[i] = Integer.parseInt(ClientSMS[i]);
			        			
			       			//Decrypting the message using keys
			       			ClientCounter[i] = ClientCounterEncrpyted[i] - 39;
							System.out.println("Clock value received from client"+ClientCounter[i]);

		        		}
		        	}
			        }
			       	if(recievingCounterValueFromClient)
			       	{
			       		for(int j = 0; j < 4 ; j++)
			       		{
			       			System.out.println("Counter value at client"+(j+1)+":"+ClientCounter[j]);
		        		}
			       		average = (int)(ClientCounter[0] + ClientCounter[1] +  ClientCounter[2] + ClientCounter[3] +ServerCounter)/5;
			       		ServerCounter = average;
			       		System.out.println("Updated server value :"+ServerCounter);
			       	}
			        
			       	if(sendCounterValueToUser[0])
		        	{
		        		//Finding the offset
						Offset =  average - ClientCounter[0];
						//System.out.println("offset :"+Offset);
						int EncryptedOffset = Offset + 39;
						Print1.println(""+EncryptedOffset);
		       		}
		        	if(sendCounterValueToUser[1])
		        	{
		        		//Finding the offset
						Offset =  average - ClientCounter[1];
						//System.out.println("offset :"+Offset);
						int EncryptedOffset = Offset + 39;
						Print2.println(""+EncryptedOffset);
		       		}
			       	if(sendCounterValueToUser[2])
			       	{
			       		//Finding the offset
						Offset =  average - ClientCounter[2];
						//System.out.println("offset :"+Offset);
						int EncryptedOffset = Offset + 39;
						Print3.println(""+EncryptedOffset);
			   		}
		        	if(sendCounterValueToUser[3])
		        	{
			       		//Finding the offset
						Offset =  average - ClientCounter[3];
						//System.out.println("offset :"+Offset);
						int EncryptedOffset = Offset + 39;
						Print4.println(""+EncryptedOffset);
			   		}
		        	System.out.println("_------------------------------------------------------------------");
		        	ServerCounter = ServerCounter + 1;
		        	System.out.println("Updated server counter value :"+ServerCounter);
			    }
		    
 	
		}catch(Exception e)
		{
			System.out.println(e);
		}//end of try-catch exception
	}//End of main

}//End of class
