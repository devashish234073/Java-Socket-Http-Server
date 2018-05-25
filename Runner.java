import api.*;

import java.io.IOException;

public class Runner {
    public static void main(String[] args) throws IOException {
        Mappings mappings = new Mappings();
        mappings.addMap("GET","/","./html/index.html");
        mappings.addMap("GET","/SAVE","./html/save.html");
        mappings.addMap("POST","/SAVE","./html/save.html");
        mappings.addMap("GET", "/dome", new AbstractResponse() {
            @Override
            public Response getResponse(Request req) {
                String res = "<html><body>";
                res+="Msg received:" + req.getAttribute("msg")+"<br>";
                res+="<a href='/'>Home</a>";
                res+="</body></html>";
                Response resp = new Response(res);
                return resp;
            }
        });
        Server server;
        while (1==1) {
            server = new Server(8888, mappings);
            Request req = server.accept();
            server.sendResponse(req);
            server.shut();
        }
    }
}
