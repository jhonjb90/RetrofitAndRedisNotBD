package pe.dhexsoft.retrofiRediss.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.dhexsoft.retrofiRediss.aggregates.response.ResponseSunat;
import pe.dhexsoft.retrofiRediss.service.InfoSunatService;

import java.io.IOException;

@RestController
@RequestMapping("/v1/retrofit/")
@RequiredArgsConstructor
public class InfoSunatController {

    private final InfoSunatService infoSunatService;

    @GetMapping("/infoSunat")
    public ResponseEntity<ResponseSunat> getIfnroSunat(@RequestParam("ruc") String ruc) throws IOException {
        return  new ResponseEntity<>(infoSunatService.getInfoSunat(ruc), HttpStatus.OK);
    }
}
