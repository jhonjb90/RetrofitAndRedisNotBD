package pe.dhexsoft.retrofiRediss.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pe.dhexsoft.retrofiRediss.aggregates.constants.Constans;
import pe.dhexsoft.retrofiRediss.aggregates.response.ResponseSunat;
import pe.dhexsoft.retrofiRediss.redis.RedisService;
import pe.dhexsoft.retrofiRediss.retrofit.ClientSunatService;
import pe.dhexsoft.retrofiRediss.retrofit.impl.ClientSunatServiceImpl;
import pe.dhexsoft.retrofiRediss.service.InfoSunatService;
import pe.dhexsoft.retrofiRediss.util.Util;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor  // estamos haciendo la inyeccion de contrstuctor
public class InfoSunatServiceImpl implements InfoSunatService {

    private final RedisService redisService;

    //Definicion una Instancia de retrofit que se pueda usar
    ClientSunatService clientSunatService = ClientSunatServiceImpl
            .getRetrofitSunat()
            .create(ClientSunatService.class);

    //inyeccion de properties
    @Value("${token.api}")
    private String token;

    @Override
    public ResponseSunat getInfoSunat(String ruc) throws IOException {
        ResponseSunat responseSunat = new ResponseSunat();
        //Logica de mi diagrama
        //Recupero la informacion de Redis
        String sunatRedisInfo = redisService.getDataFromRedis(
                Constans.REDIS_KEY_API_SUNAT+ruc);
        //validando que exista info o no
        if(Objects.nonNull(sunatRedisInfo)){
            //si existe info en redis
            responseSunat = Util.convertirDesdeString(sunatRedisInfo, ResponseSunat.class);
        } else{
            //no exixte info en redis, iremos al cliente sunat
            //ejectuar clienteSunat Retrofit
            Response<ResponseSunat> executeSunat = preparacionClienteSunat(ruc).execute(); //va devolver  call y esperamos un response para correguir ese error ejecutamos un execute()
            //Validar que responde el api
            if(executeSunat.isSuccessful() && Objects.nonNull(executeSunat.body())) {
                //Recupero el body (solo el cuerpo porque alli tengo la informacion que requiero)
                responseSunat = executeSunat.body();
                //creo mi string para guardar en redis
                String dataParaRedis = Util.convertirAString(responseSunat);
                redisService.saveInRedis(Constans.REDIS_KEY_API_SUNAT + ruc,
                        dataParaRedis, Constans.REDIS_TTL);
            }
        }
        return responseSunat;
    }

    private Call<ResponseSunat> preparacionClienteSunat(String ruc){
        return clientSunatService.getInfoSunat(Constans.BEARER+token, ruc);
    }
}
