package com.miraclemorning.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@Entity
public class Invitation {
    @Id @GeneratedValue
    @Column(name = "invitation_id")
    private Long id;

    @Column(name = "invitor_id")
    private Long invitorId;

    @Column(name = "team_id")
    private Long teamId;

    @Column(name = "invitee_email")
    private String email;

    @Convert(converter = BooleanToYNConverter.class)
    private boolean used;
}


@Converter
class BooleanToYNConverter implements AttributeConverter<Boolean, String> {

    public String convertToDatabaseColumn(Boolean attribute) {
        return (attribute != null && attribute) ? "Y" : "N";
    }

    public Boolean convertToEntityAttribute(String s) {
        return "Y".equals(s);
    }
}