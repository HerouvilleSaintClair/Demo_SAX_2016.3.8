package exemple.demo_sax;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView text ;
    int compt = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TextView)findViewById(R.id.test);
        text.setMovementMethod(ScrollingMovementMethod.getInstance());

        String path= Environment.getExternalStorageDirectory().toString() + "/temps/trajet_1_50_ligne.gpx";
        //读取服务器上的XML，获取XML流
        InputStream inputStream = null;
        String time = "";

        try {
            inputStream = new FileInputStream(new File(path)) {
            };
            List<HashMap<String, String>> list;
            list=SaxService.readXML(inputStream, "time");

            String info = "";
            SimpleDateFormat iso8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            Log.d("size",list.size()+"");
            for(int i = 0 ; i < list.size() ; i ++)
                {

                    time = list.get(i).get("time");
                    Date time_date = iso8601.parse(time);
                    info += "time "+time+"\n";
                    compt=i;
                }
                text.setText("taille:"+list.size()+" "+info);
                //打印到LogCat中
               // Log.d("gpx", "taille : "+list.size()+" \n"+info);

        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        catch (ParseException e) {
            Log.d("error", "numéro de boucle " + compt + " " + e.toString());
            Log.d("time_err",time);
            e.printStackTrace();
        }

        //解析流，设定需要解析的节点


    }
}
