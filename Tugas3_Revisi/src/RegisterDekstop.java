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


public class RegisterDekstop extends JFrame {
    private JTextField TfEmail;
    private JTextField TFUsername;
    private JPasswordField PFPassword;
    private JComboBox CBRole;
    private JButton registrasiButton, backButton;
    private JPanel MainRegis;


    public RegisterDekstop(){
        JFrame myframe = new JFrame("Register Page");
        myframe.add(MainRegis);
        myframe.setPreferredSize(new Dimension(1920,720));
        myframe.setSize(1280,720);
        myframe.setResizable(true);
        myframe.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        myframe.setLocationRelativeTo(null);
        myframe.setVisible(true);

        registrasiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(TfEmail.getText().trim().isEmpty() && TFUsername.getText().trim().isEmpty() &&  PFPassword.getText().trim().isEmpty()){
                    JOptionPane.showMessageDialog(null,"Email, Username dan Password Kosong !!!");
                }
                else if(TfEmail.getText().trim().isEmpty()){
                    JOptionPane.showMessageDialog(null,"Email Kosong !!!");
                }
                else if(TFUsername.getText().trim().isEmpty()){
                    JOptionPane.showMessageDialog(null,"Username Kosong !!!");
                }
                else if(PFPassword.getText().trim().isEmpty()){
                    JOptionPane.showMessageDialog(null,"Password Kosong !!!");
                }
                else if(TfEmail.getText().trim().isEmpty() && TFUsername.getText().trim().isEmpty()){
                    JOptionPane.showMessageDialog(null,"Email dan Username  Kosong !!!");
                }
                else if(TfEmail.getText().trim().isEmpty() &&  PFPassword.getText().trim().isEmpty()){
                    JOptionPane.showMessageDialog(null,"Email dan Password Kosong !!!");
                }
                else if(TFUsername.getText().trim().isEmpty() &&  PFPassword.getText().trim().isEmpty()){
                    JOptionPane.showMessageDialog(null,"Username dan Password Kosong !!!");
                }
                else{
                    try{
                        registerREST(myframe);
                    }
                    catch (Exception ex){
                        throw new RuntimeException(ex);
                    }
                }

            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    JFrame nf =  new LoginForm();
                    myframe.dispose();
                }
                catch (Exception erx){
                    throw new RuntimeException();
                }
            }
        });

    }

    public void registerREST(JFrame myframe) throws Exception{
            JSONObject jsonparam = new JSONObject();
            jsonparam.put("Email", TfEmail.getText());
            jsonparam.put("Username", TFUsername.getText());
            jsonparam.put("Password", PFPassword.getText());
            jsonparam.put("Role", CBRole.getSelectedItem());
            System.out.println(jsonparam.toString());
            byte[] jsdata = jsonparam.toString().getBytes("UTF-8");

            String url = link.url+"/RegisterDekstop";
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
            in.close();
            os.flush();
            os.close();
        String responsestring = response.toString();
        JSONObject resp = new JSONObject(responsestring);
        String rsp = resp.getString("response");
        System.out.println(rsp);
        if(rsp.toString().equals("Berhasil Menambahkan User")){
            System.out.println("Berhasil Add USer");
            JOptionPane.showMessageDialog(null,"Berhasil Menambahkan Akun");
            JFrame nf =  new LoginForm();
            myframe.dispose();
        }
        else {
            System.out.println("Gagal Menambahkan User");
        }
    }
}
