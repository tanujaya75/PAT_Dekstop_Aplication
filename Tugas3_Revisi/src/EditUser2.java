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

public class EditUser2 {

    private JTextField Email;
    private JTextField Username;
    private JPasswordField Password;
    private JComboBox Role;
    private JButton submitButton;
    private JButton backButton;
    private JPanel MainEditUser2;

    public EditUser2(String id,String email,String username,String password,String role) {
        JFrame myframe = new JFrame("Edit User");
        Component MainEditUser;
        myframe.add(MainEditUser2);
        myframe.setPreferredSize(new Dimension(1920, 720));
        myframe.setSize(1280, 720);
        myframe.setResizable(true);
        myframe.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        myframe.setLocationRelativeTo(null);
        myframe.setVisible(true);

        Email.setText(email);
        Username.setText(username);
        Password.setText(password);
        Role.setSelectedItem(role);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    EditUser2Rest(myframe,id,email,username,password,role);
                }
                catch (Exception ex){
                    throw new RuntimeException(ex);
                }
            }
        });


        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EditUser();
                myframe.dispose();
            }
        });

    }
    public void EditUser2Rest(JFrame myframe,String id,String email,String username,String password,String role) throws Exception{
        Preferences prefs = Preferences.systemRoot();
        String rls = null;
        String un = prefs.get("Username", null);
        String pd = prefs.get("Password", null);
        String rl = prefs.get("Role", null);
        if(rl.equals("0")){
            rls = "Admin";
        }

        JSONObject jsonparam = new JSONObject();
        jsonparam.put("Username2", un);
        jsonparam.put("Password2", pd);
        jsonparam.put("Role2", rls);

        jsonparam.put("ID", id);
        jsonparam.put("Email", Email.getText());
        jsonparam.put("Username", Username.getText());
        jsonparam.put("Password", Password.getText());
        jsonparam.put("Role", Role.getSelectedItem());

        byte[] jsdata = jsonparam.toString().getBytes("UTF-8");
        String url = link.url+"/EditUser";
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
        System.out.println(responsestring);

        in.close();
        os.flush();
        os.close();

        new EditUser();
        myframe.dispose();


    }
}
