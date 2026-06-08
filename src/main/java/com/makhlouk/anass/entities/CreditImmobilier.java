package com.makhlouk.anass.entities;

import com.makhlouk.anass.enums.TypeBien;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("IMMOBILIER")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CreditImmobilier extends Credit {

    @Enumerated(EnumType.STRING)
    private TypeBien typeBien;
}
