package lucas.tcc.gestaobovinosapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import lucas.tcc.gestaobovinosapp.adapter.EventoSanitarioAdapter;
import lucas.tcc.gestaobovinosapp.api.RetrofitClient;
import lucas.tcc.gestaobovinosapp.api.services.ApiService;
import lucas.tcc.gestaobovinosapp.lib.MKTAlertDialog;
import lucas.tcc.gestaobovinosapp.model.EventoResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventoSanitarioActivity extends Activity {
    private RecyclerView rvEventos;
    private ProgressBar progress;
    private FloatingActionButton fabAdd;
    private EventoSanitarioAdapter adapter;
    private ApiService api;
    private List<EventoResponse> eventos = new ArrayList<>();
    private Long bovinoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento_sanitario);

        rvEventos = findViewById(R.id.rvEventos);
        progress = findViewById(R.id.progressEventos);
        fabAdd = findViewById(R.id.fabAddEvento);

        api = RetrofitClient.getInstance().create(ApiService.class);
        bovinoId = getIntent().getLongExtra("bovinoId", 0);

        fabAdd.setOnClickListener(v -> {
            Intent i = new Intent(this, EventoSanitarioFormActivity.class);
            i.putExtra("bovinoId", bovinoId);
            startActivity(i);
        });

        carregarEventos();
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarEventos();
    }

    private void carregarEventos() {
        progress.setVisibility(View.VISIBLE);
        api.listarEventos().enqueue(new Callback<EventoResponse[]>() {
            @Override
            public void onResponse(Call<EventoResponse[]> call, Response<EventoResponse[]> response) {
                progress.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    eventos = new ArrayList<>(Arrays.asList(response.body()));
                    adapter = new EventoSanitarioAdapter(EventoSanitarioActivity.this, eventos);
                    rvEventos.setAdapter(adapter);
                } else {
                    new MKTAlertDialog
                            (
                                    EventoSanitarioActivity.this,
                                    MKTAlertDialog.TIPO_ALERTA,
                                    "Informação",
                                    "Nenhum evento encontrado",
                                    false,
                                    new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            sweetAlertDialog.dismissWithAnimation();
                                            finish();
                                        }
                                    }
                            ).Show();
                }
            }

            @Override
            public void onFailure(Call<EventoResponse[]> call, Throwable t) {
                progress.setVisibility(View.GONE);
                Toast.makeText(EventoSanitarioActivity.this, "Erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
