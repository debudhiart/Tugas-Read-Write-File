package id.budhiarta.tugasreadwritefile;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileHelper {
    static void writeToFile(FileModel fileModel, Context context) {
        String filename = fileModel.getFilename();
        String fileContents = fileModel.getData();
        try (FileOutputStream fos= context.openFileOutput(filename, Context.MODE_PRIVATE)){
            fos.write(fileContents.getBytes());
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    static FileModel readFromFile(Context context, String filename){
        FileModel fileModel = new FileModel();
        try{
            InputStream inputStream = context.openFileInput(filename);
            if(inputStream != null){
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ((receiveString = bufferedReader.readLine()) != null){
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                fileModel.setFilename(filename);
                fileModel.setData(stringBuilder.toString());
            }
        } catch(FileNotFoundException e){
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        return fileModel;
    }
}
