package br.com.softomic.testerest.lib;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Timeout;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RestConnection {
    private static boolean EXIBE_LOG_HTTP;
    private static int TIMEOUT;
    public  static int RECONEXAO;
    private static String PROTOCOLO;
    private static String IP;
    private static String PORTA;
    private static String CONTEXTO;
    public  static Class LOGIN_CLASS;

    private final Retrofit retrofit;

    public RestConnection() {

        //Interceptador HTTP para fazer log das requisições
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.retryOnConnectionFailure(false);
        client.connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS);//tempo para nova conexao
        client.readTimeout(TIMEOUT, TimeUnit.MILLISECONDS);//tempo de resposta apos a conexao

        if(EXIBE_LOG_HTTP) {
            client.addInterceptor(interceptor);
        }

        retrofit = new Retrofit.Builder()
                .baseUrl(PROTOCOLO + "://" + IP + ":" + PORTA + "/" + CONTEXTO + "/")
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client.build())
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public static void configurar(boolean exibeLogHttp, int timeout, int reconexao, String protocolo, String ip, String porta, String contexto, Class loginClass) {
        EXIBE_LOG_HTTP = exibeLogHttp;
        TIMEOUT = timeout;
        RECONEXAO = reconexao;
        PROTOCOLO = protocolo;
        IP = ip;
        PORTA = porta;
        CONTEXTO = contexto;
        LOGIN_CLASS = loginClass;
    }

}
