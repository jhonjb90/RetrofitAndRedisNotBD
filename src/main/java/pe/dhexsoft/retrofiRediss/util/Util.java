package pe.dhexsoft.retrofiRediss.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Util {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper(); //el OBJECT MAPPER se utiliza para convertir objetos jaca a represetnaciones json y viciversa

    //aqui con ewl object mapper vamos a convertir a string
    public static <T> String convertirAString(T object)
        throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(object); //
    }

    //aqui vamos convertir de un string a un objeto
    public static <T> T convertirDesdeString(String datoDeRedis, Class<T> tipoClase)
        throws JsonProcessingException{
        return OBJECT_MAPPER.readValue(datoDeRedis, tipoClase);
    }
}
