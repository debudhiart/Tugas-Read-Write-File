package id.budhiarta.tugasreadwritefile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_New;
    Button btn_Open;
    Button btn_Save;
    EditText edit_Content;
    EditText edit_Title;

    File path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_New = findViewById(R.id.button_new);
        btn_Open = findViewById(R.id.button_open);
        btn_Save = findViewById(R.id.button_save);
        edit_Content = findViewById(R.id.input_text);
        edit_Title = findViewById(R.id.input_judul);

        btn_New.setOnClickListener(this);
        btn_Open.setOnClickListener(this);
        btn_Save.setOnClickListener(this);
        path = getFilesDir();

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.button_new) {
            newFile();
        } else if (id == R.id.button_open) {
            showList();
        } else if (id == R.id.button_save) {
            saveFile();
        }
    }

    public void newFile() {

        edit_Title.setText("");
        edit_Content.setText("");

        Toast.makeText(this, "Clearing file", Toast.LENGTH_SHORT).show();
    }

    private void loadData(String title) {
        FileModel fileModel = FileHelper.readFromFile(this, title);
        edit_Title.setText(fileModel.getFilename());
        edit_Content.setText(fileModel.getData());
        Toast.makeText(this, "Loading " + title + " data", Toast.LENGTH_SHORT).show();
    }

    private void showList() {
        final ArrayList<String> arrayList = new ArrayList<String>();
        for (String file : path.list()) {
            arrayList.add(file);
        }
        final CharSequence[] items = arrayList.toArray(new CharSequence[arrayList.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih file yang diinginkan");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                loadData(items[item].toString());
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void saveFile() {
        if (edit_Title.getText().toString().isEmpty()) {
            Toast.makeText(this, "Title harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show();
        } else if (edit_Content.getText().toString().isEmpty()) {
            Toast.makeText(this, "Kontent harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show();
        }  else {
            String title = edit_Title.getText().toString();
            String text = edit_Content.getText().toString();
            FileModel fileModel = new FileModel();
            fileModel.setFilename(title);
            fileModel.setData(text);
            FileHelper.writeToFile(fileModel, this);
            Toast.makeText(this, "Saving " + fileModel.getFilename()  + " file", Toast.LENGTH_SHORT).show();
        }
    }
}

