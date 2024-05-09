package fio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Duc Linh Gói FIOCore chứa các phương thức để đọc file và ghi file
 */
public class FIOCore {

	/**
	 * Phương thức @readMap được sử dụng để đọc file và trả về một HashMap
	 */
	public HashMap<String, String> readMap(String path) throws IOException {
        HashMap<String, String> map = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                map.put(parts[0], parts[1]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return map;
    }

	/**
	 * Phương thức @readList được sử dụng để đọc file và trả về một ArrayList
	 */
	public ArrayList<String> readList(String path) throws IOException {
        ArrayList<String> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return list;
    }

	/**
	 * Phương thức @write được sử dụng để ghi file, nhận đầu vào là một HashMap
	 */
	public boolean write(String path, boolean append, HashMap<String, String> data) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, append))) {
            for (Map.Entry<String, String> entry : data.entrySet()) {
                bw.write(entry.getKey() + ":" + entry.getValue());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
	
	/**
	 * Phương thức @write được sử dụng để ghi file, nhận đầu vào là một ArrayList
	 */
	public void write(String path, boolean append, ArrayList<String> data) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, append))) {
            for (String string : data) {
            	bw.write(string);
                bw.newLine();
			}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	/**
	 * Phương thức @write được sử dụng để ghi file, nhận đầu vào là một String
	 */
	public void write(String path, boolean append, String data) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, append))) {
        	bw.write(data);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
