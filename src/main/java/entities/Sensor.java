package entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor @AllArgsConstructor @Data
public class Sensor {
    private String fechaGeneracion;
    private Integer idDispositivo;
    private Double temperatura;
    private Double humedad;
}