package br.com.softomic.testerest.app.services.produtos;

import java.util.List;

import br.com.softomic.testerest.app.models.Produto;
import retrofit2.Call;
import retrofit2.http.POST;

public interface ProdutosListarService {
    @POST("produtos/listar")
    Call<Produto> listar();
}
