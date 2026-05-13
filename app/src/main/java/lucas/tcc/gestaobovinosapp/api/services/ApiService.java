package lucas.tcc.gestaobovinosapp.api.services;

import lucas.tcc.gestaobovinosapp.model.BovinoModel;
import lucas.tcc.gestaobovinosapp.model.EventoRequest;
import lucas.tcc.gestaobovinosapp.model.EventoResponse;
import lucas.tcc.gestaobovinosapp.model.InvernadaModel;
import lucas.tcc.gestaobovinosapp.model.LeituraRfidModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @GET("api/invernadas")
    Call<InvernadaModel[]> listarInvernadas();

    @POST("api/invernadas")
    Call<InvernadaModel> salvarInvernada(@Body InvernadaModel nova);

    @GET("api/bovinos")
    Call<BovinoModel[]> listarBovinos();

    @POST("api/bovinos")
    Call<BovinoModel> salvarBovino(@Body BovinoModel novo);

    @GET("api/leitura-rfid/bovino/{id}")
    Call<LeituraRfidModel[]> buscarLeituraPorBovino(@Path("id") Long id);

    @GET("api/leitura-rfid/bovino/{bovinoId}/ultima")
    Call<LeituraRfidModel> buscarLeituraUltimaPorBovino(@Path("bovinoId") Long bovinoId);

    @GET("api/eventos-sanitarios/bovino/{bovinoId}")
    Call<EventoResponse[]> listarEventosPorBovino(@Path("bovinoId") Long bovinoId);

    @GET("api/eventos-sanitarios")
    Call<EventoResponse[]> listarEventos();

    @POST("api/eventos-sanitarios")
    Call<EventoResponse> criarEvento(@Body EventoRequest evento);


}
