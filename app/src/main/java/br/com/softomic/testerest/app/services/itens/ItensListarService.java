package br.com.softomic.testerest.app.services.itens;

import br.com.softomic.testerest.app.models.Item;
import retrofit2.Call;
import retrofit2.http.POST;

public interface ItensListarService {
    @POST("itens/listar")
    Call<Item> listar();
}
