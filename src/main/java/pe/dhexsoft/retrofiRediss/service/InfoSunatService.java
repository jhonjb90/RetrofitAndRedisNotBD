package pe.dhexsoft.retrofiRediss.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import pe.dhexsoft.retrofiRediss.aggregates.response.ResponseSunat;

public interface InfoSunatService {

    ResponseSunat getInfoSunat(String ruc) throws JsonProcessingException;
}
