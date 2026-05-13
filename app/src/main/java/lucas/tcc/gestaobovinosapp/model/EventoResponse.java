package lucas.tcc.gestaobovinosapp.model;

public class EventoResponse {
    private Long id;
    private Long bovinoId;
    private String tipo;
    private String descricao;
    private String dataEvento;

    public Long getId() { return id; }
    public Long getBovinoId() { return bovinoId; }
    public String getTipo() { return tipo; }
    public String getDescricao() { return descricao; }
    public String getDataEvento() { return dataEvento; }
}
