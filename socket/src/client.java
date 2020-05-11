import java.net.*;
import java.io.*;

public class client {
    //use thros IOException when use try/catch
    public static void main(String[] args) throws IOException {
        try{
            Socket socket = new Socket("127.0.0.1",4444); //init socket of client
            DataInputStream input = new DataInputStream(socket.getInputStream()); //open inputStream to socket
            DataOutputStream output = new DataOutputStream(socket.getOutputStream()); //outputStream to socket
            BufferedReader buff = new BufferedReader(new InputStreamReader(System.in)); //read text from inputStream which's better performance
            String clientQuery="", serverQueryResponse="";
            //if query == "exit" client leave server
            while(!clientQuery.equals("exit")){
                System.out.print("Name of city/province to query lottery result: "); //need fix to use with vietnamese
                clientQuery = buff.readLine(); //read line by line
                output.writeUTF(clientQuery);
                output.flush(); //flushes the outputStream and forces any buffered output bytes to be written out
                serverQueryResponse = input.readUTF();
                System.out.println(serverQueryResponse);
            }
            //close socket of client
            output.close();
            socket.close();
        } catch(Exception e){
            //if error
            System.out.println(e);
        }
    }
}