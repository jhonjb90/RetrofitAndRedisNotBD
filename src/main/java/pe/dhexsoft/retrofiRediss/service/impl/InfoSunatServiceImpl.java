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
    public ResponseSunat getInfoSunat(String ruc) throws JsonProcessingException {
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
        }
        return null;
    }
}
