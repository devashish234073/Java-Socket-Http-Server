package api;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public final class Server{

    private int PORT;
    private ServerSocket server;
    private Socket client;
    private Mappings mappings;

    public Server(int PORT, Mappings mappings) throws IOException {
        this.PORT = PORT;
        server = new ServerSocket(PORT);
        this.mappings = mappings;
    }

    public Request accept() throws IOException {
        client = server.accept();
        InputStream is=client.getInputStream();
        int c;
        String raw = "";
        String last4="";
        for(c=is.read();c!=-1;c=is.read()){
            raw+=(char)c;
            last4+=(char)c;
            if(last4.length()>4){
                last4=last4.substring(1);
            }
            if(last4.equals("\r\n\r\n")){
                int indx=raw.toLowerCase().indexOf("content-length:");
                if(indx>-1){
                    String tmp=raw.substring(indx+15);
                    int endIndx=tmp.indexOf("\r\n");
                    if(endIndx>-1){
                        try{
                            int len = Integer.parseInt(tmp.substring(0,endIndx).trim());
                            while(len>0){
                                raw+=(char)is.read();
                                len--;
                            }
                            break;
                        } catch(NumberFormatException nfe){
                            break;
                        }
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        Request request = new Request(raw);
        return request;
    }

    public void shut() throws IOException {
        server.close();
    }

    private Response getResponse(Request req) {
        AbstractResponse respAbs = mappings.getMap(req.getMethod()+"_"+req.getUrl());
        if(respAbs == null) {
            return new Response("<html><body><font color='red' size='2'>Invalid URL/method</font><br>URL: "+ req.getUrl() +"<br>method: "+ req.getMethod() +"</body></html>");
        }
        Response resp = respAbs.getResponse(req);
        return resp;
    }

    public void sendResponse(Request req) throws IOException {
        Response resp = getResponse(req);
        OutputStream out = client.getOutputStream();
        out.write(resp.toString().getBytes());
    }

}
