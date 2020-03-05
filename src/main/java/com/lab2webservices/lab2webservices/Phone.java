package com.lab2webservices.lab2webservices;

import lombok.Data;
import lombok.NoArgsConstructor;
//import org.springframework.hateoas.RepresentationModel;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
public class Phone {

    @Id @GeneratedValue private  Long id;
    private String phoneName;
    private int brandId;

    public Phone(int brandId, String phoneName){
        this.phoneName = phoneName;
        this.brandId = brandId;
    }

    public String getPhoneName() {
        return phoneName;
    }

    public Long getId() {
        return id;
    }
}
