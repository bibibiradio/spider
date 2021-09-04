package xm.bibibiradio.warphttpsender;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;

public class HeaderParser {
    static HashMap<String,String> parseContent(String content,String spiltChars) throws Exception{
        HashMap<String,String> header = new HashMap<String, String>();
        String[] lines = content.split(spiltChars);
        for(String line : lines){
            String[] kv = line.split("=");
            header.put(kv[0],kv[1]);
        }
        return header;
    }

    static HashMap<String,String> parseFile(String filePath,String spiltChars) throws Exception {
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(filePath);
            String content = new String(readAllFromInputStream(fin),"UTF-8");
            return parseContent(content,spiltChars);
        }finally {
            if(fin != null){
                fin.close();
            }
        }
    }

    static private byte[] readAllFromInputStream(InputStream inputStream) throws Exception {
        byte[] bytes = new byte[4096];
        int size = 0;
        ByteArrayOutputStream ba = new ByteArrayOutputStream();

        try {
            while ((size = inputStream.read(bytes)) > 0) {
                ba.write(bytes, 0, size);
            }
        } finally {
            inputStream.close();
        }
        return ba.toByteArray();
    }
}
