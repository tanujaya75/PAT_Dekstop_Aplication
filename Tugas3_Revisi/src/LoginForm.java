import javax.management.relation.Role;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;
import java.util.prefs.Preferences;
import org.json.*;

public class LoginForm extends JFrame{
    private JPanel MainPanel;
    private JPanel LeftPanel;
    private JTextField TfUsername;
    private JPasswordField PfPassword;
    private JButton loginbtn;
    private JButton registerbtn;
    private JComboBox CBRole;
    public JFrame myframe;
    public LoginForm(){
        myframe = new JFrame("Login Page");
       myframe.add(MainPanel);
       myframe.setPreferredSize(new Dimension(1920,720));
       myframe.setSize(1280,720);
       myframe.setResizable(true);
       myframe.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
       myframe.setLocationRelativeTo(null);
       myframe.setVisible(true);

       Preferences prefs = Preferences.systemRoot();
       String un = prefs.get("Username", null);
       String pd = prefs.get("Password", null);
       String rl = prefs.get("Role", null);
       String msg = prefs.get("Message", null);
        if (isNotEmpty(un,pd,rl)) {
            JOptionPane.showMessageDialog(null,msg);
            if(rl.toString().equals("0"))
            {
                new Dashboard();
                myframe.dispose();
            }
            else if (rl.toString().equals("1")){
                new ListParkiran();
                myframe.dispose();
            }
        }

        loginbtn.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               if(TfUsername.getText().trim().isEmpty() && PfPassword.getText().trim().isEmpty()){
                   JOptionPane.showMessageDialog(null,"Username dan Password Kosong !!!");
               }
               else if(TfUsername.getText().trim().isEmpty()){
                   JOptionPane.showMessageDialog(null,"Username Kosong !!!");
               }
               else if(PfPassword.getText().trim().isEmpty()){
                   JOptionPane.showMessageDialog(null,"Password Kosong !!!");
               }
               else{
                   try{
                       LoginRest(myframe);
                   }
                   catch (Exception ex){
                       throw new RuntimeException(ex);
                   }
               }
           }
       });

       registerbtn.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               try{
                  JFrame nf =  new RegisterDekstop();
                  myframe.dispose();
               }
               catch (Exception erx){
                   throw new RuntimeException();
               }
           }
       });

    }

    public static boolean isNotEmpty(String username, String password, String role) {
        return username != null && !username.isEmpty() && password != null && !password.isEmpty() && role != null && !role.isEmpty();
    }

    public void LoginRest(JFrame myframe) throws Exception{
        JSONObject jsonparam = new JSONObject();
        jsonparam.put("Username", TfUsername.getText());
        jsonparam.put("Password", PfPassword.getText());
        jsonparam.put("Role", CBRole.getSelectedItem());
        //System.out.println(jsonparam.toString());
        byte[] jsdata = jsonparam.toString().getBytes("UTF-8");

        String url = link.url+"/Login";
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
        String Username = resp.getString("Username");
        String Password = resp.getString("Password");
        Integer Role = resp.getInt("Role");
        String Roles = Role.toString();
        String Message = resp.getString("Message");
        System.out.println(Username);
        System.out.println(Password);
        System.out.println(Role);
        System.out.println(Message);

        Preferences prefs = Preferences.systemRoot();
        prefs.put("Username", Username);
        prefs.put("Password", Password);
        prefs.put("Role", Roles);
        prefs.put("Message", Message);

        String un = prefs.get("Username", null);
        String pd = prefs.get("Password", null);
        String rl = prefs.get("Role", null);
        String msg = prefs.get("Message", null);


        in.close();
        os.flush();
        os.close();


        if(!un.toString().equals(null) && !pd.toString().equals(null) && rl.toString().equals("0"))
        {
            JOptionPane.showMessageDialog(null,msg);
            myframe.dispose();
            new Dashboard();
        }
        else if(!un.toString().equals(null) && !pd.toString().equals(null) && rl.toString().equals("0") &&  msg.toString().equals("Anda Sudah Login Sebagai Admin")){
            JOptionPane.showMessageDialog(null,msg);
            myframe.dispose();
            new Dashboard();
        }
        else if(!un.toString().equals(null) && !pd.toString().equals(null) && rl.toString().equals("1") && msg.toString().equals("Anda Sudah Login Sebagai Operator")) {
            JOptionPane.showMessageDialog(null,msg);
            myframe.dispose();
            new ListParkiran();
        }
        else if(!un.toString().equals(null) && !pd.toString().equals(null) && rl.toString().equals("1")){
            JOptionPane.showMessageDialog(null,msg);
            myframe.dispose();
            new ListParkiran();
        }

    }


    public static void main(String[] args) {
       new LoginForm();

    }



}


