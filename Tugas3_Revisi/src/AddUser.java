import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.prefs.Preferences;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class AddUser {
    private JTextField Email;
    private JButton addUserButton;
    private JPanel MainAddUser;
    private JPasswordField Password;
    private JComboBox CBRole;
    private JTextField Username;
    private JButton BCKButton;

    public AddUser() {
        JFrame myframe = new JFrame("Add User");
        myframe.add(MainAddUser);
        myframe.setPreferredSize(new Dimension(1920, 720));
        myframe.setSize(1280, 720);
        myframe.setResizable(true);
        myframe.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        myframe.setLocationRelativeTo(null);
        myframe.setVisible(true);

        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Email.getText().trim().isEmpty() && Username.getText().trim().isEmpty() && Password.getText().trim().isEmpty()){
                    JOptionPane.showMessageDialog(null,"Email, Username dan Password Tidak Boleh Kosong !!!");
                }
                else if(Email.getText().trim().isEmpty()){
                    JOptionPane.showMessageDialog(null,"Email Tidak Boleh Kosong !!!");
                }
                else if(Username.getText().trim().isEmpty()){
                    JOptionPane.showMessageDialog(null,"Username Tidak Boleh Kosong !!!");
                }
                else if(Password.getText().trim().isEmpty()){
                    JOptionPane.showMessageDialog(null,"Password Tidak Boleh Kosong !!!");
                }
                else{
                    try {
                        AddUserREST(myframe);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        BCKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame nf = new Dashboard();
                myframe.dispose();
            }
        });

    }

    public void AddUserREST(JFrame myframe) throws Exception{
        Preferences prefs = Preferences.systemRoot();
        String rls =null;
        String un = prefs.get("Username", null);
        String pd = prefs.get("Password", null);
        String rl = prefs.get("Role", null);
        String msg = prefs.get("Message", null);
        if(rl.equals("0")){
            rls = "Admin";
        }
        JSONObject jsonparam = new JSONObject();
        jsonparam.put("Username", un);
        jsonparam.put("Password", pd);
        jsonparam.put("Role", rls);

        jsonparam.put("Email2", Email.getText());
        jsonparam.put("Username2", Username.getText());
        jsonparam.put("Password2", Password.getText());
        jsonparam.put("Role2", CBRole.getSelectedItem());
        byte[] jsdata = jsonparam.toString().getBytes("UTF-8");

        String url = link.url+"/AddUser";
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
        while ((input=in.readLine())!=null){
            response.append(input);
        }
        String responsestring = response.toString();

        JSONObject resp = new JSONObject(responsestring);
        String Respond = resp.getString("response");
        System.out.println(Respond);
        in.close();
        os.flush();
        os.close();

        JFrame nf = new Dashboard();
        myframe.dispose();


    }
}
