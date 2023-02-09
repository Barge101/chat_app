import java.net.*;
import java.io.*;

public class Client {
    private boolean status=false;
    Socket socket;
    BufferedReader br;
    PrintWriter out;

    public Client(){
        try {
            System.out.println("Sending request to server..");
            socket = new Socket("192.168.56.1",7777);
            System.out.println("Connection done..");


            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWritting();

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void startReading(){
       
        Runnable r1 = () -> {

            System.out.println("Reader started...");
            try{
                while (true) {
                        String msg = br.readLine();
                        if (msg.equals("exit")) {
                            System.out.println("Server terminated the chat");
                            status = true;
                            break;
                        }
                        System.out.println("Server: "+ msg);
                    
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        };

        new Thread(r1).start();
    }
    public void startWritting(){
        Runnable r2 = ()->{
            System.out.println("writer started...");
            try{
                while (true) {

                        if (status) {
                            break;
                        }
                        BufferedReader br1 = new BufferedReader
                        (new InputStreamReader(System.in));
                        String content = br1.readLine();
                        out.println(content);
                        out.flush();
                        if(content.equals("exit")) break;
                
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {
        System.out.println("this is client....");
        new Client();
    }
}
