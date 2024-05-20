package fio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
* @author Duc Linh
*/
public class FIOEncrypting extends FIOCore{
	
	@Override
	public HashMap<String, String> readMap(String path) throws IOException {
        HashMap<String, String> map = new HashMap<>();
        EncryptingData encrypt = new EncryptingData();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
            	String newLine;
				try {
					newLine = encrypt.settingcrypto(line, false);
	                String[] parts = newLine.split("=");
	                map.put(parts[0], parts[1]);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return map;
    }
	
	@Override
	public boolean write(String path, boolean append, HashMap<String, String> data) {
		EncryptingData encrypt = new EncryptingData();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, append))) {
            for (Map.Entry<String, String> entry : data.entrySet()) {
                try {
					bw.write(encrypt.settingcrypto(entry.getKey() + "=" + entry.getValue(), true));
				} catch (Exception e) {

					e.printStackTrace();
				}
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
