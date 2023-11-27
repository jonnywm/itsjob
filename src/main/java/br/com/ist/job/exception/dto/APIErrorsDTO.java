package br.com.ist.job.exception.dto;

import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 *
 * @author jonny
 */
@Data
@Builder
@AllArgsConstructor
public class APIErrorsDTO {

    private List<String> errors;
    
    public static APIErrorsDTO build(String error){
        return APIErrorsDTO.builder()
                .errors(Arrays.asList(error))
                .build();
    }
    
    public static APIErrorsDTO build(List<String> errors){
        return APIErrorsDTO.builder()
                .errors(errors)
                .build();
    }
}
