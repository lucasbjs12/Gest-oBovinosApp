package lucas.tcc.gestaobovinosapp;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import cn.pedant.SweetAlert.SweetAlertDialog;
import lucas.tcc.gestaobovinosapp.api.RetrofitClient;
import lucas.tcc.gestaobovinosapp.api.services.ApiService;
import lucas.tcc.gestaobovinosapp.lib.MKTAlertDialog;
import lucas.tcc.gestaobovinosapp.model.EventoRequest;
import lucas.tcc.gestaobovinosapp.model.EventoResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventoSanitarioFormActivity extends Activity {

    private TextInputEditText etTipo, etDescricao, etDataEvento;
    private Button btnSalvarEvento;
    private ApiService api;
    private Long bovinoId;
    private String bovinoNome;
    private TextView tvBovino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento_sanitario_form);

        etTipo = findViewById(R.id.etTipo);
        etDescricao = findViewById(R.id.etDescricao);
        etDataEvento = findViewById(R.id.etDataEvento);
        btnSalvarEvento = findViewById(R.id.btnSalvarEvento);
        api = RetrofitClient.getInstance().create(ApiService.class);
        tvBovino = findViewById(R.id.tvBovino);
        bovinoId = getIntent().getLongExtra("bovinoId", 0);
        bovinoNome = getIntent().getStringExtra("bovinoNome");
        tvBovino.setText("Bovino: "+bovinoId+" - "+bovinoNome);

        btnSalvarEvento.setOnClickListener(v -> salvar());
    }

    private void salvar() {
        EventoRequest req = new EventoRequest();
        req.setBovinoId(bovinoId);
        req.setTipo(etTipo.getText().toString().trim());
        req.setDescricao(etDescricao.getText().toString().trim());
        req.setDataEvento(etDataEvento.getText().toString().trim()); // formato: yyyy-MM-dd

        api.criarEvento(req).enqueue(new Callback<EventoResponse>() {
            @Override
            public void onResponse(Call<EventoResponse> call, Response<EventoResponse> response) {
                if (response.isSuccessful()) {
                    new MKTAlertDialog
                            (
                                    EventoSanitarioFormActivity.this,
                                    MKTAlertDialog.TIPO_SUCESSO,
                                    "Sucesso",
                                    "Evento registrado",
                                    false,
                                    new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            sweetAlertDialog.dismissWithAnimation();
                                            finish();
                                        }
                                    }
                            ).Show();
                } else {
                    Toast.makeText(EventoSanitarioFormActivity.this, "Erro: campos obrigatórios", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EventoResponse> call, Throwable t) {
                Toast.makeText(EventoSanitarioFormActivity.this, "Falha: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
