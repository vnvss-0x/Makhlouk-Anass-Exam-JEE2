package com.makhlouk.anass.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("PROFESSIONNEL")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CreditProfessionnel extends Credit {

    private String motif;

    private String raisonSociale;
}
