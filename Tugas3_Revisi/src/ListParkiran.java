import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import static javax.swing.WindowConstants.*;

public class ListParkiran {
    private JPanel MainListParkiran;
    private JTable table1;
    private JButton BackBUTTON;
    private JButton BookingHistory;
    private JButton logOutButton;
    private JButton refreshButton;

    String[] ID_Parkiran;
    String[] LahanParkir;
    String[] Status;
    Integer[] status;

    public ListParkiran(){
        JFrame myframe = new JFrame("List Parkiran");
        myframe.add(MainListParkiran);
        myframe.setPreferredSize(new Dimension(1920, 720));
        myframe.setSize(1280, 720);
        myframe.setResizable(true);
        myframe.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        myframe.setLocationRelativeTo(null);
        myframe.setVisible(true);
        Preferences prefs = Preferences.systemRoot();
        String rl = prefs.get("Role", null);
        try{

            ListParkiranRest(myframe);

        }
        catch (Exception  ex){
            throw new RuntimeException(ex);
        }

        if(rl.equals("0")){
            BackBUTTON.setVisible(true);
            logOutButton.setVisible(false);
        }
        else if (rl.equals("1"))
        {
            BackBUTTON.setVisible(false);
            logOutButton.setVisible(true);
        }

        BackBUTTON.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Dashboard();
                myframe.dispose();
            }
        });

        BookingHistory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BookingHistory();
                myframe.dispose();
            }
        });

        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String url = link.url + "/Logout";
                    URL obj = new URL(url);
                    HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("User-Agent", "Mozilla/5.0");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty("Accept", "application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.connect();

                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(conn.getInputStream())
                    );

                    String input;
                    StringBuffer response = new StringBuffer();
                    while ((input = in.readLine()) != null) {
                        response.append(input);
                    }
                    in.close();

                    Preferences prefs = Preferences.systemRoot();
                    prefs.clear();
                    prefs.flush();

                    JFrame nf = new LoginForm();
                    myframe.dispose();
                } catch (JSONException ex) {
                    ex.printStackTrace();
                } catch (ProtocolException ex) {
                    throw new RuntimeException(ex);
                } catch (MalformedURLException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (BackingStoreException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{

                    ListParkiranRest(myframe);

                }
                catch (Exception  ex){
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public void ListParkiranRest(JFrame myframe) throws Exception{
        Preferences prefs = Preferences.systemRoot();
        String rls = null;
        String un = prefs.get("Username", null);
        String pd = prefs.get("Password", null);
        String rl = prefs.get("Role", null);
        if(rl.equals("0")){
            rls = "Admin";
        } else if (rl.equals("1")) {
            rls = "Operator";
        }

        JSONObject Jsparam = new JSONObject();
        Jsparam.put("Username", un);
        Jsparam.put("Role", rls);
        byte[] jsdata = Jsparam.toString().getBytes("UTF-8");

        String url = link.url + "/Listparkiran";
        URL obj = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.connect();

        OutputStream os = conn.getOutputStream();
        os.write(jsdata);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream())
        );

        String input;
        StringBuffer response = new StringBuffer();
        while ((input = in.readLine()) != null) {
            response.append(input);
        }
        String responsestring = response.toString();

        try{
            JSONArray myarray = new JSONArray(responsestring);
            JSONObject resp = new JSONObject(myarray);

            ID_Parkiran = new String[myarray.length() +1];
            LahanParkir = new String[myarray.length() + 1];
            Status = new String[myarray.length() +1];
            status = new Integer[myarray.length() +1];

            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            new ObjectMapper().configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
            mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));

            List<tableparkiran> datamodeltable = new ArrayList<>();
            tableparkiran tablea = new tableparkiran(ID_Parkiran[0], LahanParkir[0], Status[0]);

            JSONObject nameobjt = new JSONObject();
            nameobjt.put("ID_Parkiran","ID_Parkiran");
            nameobjt.put("LahanParkir","LahanParkir");
            nameobjt.put("Status","Status");

            String test2 = nameobjt.toString();
            tablea = mapper.readValue(test2, tableparkiran.class);
            datamodeltable.add(tablea);

            for (int i = 0; i < myarray.length(); i++){
                JSONObject arrobj = myarray.getJSONObject(i);
                ID_Parkiran[i] = String.valueOf(arrobj.getInt("ID_Parkiran"));
                LahanParkir[i] = String.valueOf(arrobj.getString("LahanParkir"));
                status[i] = Integer.valueOf(arrobj.getInt("Status"));
//                Status[i] = status[i].toString();
                if(status[i] == 0){
                    Status[i] = "Kosong";
                } else if (status[i] == 1) {
                    Status[i] = "Booked";
                }
                else if (status[i] == 2) {
                    Status[i] = "Terisi";
                }

                //Status[i] = String.valueOf(arrobj.getString("Status"));
                System.out.println(ID_Parkiran[i]);
                System.out.println(LahanParkir[i]);
                System.out.println(Status[i]);

                try {
                    JSONObject objt = new JSONObject();
                    objt.put("ID_Parkiran", ID_Parkiran[i]);
                    objt.put("LahanParkir", LahanParkir[i]);
                    objt.put("Status", Status[i]);
                    String test1 = objt.toString();
                    tablea = mapper.readValue(test1, tableparkiran.class);
                    System.out.println(tablea);

                } catch (JsonMappingException e) {
                    throw new RuntimeException(e);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                datamodeltable.add(tablea);
            }
            tablemodelparkiran model = new tablemodelparkiran(datamodeltable);
            table1.setModel(model);

        }
        catch (JSONException e){
            e.printStackTrace();
        }

    }
}
