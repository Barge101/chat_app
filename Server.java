import java.io.*;
import java.net.*;

class Server{
    private boolean status=false;
    ServerSocket server;
    Socket socket;
    
    BufferedReader br;
    PrintWriter out;

    public Server(){
        try{
            server = new ServerSocket(7777);
            System.out.println("server is ready to accept connection");
            System.out.println("waiting.....");
            socket=server.accept();

            br = new BufferedReader(new InputStreamReader(socket.
            getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWritting();
        }
        catch(Exception e){
            e.printStackTrace();
        }   
    }
    public void startReading(){
       
        Runnable r1 = ()->{

            System.out.println("Reader started...");
            try{
                while (true) {  
                        String msg = br.readLine();
                        if (msg.equals("exit")) {
                            System.out.println("Client terminated the chat");
                            status = true;
                            socket.close();
                            break;
                        }
                        System.out.println("Client: "+ msg);
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
            try {
                while (true) {
                        if (status) {
                            break;
                        }
                        BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
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
        System.out.println("this is server..going to start server");
        new Server();
    }
}