import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class tablehistory {


    private String ID_Booking;
    private String Parkiran;
    private String Kendaraan;
    private String PlatNomor;
    private String Username;

    private String Status;
    @JsonCreator
    public tablehistory(@JsonProperty("ID_Booking") String ID_Booking,
                        @JsonProperty("Parkiran") String Parkiran,
                        @JsonProperty("Kendaraan") String Kendaraan,
                        @JsonProperty("PlatNomor") String PlatNomor,
                        @JsonProperty("Username") String Username,
                        @JsonProperty("Status") String Status) {
        this.ID_Booking = ID_Booking;
        this.Parkiran = Parkiran;
        this.Kendaraan = Kendaraan;
        this.PlatNomor =PlatNomor;
        this.Username = Username;
        this.Status= Status;


    }
        public String getID_Booking () {
            return ID_Booking;
        }

        public void setID_Booking (String ID){
            this.ID_Booking = ID_Booking;
        }


        public String getParkiran () {
            return Parkiran;
        }

        public void setParkiran (String Parkiran){
            this.Parkiran = Parkiran;
        }


        public String getKendaraan () {
            return Kendaraan;
        }

        public void setKendaraan (String Kendaraan){
            this.Kendaraan = Kendaraan;
        }


        public String getPlatNomor () {
            return PlatNomor;
        }

        public void setPlatNomor (String PlatNomor){
            this.PlatNomor = PlatNomor;
        }


        public String getUsername () {
            return Username;
        }

        public void setUsername (String Username){
            this.Username = Username;
        }

        public String getStatus () {
        return Status;
    }

        public void setStatus (String Status){
        this.Status = Status;
    }




}

