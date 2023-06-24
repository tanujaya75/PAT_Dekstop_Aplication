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
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import org.json.*;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;

public class Dashboard extends JFrame {
    private JButton DeleteUser;
    private JButton AddUserBtn;
    private JButton EditUserBtn;
    private JButton ListParkiranBtn;
    private JTable table1;
    private JButton logOutButton;
    private JPanel MainDashboard;
    private JButton refreshButton;
    String[] ID;
    Integer[] id;
    String[] Email;
    String[] Username;
    String[] Password;
    String[] Role;

    public Dashboard() {
        JFrame myframe = new JFrame("Dashboard");
        myframe.add(MainDashboard);
        myframe.setPreferredSize(new Dimension(1920, 720));
        myframe.setSize(1280, 720);
        myframe.setResizable(true);
        myframe.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        myframe.setLocationRelativeTo(null);
        myframe.setVisible(true);
        try {
            userinfoREST(myframe);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               try {
                   userinfoREST(myframe);
               }
               catch (Exception ex){
                   throw new RuntimeException(ex);
               }
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

        DeleteUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int a = tablemodel.row();
                String tes = String.valueOf(tablemodel.selectuser(a));
                String data = tes.substring(1, tes.length() - 1);
                String[] dataArray = data.split(", ");
                String username = dataArray[0];
                String password = dataArray[1];
                String role = dataArray[2];

                Preferences prefs = Preferences.systemRoot();
                String rls = null;
                String un = prefs.get("Username", null);
                String pd = prefs.get("Password", null);
                String rl = prefs.get("Role", null);
                String msg = prefs.get("Message", null);
                if (rl.equals("0")) {
                    rls = "Admin";
                }
                try {
                    JSONObject Jsparam = new JSONObject();
                    Jsparam.put("Username", un);
                    Jsparam.put("Password", pd);
                    Jsparam.put("Role", rls);
                    Jsparam.put("Username2", username);
                    Jsparam.put("Password2", password);
                    Jsparam.put("Role2", role);
                    byte[] jsdata = Jsparam.toString().getBytes("UTF-8");

                    String url = link.url + "/RemoveUser";
                    URL obj = new URL(url);
                    HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
                    conn.setRequestMethod("DELETE");
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
                    JSONObject resp = new JSONObject(responsestring);
                    JOptionPane.showMessageDialog(null, resp.get("response"));
                    JFrame nf = new Dashboard();
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

        AddUserBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddUser();
                myframe.dispose();
            }
        });

        EditUserBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EditUser();
                myframe.dispose();
            }
        });

        ListParkiranBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ListParkiran();
                myframe.dispose();
            }
        });

    }

    public void userinfoREST(JFrame myframe) throws Exception {
        Preferences prefs = Preferences.systemRoot();
        String rls = null;
        String un = prefs.get("Username", null);
        String pd = prefs.get("Password", null);
        String rl = prefs.get("Role", null);
        String msg = prefs.get("Message", null);
        System.out.println("\n");
        System.out.println(un);
        System.out.println(pd);
        System.out.println(rl);
        System.out.println(msg);
        if (rl.equals("0")) {
            rls = "Admin";
        }


        JSONObject Jsparam = new JSONObject();
        Jsparam.put("Username", un);
        Jsparam.put("Password", pd);
        Jsparam.put("Role", rls);
        byte[] jsdata = Jsparam.toString().getBytes("UTF-8");


        String url = link.url + "/ShowUser";
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

            ID = new String[myArray.length() + 1];
            id = new Integer[myArray.length() + 1];
            Email = new String[myArray.length() + 1];
            Username = new String[myArray.length() + 1];
            Password = new String[myArray.length() + 1];
            Role = new String[myArray.length() + 1];

            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            new ObjectMapper().configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
            mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));

            List<table> datamodeltable = new ArrayList<table>();
            table tablea = new table(ID[0], Email[0], Username[0], Password[0], Role[0]);

            JSONObject nameobjt = new JSONObject();
            nameobjt.put("ID", "ID");
            nameobjt.put("Email", "Email");
            nameobjt.put("Username", "Username");
            nameobjt.put("Password", "Password");
            nameobjt.put("Role", "Role");
            String test2 = nameobjt.toString();
            tablea = mapper.readValue(test2, table.class);
            datamodeltable.add(tablea);

            for (int i = 0; i < myArray.length(); i++) {
                JSONObject arrObj = myArray.getJSONObject(i);
                id[i] = Integer.valueOf(arrObj.getInt("ID"));
                ID[i] = id[i].toString();
                Email[i] = String.valueOf(arrObj.getString("Email"));
                Username[i] = String.valueOf(arrObj.getString("Username"));
                Password[i] = String.valueOf(arrObj.getString("Password"));
                Role[i] = String.valueOf(arrObj.getString("Role"));

                try {
                    JSONObject objt = new JSONObject();
                    objt.put("ID", ID[i]);
                    objt.put("Email", Email[i]);
                    objt.put("Username", Username[i]);
                    objt.put("Password", Password[i]);
                    objt.put("Role", Role[i]);
                    String test1 = objt.toString();
                    tablea = mapper.readValue(test1, table.class);
                    System.out.println(tablea);

                } catch (JsonMappingException e) {
                    throw new RuntimeException(e);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                datamodeltable.add(tablea);

            }
            tablemodel model = new tablemodel(datamodeltable);
            table1.setModel(model);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        in.close();
        os.flush();
        os.close();


    }
}
