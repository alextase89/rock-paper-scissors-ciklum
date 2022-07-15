package com.ciklum.rockpaperscissor.api;

import java.util.List;

public record GameResponseDTO(String id, List<PlayRoundDTO> rounds) { }
