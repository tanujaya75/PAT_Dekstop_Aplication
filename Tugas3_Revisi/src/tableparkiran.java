import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class tableparkiran {


    private String ID_Parkiran;
    private String LahanParkir;
    private String Status;

    @JsonCreator
    public tableparkiran(@JsonProperty("ID_Parkiran") String ID_Parkiran,
                         @JsonProperty("LahanParkir") String LahanParkir,
                         @JsonProperty("Status") String Status) {
        this.ID_Parkiran = ID_Parkiran;
        this.LahanParkir = LahanParkir;
        this.Status = Status;


    }
        public String getID_Parkiran () {
            return ID_Parkiran;
        }

        public void setID_Parkiran (String ID_Parkiran){
            this.ID_Parkiran = ID_Parkiran;
        }

        public String getLahanParkir () {
            return LahanParkir;
        }

        public void setLahanParkir (String LahanParkir){
            this.LahanParkir = LahanParkir;
        }

        public String getStatus () {
            return Status;
        }

        public void setStatus (String Status){
            this.Status = Status;
        }






}

