package com.hades.Sample.act;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.hades.Sample.Model.Person;
import com.hades.Sample.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExcelActivity extends AppCompatActivity {

    /**
     * 导入jxl库
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excel);

        String tmpdirPath = Environment.getExternalStorageDirectory() + "/Excel/";
        String tmpFilePath = tmpdirPath + "test.xls";

        List<Person> list = new ArrayList<>();
        for (int i=0; i<10; i++){
            Person p = new Person();
            p.id = i;
            p.name = "李" + i;
            p.age = 25 + i;
            p.phone = "1861269602" + i;
            list.add(p);
        }


        File dir = new File(tmpdirPath);
        if (!dir.exists()){
            dir.mkdirs();
        }


        File file = new File(tmpFilePath);
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        writeXls(file, list);
        readXls();




    }

    private String readXls(){
        try {
            InputStream is = getAssets().open("person.xls");
            Workbook wb = Workbook.getWorkbook(is);
            Sheet sheet = wb.getSheet(0);
            int row = sheet.getRows();
            int col = sheet.getColumns();
            for (int i=1; i < row; i++){
                for (int j = 0; j<col; j++){
                    Cell cellarea = sheet.getCell(j, i);
                    Log.i("test", cellarea.getContents());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void writeXls(File file, List<Person> persons){

        try {
            WritableWorkbook book = Workbook.createWorkbook(file);
            WritableSheet sheet=book.createSheet("personinfo", 0);
            writeHeader(sheet);
            for (int i = 0; i<persons.size(); i++){
                Person per = persons.get(i);
                Label l1 = new Label(0, i + 1, String.valueOf(per.id));
                sheet.addCell(l1);
                Label l2 = new Label(1, i + 1, String.valueOf(per.name));
                sheet.addCell(l2);
                Label l3 = new Label(2, i + 1, String.valueOf(per.age));
                sheet.addCell(l3);
                Label l4 = new Label(3, i + 1, String.valueOf(per.phone));
                sheet.addCell(l4);
            }
            book.write();
            book.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RowsExceededException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }

    }

    String[] HEADER = {"工号", "姓名", "年龄", "电话"};
    private void writeHeader(WritableSheet sheet) {
        for (int i=0; i<HEADER.length; i++){
            Label header = new Label(i, 0, HEADER[i]);
            try {
                sheet.addCell(header);
            } catch (WriteException e) {
                e.printStackTrace();
            }
        }
    }

}
