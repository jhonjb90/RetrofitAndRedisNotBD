package pe.dhexsoft.retrofiRediss.retrofit.impl;

import pe.dhexsoft.retrofiRediss.aggregates.constants.Constans;
import pe.dhexsoft.retrofiRediss.aggregates.response.ResponseSunat;
import pe.dhexsoft.retrofiRediss.retrofit.ClientSunatService;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientSunatServiceImpl {

    private static Retrofit retrofit = null;

    public static Retrofit getRetrofitSunat(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constans.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
