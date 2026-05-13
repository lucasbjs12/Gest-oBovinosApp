package lucas.tcc.gestaobovinosapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import cn.pedant.SweetAlert.SweetAlertDialog;
import lucas.tcc.gestaobovinosapp.api.RetrofitClient;
import lucas.tcc.gestaobovinosapp.api.services.ApiService;
import lucas.tcc.gestaobovinosapp.lib.MKTAlertDialog;
import lucas.tcc.gestaobovinosapp.model.InvernadaModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvernadaCadastroActivity extends Activity {

    private TextInputEditText etDescricao, etUrlFoto, etObservacoes;
    private Button btnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invernada_cadastro);

        etDescricao = findViewById(R.id.etDescricao);
        etUrlFoto = findViewById(R.id.etUrlFoto);
        etObservacoes = findViewById(R.id.etObservacoes);
        btnSalvar = findViewById(R.id.btnSalvar);

        btnSalvar.setOnClickListener(v -> salvarInvernada());
    }

    private void salvarInvernada() {
        String descricao = etDescricao.getText().toString().trim();
        String urlFoto = etUrlFoto.getText().toString().trim();
        String observacoes = etObservacoes.getText().toString().trim();

        if (descricao.isEmpty()) {
            etDescricao.setError("Informe uma descrição");
            return;
        }

        InvernadaModel nova = new InvernadaModel();
        nova.setDescricao(descricao);
        nova.setUrlFoto(urlFoto);
        nova.setObservacoes(observacoes);

        ApiService api = RetrofitClient.getInstance().create(ApiService.class);
        api.salvarInvernada(nova).enqueue(new Callback<InvernadaModel>() {
            @Override
            public void onResponse(Call<InvernadaModel> call, Response<InvernadaModel> response) {
                if (response.isSuccessful()) {
                    new MKTAlertDialog
                            (
                                    InvernadaCadastroActivity.this,
                                    MKTAlertDialog.TIPO_ALERTA,
                                    "Informação",
                                    "Invernada cadastrada com sucesso!",
                                    false,
                                    new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            sweetAlertDialog.dismissWithAnimation();
                                            finish(); // volta para lista
                                        }
                                    }
                            ).Show();
                } else {
                    Toast.makeText(InvernadaCadastroActivity.this,
                            "Erro ao salvar: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<InvernadaModel> call, Throwable t) {
                Toast.makeText(InvernadaCadastroActivity.this,
                        "Erro de conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
