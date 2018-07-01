package br.com.softomic.testerest.app.telas.secundaria;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import br.com.softomic.testerest.R;
import br.com.softomic.testerest.app.models.Item;
import br.com.softomic.testerest.lib.ExecutarTarefaRest;
import br.com.softomic.testerest.lib.RestActivity;

public class SecundariaActivity extends AppCompatActivity implements RestActivity {

    private ProgressBar progressBar;
    private Button btnParar;
    private Button btnExecutar;

    private ExecutarTarefaRest tarefaListarItens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secundaria);

        progressBar = (ProgressBar) findViewById(R.id.secundaria_progressBar);
        btnParar = (Button) findViewById(R.id.secundaria_btn_parar);
        btnExecutar = (Button) findViewById(R.id.secundaria_btn_executar);

        btnParar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pararServicos();
            }
        });

        btnExecutar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excutarTarefaListarItens();
            }
        });

        excutarTarefaListarItens();
    }

    public void retornoTarefaListarItens(Item item) {

        System.out.println("Id: " + item.getId());
        System.out.println("Descrição: " + item.getDescricao());

    }

    private void excutarTarefaListarItens() {
        if(tarefaListarItens == null || !tarefaListarItens.estaRodando()) {
            tarefaListarItens = new ExecutarTarefaRest(this, false, 0, progressBar,
                    new TarefaItensListarItens(this));
            tarefaListarItens.execute();
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void pararServicos() {
        if(tarefaListarItens != null) {
            tarefaListarItens.parar();
        }
    }

    @Override
    protected void onStop() {
        pararServicos();
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        pararServicos();
        super.onBackPressed();
    }

    @Override
    public void finish() {
        pararServicos();
        super.finish();
    }
}
