package com.fwd.tictactoe.repository;

import com.fwd.tictactoe.data.GameData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GameDataRepository extends JpaRepository<GameData, UUID> {
}
