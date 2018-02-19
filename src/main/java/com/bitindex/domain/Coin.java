package com.bitindex.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "coins")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Coin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    private String name;
    @Column(name = "name_api")
    private String nameApi;
    private String title;
    @Column(length = 10000)
    private String description;
    private int rank;
    private int active;
    @Lob
    private byte[] pic;



}
