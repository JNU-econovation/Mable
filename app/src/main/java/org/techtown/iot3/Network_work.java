package org.techtown.iot3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Network_work {

    public String getStringFromInputStream(InputStream in) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line ;

        try{
            br = new BufferedReader(new InputStreamReader(in));
            while((line=br.readLine())!=null) sb.append(line);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(br!=null){
                try{
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}
