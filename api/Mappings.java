package api;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

public class Mappings{
    HashMap<String, AbstractResponse> urlMappings;
    public Mappings(){
        urlMappings = new HashMap<String, AbstractResponse>();
    }

    protected AbstractResponse getMap(String key){
        return urlMappings.get(key);
    }

    public void addMap(String method, String url, AbstractResponse resp){
        urlMappings.put(method+"_"+url,resp);
    }

    public void addMap(String method, String url, String filepath) throws IOException {
        urlMappings.put(method+"_"+url,new AbstractResponse(){
            @Override
            public Response getResponse(Request req) {
                String res = "";
                try{
                    FileReader fr = new FileReader(filepath);
                    int c;
                    for(c=fr.read();c!=-1;c=fr.read()){
                        res+=(char)c;
                    }
                } catch (FileNotFoundException fnfe){
                    return new Response("<html><body>Unable to find resource ["+url+"]</body></html>");
                } catch (IOException ioe){
                    return new Response("<html><body>Unable to read resource ["+url+"]</body></html>");
                }
                res=replaceRequestAttribute(res,req);
                Response resp = new Response(res);
                return resp;
            }
        });
    }

    private String replaceRequestAttribute(String res, Request req){
        Iterator itr = req.getAttributeIterator();
        while(itr.hasNext()) {
            String key = (String) itr.next();
            String val = req.getAttribute(key);
            res = res.replace("${"+key+"}",val);
        }
        return res;
    }

    private int indexOfAfter(String str,String toSearch,int after){
        str=str.substring(after);
        return after+str.indexOf(toSearch);
    }
}
