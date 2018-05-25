package api;

import java.util.Date;

public final class Response {

    private String resp = null;

    public Response(String resp){
        Date date = new Date();
        String start = "HTTP/1.1 200 OK\r\n";
        String header = "Date: "+date.toString()+"\r\n";
        header+= "Content-Type: text/html\r\n";
        header+= "Content-length: "+resp.length()+"\r\n";
        header+="\r\n";
        this.resp=start+header+resp;
    }

    public String toString(){
        return resp;
    }
}