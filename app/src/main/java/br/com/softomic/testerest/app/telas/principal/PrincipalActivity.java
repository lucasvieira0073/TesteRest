package br.com.softomic.testerest.app.telas.principal;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import br.com.softomic.testerest.R;
import br.com.softomic.testerest.app.models.Item;
import br.com.softomic.testerest.app.models.Produto;
import br.com.softomic.testerest.app.telas.secundaria.SecundariaActivity;
import br.com.softomic.testerest.lib.ExecutarTarefaRest;
import br.com.softomic.testerest.lib.RestActivity;

public class PrincipalActivity extends AppCompatActivity implements RestActivity {

    private ProgressBar progressBar;
    private Button button;
    private Button btnParar;
    private Button btnExecutar;

    private ExecutarTarefaRest tarefaListarProdutos;
    private ExecutarTarefaRest tarefaListarItens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        progressBar = (ProgressBar) findViewById(R.id.principal_progressBar);
        button = (Button) findViewById(R.id.principal_btn);
        btnParar = (Button) findViewById(R.id.principal_btn_parar);
        btnExecutar = (Button) findViewById(R.id.principal_btn_executar);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirTelaSecundaria();
            }
        });

        btnParar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pararServicos();
            }
        });

        btnExecutar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executarTarefaListarProdutos();
            }
        });

        executarTarefaListarItens();

        executarTarefaListarProdutos();
    }

    public void retornoTarefaListarItens(Item item) {

        System.out.println("Id: " + item.getId());
        System.out.println("Descrição: " + item.getDescricao());
    }

    public void retornoTarefaListarPrdutos(Produto produto) {

        System.out.println("Id: " + produto.getId());
        System.out.println("Descrição: " + produto.getDescricao());

    }

    private void abrirTelaSecundaria() {
        Intent intent = new Intent(this, SecundariaActivity.class);
        startActivity(intent);
        finish();
    }

    private void executarTarefaListarProdutos() {
        pararTarefaListarItens();
        if(tarefaListarProdutos == null || !tarefaListarProdutos.estaRodando()) {
            tarefaListarProdutos = new ExecutarTarefaRest(this, false , 0 , progressBar,
                    new TarefaProdutosListarProdutos(this));
            tarefaListarProdutos.execute();
            executarTarefaListarItens();
        }
    }

    private void executarTarefaListarItens() {
        if(tarefaListarItens == null || !tarefaListarItens.estaRodando()) {
            tarefaListarItens = new ExecutarTarefaRest(this, true, 5000, progressBar,
                    new TarefaItensListarItens(this));
            tarefaListarItens.execute();
        }
    }

    private void pararTarefaListarItens() {
        if(tarefaListarItens != null) {
            tarefaListarItens.parar();
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void pararServicos() {
        if(tarefaListarProdutos!= null) {
            tarefaListarProdutos.parar();
        }
        if(tarefaListarItens != null) {
            tarefaListarItens.parar();
        }
    }

    @Override
    public void onBackPressed() {
        pararServicos();
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        pararServicos();
        super.onStop();
    }

    @Override
    public void finish() {
        pararServicos();
        super.finish();
    }
}
