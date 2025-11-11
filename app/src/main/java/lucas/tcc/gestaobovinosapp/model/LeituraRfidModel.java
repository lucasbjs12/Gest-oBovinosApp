package lucas.tcc.gestaobovinosapp.model;

public class LeituraRfidModel {
    private Long bovinoId;
    private String antena;
    private String timestamp;

    public Long getBovinoId() { return bovinoId; }
    public void setBovinoId(Long bovinoId) { this.bovinoId = bovinoId; }

    public String getAntena() { return antena; }
    public void setAntena(String antena) { this.antena = antena; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public String getHoraFormatada() {
        if (timestamp == null || timestamp.isEmpty()) return "-";
        try {
            String hora = timestamp.substring(11, 16);
            String data = timestamp.substring(8, 10) + "/" +
                    timestamp.substring(5, 7) + "/" +
                    timestamp.substring(0, 4);
            return hora + " — " + data;
        } catch (Exception e) {
            return timestamp;
        }
    }
}