package lucas.tcc.gestaobovinosapp.model;

public class BovinoModel {
    private Long id;
    private String nomeAnimal;
    private String codigoEpc;
    private String codigoInterno;
    private String numeroBrinco;
    private String raca;
    private String dataNascimento;
    private Double pesoAtualKg;
    private String pelagem;
    private InvernadaModel invernada;
    private Long invernadaId;
    private String invernadaDescricao;

    public Long getId() { return id; }
    public String getNomeAnimal() { return nomeAnimal; }
    public String getRaca() { return raca; }
    public String getNumeroBrinco() { return numeroBrinco; }
    public InvernadaModel getInvernada() { return invernada; }

    public String getCodigoEpc() {
        return codigoEpc;
    }

    public String getCodigoInterno() {
        return codigoInterno;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public Double getPesoAtualKg() {
        return pesoAtualKg;
    }

    public String getPelagem() {
        return pelagem;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNomeAnimal(String nomeAnimal) {
        this.nomeAnimal = nomeAnimal;
    }

    public void setCodigoEpc(String codigoEpc) {
        this.codigoEpc = codigoEpc;
    }

    public void setCodigoInterno(String codigoInterno) {
        this.codigoInterno = codigoInterno;
    }

    public void setNumeroBrinco(String numeroBrinco) {
        this.numeroBrinco = numeroBrinco;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setPesoAtualKg(Double pesoAtualKg) {
        this.pesoAtualKg = pesoAtualKg;
    }

    public void setPelagem(String pelagem) {
        this.pelagem = pelagem;
    }

    public void setInvernada(InvernadaModel invernada) {
        this.invernada = invernada;
    }

    public Long getInvernadaId() {
        return invernadaId;
    }

    public void setInvernadaId(Long invernadaId) {
        this.invernadaId = invernadaId;
    }

    public String getInvernadaDescricao() {
        return invernadaDescricao;
    }

    public void setInvernadaDescricao(String invernadaDescricao) {
        this.invernadaDescricao = invernadaDescricao;
    }
}
