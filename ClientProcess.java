import java.io.*; 
import java.net.*; 
  
class ClientProcess { 
    public static void main(String args[]) throws Exception 
    { 
    	while(true){
   	System.out.print("User  : ");  		
    		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));       	
      	/*
      		Create a client socket
      	*/
      	DatagramSocket clientSocket = new DatagramSocket(); 
      	
      	/*
    		Gets the IP address of the computer on which the other process in running
			in this case, it's localhost because both two processes (client and server)
      		are running on the same local host
      	*/
      	InetAddress IPAddress = InetAddress.getByName("localhost"); 
      	
      	byte[] sendData = new byte[1024]; 
      	byte[] receiveData = new byte[1024]; 
  
  			/*
  				the data message we will send is from the user input
  			*/
  			String sentence = inFromUser.readLine(); 
      	sendData = sentence.getBytes();         

				/*      	
      		Creates an object that represents the datagram message itself.  
      		This object can be created by instantiating a DatagramPacket 
      		object which carries the payload data as a reference to a byte array, 
      		and the destination address (the host ID and port number to which 
      		the receivers socket is bound).
				*/
      	DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 5000); 

  			/*
  				Call send method to send the data message to the other process 
  				according to its port number and IP address
  			*/
      	clientSocket.send(sendPacket); 
  
      	/*
  				Create an receivePacket object to receive data message sent from the other process
  			*/
  			
      	DatagramPacket receivePacket =  new DatagramPacket(receiveData, receiveData.length); 
      	clientSocket.receive(receivePacket); 
  
      	String modifiedSentence =  new String(receivePacket.getData()).trim(); 
				/*
					Prints the message sent from the other process
				*/
      	System.out.println("\nServer: " + modifiedSentence + "\n"); 
				/*
					Closes the communication channel of message passing in between two processes
				*/
      	clientSocket.close(); 
			}
    }
} 
