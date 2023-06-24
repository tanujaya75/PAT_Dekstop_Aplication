import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;


public class tablemodelhistory extends AbstractTableModel {
    static int row;
    private final String[] judul = {"ID_Booking", "Parkiran", "Kendaraan", "PlatNomor", "Username","Status"};
    private static List<tablehistory> data = new ArrayList<tablehistory>();

    public tablemodelhistory(List<tablehistory> data) {
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
            case 0 -> data.get(rowIndex).getID_Booking();
            case 1 -> data.get(rowIndex).getParkiran();
            case 2 -> data.get(rowIndex).getKendaraan();
            case 3 -> data.get(rowIndex).getPlatNomor();
            case 4 -> data.get(rowIndex).getUsername();
            case 5 -> data.get(rowIndex).getStatus();
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

    public static ArrayList<String> selectuser(int row) {
        String id_booking,jenis,plat,username;
        ArrayList<String> list = new ArrayList<>();
        username = data.get(row).getUsername();
        id_booking = data.get(row).getID_Booking();
        jenis = data.get(row).getKendaraan();
        plat = data.get(row).getPlatNomor();

        list.add(username);
        list.add(id_booking);
        list.add(jenis);
        list.add(plat);

        return list;
    }
}