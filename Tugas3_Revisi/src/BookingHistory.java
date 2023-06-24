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
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class BookingHistory {
    private JPanel MainBookingHistory;
    private JTable BookingTable;
    private JButton backButton;
    private JButton checkOutButton;
    private JButton validButton;
    private JButton refreshButton;

    String[] ID_Booking;
    String[] Parkiran;
    String[] Kendaraan;
    String[] PlatNomor;
    String[] Username;
    String[] Status;
    Integer[] status;

    public BookingHistory(){
        JFrame myframe = new JFrame("List Parkiran");
        myframe.add(MainBookingHistory);
        myframe.setPreferredSize(new Dimension(1920, 720));
        myframe.setSize(1280, 720);
        myframe.setResizable(true);
        myframe.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        myframe.setLocationRelativeTo(null);
        myframe.setVisible(true);

        try{
            BookingHistoryRest(myframe);
        }
        catch (Exception ex){
            throw new RuntimeException(ex);
        }

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ListParkiran();
                myframe.dispose();
            }
        });

        validButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int a = tablemodelhistory.row();
                String tes = String.valueOf(tablemodelhistory.selectuser(a));
                String data = tes.substring(1, tes.length() - 1);
                String[] dataArray = data.split(", ");
                String username = dataArray[0];
                String idbooking = dataArray[1];
                String jenis= dataArray[2];
                String plat = dataArray[3];

                Preferences prefs = Preferences.systemRoot();
                String rls = null;
                String un = prefs.get("Username", null);
                String pd = prefs.get("Password", null);
                String rl = prefs.get("Role", null);
                String msg = prefs.get("Message", null);
                if (rl.equals("0")) {
                    rls = "Admin";
                } else if (rl.equals("1")) {
                    rls = "Operator";
                }
                try {
                    JSONObject Jsparam = new JSONObject();
                    Jsparam.put("Username", un);
                    Jsparam.put("Password", pd);
                    Jsparam.put("Role", rls);

                    Jsparam.put("Username2", username);
                    Jsparam.put("ID_Booking", idbooking);
                    Jsparam.put("Jenis", jenis);
                    Jsparam.put("PlatNomor", plat);

                    byte[] jsdata = Jsparam.toString().getBytes("UTF-8");

                    String url = link.url + "/Validation";
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
                    in.close();
                    os.flush();
                    os.close();
                    //JSONObject resp = new JSONObject(responsestring);
                    JOptionPane.showMessageDialog(null, responsestring);
                    new BookingHistory();
                    myframe.dispose();

                } catch (ProtocolException ex) {
                    throw new RuntimeException(ex);
                } catch (MalformedURLException ex) {
                    throw new RuntimeException(ex);
                } catch (UnsupportedEncodingException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        checkOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int a = tablemodelhistory.row();
                String tes = String.valueOf(tablemodelhistory.selectuser(a));
                String data = tes.substring(1, tes.length() - 1);
                String[] dataArray = data.split(", ");
                String username = dataArray[0];
                String idbooking = dataArray[1];
                String jenis= dataArray[2];
                String plat = dataArray[3];

                Preferences prefs = Preferences.systemRoot();
                String rls = null;
                String un = prefs.get("Username", null);
                String pd = prefs.get("Password", null);
                String rl = prefs.get("Role", null);
                String msg = prefs.get("Message", null);
                if (rl.equals("0")) {
                    rls = "Admin";
                } else if (rl.equals("1")) {
                    rls = "Operator";
                }
                try {
                    JSONObject Jsparam = new JSONObject();
                    Jsparam.put("Username", un);
                    Jsparam.put("Password", pd);
                    Jsparam.put("Role", rls);

                    Jsparam.put("Username2", username);
                    Jsparam.put("ID_Booking", idbooking);
                    Jsparam.put("Jenis", jenis);
                    Jsparam.put("PlatNomor", plat);

                    byte[] jsdata = Jsparam.toString().getBytes("UTF-8");

                    String url = link.url + "/CheckOut";
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
                    in.close();
                    os.flush();
                    os.close();
                    //JSONObject resp = new JSONObject(responsestring);
                    JOptionPane.showMessageDialog(null, responsestring);
                    new BookingHistory();
                    myframe.dispose();

                } catch (ProtocolException ex) {
                    throw new RuntimeException(ex);
                } catch (MalformedURLException ex) {
                    throw new RuntimeException(ex);
                } catch (UnsupportedEncodingException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    BookingHistoryRest(myframe);
                }
                catch (Exception ex){
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public void BookingHistoryRest(JFrame myframe) throws Exception{
        Preferences prefs = Preferences.systemRoot();
        String rls = null;
        String un = prefs.get("Username", null);
        String pd = prefs.get("Password", null);
        String rl = prefs.get("Role", null);
        if(rl.equals("0")){
            rls = "Admin";
        }
        else if(rl.equals("1")){
            rls = "Operator";
        }

        JSONObject Jsparam = new JSONObject();
        Jsparam.put("Username", un);
        Jsparam.put("Role", rls);
        byte[] jsdata = Jsparam.toString().getBytes("UTF-8");

        String url = link.url + "/BookingHistory";
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

            ID_Booking = new String[myarray.length() +1];
            Parkiran = new String[myarray.length() + 1];
            Kendaraan = new String[myarray.length() +1];
            PlatNomor = new String[myarray.length() +1];
            Username = new String[myarray.length() +1];
            Status = new String[myarray.length() +1];
            status = new Integer[myarray.length() +1];

            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            new ObjectMapper().configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
            mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));

            List<tablehistory> datamodeltable = new ArrayList<>();
            tablehistory tablea = new tablehistory(ID_Booking[0], Parkiran[0], Kendaraan[0], PlatNomor[0], Username[0], Status[0]);

            JSONObject nameobjt = new JSONObject();
            nameobjt.put("ID_Booking","ID_Booking");
            nameobjt.put("Parkiran","Parkiran");
            nameobjt.put("Kendaraan","Kendaraan");
            nameobjt.put("PlatNomor","PlatNomor");
            nameobjt.put("Username","Username");
            nameobjt.put("Status","Status");

            String test2 = nameobjt.toString();
            tablea = mapper.readValue(test2, tablehistory.class);
            datamodeltable.add(tablea);

            for (int i = 0; i < myarray.length(); i++){
                JSONObject arrobj = myarray.getJSONObject(i);
                ID_Booking[i] = String.valueOf(arrobj.getInt("ID_Booking"));
                Parkiran[i] = String.valueOf(arrobj.getString("LahanParkir"));
                Kendaraan[i] = String.valueOf(arrobj.getString("Jenis"));
                PlatNomor[i] = String.valueOf(arrobj.getString("PlatNomor"));
                Username[i] = String.valueOf(arrobj.getString("Username"));
                status[i] = Integer.valueOf(arrobj.getInt("Status"));
//                Status[i] = status[i].toString();
                if(status[i] == 1){
                    Status[i] = "Booked";
                } else if (status[i] == 2) {
                    Status[i] = "Valid";
                }

                //Status[i] = String.valueOf(arrobj.getString("Status"));
//                System.out.println(ID_Parkiran[i]);
//                System.out.println(LahanParkir[i]);
//                System.out.println(Status[i]);

                try {
                    JSONObject objt = new JSONObject();
                    objt.put("ID_Booking", ID_Booking[i]);
                    objt.put("Parkiran", Parkiran[i]);
                    objt.put("Kendaraan", Kendaraan[i]);
                    objt.put("PlatNomor", PlatNomor[i]);
                    objt.put("Username", Username[i]);
                    objt.put("Status", Status[i]);
                    String test1 = objt.toString();
                    tablea = mapper.readValue(test1, tablehistory.class);
                    //System.out.println(tablea);

                } catch (JsonMappingException e) {
                    throw new RuntimeException(e);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                datamodeltable.add(tablea);
            }
            tablemodelhistory model = new tablemodelhistory(datamodeltable);
            BookingTable.setModel(model);

        }
        catch (JSONException e){
            e.printStackTrace();
        }



    }

}
