package company.solo.gametogether.entity;

import company.solo.gametogether.common.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Friend extends BaseTimeEntity {

    //자기 자신
    @Id @GeneratedValue
    private Long id;



}
