import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;


public class tablemodelparkiran extends AbstractTableModel {
    static int row;
    private final String[] judul = {"ID_Parkiran", "LahanParkir", "Status"};
    private static List<tableparkiran> data = new ArrayList<tableparkiran>();

    public tablemodelparkiran(List<tableparkiran> data) {
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return judul.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        row = rowIndex;
        System.out.println(rowIndex);
        return switch (columnIndex) {
            case 0 -> data.get(rowIndex).getID_Parkiran();
            case 1 -> data.get(rowIndex).getLahanParkir();
            case 2 -> data.get(rowIndex).getStatus();
            default -> "-";
        };
    }

    @Override
    public String getColumnName(int columnIndex) {
        return judul[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (getValueAt(0, columnIndex) != null) {
            return getValueAt(0, columnIndex).getClass();
        } else {
            return Object.class;
        }
    }

    public static int row() {
        return row;
    }

//    public static ArrayList<String> selectuser(int row) {
//        String id,email,username,password,role;
//        ArrayList<String> list = new ArrayList<>();
//        id = data.get(row).getID();
//        email = data.get(row).getEmail();
//        username = data.get(row).getUsername();
//        password = data.get(row).getPassword();
//        role = data.get(row).getRole();
//        list.add(id);
//        list.add(email);
//        list.add(username);
//        list.add(password);
//        list.add(role);
//        return list;
//    }
}