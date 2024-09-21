package com.example.zgzstreetmanager.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "STREET")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Street {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private double latitude;
    private double longitude;

    @Enumerated(EnumType.STRING)
    private DistrictEnum district;
}
