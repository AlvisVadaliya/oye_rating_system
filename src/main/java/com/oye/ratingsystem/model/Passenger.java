package com.oye.ratingsystem.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Data
@Entity
public class Passenger {
    @Id
    Integer id;

    @OneToOne
    Person person;

    @OneToMany(mappedBy = "passenger")
    List<Ride> rides;

    Float avgRating;

    Integer ratingCount;
}
