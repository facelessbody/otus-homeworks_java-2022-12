package ru.otus.crm.model;


import java.util.List;
import java.util.Optional;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "client")
public class Client implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(mappedBy = "client",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private Address address;

    @OneToMany(mappedBy = "client",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Phone> phones;

    private Client(Long id, String name, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;
        if (address != null) {
            this.address = address;
            this.address.setClient(this);
        }
        if (phones != null && !phones.isEmpty()) {
            this.phones = phones;
            this.phones.forEach(phone -> phone.setClient(this));
        }
    }

    @Override
    public Client clone() {
        var copiedAddress = Optional.ofNullable(this.address)
                .map(Address::clone)
                .orElse(null);
        var copiedPhones = Optional.ofNullable(this.phones)
                .map(phones -> phones.stream()
                        .map(Phone::clone)
                        .toList())
                .orElse(null);
        return new Client(this.id, this.name, copiedAddress, copiedPhones);
    }
}
