package api;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

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

                } catch (IOException ioe){

                }
                res=replaceRequestAttribute(res,req);
                Response resp = new Response(res);
                return resp;
            }
        });
    }

    private String replaceRequestAttribute(String res, Request req){
        res=res.replace("${}","");
        int indx=res.indexOf("${");
        while(indx>-1){
            int endIndx=indexOfAfter(res,"}", indx);
            if(endIndx>-1){
                String var=res.substring(indx+2,endIndx);
                res=res.replace("${"+var+"}", req.getAttribute(var));
            } else {
                break;
            }
            indx=res.indexOf("${");
        }
        return res;
    }

    private int indexOfAfter(String str,String toSearch,int after){
        str=str.substring(after);
        return after+str.indexOf(toSearch);
    }
}
