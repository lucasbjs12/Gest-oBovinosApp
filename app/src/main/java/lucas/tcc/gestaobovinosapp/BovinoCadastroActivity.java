package lucas.tcc.gestaobovinosapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import lucas.tcc.gestaobovinosapp.api.RetrofitClient;
import lucas.tcc.gestaobovinosapp.api.services.ApiService;
import lucas.tcc.gestaobovinosapp.lib.MKTAlertDialog;
import lucas.tcc.gestaobovinosapp.model.BovinoModel;
import lucas.tcc.gestaobovinosapp.model.InvernadaModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BovinoCadastroActivity extends Activity {

    private TextInputEditText etNomeAnimal, etCodigoEpc, etCodigoInterno, etNumeroBrinco,
            etRaca, etDataNascimento, etPeso, etPelagem;
    private Spinner spInvernadas;
    private Button btnSalvarBovino;
    private List<InvernadaModel> listaInvernadas = new ArrayList<>();
    private Long invernadaSelecionadaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bovino_cadastro);

        etNomeAnimal = findViewById(R.id.etNomeAnimal);
        etCodigoEpc = findViewById(R.id.etCodigoEpc);
        etCodigoInterno = findViewById(R.id.etCodigoInterno);
        etNumeroBrinco = findViewById(R.id.etNumeroBrinco);
        etRaca = findViewById(R.id.etRaca);
        etDataNascimento = findViewById(R.id.etDataNascimento);
        etPeso = findViewById(R.id.etPeso);
        etPelagem = findViewById(R.id.etPelagem);
        spInvernadas = findViewById(R.id.spInvernadas);
        btnSalvarBovino = findViewById(R.id.btnSalvarBovino);

        carregarInvernadas();

        btnSalvarBovino.setOnClickListener(v -> salvarBovino());
    }

    private void carregarInvernadas() {
        ApiService api = RetrofitClient.getInstance().create(ApiService.class);
        api.listarInvernadas().enqueue(new Callback<InvernadaModel[]>() {
            @Override
            public void onResponse(Call<InvernadaModel[]> call, Response<InvernadaModel[]> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaInvernadas = new ArrayList<>(Arrays.asList(response.body()));
                    List<String> nomes = new ArrayList<>();
                    for (InvernadaModel inv : listaInvernadas) {
                        nomes.add(inv.getDescricao());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            BovinoCadastroActivity.this, android.R.layout.simple_spinner_dropdown_item, nomes);
                    spInvernadas.setAdapter(adapter);

                    spInvernadas.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                            invernadaSelecionadaId = listaInvernadas.get(position).getId();
                        }

                        @Override
                        public void onNothingSelected(android.widget.AdapterView<?> parent) {
                            invernadaSelecionadaId = null;
                        }
                    });
                } else {
                    Toast.makeText(BovinoCadastroActivity.this, "Erro ao carregar invernadas", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<InvernadaModel[]> call, Throwable t) {
                Toast.makeText(BovinoCadastroActivity.this, "Falha de conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void salvarBovino() {
        String nome = etNomeAnimal.getText().toString().trim();
        String epc = etCodigoEpc.getText().toString().trim();
        String interno = etCodigoInterno.getText().toString().trim();
        String brinco = etNumeroBrinco.getText().toString().trim();
        String raca = etRaca.getText().toString().trim();
        String dataNasc = etDataNascimento.getText().toString().trim();
        String pelagem = etPelagem.getText().toString().trim();
        String pesoStr = etPeso.getText().toString().trim();

        if (nome.isEmpty()) {
            etNomeAnimal.setError("Informe o nome do animal");
            return;
        }
        if (invernadaSelecionadaId == null) {
            Toast.makeText(this, "Selecione uma invernada", Toast.LENGTH_SHORT).show();
            return;
        }

        Double peso = pesoStr.isEmpty() ? null : Double.valueOf(pesoStr);

        BovinoModel bovino = new BovinoModel();
        bovino.setNomeAnimal(nome);
        bovino.setCodigoEpc(epc);
        bovino.setCodigoInterno(interno);
        bovino.setNumeroBrinco(brinco);
        bovino.setRaca(raca);
        bovino.setDataNascimento(dataNasc);
        bovino.setPesoAtualKg(peso);
        bovino.setPelagem(pelagem);
        bovino.setInvernadaId(invernadaSelecionadaId);

        ApiService api = RetrofitClient.getInstance().create(ApiService.class);
        api.salvarBovino(bovino).enqueue(new Callback<BovinoModel>() {
            @Override
            public void onResponse(Call<BovinoModel> call, Response<BovinoModel> response) {
                if (response.isSuccessful()) {
                    new MKTAlertDialog
                            (
                                    BovinoCadastroActivity.this,
                                    MKTAlertDialog.TIPO_SUCESSO,
                                    "Sucesso",
                                    "Bovino cadastrado com sucesso!",
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
                    Toast.makeText(BovinoCadastroActivity.this,
                            "Erro ao salvar bovino: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BovinoModel> call, Throwable t) {
                Toast.makeText(BovinoCadastroActivity.this,
                        "Erro de conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
