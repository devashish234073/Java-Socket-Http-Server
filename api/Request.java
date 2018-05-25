package api;

import java.util.HashMap;

public final class Request {

    private String req = null;
    private String method = null;
    private String url = null;
    private String httpVersion = null;
    private HashMap<String,String> attributes;

    public Request(String req) {
        this.req = req;
        attributes = new HashMap<String, String>();
        parse();
    }

    private void parse(){
        String[] temp = req.split("\r\n");
        String firstLine = temp[0];
        String[] firstLineSplit = firstLine.split(" ");
        if(firstLineSplit.length==3){
            method = firstLineSplit[0];
            httpVersion = firstLineSplit[2];
            if(method.equals("POST")){
                url = firstLineSplit[1];
                setAttributes(temp[temp.length-1]);
            } else if(method.equals("GET")){
                String[] arr=firstLineSplit[1].split("[?]");
                if(arr.length==2){
                    url=arr[0];
                    setAttributes(arr[1]);
                } else {
                    url = firstLineSplit[1];
                }
            } else {
                url = firstLineSplit[1];
            }
        }
    }

    private void setAttributes(String rawAttributes) {
        String[] attribs=rawAttributes.split("&");
        for(int i=0;i<attribs.length;i++){
            String[] attr=attribs[i].split("=");
            if(attr.length==2){
                setAttribute(attr[0],attr[1].replace("+"," "));
            }
        }
    }

    public String getAttribute(String key) {
        String ret = attributes.get(key);
        if(ret == null) {
            return "null";
        }
        return ret;
    }

    public void setAttribute(String key, String value) {
        attributes.put(key, value);
    }

    public String getMethod() {
        return method;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public String getUrl() {
        return url;
    }

    public String toString(){
        return req;
    }
}