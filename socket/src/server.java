import java.net.*;
import java.net.ServerSocket;
import java.io.*;
import java.util.Arrays;
import java.util.List;

public class server {
    //use thros IOException when use try/catch
    public static void main(String[] args) throws IOException {
        try{
            ServerSocket server = new ServerSocket(4444); //init socket of server
            int num_of_client = 0; //number of client connect to server
            System.out.println("Creating server....");
            while(true){
                num_of_client++;
                Socket serverClient = server.accept(); //accept client connect to server
                System.out.println("Client " + num_of_client + " connected to server");
                ServerMultiClient SCThread = new ServerMultiClient(serverClient, num_of_client);
                SCThread.start();
            }
        } catch(Exception e){
            //if error
            System.out.println(e);
        }
    }
}

class ServerMultiClient extends Thread {
    Socket serverClient;
    int clientNo;
    ServerMultiClient(Socket inSocket, int num_of_client){
        serverClient = inSocket;
        clientNo = num_of_client;
    }
    //run() use for any application uses thread
    public void run(){
        try{
            DataInputStream inStream = new DataInputStream(serverClient.getInputStream()); //open inputStream to socket
            DataOutputStream outStream = new DataOutputStream(serverClient.getOutputStream()); //outputStream to socket
            String clientQuery="", serverQueryResponse="";
            //if query == "exit" client leave server
            while(!clientQuery.equals("exit")){
                clientQuery = inStream.readUTF();
                System.out.println("Received query from client " + clientNo + ": " + clientQuery);
                /*data for query*/
                /*String help = "h", all_contry = "all";
                String[] specific_city = {"HaNoi", "DaNang", "DongNai", "BienHoa", "VungTau", "DaLat", "BinhDinh", "QuangNam", "GiaLai", "BinhDuong"};
                if(clientQuery.equals(help)) serverQueryResponse = "Response from server to client" + clientNo + ": please use query all for the weather of all city/province or input name of city/province to query";
                else if(clientQuery.equals(all_contry)) serverQueryResponse = "Response from server to client" + clientNo + ": \nHaNoi: 30 do C, Nang\nDaNang: 29 do C, Mua\nDongNai: 34 do C, Nang\nBienHoa: 27 do C, Mua\nVungTau: 29 do C, Nang\nDaLat: 24 do C, Mua\nBinhDinh: 23 do C, Mua\nQuangNam: 33 do C, Nang\nGiaLai: 26 do C, Nang\nBinhDuong: 32 do C, Nang";
                else if(clientQuery.equals(specific_city[0])) serverQueryResponse = "Response from server to client" + clientNo + ": \nHaNoi: 30 do C, Nang";
                else if(clientQuery.equals(specific_city[1])) serverQueryResponse = "Response from server to client" + clientNo + ": \nDaNang: 29 do C, Mua";
                else if(clientQuery.equals(specific_city[2])) serverQueryResponse = "Response from server to client" + clientNo + ": \nDongNai: 34 do C, Nang";
                else if(clientQuery.equals(specific_city[3])) serverQueryResponse = "Response from server to client" + clientNo + ": \nBienHoa: 27 do C, Mua";
                else if(clientQuery.equals(specific_city[4])) serverQueryResponse = "Response from server to client" + clientNo + ": \nVungTau: 29 do C, Nang";
                else if(clientQuery.equals(specific_city[5])) serverQueryResponse = "Response from server to client" + clientNo + ": \nDaLat: 24 do C, Mua";
                else if(clientQuery.equals(specific_city[6])) serverQueryResponse = "Response from server to client" + clientNo + ": \nBinhDinh: 23 do C, Mua";
                else if(clientQuery.equals(specific_city[7])) serverQueryResponse = "Response from server to client" + clientNo + ": \nQuangNam: 33 do C, Nang";
                else if(clientQuery.equals(specific_city[8])) serverQueryResponse = "Response from server to client" + clientNo + ": \nGiaLai: 26 do C, Nang";
                else if(clientQuery.equals(specific_city[9])) serverQueryResponse = "Response from server to client" + clientNo + ": \nBinhDuong: 32 do C, Nang";
                else serverQueryResponse = "Response from server to client" + clientNo + ": Name of city/province of your query is not available in our list, list of city/province is supported by server: HaNoi, DaNang, DongNai, BienHoa, VungTau, DaLat, BinhDuong, QuangNam, GiaLai, BinhDuong";*/

                //check digit
                int digit = 0;
                for(int i = 0; i < clientQuery.length(); i++){
                    if(Character.isDigit(clientQuery.charAt(i))) {
                        digit++;
                    }
                }

                String notDigitReward = clientQuery.replaceAll("[^0-9]+", " ");
                //check digit
                String help = "h";
                String[] specific_city = {"HaNoi", "LongAn", "DongNai"};
                String[] reward_of_city1 = {"HaNoi 123456", "HaNoi 12345", "HaNoi 1234", "HaNoi 123", "HaNoi 12"};
                String[] reward_of_city2 = {"LongAn 654321", "LongAn 65432", "LongAn 6543", "LongAn 654", "LongAn 65"};
                if(clientQuery.equals(help)) serverQueryResponse = "Response from server to client" + clientNo + ": please input name of city/province or city/province<space>number ticker";
                else if(clientQuery.equals(specific_city[0])) serverQueryResponse = "Response from server to client" + clientNo + ": \nLottery result of HaNoi\nSpecial reward: 123456\nFirst reward: 12345\nSecond reward: 1234\nThird reward: 123\nFourth reward: 12";
                else if(clientQuery.equals(specific_city[1])) serverQueryResponse = "Response from server to client" + clientNo + ": \nLottery result of LongAn\nSpecial reward: 654321\nFirst reward: 65432\nSecond reward: 6543\nThird reward: 654\nFourth reward: 65";
                else if(clientQuery.equals(specific_city[2])) serverQueryResponse = "Response from server to client" + clientNo + ": \nLottery of DongNai don't open today";

                else if(clientQuery.equals(reward_of_city1[0])) serverQueryResponse = "Response from server to client" + clientNo + ": You won special reward with 100,000,000 VND";
                else if(clientQuery.equals(reward_of_city1[1])) serverQueryResponse = "Response from server to client" + clientNo + ": You won first reward with 10,000,000 VND";
                else if(clientQuery.equals(reward_of_city1[2])) serverQueryResponse = "Response from server to client" + clientNo + ": You won second reward with 1,000,000 VND";
                else if(clientQuery.equals(reward_of_city1[3])) serverQueryResponse = "Response from server to client" + clientNo + ": You won third reward with 100,000 VND";
                else if(clientQuery.equals(reward_of_city1[4])) serverQueryResponse = "Response from server to client" + clientNo + ": You won forth reward with 50,000 VND";

                else if(clientQuery.equals(reward_of_city2[0])) serverQueryResponse = "Response from server to client" + clientNo + ": You won special reward with 100,000,000 VND";
                else if(clientQuery.equals(reward_of_city2[1])) serverQueryResponse = "Response from server to client" + clientNo + ": You won first reward with 10,000,000 VND";
                else if(clientQuery.equals(reward_of_city2[2])) serverQueryResponse = "Response from server to client" + clientNo + ": You won second reward with 1,000,000 VND";
                else if(clientQuery.equals(reward_of_city2[3])) serverQueryResponse = "Response from server to client" + clientNo + ": You won third reward with 100,000 VND";
                else if(clientQuery.equals(reward_of_city2[4])) serverQueryResponse = "Response from server to client" + clientNo + ": You won forth reward with 50,000 VND";


                //else serverQueryResponse = "Response from server to client" + clientNo + ": Name of city/province of your query is not available in our list";

                else{
                    if(clientQuery.contains(" ")){
                        if(!(clientQuery.equals(reward_of_city1[0]) || clientQuery.equals(reward_of_city1[1]) || clientQuery.equals(reward_of_city1[2]) || clientQuery.equals(reward_of_city1[3]) || clientQuery.equals(reward_of_city1[4])) && digit == 6)
                            serverQueryResponse = "Response from server to client" + clientNo + ": Name of city/province of your query is not available in our list";
                        else if(!(clientQuery.equals(reward_of_city1[0]) || clientQuery.equals(reward_of_city1[1]) || clientQuery.equals(reward_of_city1[2]) || clientQuery.equals(reward_of_city1[3]) || clientQuery.equals(reward_of_city1[4])) && digit < 6)
                            serverQueryResponse = "Response from server to client" + clientNo + ": Your ticket lottery must have 6 digits";
                        else if((clientQuery.equals(reward_of_city1[0]) || clientQuery.equals(reward_of_city1[1]) || clientQuery.equals(reward_of_city1[2]) || clientQuery.equals(reward_of_city1[3]) || clientQuery.equals(reward_of_city1[4])) && digit < 6)
                            serverQueryResponse = "Response from server to client" + clientNo + ": Your ticket lottery must have 6 digits";
                    }
                    //else serverQueryResponse = "Response from server to client" + clientNo + ": Name of city/province of your query is not available in our list";
                    else if(!clientQuery.contains(" ")){
                        if(!(clientQuery.equals(specific_city[0]) || clientQuery.equals(specific_city[1]) || clientQuery.equals(specific_city[2])))
                            serverQueryResponse = "Response from server to client" + clientNo + ": Name of city/province of your query is not available in our list";
                    }

                    else if(notDigitReward != "123456" || notDigitReward != "12345" || notDigitReward != "1234" || notDigitReward != "123" || notDigitReward != "12")
                        serverQueryResponse = "Response from server to client" + clientNo +": Good luck";
                    ////
                }
                /*data for query*/
                outStream.writeUTF(serverQueryResponse);
                outStream.flush(); //flushes the outputStream and forces any buffered output bytes to be written out
            }
            inStream.close();
            outStream.close();
            serverClient.close();
        } catch(Exception e){
            System.out.println(e);
        } finally{
            System.out.println("Client " + clientNo + " left server");
        }
    }
}