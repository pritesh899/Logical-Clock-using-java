import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
public class ClientClock 
{

	public static void main(String[] args) 
	{
		//variable for client counter
		int ClientCounter ;
				
		//variable for sending the encrypted counter
		int EcryptedClientCounter;
		
		//variable for receiving the encrypted offset
		int EcryptedOffset;
		
		//variable for storing the offset received from the server
		int offset;
		
		//String variable for server response
		String ReponseFromServer;
		
		//Boolean value
		boolean T = true;
		
		PrintWriter Print = null;
		Socket s =  null;
				 
		try
		{
			//Creating and connecting the server using IP address and port of the server
			s=new Socket("10.234.136.55",1377);

			// instantiating the four BufferedReader objects for four clients
			BufferedReader Buffer = new BufferedReader(new InputStreamReader(s.getInputStream()));
				
			// instantiating the four PrintWriter objects for four clients
		     Print = new PrintWriter(s.getOutputStream(), true);
			    
			//Initializing the client counter
			ClientCounter = 0;
					
			
			//For keeping the track of iterations
	
			System.out.println("Recieved");
			while(T)
			{
				ReponseFromServer = Buffer.readLine();
				if( ReponseFromServer.equals("CounterValue"))
				{	
					System.out.println("Recieved");
					T =false;
					break;
				}
			}
			System.out.print("Recieved12");
			//while(true)
			for(int l=0;l<10000;l++)
			{
				
					//Printing the clients original counter 
					System.out.println("Client Counter orginal :"+ClientCounter);
					
					//Generating the random number to trigger send, receive, internal and byzantine failure events
					//Random random = new Random();
					//int RandomNumber = random.nextInt(100);
					float RandomNumber = new Random().nextFloat();

					//Send Event
					if(RandomNumber >= 0.00 && RandomNumber <0.40)
					{
						EcryptedClientCounter = ClientCounter + 39;
						Print.println(""+EcryptedClientCounter);
						System.out.println("Send event");
						
					}
					else if(RandomNumber >= 0.40 && RandomNumber < 0.80)//Receive Event
					{
						Print.println("RECEIVE");
						EcryptedOffset = Integer.parseInt( Buffer.readLine());
						offset = EcryptedOffset - 39;
						ClientCounter = ClientCounter + offset;
						System.out.println("Updated counter at client :" +ClientCounter);
						System.out.println("receive event");
					}
					else if(RandomNumber >= 0.80 && RandomNumber < 0.95)
					{
						System.out.println("Internal Event");
						Thread.sleep(500);//wait for 5 msec
					}
					else if(RandomNumber >= 0.95 && RandomNumber <= 1.00)
					{
						// I am incrementing clock value by 50
						System.out.println("Byzantine Behavior");
						ClientCounter = ClientCounter + 10;
						System.out.println("Clients clock value updated after byzantine behavior:"+ClientCounter);	
					}
					ClientCounter = ClientCounter + 2;
					System.out.println("Clients clock updated value :"+ClientCounter);	
					System.out.println("-------------------------------------------------------");
			}//End of while loop 
		}catch(Exception e)
		{
					System.out.println(e);
		}//End of try catch
	}//End of main		
		

}//End of class
