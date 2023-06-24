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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class EditUser {
    private JTable table23;
    private JPanel MainEditUser;
    private JButton BCKBTN;
    private JButton refreshButton;

    String[] ID;
    Integer[] id;
    String[] Email;
    String[] Username;
    String[] Password;
    String[] Role;

    public EditUser() {
        JFrame myframe = new JFrame("Edit User");
        myframe.add(MainEditUser);
        myframe.setPreferredSize(new Dimension(1920, 720));
        myframe.setSize(1280, 720);
        myframe.setResizable(true);
        myframe.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        myframe.setLocationRelativeTo(null);
        myframe.setVisible(true);
        try{
            EditUserRest(myframe);
        }
        catch (Exception ex){
            throw new RuntimeException(ex);
        }


        table23.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int a = tablemodel2.row();
                String tes = String.valueOf(tablemodel2.selectuser(a));
                String data = tes.substring(1, tes.length() - 1);
                String[] dataArray = data.split(", ");
                String id = dataArray[0];
                String email = dataArray[1];
                String username = dataArray[2];
                String password = dataArray[3];
                String role = dataArray[4];

                new EditUser2(id,email,username,password,role);
                myframe.dispose();
            }
        });

        BCKBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame nf = new Dashboard();
                myframe.dispose();
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EditUserRest(myframe);
                }
                catch (Exception ex){
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public void EditUserRest(JFrame myframe) throws Exception{
        Preferences prefs = Preferences.systemRoot();
        String rls = null;
        String un = prefs.get("Username", null);
        String pd = prefs.get("Password", null);
        String rl = prefs.get("Role", null);
        String msg = prefs.get("Message", null);
        if(rl.equals("0")){
            rls = "Admin";
        }

        JSONObject Jsparam = new JSONObject();
        Jsparam.put("Username", un);
        Jsparam.put("Password", pd);
        Jsparam.put("Role", rls);
        byte[] jsdata = Jsparam.toString().getBytes("UTF-8");
        String url = link.url+"/ShowUser";
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

        try {
            JSONObject resp = new JSONObject(responsestring);
            JSONArray myArray = resp.getJSONArray("response");
            System.out.println(myArray);

            ID = new String[myArray.length()+1];
            id =  new Integer[myArray.length()+1];
            Email = new String[myArray.length()+1];
            Username = new String[myArray.length()+1];
            Password = new String[myArray.length()+1];
            Role = new String[myArray.length()+1];

            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            new ObjectMapper().configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
            mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));

            List<table2> datamodeltable = new ArrayList();
            table2 tablea2 = new table2(ID[0], Email[0], Username[0], Password[0], Role[0]);

            JSONObject nameobjt= new JSONObject();
            nameobjt.put("ID", "ID");
            nameobjt.put("Email", "Email");
            nameobjt.put("Username", "Username");
            nameobjt.put("Password", "Password");
            nameobjt.put("Role", "Role");

            String test3 = nameobjt.toString();

            tablea2 = mapper.readValue(test3, table2.class);
            datamodeltable.add(tablea2);

            for (int i = 0; i < myArray.length(); i++) {
                JSONObject arrObj = myArray.getJSONObject(i);
                id[i] = Integer.valueOf(arrObj.getInt("ID"));
                ID[i] = id[i].toString();
                Email[i] = String.valueOf(arrObj.getString("Email"));
                Username[i] = String.valueOf(arrObj.getString("Username"));
                Password[i] = String.valueOf(arrObj.getString("Password"));
                Role[i] = String.valueOf(arrObj.getString("Role"));

                try {
                    JSONObject objt= new JSONObject();
                    objt.put("ID", ID[i]);
                    objt.put("Email", Email[i]);
                    objt.put("Username", Username[i]);
                    objt.put("Password", Password[i]);
                    objt.put("Role", Role[i]);

                    String test1 = objt.toString();
                    tablea2 = mapper.readValue(test1, table2.class);
                    System.out.println(tablea2);

                } catch (JsonMappingException e) {
                    throw new RuntimeException(e);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                datamodeltable.add(tablea2);

            }
            tablemodel2 model = new tablemodel2(datamodeltable);
            table23.setModel(model);
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        in.close();
        os.flush();
        os.close();
    }
}
