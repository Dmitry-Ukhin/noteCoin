package com.noteCoin.models;


import javax.persistence.*;

@Entity
@Table(name="KeyWord")
public class KeyWord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "key_word")
    private String key;

    @Column(name = "word")
    private String word;

    public Long getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getWord() {
        return word;
    }
}
