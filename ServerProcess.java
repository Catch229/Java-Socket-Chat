/*
* Kolton Sumner
* Computer Networks
* Final Part A
* 4/13/2020
*/

import java.io.*;
import java.net.*;
import java.util.*;

class ServerProcess {
	
	public static class Session {
		InetAddress IPAddress;
		String username;
		String passwd;
		public Session(InetAddress IP){
			IPAddress = IP;
			username = null;
			passwd = null;
		} 
	}

    public static void main(String args[]) throws Exception {

        /*
			In the server process, a DatagramSocket object must also be instantiated 
  			and bound to a local port number which is 5000 in this case.  
         */
        DatagramSocket serverSocket = new DatagramSocket(5000);
        String msg = "Something went wrong.";
        ArrayList<Session> sessions = new ArrayList<>();
        ArrayList<String> user = new ArrayList<>();
        ArrayList<String> passwd = new ArrayList<>();
        user.add("Alice");
        passwd.add("123456");

        while (true) {

            byte[] receiveData = new byte[1024];
            byte[] sendData = new byte[1024];

            /*
                To receive data message sent to the server socket, the server process creates 
                DatagramPacket object which references a byte array and then calls a receive 
    			method in its DatagramSocket object to receive the message sent from the client process.  
             */
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);

            String sentence = new String(receivePacket.getData()).trim();

            /*
				Gets the IP address of the computer on which the client process is running
             */
            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();
            
            Session temp = new Session(IPAddress); //Create temp session object with IP
            
            if(sessionExists(sessions, IPAddress) == -1){ //Check if a session has started
                sessions.add(temp);
                msg = "Welcome to Keene State College Server, what is your username?";
            } else if (sessions.get(sessionExists(sessions, IPAddress)).username == null){ //Check if a username has been entered
                if (user.contains(sentence)){ //Verify user
                    sessions.get(sessionExists(sessions, IPAddress)).username = sentence;
                    msg = "Username is OK, what is your password?";
                } else {
                    msg = "Username is NOT OK, what is your username?";
                }
            } else if (sessions.get(sessionExists(sessions, IPAddress)).passwd == null){ //Check if a password has been entered
                if(user.indexOf(sessions.get(sessionExists(sessions, IPAddress)).username) == passwd.indexOf(sentence)){ //Verify password
                    sessions.get(sessionExists(sessions, IPAddress)).passwd = sentence;
                    msg = "Password is OK, you are now connected to the Keene network.";
                } else {
                    msg = "Password is NOT OK, what is your password?";
                }
            } else {
                msg = sentence.toUpperCase(); //Normal function once logged in
            }
            
			//Finally send the msg variable back to client
            sendData = msg.getBytes();  
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            serverSocket.send(sendPacket);

        }
    }
    
	//Help method to find sessions with given IPs
    public static int sessionExists(ArrayList<Session> list, InetAddress ip){
        for(int i = 0; i < list.size(); i++){
            if (list.get(i).IPAddress.equals(ip))
                return i;
        }
        return -1;
    }
}
