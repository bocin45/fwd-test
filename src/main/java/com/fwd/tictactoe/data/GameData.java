package com.fwd.tictactoe.data;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;
import java.util.UUID;


@Entity
@Data
@SuperBuilder
@NoArgsConstructor
public class GameData {

    @Id
    private UUID id;
    @Column
    private int size;
    @Column
    private String[][] board;
    @Column
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Integer> availableSlot;


}
