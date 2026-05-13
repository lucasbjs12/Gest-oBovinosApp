package lucas.tcc.gestaobovinosapp.model;

public class EventoRequest {
    private Long bovinoId;
    private String tipo;
    private String descricao;
    private String dataEvento; // enviado como texto ISO (ex: 2025-11-10)

    public Long getBovinoId() { return bovinoId; }
    public void setBovinoId(Long bovinoId) { this.bovinoId = bovinoId; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getDataEvento() { return dataEvento; }
    public void setDataEvento(String dataEvento) { this.dataEvento = dataEvento; }
}
